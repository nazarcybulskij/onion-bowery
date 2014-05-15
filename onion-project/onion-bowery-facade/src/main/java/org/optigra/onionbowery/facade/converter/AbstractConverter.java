package org.optigra.onionbowery.facade.converter;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for all converters.
 * 
 * @date Feb 7, 2014
 * @author ivanursul
 * 
 */
public abstract class AbstractConverter<SOURCE, TARGET> implements Converter<SOURCE, TARGET> {

    @Override
    public List<TARGET> convertAll(final List<SOURCE> sources) {

        return convertAll(sources, new ArrayList<TARGET>(sources.size()));
    }

    @Override
    public List<TARGET> convertAll(final List<SOURCE> sources, final List<TARGET> targets) {

        for (SOURCE source : sources) {
            targets.add(convert(source));
        }

        return targets;
    }
}
