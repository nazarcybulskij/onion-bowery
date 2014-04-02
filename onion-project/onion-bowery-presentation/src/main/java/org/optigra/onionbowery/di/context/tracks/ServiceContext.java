package org.optigra.onionbowery.di.context.tracks;

import org.optigra.onionbowery.dao.ContentRepository;
import org.optigra.onionbowery.di.bean.Bean;
import org.optigra.onionbowery.di.bean.Scope;
import org.optigra.onionbowery.di.context.AbstractAppContext;
import org.optigra.onionbowery.service.content.ContentService;
import org.optigra.onionbowery.service.content.DefaultContentService;

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
