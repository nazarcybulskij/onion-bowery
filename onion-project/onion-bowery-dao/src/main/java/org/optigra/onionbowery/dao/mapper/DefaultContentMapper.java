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

import org.optigra.onionbowery.model.Content;

/**
 * @date Apr 2, 2014
 * @author ivanursul
 *
 */
public class DefaultContentMapper implements ContentMapper<Content> {

    private static final String JCR_PREFIX = "jcr:";
    private static final String FILE = "file";

    @Override
    public Content map(final Node node) throws Exception {
        
        Content content = new Content();
        
        Map<String, String> properties = new HashMap<String, String>();
        for (PropertyIterator iterator = node.getProperties(); iterator.hasNext();) {
            Property property = iterator.nextProperty();
            
            if(!FILE.equals(property.getName()) && !property.getName().contains(JCR_PREFIX)) {
                properties.put(property.getName(), property.getString());
            }
        }
        
        List<String> subNodes = new ArrayList<>();
        for(NodeIterator iterator = node.getNodes(); iterator.hasNext();) {
            Node nextNode = iterator.nextNode();
            subNodes.add(nextNode.getName());
        }
        
        // Need to check, in case, when node is only a "folder" and contains no files.
        if(node.hasProperty(FILE)) {
            Property fileProperty = node.getProperty(FILE);
            InputStream inputStream = fileProperty.getBinary().getStream();
            content.setInputStream(inputStream);
        }
        
        content.setFileName(node.getName());
        content.setProperties(properties);
        content.setSubNodes(subNodes);
        
        content.setContentId(node.getIdentifier());
        content.setPath(node.getPath());
        
        return content;
    }

}
