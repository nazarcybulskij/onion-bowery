package org.optigra.onionbowery.controller.content;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.onion.bowery.facade.content.ContentFacade;
import org.onion.bowery.resource.ContentResource;
import org.optigra.onionbowery.controller.AbstractController;
import org.optigra.onionbowery.servlet.request.RequestWrapper;
import org.optigra.onionbowery.servlet.response.ResponseWrapper;

/**
 * @date Mar 31, 2014
 * @author ivanursul
 * 
 */
public class ContentPostController extends AbstractController {

    private ContentFacade contentFacade;

    private ServletFileUpload servletFileUpload;

    @Override
    public void handle(final RequestWrapper request, final ResponseWrapper response) throws Exception {

        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request must have multipart data!");
        }
        
        Map<String, FileItem> parameters = getParameters(request);
        
        String path = parameters.get("path").getString();
        FileItem fileItem = parameters.get("file");
        
        ContentResource content = contentFacade.storeContent(fileItem.getInputStream(), fileItem.getName(), path);
        
        response.setResponseObject(content);
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
