package com.freshdesk.sdk.plug;

import com.freshdesk.sdk.FAException;
import com.freshdesk.sdk.ManifestContents;
import java.io.File;
import java.io.IOException;
import org.wiztools.commons.FileUtil;

/**
 *
 * @author raghav
 */
public class PlugResponse {
    
    private final File htmlFile;
    private final File cssFile;
    private final File jsFile;
    private final ManifestContents manifest;
    
    public PlugResponse(File libDir, ManifestContents mf) {
        if(libDir.isDirectory() && libDir.canRead()) {
            htmlFile = new File(libDir, PlugFiles.toString(PlugFiles.HTML));
            cssFile = new File(libDir, PlugFiles.toString(PlugFiles.CSS));
            jsFile = new File(libDir, PlugFiles.toString(PlugFiles.JS));
            if(!(htmlFile.isFile() && htmlFile.canRead()
                && cssFile.isFile() && cssFile.canRead()
                && jsFile.isFile() && jsFile.canRead())) {
                throw new FAException("Files missing");
            }
            manifest = mf;
        }
        else {
            throw new FAException("Lib Directory corrupt/ not readable.");
        }
    }
    
    private String getFileContent(File f) throws IOException {
        return FileUtil.getContentAsString(f, manifest.getCharset());
    }
    
    public String getPlugResponse() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(getHtmlContent())
                .append("\n")
                .append(getCssContent())
                .append("\n")
                .append(getJsContent());
        return sb.toString();
    }
    
    private String getCssContent() throws IOException {
        StringBuilder sb = new StringBuilder("<style>");
        final String cssContents = getFileContent(cssFile);
        sb.append(cssContents).append("</style>");
        return sb.toString();
    }
    
    private String getHtmlContent() throws IOException {
        return getFileContent(htmlFile);
    }
    
    private String getJsContent() throws IOException {
        StringBuilder sb = new StringBuilder();
        final String jsContents = getFileContent(jsFile);
        sb.append("<script type='text/javascript'>\n");
        sb.append("var plugCodeTest = ")
                .append(jsContents)
                .append("Freshapp.run(plugCodeTest.init());\n")
                .append("</script>");
        return sb.toString();
    }
}
