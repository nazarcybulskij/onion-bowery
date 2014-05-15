package org.optigra.onionbowery.dao.jcr;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public interface JcrSessionFactory {

    Session getCurrentSession() throws LoginException, RepositoryException;

}