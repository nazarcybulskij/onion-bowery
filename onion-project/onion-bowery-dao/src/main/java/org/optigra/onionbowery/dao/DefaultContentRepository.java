package org.optigra.onionbowery.dao;

import java.io.IOException;
import java.util.List;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionHistory;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.value.BinaryImpl;
import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.utils.jcr.JcrUtils;
import org.optigra.onionbowery.dao.jcr.JcrHelper;
import org.optigra.onionbowery.dao.jcr.JcrSessionFactory;
import org.optigra.onionbowery.dao.mapper.ContentMapper;
import org.optigra.onionbowery.dao.version.VersionRetriever;
import org.optigra.onionbowery.model.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultContentRepository implements ContentRepository {

    private static final Logger logger = LoggerFactory.getLogger(DefaultContentRepository.class);

    private static final String FOLDER = "folder";
    private static final String EMPTY_STRING = "";
    
    private static final String FILE = "file";
    private static final String CONTENT_TYPE = "contentType";

    private JcrSessionFactory sessionFactory;
    private ContentMapper<Content> contentMapper;
    private VersionRetriever versionRetriever;
    
    @Override
    public Content storeContent(final Content content) {
        Session session = null;
        Content result = null;
        try {
            session = sessionFactory.getCurrentSession();
            Node node = storeNode(session, content);
            
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

    private Node storeNode(final Session session, final Content content) throws Exception {
        
        Node lastNode = getLastNodeFromPath(session.getRootNode(), content.getPath());
        String validFileName =  JcrUtils.getValidFilename(content.getFileName());
        
        Node node;
        if(lastNode.hasNode(validFileName)) { // New Version
            node = lastNode.getNode(validFileName);
            session.getWorkspace().getVersionManager().checkout(node.getPath());
        } else {
            node = lastNode.addNode(validFileName, NodeType.NT_UNSTRUCTURED);
            node.addMixin(JcrConstants.MIX_VERSIONABLE);
        }
        
        return node;
    }

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

            List<Content> versions = versionRetriever.getVersions(session, path);
            
            Node node  = getRequiredNode(path, versionNumber, session, versions);
            
            nodeContent = contentMapper.map(node);
            nodeContent.setVersions(versions);
            nodeContent.setPath(path);
            nodeContent.setFileName(basicNode.getName());
            nodeContent.setVersion(String.valueOf(versionNumber));
            
        } catch (Throwable e) {
            logger.warn(e.getMessage());
            throw new ContentException("Content not found", e);
        }
        
        return nodeContent;
    }

    private Node getRequiredNode(final String path, final double versionNumber, final Session session, final List<Content> versions) throws Exception {
        
    	VersionHistory history = null;
    	
    	if(JcrHelper.isVersionable(path)) {
    		history = session.getWorkspace().getVersionManager().getVersionHistory(path);
    	}
    	
        Node node;
        
        if(versions == null || versions.size() == 0) { // No Versions
            node = session.getNode(path);
        } else if(versionNumber == 0.0) { // Default value
            node = history.getVersion(versions.get(versions.size()-1).getVersion()).getFrozenNode();
        } else {
            node = history.getVersion(String.valueOf(versionNumber)).getFrozenNode();
        }
        
        return node;
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

	public void setVersionRetriever(VersionRetriever versionRetriever) {
		this.versionRetriever = versionRetriever;
	}
    
}
