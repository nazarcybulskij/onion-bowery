package org.optigra.onionbowery.di.context.tracks;

import java.io.InputStream;

import javax.jcr.Credentials;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletContext;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.ConfigurationException;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.onion.bowery.dao.ContentRepository;
import org.onion.bowery.dao.JcrRepositoryImpl;
import org.onion.bowery.dao.jcr.JcrSessionFactory;
import org.onion.bowery.dao.jcr.JcrSessionFactoryImpl;
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
        put("contentRepository", contentRepository());
    }

    private Bean<RepositoryConfig> jcrRepositoryConfig() throws ConfigurationException {
        
        ServletContext context = getBean("servletContext", ServletContext.class);
        InputStream in =  context.getResourceAsStream("WEB-INF/jcr/jackrabbit-repository.xml");
        RepositoryConfig repositoryConfig = RepositoryConfig.create(in , "/content");
        
        Bean<RepositoryConfig> repositotyConfigBean = new Bean<>();
        repositotyConfigBean.setClz(RepositoryConfig.class);
        repositotyConfigBean.setInstance(repositoryConfig);
        repositotyConfigBean.setScope(Scope.SINGLETON);
        
        return repositotyConfigBean;
    }


    private Bean<Repository> jcrRepository() throws RepositoryException {

        RepositoryImpl jcrRepository = RepositoryImpl.create(getBean("jcrRepositoryConfig", RepositoryConfig.class));
        
        Bean<Repository> jcrRepositoryBean = new Bean<>();
        jcrRepositoryBean.setClz(Repository.class);
        jcrRepositoryBean.setInstance(jcrRepository);
        jcrRepositoryBean.setScope(Scope.SINGLETON);
        
        return jcrRepositoryBean;
    }
    
    private Bean<Session> jcrSession() throws RepositoryException {
        
        RepositoryImpl jcrRepository = getBean("jcrRepository", RepositoryImpl.class);
        Credentials credentials = new SimpleCredentials("user", "pass".toCharArray());
        
        Session session = jcrRepository.login(credentials);
        
        Bean<Session> sessionBean = new Bean<>();
        sessionBean.setClz(Session.class);
        sessionBean.setInstance(session);
        sessionBean.setScope(Scope.SINGLETON);
        
        return sessionBean;
    }

    private Bean<JcrSessionFactory> jcrSessionFactory() {
        
        JcrSessionFactoryImpl jcrSessionFactory = new JcrSessionFactoryImpl();
        jcrSessionFactory.setSession(getBean("jcrSession", Session.class));
        
        Bean<JcrSessionFactory> jcrSessionFactoryBean = new Bean<>();
        jcrSessionFactoryBean.setClz(JcrSessionFactory.class);
        jcrSessionFactoryBean.setInstance(jcrSessionFactory);
        jcrSessionFactoryBean.setScope(Scope.SINGLETON);
        
        return jcrSessionFactoryBean;
    }

    private Bean<ContentRepository> contentRepository() {
        
        JcrRepositoryImpl contentRepository = new JcrRepositoryImpl();
        contentRepository.setSessionFactory(getBean("jcrSessionFactory", JcrSessionFactory.class));
        
        Bean<ContentRepository> contentRepositoryBean = new Bean<>();
        contentRepositoryBean.setClz(ContentRepository.class);
        contentRepositoryBean.setInstance(contentRepository);
        contentRepositoryBean.setScope(Scope.SINGLETON);
        
        return contentRepositoryBean;
    }

}
