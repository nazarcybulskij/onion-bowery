package org.optigra.onionbowery.dao;

import java.io.IOException;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.VersionException;

import org.apache.jackrabbit.value.BinaryImpl;
import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;
import org.optigra.onionbowery.common.utils.jcr.JcrUtils;
import org.optigra.onionbowery.dao.jcr.JcrSessionFactory;
import org.optigra.onionbowery.dao.mapper.ContentMapper;
import org.optigra.onionbowery.model.Content;
import org.optigra.onionbowery.model.NodeContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultContentRepository implements ContentRepository {
    private static final Logger logger = LoggerFactory.getLogger(DefaultContentRepository.class);

    private static final String FILE = "file";
    
    private JcrSessionFactory sessionFactory;
    
    private ContentMapper<NodeContent> contentMapper;
    
    @Override
    public String storeContent(final Content content) throws ContentException {
        Session session = null;
        String contentId = null;
        try {
            session = sessionFactory.getCurrentSession();
            
            Node root = getLastNode(session.getRootNode(), content.getPath());
            
            String validFileName = JcrUtils.getValidFilename(content.getFileName());
            
            Node node = null;
            
            if(root.hasNode(validFileName)) {
                node = root.getNode(validFileName);
            } else {
                node = root.addNode(validFileName, NodeType.NT_UNSTRUCTURED);
            }
            
            setProperties(content, node);
            contentId = node.getIdentifier();
            String path = node.getPath();

            content.setContentId(contentId);
            content.setPath(path);
            
            logger.info(String.format("Content stored, id: %s", contentId));
            
            session.save();
        } catch (Throwable e) {
            throw new ContentException();
        }
        
        return contentId;
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
        }
        
        return getLastNode(nextNode, JcrUtils.reducePath(path));
    }

    private void setProperties(final Content content, final Node node) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException, IOException {
        Binary binary = new BinaryImpl(content.getStream());

        node.setProperty(FILE, binary);
        
        for (String key : content.getAttributes().keySet()) {
            node.setProperty(key, content.getAttributes().get(key));
        }
    }

    @Override
    public NodeContent getContentByPath(final String path) throws ContentNotFoundException {
        Session session = null;
        NodeContent nodeContent = null;
        
        try {
            session = sessionFactory.getCurrentSession();
            Node node = session.getNode(path);
            nodeContent = contentMapper.map(node);
            
        } catch (Throwable e) {
            throw new ContentNotFoundException();
        }
        
        return nodeContent;
    }

    public void setSessionFactory(final JcrSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setContentMapper(final ContentMapper<NodeContent> contentMapper) {
        this.contentMapper = contentMapper;
    }
    
}
