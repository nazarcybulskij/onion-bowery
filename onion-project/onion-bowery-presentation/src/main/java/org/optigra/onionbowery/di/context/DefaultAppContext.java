package org.optigra.onionbowery.di.context;


import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.optigra.onionbowery.controller.Controller;
import org.optigra.onionbowery.controller.content.ContentDeleteController;
import org.optigra.onionbowery.controller.content.ContentGetController;
import org.optigra.onionbowery.controller.content.ContentPostController;
import org.optigra.onionbowery.di.bean.Bean;
import org.optigra.onionbowery.di.bean.Scope;
import org.optigra.onionbowery.di.context.tracks.DaoContext;
import org.optigra.onionbowery.di.context.tracks.FacadeContext;
import org.optigra.onionbowery.di.context.tracks.ServiceContext;
import org.optigra.onionbowery.facade.content.ContentFacade;
import org.optigra.onionbowery.handler.DefaultRequestHandler;
import org.optigra.onionbowery.servlet.data.DataConverter;
import org.optigra.onionbowery.servlet.data.DefaultDataConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DefaultAppContext extends AbstractAppContext {
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
    private List<AbstractAppContext> contexts;
    
    public DefaultAppContext(final ServletContext servletContext) throws Exception {
        
        put("servletContext", servletContext(servletContext));
        
        contexts = Arrays.<AbstractAppContext>asList(
                new DaoContext(),
                new ServiceContext(),
                new FacadeContext());
        
        init(new HashMap<String, Bean<?>>());
    }


    @Override
    public void init(final Map<String, Bean<?>> dependencies) throws Exception {
        
        for (AbstractAppContext context : contexts) {
            context.init(this.dependencies);
        }
        
        dependencies();
    }

    @Override
    public void dependencies() {
    	put("dataConverter", dataConverter());
        put("servletFileUpload", servletFileUpload());

        put("contentDeleteController", contentDeleteController());
        put("contentPostController", contentPostController());
        put("contentGetController", contentGetController());
        
        put("controllersMap", controllersMap());

        put("requestHandler", requestHandler());
    }
    
    private Bean<ServletContext> servletContext(final ServletContext servletContext) {
        return new Bean<ServletContext>(servletContext, ServletContext.class, Scope.SINGLETON);
    }

    private Bean<DataConverter> dataConverter() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        DefaultDataConverter instance = new DefaultDataConverter();
        instance.setGson(gson);

        return new Bean<DataConverter>(instance, DataConverter.class, Scope.SINGLETON);
    }
    
    private Bean<DefaultRequestHandler> requestHandler() {
        DefaultRequestHandler instance = new DefaultRequestHandler();
        instance.setControllers(getBean("controllersMap", Map.class));
        instance.setDataConverter(getBean("dataConverter", DataConverter.class));
        
        return new Bean<DefaultRequestHandler>(instance, DefaultRequestHandler.class, Scope.SINGLETON);
    }

    private Bean<Map> controllersMap() {
        Map<String, Controller> instance = new HashMap<String, Controller>();
        urlMapping(instance);
        
        return new Bean<Map>(instance, Map.class, Scope.SINGLETON);
    }

    private Bean<Controller> contentGetController() {
        ContentGetController instance = new ContentGetController();
        instance.setContentFacade(getBean("contentFacade", ContentFacade.class));
        instance.setDataConverter(getBean("dataConverter", DataConverter.class));
        
        return new Bean<Controller>(instance, Controller.class, Scope.SINGLETON);
    }
    
    private Bean<Controller> contentPostController() {
        ContentPostController instance = new ContentPostController();
        instance.setDataConverter(getBean("dataConverter", DataConverter.class));
        instance.setContentFacade(getBean("contentFacade", ContentFacade.class));
        instance.setServletFileUpload(getBean("servletFileUpload", ServletFileUpload.class));
        
        return new Bean<Controller>(instance, Controller.class, Scope.SINGLETON);
    }
    
    private Bean<Controller> contentDeleteController() {
        ContentDeleteController instance = new ContentDeleteController();
        instance.setContentFacade(getBean("contentFacade", ContentFacade.class));
        instance.setDataConverter(getBean("dataConverter", DataConverter.class));
        
        return new Bean<Controller>(instance, Controller.class, Scope.SINGLETON);
    }

    private Bean<ServletFileUpload> servletFileUpload() {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);

        return new Bean<ServletFileUpload>(upload, ServletFileUpload.class, Scope.SINGLETON);
    }

    private void urlMapping(final Map<String, Controller> controllersMap) {
        controllersMap.put("/contentGET", getBean("contentGetController", Controller.class));
        controllersMap.put("/contentPOST", getBean("contentPostController", Controller.class));
        controllersMap.put("/contentPUT", getBean("contentPostController", Controller.class));
        controllersMap.put("/contentDELETE", getBean("contentDeleteController", Controller.class));
    }

}
