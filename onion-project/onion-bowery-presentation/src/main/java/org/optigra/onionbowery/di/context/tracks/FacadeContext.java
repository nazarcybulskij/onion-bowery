package org.optigra.onionbowery.di.context.tracks;

import org.onion.bowery.facade.content.ContentFacade;
import org.onion.bowery.facade.content.DefaultContentFacade;
import org.onion.bowery.facade.converter.ContentConverterImpl;
import org.onion.bowery.facade.converter.Converter;
import org.onion.bowery.service.content.ContentService;
import org.optigra.onionbowery.di.bean.Bean;
import org.optigra.onionbowery.di.bean.Scope;
import org.optigra.onionbowery.di.context.AbstractAppContext;

/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class FacadeContext extends AbstractAppContext {

    @Override
    public void dependencies() {
        put("contentConverter", contentConverter());
        put("contentFacade", contentFacade());
    }
    
    private Bean<Converter> contentConverter() {

        ContentConverterImpl contentConverter = new ContentConverterImpl();
        
        Bean<Converter> contentFacadeBean = new Bean<>();
        
        contentFacadeBean.setClz(Converter.class);
        contentFacadeBean.setInstance(contentConverter);
        contentFacadeBean.setScope(Scope.SINGLETON);
        
        return contentFacadeBean;
    }

    private Bean<ContentFacade> contentFacade() {

        DefaultContentFacade contentFacade = new DefaultContentFacade();
        contentFacade.setContentConverter(getBeanWrapper("contentConverter", Converter.class).getInstance());
        contentFacade.setContentService(getBeanWrapper("contentService", ContentService.class).getInstance());
        
        Bean<ContentFacade> contentFacadeBean = new Bean<>();
        contentFacadeBean = new Bean<>();
        contentFacadeBean.setClz(ContentFacade.class);
        contentFacadeBean.setInstance(contentFacade);
        contentFacadeBean.setScope(Scope.SINGLETON);
        
        return contentFacadeBean;
    }


}
