package org.optigra.onionbowery.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.Workspace;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;
import javax.jcr.version.VersionManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.optigra.onionbowery.dao.jcr.JcrSessionFactory;
import org.optigra.onionbowery.dao.mapper.ContentMapper;
import org.optigra.onionbowery.model.Content;

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
    private ContentMapper<Content> contentMapper;
    
    @Mock
    private Workspace workspace;

    @Mock
    private VersionManager versionManager;

    @Mock
    private VersionHistory versionHistory;

    @Mock
    private VersionIterator versionIterator;

    @Mock
    private Version vers;

    @InjectMocks
    private DefaultContentRepository unit = new DefaultContentRepository();



    @Before
    public void setup() throws UnsupportedRepositoryOperationException, RepositoryException {
        when(session.getWorkspace()).thenReturn(workspace);
        when(workspace.getVersionManager()).thenReturn(versionManager);
    }
    
    @Test
    public void testGetContentByPath() throws Exception {
        // Given
        String path = "contentId";
        String name = "name";
        double version = 1.1;
        InputStream stream = new ByteArrayInputStream("somstring".getBytes("UTF-8"));
        Content expectedNodeContent = new Content();
        expectedNodeContent.setInputStream(stream);
        expectedNodeContent.setFileName(name);
        
        // When
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.getNode(anyString())).thenReturn(node);
        when(session.getWorkspace()).thenReturn(workspace);
        when(workspace.getVersionManager()).thenReturn(versionManager);
        when(versionManager.getVersionHistory(anyString())).thenReturn(versionHistory);
        when(versionHistory.getAllVersions()).thenReturn(versionIterator);
        when(versionIterator.hasNext()).thenReturn(false);
        when(versionHistory.getVersion(anyString())).thenReturn(vers);
        when(contentMapper.map(any(Node.class))).thenReturn(expectedNodeContent);
        when(vers.getFrozenNode()).thenReturn(node);
        Content actualNodeContent = unit.getContentByPath(path ,version);
        
        // Then
        verify(sessionFactory).getCurrentSession();
        verify(session, times(2)).getNode(path);
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
        content.setInputStream(stream);
        content.setPath(path);
        content.setProperties(attributes);
        
        // When
        String contentId = "cotnentId";
        Content expectedContent = new Content();

        when(session.getRootNode()).thenReturn(node);
        
        when(node.hasNode("path")).thenReturn(true);
        when(node.getNode("path")).thenReturn(node);

        when(node.hasNode("to")).thenReturn(true);
        when(node.getNode("to")).thenReturn(node);
        
        when(node.hasNode("me")).thenReturn(true);
        when(node.getNode("me")).thenReturn(node);
        
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(node.addNode(anyString(), anyString())).thenReturn(node);
        when(node.getIdentifier()).thenReturn(contentId);
        when(contentMapper.map(any(Node.class))).thenReturn(expectedContent);
        
        Content actualContent = unit.storeContent(content);

        // Then
        verify(sessionFactory).getCurrentSession();
        verify(session).getRootNode();
        verify(node).getIdentifier();
        verify(session).save();
        
        assertEquals(expectedContent, actualContent);
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
        content.setInputStream(stream);
        content.setPath(path);
        content.setProperties(attributes);
        
        // When
        String contentId = "contentId";
        Content expectedContent = new Content();
        
        when(session.getRootNode()).thenReturn(node);
        
        when(node.hasNode("path")).thenReturn(true);
        when(node.getNode("path")).thenReturn(node);
        
        when(node.hasNode("to")).thenReturn(false);
        when(node.addNode("to")).thenReturn(node);
        
        when(node.hasNode("me")).thenReturn(false);
        when(node.addNode("me")).thenReturn(node);
        
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(node.addNode(anyString(), anyString())).thenReturn(node);
        when(node.getIdentifier()).thenReturn(contentId);
        when(contentMapper.map(any(Node.class))).thenReturn(expectedContent);
        
        Content actualContent = unit.storeContent(content);
        
        // Then
        verify(sessionFactory).getCurrentSession();
        verify(session).getRootNode();
        verify(node).getIdentifier();
        verify(session).save();
        
        assertEquals(expectedContent, actualContent);
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
        content.setInputStream(stream);
        content.setPath(path);
        content.setProperties(attributes);
        
        // When
        String expectedContentId = "contentId";
        Content expectedContent = new Content();
        
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
        when(contentMapper.map(any(Node.class))).thenReturn(expectedContent);
        
        Content actualContent = unit.storeContent(content);
        
        // Then
        verify(sessionFactory).getCurrentSession();
        verify(session).getRootNode();
        verify(node).getIdentifier();
        verify(session).save();
        
        assertEquals(expectedContent, actualContent);
    }
    
    @Test
    public void testDeleteRepository() throws Exception {
        // Given
        String contentPath = "/content/path";

        // When
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.getNode(anyString())).thenReturn(node);
        
        unit.deleteContent(contentPath);

        // Then
        verify(node).remove();
        verify(session).getNode(contentPath);
    }
}
