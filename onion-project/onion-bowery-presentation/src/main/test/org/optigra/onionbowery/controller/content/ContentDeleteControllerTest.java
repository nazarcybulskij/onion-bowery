package org.optigra.onionbowery.controller.content;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.optigra.onionbowery.facade.content.ContentFacade;
import org.optigra.onionbowery.resource.MessageResource;
import org.optigra.onionbowery.resource.MessageType;
import org.optigra.onionbowery.servlet.request.RequestWrapper;
import org.optigra.onionbowery.servlet.response.ResponseWrapper;

@RunWith(MockitoJUnitRunner.class)
public class ContentDeleteControllerTest {
    
    protected static final String CONTENT_PATH = "contentPath";
    
    @Captor
    private ArgumentCaptor<String> stringCaptor;
    
    @Captor
    private ArgumentCaptor<MessageResource> messageCaptor;
    
    @Mock
    private ContentFacade contentFacade;
    
    @Mock
    private RequestWrapper request;

    @Mock
    private ResponseWrapper response;

    @InjectMocks
    private ContentDeleteController unit = new ContentDeleteController();
    
    @Test
    public void testHandle() throws Exception {
        // Given
        String contentPath = "/content/path";
        MessageResource messageResource = new MessageResource(MessageType.INFO, "Message deleted successfuly");
        
        // When
        when(request.getParameter(CONTENT_PATH)).thenReturn(contentPath);
        unit.handle(request, response);

        // Then
        verify(contentFacade).deleteContent(stringCaptor.capture());
        verify(response).setResponseObject(messageCaptor.capture());
        assertEquals(contentPath, stringCaptor.getValue());
        assertEquals(messageResource, messageCaptor.getValue());
    }
}
