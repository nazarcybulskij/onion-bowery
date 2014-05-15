package org.optigra.onionbowery.dao.mapper;

import javax.jcr.Node;

/**
 * @date Apr 2, 2014
 * @author ivanursul
 *
 */
public interface ContentMapper<T> {

    T map(Node node) throws Exception;
    
}
