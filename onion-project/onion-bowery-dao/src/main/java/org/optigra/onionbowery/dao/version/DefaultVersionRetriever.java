package org.optigra.onionbowery.dao.version;

import java.util.LinkedList;
import java.util.List;

import javax.jcr.Session;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;

import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.dao.jcr.JcrHelper;
import org.optigra.onionbowery.dao.mapper.ContentMapper;
import org.optigra.onionbowery.model.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultVersionRetriever implements VersionRetriever {
	private static final Logger logger = LoggerFactory.getLogger(DefaultVersionRetriever.class);
	
    private static final String JCR_PREFIX = "jcr:";

    private ContentMapper<Content> contentMapper;
    
	@Override
	public List<Content> getVersions(Session session, String path) {
		List<Content> versions = null;
		try {
			
			if (!JcrHelper.isVersionable(path)) {
				return versions;
			}
			
			VersionHistory history = session.getWorkspace().getVersionManager().getVersionHistory(path);
			versions = getVersions(history);
			
		} catch (Exception e) {
			logger.error("Error occured during retrieving messages", e);
			throw new ContentException("Error occured during retrieving messages", e);
		}
		
		return versions;
	}

	private List<Content> getVersions(final VersionHistory history) throws Exception {
		LinkedList<Content> versions = new LinkedList<>();
		
		if (history == null) {
			return versions;
		}
		
		Content content = null;
		for (VersionIterator it = history.getAllVersions(); it.hasNext();) {
			Version version = (Version) it.next();
			
			if (!version.getName().contains(JCR_PREFIX)) {
				content = contentMapper.map(version);
				content.setVersion(version.getName());
				versions.add(content);
			}
		}

		return versions;
	}

	public void setContentMapper(ContentMapper<Content> contentMapper) {
		this.contentMapper = contentMapper;
	}

}
