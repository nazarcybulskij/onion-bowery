package org.optigra.onionbowery.dao.mapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;

import org.optigra.onionbowery.model.NodeContent;

/**
 * @date Apr 2, 2014
 * @author ivanursul
 *
 */
public class DefaultContentMapper implements ContentMapper<NodeContent> {

    private static final String FILE = "file";

    @Override
    public NodeContent map(final Node node) throws Exception {
        
        NodeContent nodeContent = new NodeContent();
        
        Map<String, Object> properties = new HashMap<String, Object>();
        for (PropertyIterator iterator = node.getProperties(); iterator.hasNext();) {
            Property property = iterator.nextProperty();
            
            if(!FILE.equals(property.getName())) {
                properties.put(property.getName(), property.getString());
            }
        }
        
        List<String> subNodes = new ArrayList<>();
        for(NodeIterator iterator = node.getNodes(); iterator.hasNext();) {
            Node nextNode = iterator.nextNode();
            subNodes.add(nextNode.getName());
        }
        
        Property fileProperty = node.getProperty(FILE);
        InputStream inputStream = fileProperty.getBinary().getStream();
        
        nodeContent.setName(node.getName());
        nodeContent.setProperties(properties);
        nodeContent.setSubNodes(subNodes);
        nodeContent.setInputStream(inputStream);
        
        return nodeContent;
    }

}
