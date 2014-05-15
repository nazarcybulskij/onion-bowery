package org.optigra.onionbowery.controller.content;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.optigra.onionbowery.controller.AbstractController;
import org.optigra.onionbowery.exception.NotMultipartFormDataRequestException;
import org.optigra.onionbowery.exception.RequestParameterException;
import org.optigra.onionbowery.facade.content.ContentFacade;
import org.optigra.onionbowery.resource.ContentResource;
import org.optigra.onionbowery.servlet.request.RequestWrapper;
import org.optigra.onionbowery.servlet.response.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @date Mar 31, 2014
 * @author ivanursul
 * 
 */
public class ContentPostController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(ContentPostController.class);
	
    private ContentFacade contentFacade;

    private ServletFileUpload servletFileUpload;

    @Override
    public void handle(final RequestWrapper request, final ResponseWrapper response) {

    	if (!ServletFileUpload.isMultipartContent(request)) {
            throw new NotMultipartFormDataRequestException(NotMultipartFormDataRequestException.DEFAULT_MESSAGE);
        }
        
        try {
        	
	        Map<String, FileItem> requestParams = getParameters(request);
	        
	        Map<String, String> parameters = getContentParameters(requestParams);
	        String path = requestParams.get("path").getString();
	        FileItem fileItem = requestParams.get("file");
	        InputStream inputStream = fileItem.getInputStream();
	        String name = fileItem.getName();
	        String contentType = fileItem.getContentType();
	        
	        ContentResource content = contentFacade.storeContent(inputStream, contentType, name, path, parameters);
	        response.setResponseObject(content);
	        
        } catch(FileUploadException | IOException e) {
        	logger.warn(RequestParameterException.DEFAULT_MESSAGE, e);
        	throw new RequestParameterException(RequestParameterException.DEFAULT_MESSAGE, e);
		}
        
    }

    private Map<String, String> getContentParameters(final Map<String, FileItem> parameters) {
        
        Map<String, String> attributes = new HashMap<>();
        
        for(String name: parameters.keySet()) {
            
            FileItem fileItem = parameters.get(name);
            if(fileItem.isFormField()) {
                attributes.put(name, fileItem.getString());
            }
        }
        
        return attributes;
    }

    private Map<String, FileItem> getParameters(final RequestWrapper request) throws FileUploadException {

        Map<String, FileItem> parameters = new HashMap<>();
        
        List<FileItem> formItems = servletFileUpload.parseRequest(request);
        if (formItems != null && formItems.size() > 0) {
            for (FileItem item : formItems) {
                parameters.put(item.getFieldName(), item);
            }
        }
        
        return parameters;
    }

    public void setContentFacade(final ContentFacade contentFacade) {
        this.contentFacade = contentFacade;
    }

    public void setServletFileUpload(final ServletFileUpload servletFileUpload) {
        this.servletFileUpload = servletFileUpload;
    }

}
