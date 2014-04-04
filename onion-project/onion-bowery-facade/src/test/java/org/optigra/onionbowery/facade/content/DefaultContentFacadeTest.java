package org.optigra.onionbowery.facade.content;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.optigra.onionbowery.facade.converter.Converter;
import org.optigra.onionbowery.model.Content;
import org.optigra.onionbowery.resource.ContentResource;
import org.optigra.onionbowery.service.content.ContentService;

@RunWith(MockitoJUnitRunner.class)
public class DefaultContentFacadeTest {

    @Captor
    private ArgumentCaptor<Content> contentCaptor;
    
    @Captor
    private ArgumentCaptor<String> stringCaptor;
    
    @Mock
    private ContentService contentService;
    
    @Mock
    private Converter<Content, ContentResource> contentConverter;
    
    
    @InjectMocks
    private DefaultContentFacade unit = new DefaultContentFacade();
    
    @Test
    public void testGetContentByPath() throws Exception {
        // Given
        String contentPath = "/contentPath";
        String name = "name";
        double version = 1.2;
        
        Content content = new Content();
        content.setFileName(name);
        content.setPath(contentPath);
        
        ContentResource expectedNodeContent = new ContentResource();
        expectedNodeContent.setFileName(name);
        
        // When
        when(contentService.getContentByPath(anyString(), anyDouble())).thenReturn(content);
        when(contentConverter.convert(any(Content.class))).thenReturn(expectedNodeContent);
        
        ContentResource actualNodeContent = unit.getContentByPath(contentPath, version);

        // Then
        verify(contentService).getContentByPath(contentPath, version);
        verify(contentConverter).convert(content);
        assertEquals(expectedNodeContent, actualNodeContent);
    }

    @Test
    public void testStoreContent() throws Exception {
        // Given
        String inputContent = "very big content";
        String fileName = "fileName";
        InputStream stream = new ByteArrayInputStream(inputContent.getBytes());
        String path = "/common/fileName";
        Map<String, String> attributes = Collections.emptyMap();

        ContentResource expectedContentResource = new ContentResource();
        expectedContentResource.setPath(path);
        
        Content content = new Content();
        content.setPath(path);
        content.setInputStream(stream);
        content.setFileName(fileName);
        content.setProperties(attributes);
        
        // When
        when(contentConverter.convert(any(Content.class))).thenReturn(expectedContentResource);
        
        ContentResource actualContentResource = unit.storeContent(stream, fileName, path, attributes);

        // Then
        verify(contentService).storeContent(contentCaptor.capture());
        assertEquals(content, contentCaptor.getValue());
        assertEquals(expectedContentResource, actualContentResource);
    }
    
    @Test
    public void testDeleteContent() throws Exception {
        // Given
        String contentPath = "/content/path";

        // When
        unit.deleteContent(contentPath);

        // Then
        verify(contentService).deleteContent(stringCaptor.capture());
        assertEquals(contentPath, stringCaptor.getValue());
    }
}
