package org.optigra.onionbowery.common.utils.jcr;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.io.FilenameUtils;

/**
 * @date Apr 2, 2014
 * @author ivanursul
 *
 */
public class JcrUtils {

    private static final String SLASH_REGEX = "^/";
    private static final String DOT = ".";
    private static final String EMPTY_STRING = "";
    private static final String SPECIAL_CHARACTERS_PATTERN = "[^a-zA-Z0-9]";
    private static final String SLASH = "/";
    
    public static String getValidFilename(final String filename) {
        
        String extension = FilenameUtils.getExtension(filename);
        String clearName = filename.replace(DOT + extension, EMPTY_STRING);
        String formattedFileName = clearName.replaceAll(SPECIAL_CHARACTERS_PATTERN, EMPTY_STRING).toLowerCase().trim();
        
        if(!extension.isEmpty()) {
            formattedFileName += DOT + extension;
        }
        
        return formattedFileName;
    }
    
    public static String reducePath(final String path) {
        
        if(EMPTY_STRING.equals(path)) {
            return EMPTY_STRING;
        }
        
        String[] paths = path.replaceFirst(SLASH_REGEX, EMPTY_STRING).split(SLASH);
        
        Queue<String> queue = new LinkedList<>(Arrays.asList(paths));
        queue.poll();
        
        StringBuilder sb = new StringBuilder();
        
        for (String string : queue) {
            sb.append(SLASH);
            sb.append(string);
        }
        
        return sb.toString();
    }
    
    public static String getFirstPathItem(final String path) {
        return path.replaceFirst(SLASH_REGEX, EMPTY_STRING).split(SLASH, -1)[0];
    }
    
}
