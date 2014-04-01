package org.onion.bowery.facade.content;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
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
import org.onion.bowery.facade.converter.Converter;
import org.onion.bowery.model.Content;
import org.onion.bowery.resource.ContentResource;
import org.onion.bowery.service.content.ContentService;

@RunWith(MockitoJUnitRunner.class)
public class DefaultContentFacadeTest {

    @Captor
    private ArgumentCaptor<Content> contentCaptor;
    
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
        String content = "very large content";
        InputStream expectedStream = new ByteArrayInputStream(content.getBytes());
        
        // When
        when(contentService.getContentByPath(anyString())).thenReturn(expectedStream);
        
        InputStream actualStream = unit.getContentByPath(contentPath);

        // Then
        verify(contentService).getContentByPath(contentPath);
        assertEquals(expectedStream, actualStream);
    }

    @Test
    public void testStoreContent() throws Exception {
        // Given
        String inputContent = "very big content";
        String fileName = "fileName";
        InputStream stream = new ByteArrayInputStream(inputContent.getBytes());
        String path = "/common/fileName";

        ContentResource expectedContentResource = new ContentResource();
        expectedContentResource.setPath(path);
        
        Content content = new Content();
        content.setPath(path);
        content.setStream(stream);
        content.setFileName(fileName);
        
        // When
        when(contentConverter.convert(any(Content.class))).thenReturn(expectedContentResource);
        
        ContentResource actualContentResource = unit.storeContent(stream, fileName, path);

        // Then
        verify(contentService).storeContent(contentCaptor.capture());
        assertEquals(content, contentCaptor.getValue());
        assertEquals(expectedContentResource, actualContentResource);
    }
}
