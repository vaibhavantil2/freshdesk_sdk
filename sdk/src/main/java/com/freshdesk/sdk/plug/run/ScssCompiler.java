package com.freshdesk.sdk.plug.run;

import com.freshdesk.sdk.FAException;
import com.vaadin.sass.internal.ScssStylesheet;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author raghav
 */
public class ScssCompiler {
    
    private final File inputFile;
    private final File outputFile;

    public ScssCompiler(File inputFile, File outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }
    
    public void compile() {
        if(!inputFile.canRead()) {
            throw new FAException("File not Found");
        }
        try {
            ScssStylesheet scss = ScssStylesheet.get(inputFile.getCanonicalPath());
            scss.compile();
            try (Writer writer = createWriter()) {
                scss.write(writer);
            }
        }
        catch(Exception e) {
            throw new FAException(e);
        }
    }
    
    private Writer createWriter() throws IOException {
        if(outputFile != null) {
            return new FileWriter(outputFile);
        }
        else {
            return new OutputStreamWriter(System.out, "UTF-8");
        }
    }
    
}
