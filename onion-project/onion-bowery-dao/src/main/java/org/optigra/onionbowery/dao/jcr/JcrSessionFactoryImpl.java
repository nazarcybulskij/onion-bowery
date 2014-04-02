package org.optigra.onionbowery.dao.jcr;

import javax.jcr.Session;

public class JcrSessionFactoryImpl implements JcrSessionFactory {
    
    private Session session;

    @Override
    public Session getCurrentSession() {
        return session;
    }

    public void setSession(final Session session) {
        this.session = session;
    }

}
