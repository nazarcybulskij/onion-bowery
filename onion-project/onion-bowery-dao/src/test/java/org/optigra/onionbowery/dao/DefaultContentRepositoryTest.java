package org.optigra.onionbowery.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.optigra.onionbowery.dao.jcr.JcrSessionFactory;
import org.optigra.onionbowery.dao.mapper.ContentMapper;
import org.optigra.onionbowery.model.Content;
import org.optigra.onionbowery.model.NodeContent;

@RunWith(MockitoJUnitRunner.class)
public class DefaultContentRepositoryTest {

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
    
    @Mock
    private ContentMapper<NodeContent> contentMapper;

    @InjectMocks
    private DefaultContentRepository unit = new DefaultContentRepository();

    @Test
    public void testGetContentByPath() throws Exception {
        // Given
        String contentId = "contentId";
        String name = "name";
        InputStream stream = new ByteArrayInputStream("somstring".getBytes("UTF-8"));
        NodeContent expectedNodeContent = new NodeContent();
        expectedNodeContent.setInputStream(stream);
        expectedNodeContent.setName(name);
        
        
        // When
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.getNode(anyString())).thenReturn(node);
        when(contentMapper.map(any(Node.class))).thenReturn(expectedNodeContent);
        
        NodeContent actualNodeContent = unit.getContentByPath(contentId);
        
        // Then
        verify(sessionFactory).getCurrentSession();
        verify(session).getNode(contentId);
        verify(contentMapper).map(node);
        
        assertEquals(expectedNodeContent, actualNodeContent);
    }
    
    @Test
    public void testStoreContent() throws Exception {
        // Given
        InputStream stream = new ByteArrayInputStream("somstring".getBytes("UTF-8"));
        String path = "/path/to/me";
        String fileName = "filename";
        
        Map<String, String> attributes = new HashMap<String, String>();
        Content content = new Content();
        content.setFileName(fileName);
        content.setStream(stream);
        content.setPath(path);
        content.setAttributes(attributes);
        
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
        when(node.addNode(anyString(), anyString())).thenReturn(node);
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
        
        Map<String, String> attributes = new HashMap<>();
        Content content = new Content();
        content.setFileName(fileName);
        content.setStream(stream);
        content.setPath(path);
        content.setAttributes(attributes);
        
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
        when(node.addNode(anyString(), anyString())).thenReturn(node);
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
        
        Map<String, String> attributes = new HashMap<>();
        Content content = new Content();
        content.setFileName(fileName);
        content.setStream(stream);
        content.setPath(path);
        content.setAttributes(attributes);
        
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
        when(node.addNode(anyString(), anyString())).thenReturn(node);
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
