package org.optigra.onionbowery.dao;

import java.io.IOException;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.jcr.Workspace;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.VersionException;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.value.BinaryImpl;
import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;
import org.optigra.onionbowery.common.utils.jcr.JcrUtils;
import org.optigra.onionbowery.dao.jcr.JcrSessionFactory;
import org.optigra.onionbowery.dao.mapper.ContentMapper;
import org.optigra.onionbowery.model.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultContentRepository implements ContentRepository {
    private static final Logger logger = LoggerFactory.getLogger(DefaultContentRepository.class);

    private static final String FILE = "file";
    
    private JcrSessionFactory sessionFactory;
    
    private ContentMapper<Content> contentMapper;
    
    @Override
    public Content storeContent(final Content content) throws ContentException {
        Session session = null;
        Content result = null;
        
        try {
            session = sessionFactory.getCurrentSession();
            Workspace workspace = session.getWorkspace();
            
            Node root = getLastNode(session.getRootNode(), content.getPath());
            
            String validFileName = JcrUtils.getValidFilename(content.getFileName());
            
            Node node = null;
            
            if(root.hasNode(validFileName)) {
                node = root.getNode(validFileName);
                workspace.getVersionManager().checkout(node.getPath());
            } else {
                node = root.addNode(validFileName, NodeType.NT_UNSTRUCTURED);
                node.addMixin(JcrConstants.MIX_VERSIONABLE);
            }
            
            setProperties(content, node);
            String contentId = node.getIdentifier();
            
            result = contentMapper.map(node);
            
            logger.info(String.format("Content stored, id: %s", contentId));
            
            session.save();
            workspace.getVersionManager().checkin(node.getPath());
        } catch (Throwable e) {
            throw new ContentException("Errors during storing content");
        }
        
        return result;
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
    private Node getLastNode(final Node node, final String path) throws RepositoryException {
        
        if (path.isEmpty()) {
            return node;
        }
        
        String currentPath = JcrUtils.getFirstPathItem(path);
        Node nextNode = null;
        
        if(node.hasNode(currentPath)) {
            nextNode = node.getNode(currentPath);
        } else {
            nextNode = node.addNode(currentPath, NodeType.NT_UNSTRUCTURED);
            nextNode.addMixin(JcrConstants.MIX_VERSIONABLE);
        }
        
        return getLastNode(nextNode, JcrUtils.reducePath(path));
    }

    private void setProperties(final Content content, final Node node) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException, IOException {
        Binary binary = new BinaryImpl(content.getInputStream());

        node.setProperty(FILE, binary);
        
        for (String key : content.getProperties().keySet()) {
            node.setProperty(key, content.getProperties().get(key));
        }
    }

    @Override
    public Content getContentByPath(final String path) throws ContentNotFoundException {
        Session session = null;
        Content nodeContent = null;
        
        try {
            session = sessionFactory.getCurrentSession();
            Node node = session.getNode(path);
            nodeContent = contentMapper.map(node);
            
/*            VersionHistory history = session.getWorkspace().getVersionManager().getVersionHistory(path);
            for (VersionIterator it = history.getAllVersions(); it.hasNext();) {
                Version version = (Version) it.next();
                logger.info("Version: " + version.getName());
            }*/
            
        } catch (Throwable e) {
            throw new ContentNotFoundException("Content not found");
        }
        
        return nodeContent;
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
