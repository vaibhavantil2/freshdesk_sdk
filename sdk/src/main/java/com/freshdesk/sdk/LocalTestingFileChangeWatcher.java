package com.freshdesk.sdk;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author subhash
 */
public class LocalTestingFileChangeWatcher {
    
    private final NotifyLocalModification notifier;
    
    public LocalTestingFileChangeWatcher(NotifyLocalModification notifier) {
        this.notifier = notifier;
    }
    
    public void watch(File prjDir) throws IOException {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        final SimpleFileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                return FileVisitResult.CONTINUE;
            }
        };
        Files.walkFileTree(prjDir.toPath(), fileVisitor);
        
        while(true) {
            WatchKey watchKey;
            try {
                watchKey = watcher.take();
            }
            catch(InterruptedException ex) {
                ex.printStackTrace(System.err);
                return;
            }
            watchKey.pollEvents().stream().forEach((event) -> {
                WatchEvent.Kind<?> kind = event.kind();
                if (!(kind == OVERFLOW)) {
                    WatchEvent<Path> ev = (WatchEvent<Path>)event;
                    Path filename = ev.context();
                    notifier.notify(filename.toFile());
                }
            });
            
            boolean valid = watchKey.reset();
            if (!valid) {
                break;
            }
        }
    }
}
