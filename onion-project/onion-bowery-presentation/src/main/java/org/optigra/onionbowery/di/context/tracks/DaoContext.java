package org.optigra.onionbowery.di.context.tracks;

import java.io.InputStream;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletContext;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.ConfigurationException;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.optigra.onionbowery.dao.ContentRepository;
import org.optigra.onionbowery.dao.DefaultContentRepository;
import org.optigra.onionbowery.dao.jcr.JcrSessionFactory;
import org.optigra.onionbowery.dao.jcr.JcrSessionFactoryImpl;
import org.optigra.onionbowery.dao.mapper.DefaultContentMapper;
import org.optigra.onionbowery.dao.version.DefaultVersionRetriever;
import org.optigra.onionbowery.dao.version.VersionRetriever;
import org.optigra.onionbowery.di.bean.Bean;
import org.optigra.onionbowery.di.bean.Scope;
import org.optigra.onionbowery.di.context.AbstractAppContext;

/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public class DaoContext extends AbstractAppContext {

    @Override
    public void dependencies() throws Exception {
        put("jcrRepositoryConfig", jcrRepositoryConfig());
        put("jcrRepository", jcrRepository());
        
        put("jcrSession", jcrSession());
        put("jcrSessionFactory", jcrSessionFactory());
        put("contentMapper", contentMapper());
        
        put("versionRetriever", versionRetriever());
        
        put("contentRepository", contentRepository());
    }

	private Bean<DefaultContentMapper> contentMapper() {
        DefaultContentMapper instance = new DefaultContentMapper();
        return new Bean<DefaultContentMapper>(instance, DefaultContentMapper.class, Scope.SINGLETON);
    }
	
    private Bean<DefaultVersionRetriever> versionRetriever() {
    	DefaultVersionRetriever instance = new DefaultVersionRetriever();
    	instance.setContentMapper(getBean("contentMapper", DefaultContentMapper.class));
    	
		return new Bean<DefaultVersionRetriever>(instance, DefaultVersionRetriever.class, Scope.SINGLETON);
	}

    private Bean<RepositoryConfig> jcrRepositoryConfig() throws ConfigurationException {
        ServletContext context = getBean("servletContext", ServletContext.class);
        InputStream in =  context.getResourceAsStream("WEB-INF/jcr/jackrabbit-repository.xml");
        RepositoryConfig instance = RepositoryConfig.create(in , "/content");
        
        return new Bean<RepositoryConfig>(instance, RepositoryConfig.class, Scope.SINGLETON);
    }


    private Bean<Repository> jcrRepository() throws RepositoryException {
        RepositoryImpl instance = RepositoryImpl.create(getBean("jcrRepositoryConfig", RepositoryConfig.class));
        
        return new Bean<Repository>(instance, Repository.class, Scope.SINGLETON);
    }
    
    private Bean<Session> jcrSession() throws RepositoryException {
        RepositoryImpl jcrRepository = getBean("jcrRepository", RepositoryImpl.class);
        Session instance = jcrRepository.login(new SimpleCredentials("user", "pass".toCharArray()));
        
        return new Bean<Session>(instance, Session.class, Scope.SINGLETON);
    }

    private Bean<JcrSessionFactory> jcrSessionFactory() {
        JcrSessionFactoryImpl instance = new JcrSessionFactoryImpl();
        instance.setSession(getBean("jcrSession", Session.class));
        
        return new Bean<JcrSessionFactory>(instance, JcrSessionFactory.class, Scope.SINGLETON);
    }

    private Bean<ContentRepository> contentRepository() {
        DefaultContentRepository instance = new DefaultContentRepository();
        instance.setSessionFactory(getBean("jcrSessionFactory", JcrSessionFactory.class));
        instance.setContentMapper(getBean("contentMapper", DefaultContentMapper.class));
        instance.setVersionRetriever(getBean("versionRetriever", VersionRetriever.class));
        
        return new Bean<ContentRepository>(instance, ContentRepository.class, Scope.SINGLETON);
    }

}
