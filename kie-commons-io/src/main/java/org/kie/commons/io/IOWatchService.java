package org.kie.commons.io;

import org.kie.commons.java.nio.file.WatchService;

public interface IOWatchService {

    void dispose();

    void addWatchService( final WatchService watchService );

}
