package org.optigra.onionbowery.dao.version;

import java.util.List;

import javax.jcr.Session;

import org.optigra.onionbowery.model.Content;

public interface VersionRetriever {

	List<Content> getVersions(Session session, String path);

}
