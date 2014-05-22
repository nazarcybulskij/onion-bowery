package org.optigra.onionbowery.di.context.tracks;

import org.optigra.onionbowery.di.bean.Bean;
import org.optigra.onionbowery.di.bean.Scope;
import org.optigra.onionbowery.di.context.AbstractAppContext;
import org.optigra.onionbowery.facade.content.ContentFacade;
import org.optigra.onionbowery.facade.content.DefaultContentFacade;
import org.optigra.onionbowery.facade.converter.ContentConverterImpl;
import org.optigra.onionbowery.facade.converter.Converter;
import org.optigra.onionbowery.service.content.ContentService;

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
        return new Bean<Converter>(contentConverter, Converter.class, Scope.SINGLETON);
    }

    private Bean<ContentFacade> contentFacade() {
        DefaultContentFacade contentFacade = new DefaultContentFacade();
        contentFacade.setContentConverter(getBeanWrapper("contentConverter", Converter.class).getInstance());
        contentFacade.setContentService(getBeanWrapper("contentService", ContentService.class).getInstance());
        
        return new Bean<ContentFacade>(contentFacade, ContentFacade.class, Scope.SINGLETON);
    }


}
