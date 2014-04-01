package org.optigra.onionbowery.di.context.tracks;

import org.onion.bowery.dao.ContentRepository;
import org.onion.bowery.service.content.ContentService;
import org.onion.bowery.service.content.DefaultContentService;
import org.optigra.onionbowery.di.bean.Bean;
import org.optigra.onionbowery.di.bean.Scope;
import org.optigra.onionbowery.di.context.AbstractAppContext;

/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public class ServiceContext extends AbstractAppContext {

    @Override
    public void dependencies() {
        put("contentService", contentService());
    }

    private Bean<ContentService> contentService() {

            DefaultContentService contentService = new DefaultContentService();
            contentService.setContentRepository(getBean("contentRepository", ContentRepository.class));
            
            Bean<ContentService> contentFacadeBean = new Bean<>();
            contentFacadeBean = new Bean<>();
            contentFacadeBean.setClz(ContentService.class);
            contentFacadeBean.setInstance(contentService);
            contentFacadeBean.setScope(Scope.SINGLETON);
            
            return contentFacadeBean;
    }

}
