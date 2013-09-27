package org.kie.commons.java.nio.base;

import org.kie.commons.java.nio.file.Path;

public interface WatchContext {

    Path getPath();

    Path getOldPath();

    String getSessionId();

    String getUser();
}
