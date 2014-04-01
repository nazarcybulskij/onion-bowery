package org.onion.bowery.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.onion.bowery.dao.jcr.JcrSessionFactory;
import org.onion.bowery.model.Content;

@RunWith(MockitoJUnitRunner.class)
public class JcrRepositoryImplTest {

    @Mock
    private Session session;
    
    @Mock
    private JcrSessionFactory sessionFactory;
    
    @Mock
    private Node node;
    
    @Mock
    private Property property;
    
    @Mock
    private Binary binary;

    @InjectMocks
    private JcrRepositoryImpl unit = new JcrRepositoryImpl();
    
    @Test
    public void testGetContentByUuid() throws Exception {
        // Given
        String contentId = "contentId";
        InputStream expectedStream = new ByteArrayInputStream("somstring".getBytes("UTF-8"));

        // When
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.getNodeByIdentifier(anyString())).thenReturn(node);
        when(node.getProperty(anyString())).thenReturn(property);
        when(property.getBinary()).thenReturn(binary);
        when(binary.getStream()).thenReturn(expectedStream);

        InputStream actualStream = unit.getContentByUuid(contentId);

        // Then
        verify(sessionFactory).getCurrentSession();
        verify(session).getNodeByIdentifier(contentId);
        verify(property).getBinary();
        verify(binary).getStream();
        
        assertEquals(expectedStream, actualStream);
    }

    @Test
    public void testGetContentByPath() throws Exception {
        // Given
        String contentId = "contentId";
        InputStream expectedStream = new ByteArrayInputStream("somstring".getBytes("UTF-8"));
        
        // When
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.getNode(anyString())).thenReturn(node);
        when(node.getProperty(anyString())).thenReturn(property);
        when(property.getBinary()).thenReturn(binary);
        when(binary.getStream()).thenReturn(expectedStream);
        
        InputStream actualStream = unit.getContentByPath(contentId);
        
        // Then
        verify(sessionFactory).getCurrentSession();
        verify(session).getNode(contentId);
        verify(property).getBinary();
        verify(binary).getStream();
        
        assertEquals(expectedStream, actualStream);
    }
    
    @Test
    public void testStoreContent() throws Exception {
        // Given
        InputStream stream = new ByteArrayInputStream("somstring".getBytes("UTF-8"));
        String path = "/path/to/me";
        String fileName = "filename";
        
        Content content = new Content();
        content.setFileName(fileName);
        content.setStream(stream);
        content.setPath(path);
        
        // When
        String expectedContentId = "contentId";

        when(session.getRootNode()).thenReturn(node);
        
        when(node.hasNode("path")).thenReturn(true);
        when(node.getNode("path")).thenReturn(node);

        when(node.hasNode("to")).thenReturn(true);
        when(node.getNode("to")).thenReturn(node);
        
        when(node.hasNode("me")).thenReturn(true);
        when(node.getNode("me")).thenReturn(node);
        
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(node.addNode(anyString())).thenReturn(node);
        when(node.getIdentifier()).thenReturn(expectedContentId);
        
        String actualContentId = unit.storeContent(content);

        // Then
        verify(sessionFactory).getCurrentSession();
        verify(session).getRootNode();
        verify(node).getIdentifier();
        verify(session).save();
        
        assertEquals(expectedContentId, actualContentId);
    }

    @Test
    public void testStoreContentWithDifferentPath() throws Exception {
        // Given
        InputStream stream = new ByteArrayInputStream("somstring".getBytes("UTF-8"));
        String path = "/path/to/me";
        String fileName = "filename";
        
        Content content = new Content();
        content.setFileName(fileName);
        content.setStream(stream);
        content.setPath(path);
        
        // When
        String expectedContentId = "contentId";
        
        when(session.getRootNode()).thenReturn(node);
        
        when(node.hasNode("path")).thenReturn(true);
        when(node.getNode("path")).thenReturn(node);
        
        when(node.hasNode("to")).thenReturn(false);
        when(node.addNode("to")).thenReturn(node);
        
        when(node.hasNode("me")).thenReturn(false);
        when(node.addNode("me")).thenReturn(node);
        
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(node.addNode(anyString())).thenReturn(node);
        when(node.getIdentifier()).thenReturn(expectedContentId);
        
        String actualContentId = unit.storeContent(content);
        
        // Then
        verify(sessionFactory).getCurrentSession();
        verify(session).getRootNode();
        verify(node).getIdentifier();
        verify(session).save();
        
        assertEquals(expectedContentId, actualContentId);
    }

    @Test
    public void testStoreContentWithNotValidFilename() throws Exception {
        // Given
        InputStream stream = new ByteArrayInputStream("somstring".getBytes("UTF-8"));
        String path = "/path/to/me";
        String fileName = "filename 2013 - 01.jpg";
        
        Content content = new Content();
        content.setFileName(fileName);
        content.setStream(stream);
        content.setPath(path);
        
        // When
        String expectedContentId = "contentId";
        
        when(session.getRootNode()).thenReturn(node);
        
        when(node.hasNode("path")).thenReturn(true);
        when(node.getNode("path")).thenReturn(node);
        
        when(node.hasNode("to")).thenReturn(false);
        when(node.addNode("to")).thenReturn(node);
        
        when(node.hasNode("me")).thenReturn(false);
        when(node.addNode("me")).thenReturn(node);
        
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(node.addNode(anyString())).thenReturn(node);
        when(node.getIdentifier()).thenReturn(expectedContentId);
        
        String actualContentId = unit.storeContent(content);
        
        // Then
        verify(sessionFactory).getCurrentSession();
        verify(session).getRootNode();
        verify(node).getIdentifier();
        verify(session).save();
        
        assertEquals(expectedContentId, actualContentId);
    }
}
