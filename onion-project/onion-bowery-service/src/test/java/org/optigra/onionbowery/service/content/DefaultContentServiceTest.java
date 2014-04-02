package org.optigra.onionbowery.service.content;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.optigra.onionbowery.dao.ContentRepository;
import org.optigra.onionbowery.model.Content;
import org.optigra.onionbowery.service.content.DefaultContentService;

@RunWith(MockitoJUnitRunner.class)
public class DefaultContentServiceTest {

    @Mock
    private ContentRepository contentRepository;
    
    @InjectMocks
    private DefaultContentService unit = new DefaultContentService();
    
    @Test
    public void testGetContentByUuid() throws Exception {
        // Given
        String id = "uuid";
        InputStream expectedStream = new ByteArrayInputStream("somstring".getBytes("UTF-8"));

        // When
        when(contentRepository.getContentByUuid(anyString())).thenReturn(expectedStream);
        InputStream actualStream = unit.getContentByUuid(id);

        // Then
        verify(contentRepository).getContentByUuid(id);
        assertEquals(expectedStream, actualStream);
    }

    @Test
    public void testGetContentByPath() throws Exception {
        // Given
        String path = "/path/to/file.ext";
        InputStream expectedStream = new ByteArrayInputStream("somstring".getBytes("UTF-8"));
        
        // When
        when(contentRepository.getContentByPath(anyString())).thenReturn(expectedStream);
        InputStream actualStream = unit.getContentByPath(path);
        
        // Then
        verify(contentRepository).getContentByPath(path);
        assertEquals(expectedStream, actualStream);
    }
    
    @Test
    public void testmethodName() throws Exception {
        // Given
        InputStream stream = new ByteArrayInputStream("somstring".getBytes("UTF-8"));
        String path = "/path/to/my/file.ext";
        
        Content content = new Content();
        content.setStream(stream);
        content.setPath(path);
        
        String expectedContentId = "contentId";
        
        // When
        when(contentRepository.storeContent(any(Content.class))).thenReturn(expectedContentId);
        String actualContentId = unit.storeContent(content);

        // Then
        verify(contentRepository).storeContent(content);
        assertEquals(expectedContentId, actualContentId);
    }
}
