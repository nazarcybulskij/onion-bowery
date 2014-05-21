package org.optigra.onionbowery.dao;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.value.BinaryImpl;
import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.utils.jcr.JcrUtils;
import org.optigra.onionbowery.dao.jcr.JcrSessionFactory;
import org.optigra.onionbowery.dao.mapper.ContentMapper;
import org.optigra.onionbowery.model.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultContentRepository implements ContentRepository {

    private static final Logger logger = LoggerFactory.getLogger(DefaultContentRepository.class);

    private static final String FOLDER = "folder";
    private static final String EMPTY_STRING = "";
    private static final String ROOT = "/";
    
    private static final String JCR_PREFIX = "jcr:";
    private static final String FILE = "file";
    private static final String CONTENT_TYPE = "contentType";

    private JcrSessionFactory sessionFactory;
    
    private ContentMapper<Content> contentMapper;
    
    @Override
    public Content storeContent(final Content content) {
        Session session = null;
        Content result = null;
        try {
            session = sessionFactory.getCurrentSession();
            Node node = getAppropriateToContentNode(session, content);
            
            processNodeProperties(content, node);

            result = contentMapper.map(node);
            
            session.save();
            session.getWorkspace().getVersionManager().checkin(node.getPath());
            logger.info(String.format("Content stored, id: %s", node.getIdentifier()));
        } catch (Throwable e) {
            logger.warn(e.getMessage());
            throw new ContentException("Errors during storing content", e);
        }
        
        return result;
    }

    /**
     * The method is getting your file, according path, and filename.
     * @date Apr 4, 2014
     * @author ivanursul
     * @param session
     * @param content
     * @return
     * @throws Exception
     */
    private Node getAppropriateToContentNode(final Session session, final Content content) throws Exception {
        
        Node lastNode = getLastNodeFromPath(session.getRootNode(), content.getPath());
        String validFileName =  JcrUtils.getValidFilename(content.getFileName());
        
        Node node;
        if(lastNode.hasNode(validFileName)) {
            node = lastNode.getNode(validFileName);
            session.getWorkspace().getVersionManager().checkout(node.getPath());
        } else {
            node = lastNode.addNode(validFileName, NodeType.NT_UNSTRUCTURED);
            node.addMixin(JcrConstants.MIX_VERSIONABLE);
        }
        
        return node;
    }

    /**
     * Method, that is working recursively.It will parse your path ("/users/1/images/nature"),
     * to [users, 1, images, nature] and start from users, and check if users exists, or not exists.
     * if exists - then nextNode will be this node, if not exists, then next node will be created.
     * After that, function will call itself with parameters (nextNode, "/1/images/nature")
     * 
     * @date Apr 2, 2014
     * @author ivanursul
     * @param node the node, where this operations will be executing
     * @param path current path
     * @return The last node.
     * @throws RepositoryException
     */
    private Node getLastNodeFromPath(final Node node, final String path) throws RepositoryException {
        
        if (path.isEmpty()) {
            return node;
        }
        
        String currentPath = JcrUtils.getFirstPathItem(path);
        Node nextNode = null;
        
        if(EMPTY_STRING.equals(currentPath)) {
            nextNode = node;
        } else if(node.hasNode(currentPath)) {
            nextNode = node.getNode(currentPath);
        } else {
            nextNode = node.addNode(currentPath, NodeType.NT_UNSTRUCTURED);
            nextNode.addMixin(JcrConstants.MIX_VERSIONABLE);
            nextNode.setProperty(CONTENT_TYPE, FOLDER);
        }
        
        return getLastNodeFromPath(nextNode, JcrUtils.reducePath(path));
    }

    private void processNodeProperties(final Content content, final Node node) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException, IOException {
        Binary binary = new BinaryImpl(content.getInputStream());

        node.setProperty(FILE, binary);
        node.setProperty(CONTENT_TYPE, content.getContentType());
        
        for (String key : content.getProperties().keySet()) {
            node.setProperty(key, content.getProperties().get(key));
        }
    }

    @Override
    public Content getContentByPath(final String path, final double versionNumber) throws ContentException {
        Session session = null;
        Content nodeContent = null;
        
        try {
            session = sessionFactory.getCurrentSession();
            Node basicNode = session.getNode(path);
            
            VersionHistory history = null;
            
            if(!ROOT.equals(path)) {
            	history = session.getWorkspace().getVersionManager().getVersionHistory(path);
            }
            Deque<String> versions = getVersions(history);
            
            Node node  = getRequiredNode(path, versionNumber, session, versions);
            
            nodeContent = contentMapper.map(node);
            nodeContent.setVersions(versions);
            
            // TODO : repeating it, due to bad names in version nodes.
            // Need to think, how to remove it.
            nodeContent.setPath(path);
            nodeContent.setFileName(basicNode.getName());
        } catch (Throwable e) {
            logger.warn(e.getMessage());
            throw new ContentException("Content not found", e);
        }
        
        return nodeContent;
    }

    private Node getRequiredNode(final String path, final double versionNumber, final Session session, final Deque<String> versions) throws Exception {
        
    	VersionHistory history = null;
    	
    	if(!ROOT.equals(path)){
    		history = session.getWorkspace().getVersionManager().getVersionHistory(path);
    	}
    	
        Node node;
        
        if(versions.size() == 0) {
            node = session.getNode(path);
        } else if(versionNumber == 0.0) {
            node = history.getVersion(versions.getLast()).getFrozenNode();
        } else {
            node = history.getVersion(String.valueOf(versionNumber)).getFrozenNode();
        }
        
        return node;
    }
    
    private Deque<String> getVersions(final VersionHistory history) throws Exception {
        LinkedList<String> versions = new LinkedList<>();
        
        if(history == null) {
        	return versions;
        }
        
        for (VersionIterator it = history.getAllVersions(); it.hasNext();) {
            Version version = (Version) it.next();
            if(!version.getName().contains(JCR_PREFIX)) {
                versions.add(version.getName());
            }
        }
        
        return versions;
    }


    @Override
    public void deleteContent(final String contentPath) throws ContentException {
        Session session = null;
        
        try {
            session = sessionFactory.getCurrentSession();
            Node node = session.getNode(contentPath);
            node.remove();
            
            session.save();
            
        } catch (Throwable e) {
            throw new ContentException();
        }
    }
    
    public void setSessionFactory(final JcrSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setContentMapper(final ContentMapper<Content> contentMapper) {
        this.contentMapper = contentMapper;
    }
}
