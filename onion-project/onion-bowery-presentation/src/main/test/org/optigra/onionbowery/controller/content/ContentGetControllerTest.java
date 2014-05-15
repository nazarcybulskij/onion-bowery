package org.optigra.onionbowery.controller.content;

import static org.junit.Assert.assertEquals;
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
import org.optigra.onionbowery.facade.content.ContentFacade;
import org.optigra.onionbowery.resource.ContentResource;
import org.optigra.onionbowery.servlet.ByteArrayServletOutputStream;
import org.optigra.onionbowery.servlet.request.RequestWrapper;
import org.optigra.onionbowery.servlet.response.ResponseWrapper;

@RunWith(MockitoJUnitRunner.class)
public class ContentGetControllerTest {

    protected static final String CONTENT_VERSION = "contentVersion";
    protected static final String CONTENT_PATH = "contentPath";
    protected static final String REQUEST_TYPE = "requestType";
    protected static final String CONTENT_REQUEST_TYPE = "content";
    
    @Captor
    private ArgumentCaptor<ContentResource> contentCaptor;
    
    @Mock
    private ContentFacade contentFacade;
    
    @Mock
    private RequestWrapper request;

    @Mock
    private ResponseWrapper response;

    @InjectMocks
    private ContentGetController unit = new ContentGetController();
    
    @Test
    public void testHandleWithWOutputStream() throws Exception {
        // Given
        String contentPath = "/users/user1/content";
        double version = 0.0;
        String contentId = "contentId";
        String fileName = "filename";
        String expectedContent = "content";
        InputStream inputStream = new ByteArrayInputStream(expectedContent.getBytes());
        
        
        ByteArrayServletOutputStream outputStream = new ByteArrayServletOutputStream(expectedContent.getBytes().length);
        
        ContentResource contentResourse = new ContentResource();
        contentResourse.setContentId(contentId);
        contentResourse.setFileName(fileName);
        contentResourse.setInputStream(inputStream);
        contentResourse.setPath(contentPath + fileName);
        
        // When
        when(request.getParameter(CONTENT_PATH)).thenReturn(contentPath);
        when(request.getParameter(REQUEST_TYPE, CONTENT_REQUEST_TYPE)).thenReturn(CONTENT_REQUEST_TYPE);
        when(request.getParameter(CONTENT_VERSION, 0.0)).thenReturn(version);
        when(contentFacade.getContentByPath(anyString(), anyDouble())).thenReturn(contentResourse);
        when(response.getOutputStream()).thenReturn(outputStream);
        
        unit.handle(request, response);

        String actualContent = new String(outputStream.getBytes());
        
        // Then
        verify(response).setResponseObject(contentResourse);
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void testHandleWithoutOutputStream() throws Exception {
        // Given
        String contentPath = "/users/user1/content";
        double version = 0.0;
        String contentId = "contentId";
        String fileName = "filename";
        String expectedContent = "content";
        InputStream inputStream = new ByteArrayInputStream(expectedContent.getBytes());
        String jsonContentType = "json";
        ContentResource contentResourse = new ContentResource();
        contentResourse.setContentId(contentId);
        contentResourse.setFileName(fileName);
        contentResourse.setInputStream(inputStream);
        contentResourse.setPath(contentPath + fileName);
        
        // When
        when(request.getParameter(CONTENT_PATH)).thenReturn(contentPath);
        when(request.getParameter(REQUEST_TYPE, CONTENT_REQUEST_TYPE)).thenReturn(jsonContentType);
        when(request.getParameter(CONTENT_VERSION, 0.0)).thenReturn(version);
        when(contentFacade.getContentByPath(anyString(), anyDouble())).thenReturn(contentResourse);
        
        unit.handle(request, response);
        
        // Then
        verify(response).setResponseObject(contentCaptor.capture());
        assertEquals(contentResourse, contentCaptor.getValue());
    }
    
}
