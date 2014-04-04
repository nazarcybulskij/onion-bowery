package org.optigra.onionbowery.service.content;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.optigra.onionbowery.dao.ContentRepository;
import org.optigra.onionbowery.model.Content;

@RunWith(MockitoJUnitRunner.class)
public class DefaultContentServiceTest {

    @Captor
    private ArgumentCaptor<String> stringCaptor;
    
    @Mock
    private ContentRepository contentRepository;
    
    @InjectMocks
    private DefaultContentService unit = new DefaultContentService();

    @Test
    public void testGetContentByPath() throws Exception {
        // Given
        String path = "/path/to/file.ext";
        String name = "name";
        double version = 1.1;
        Content expectedContent = new Content();
        expectedContent.setFileName(name);
        
        // When
        when(contentRepository.getContentByPath(anyString(), anyDouble())).thenReturn(expectedContent);
        Content actualContent = unit.getContentByPath(path, version);
        
        // Then
        verify(contentRepository).getContentByPath(path, version);
        assertEquals(expectedContent, actualContent);
    }
    
    @Test
    public void testmethodName() throws Exception {
        // Given
        InputStream stream = new ByteArrayInputStream("somstring".getBytes("UTF-8"));
        String path = "/path/to/my/file.ext";
        
        Content content = new Content();
        content.setInputStream(stream);
        content.setPath(path);
        
        Content expectedContentId = new Content();
        
        // When
        when(contentRepository.storeContent(any(Content.class))).thenReturn(expectedContentId);
        Content actualContent = unit.storeContent(content);

        // Then
        verify(contentRepository).storeContent(content);
        assertEquals(expectedContentId, actualContent);
    }
    
    @Test
    public void testDeleteContent() throws Exception {
        // Given
        String contentPath = "/content/path";

        // When
        unit.deleteContent(contentPath);

        // Then
        verify(contentRepository).deleteContent(stringCaptor.capture());
        assertEquals(contentPath, stringCaptor.getValue());
    }
}
