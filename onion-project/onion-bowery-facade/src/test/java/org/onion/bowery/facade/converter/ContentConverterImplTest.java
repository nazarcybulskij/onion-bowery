package org.onion.bowery.facade.converter;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.onion.bowery.model.Content;
import org.onion.bowery.resource.ContentResource;

public class ContentConverterImplTest {

    private ContentConverterImpl unit = new ContentConverterImpl();
    
    @Test
    public void testConvert() throws Exception {
        // Given
        InputStream stream = new ByteArrayInputStream("veryLarge".getBytes());
        String contentId = "contentId";
        String fileName = "filename";
        String path = "path";
        
        Content source = new Content();
        source.setContentId(contentId);
        source.setFileName(fileName);
        source.setPath(path);
        source.setStream(stream);

        ContentResource expected = new ContentResource();
        expected.setContentId(contentId);
        expected.setFileName(fileName);
        expected.setPath(path);
        
        // When
        ContentResource actual = unit.convert(source);

        // Then
        assertEquals(expected, actual);
    }
}
