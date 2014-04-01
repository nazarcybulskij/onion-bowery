package org.onion.bowery.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import org.apache.commons.io.FilenameUtils;
import org.apache.jackrabbit.value.BinaryImpl;
import org.apache.log4j.Logger;
import org.onion.bowery.dao.jcr.JcrSessionFactory;
import org.onion.bowery.model.Content;
import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;

public class JcrRepositoryImpl implements ContentRepository {
    private static Logger logger = Logger.getLogger(JcrRepositoryImpl.class); 

    private static final String DOT = ".";
    private static final String EMPTY_STRING = "";
    private static final String SPECIAL_CHARACTERS_PATTERN = "[^a-zA-Z0-9]";
    private static final String SLASH = "/";
	private static final String FILE = "file";
	private static final String DATE = "date";
	
	private JcrSessionFactory sessionFactory;
	
	@Override
	public String storeContent(final Content content) throws ContentException {
		Session session = null;
		String contentId = null;
		try {
			session = sessionFactory.getCurrentSession();
			
			Node root = getNode(session.getRootNode(), content.getPath());
			
			String formattedFileName = getValidFilename(content.getFileName());
			
			Node node = null;
			
			if(root.hasNode(formattedFileName)) {
			    node = root.getNode(formattedFileName);
			} else {
			    node = root.addNode(formattedFileName);
			}
			
			setNodeInformation(content, node);
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

    private String getValidFilename(final String filename) {
        
        String extension = FilenameUtils.getExtension(filename);
        String clearName = filename.replace(DOT + extension, EMPTY_STRING);
        String formattedFileName = clearName.replaceAll(SPECIAL_CHARACTERS_PATTERN, EMPTY_STRING).toLowerCase().trim() + "_" + Calendar.getInstance().getTimeInMillis();
        
        if(!extension.isEmpty()) {
            formattedFileName += DOT + extension;
        }
        
        return formattedFileName;
    }

    private Node getNode(final Node node, final String path) throws RepositoryException {
        
        if (path.isEmpty()) {
            return node;
        }
        
        String currentPath = getFirstPathItem(path);
        Node nextNode = null;
        
        if(node.hasNode(currentPath)) {
            nextNode = node.getNode(currentPath);
        } else {
            nextNode = node.addNode(currentPath);
        }
        
        return getNode(nextNode, reducePath(path));
    }

    private String reducePath(final String path) {
        
        String[] paths = path.replaceFirst("^/", EMPTY_STRING).split(SLASH);
        
        Queue<String> queue = new LinkedList<>(Arrays.asList(paths));
        queue.poll();
        
        StringBuilder sb = new StringBuilder();
        
        for (String string : queue) {
            sb.append(SLASH);
            sb.append(string);
        }
        
        return sb.toString();
    }

    private String getFirstPathItem(final String path) {
        
        String[] paths = path.replaceFirst("^/", EMPTY_STRING).split(SLASH, -1);
        
        return paths[0];
    }

    private void setNodeInformation(final Content content, final Node node) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException, IOException {
		Binary binary = new BinaryImpl(content.getStream());

		node.setProperty(FILE, binary);
		node.setProperty(DATE, Calendar.getInstance().getTimeInMillis());
	}


	@Override
	public InputStream getContentByUuid(final String id) throws ContentNotFoundException {
		Session session = null;
		InputStream stream = null;
		
		try {
			session = sessionFactory.getCurrentSession();
			Node node = session.getNodeByIdentifier(id);
			Property prop = node.getProperty(FILE);
			stream = prop.getBinary().getStream();
			
		} catch (Throwable e) {
			throw new ContentNotFoundException();
		}
		
        return stream;
	}


    @Override
    public InputStream getContentByPath(final String path) throws ContentNotFoundException {
        Session session = null;
        InputStream stream = null;
        
        try {
            session = sessionFactory.getCurrentSession();
            Node node = session.getNode(path);
            Property prop = node.getProperty(FILE);
            stream = prop.getBinary().getStream();
            
        } catch (Throwable e) {
            throw new ContentNotFoundException();
        }
        
        return stream;
    }

    public void setSessionFactory(final JcrSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
}
