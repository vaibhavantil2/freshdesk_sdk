package com.freshdesk.sdk;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.zeroturnaround.zip.FileSource;
import org.zeroturnaround.zip.ZipEntrySource;
import org.zeroturnaround.zip.ZipUtil;

/**
 *
 * @author subhash
 */
final class PkgZipper {
    
    private final boolean verbose;
    
    PkgZipper(boolean verbose) {
        this.verbose = verbose;
    }
    
    private static final List<String> NON_PKG_DIRS = Collections.unmodifiableList(
            Arrays.asList(new String[]{
                "dist",
                "work"
            }));
    
    private void addSubFiles(List<ZipEntrySource> l,
            String folderName,
            File folder) {
        
        if(!folderName.endsWith("/")) {
            throw new IllegalArgumentException("folderName must end with `/'.");
        }
        
        for(File f: folder.listFiles()) {
            final String name = f.getName();
            
            final String fullName = folderName + name;
            if(f.isDirectory()) {
                addSubFiles(l, fullName + "/", f);
            }
            else {
                if(verbose) System.out.println("Adding: " + fullName);
                l.add(new FileSource(fullName, f));
            }
        }
    }
    
    private ZipEntrySource[] getZipSources(File prjDir) {
        List<ZipEntrySource> l = new ArrayList<>();
        
        for(File f: prjDir.listFiles()) {
            final String name = f.getName();
            
            // Skip file test:
            if(name.startsWith(".") || NON_PKG_DIRS.contains(name)) {
                if(verbose) {
                    System.out.print("Skipping: " + name);
                    System.out.println((f.isDirectory()? "/": ""));
                }
                continue;
            }
            
            // Packaging logic:
            if(f.isDirectory()) {
                addSubFiles(l, name + "/", f);
            }
            else {
                if(verbose) System.out.println("Adding: " + name);
                l.add(new FileSource(name, f));
            }
        }
        
        return l.toArray(new ZipEntrySource[]{});
    }
    
    public void pack(File prjDir, File pkg) {
        ZipUtil.pack(getZipSources(prjDir), pkg);
    }
}
