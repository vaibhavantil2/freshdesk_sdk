package com.freshdesk.sdk;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 *
 * @author subhash
 */
public interface NotifyLocalModification {
    void notify(WatchEvent<Path> kind);
}
