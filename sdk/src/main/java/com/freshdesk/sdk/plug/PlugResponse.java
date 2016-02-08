package com.freshdesk.sdk.plug;

import com.cathive.sass.SassCompilationException;
import com.cathive.sass.SassContext;
import com.cathive.sass.SassFileContext;
import com.cathive.sass.SassOptions;
import com.cathive.sass.SassOutputStyle;
import com.freshdesk.sdk.FAException;
import com.freshdesk.sdk.ManifestContents;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
        sb.append(getCssContent())
                .append("\n")
                .append(getHtmlContent())
                .append("\n")
                .append(getJsContent());
        return sb.toString();
    }
    
    private String getCssContent() throws IOException {
        StringBuilder sb = new StringBuilder("<style>");//.append("#testplug { \n");
        File workDir = new File(".", "work");
        if(!workDir.isDirectory()) {
            workDir.mkdirs();
        }
        File compiledCssFile = new File(workDir, "app.css");
        OutputStream os = new FileOutputStream(compiledCssFile);
        try {
            SassContext ctx = SassFileContext.create(cssFile.toPath());
            SassOptions options = ctx.getOptions();
            options.setOutputStyle(SassOutputStyle.EXPANDED);
            ctx.compile(os);
            final String cssContents = getFileContent(compiledCssFile);
            sb.append(cssContents).append("</style>");
            return sb.toString();
        }
        catch (FileNotFoundException ex) {
            throw new FAException("app.scss File unavailable.", ex);
        }
        catch (SassCompilationException ex) {
            throw new FAException("Scss Compilation Failed.", ex);
        }
    }
    
    private String getHtmlContent() throws IOException {
        return getFileContent(htmlFile);
    }
    
    private String getJsContent() throws IOException {
        StringBuilder sb = new StringBuilder();
        final String jsContents = getFileContent(jsFile);
        sb.append("<script type='text/javascript'>\n")
                .append("Freshapp.run(function() { \n var examplePlug = (function() { \n " +  jsContents +"}()); examplePlug.init(); });\n")
                .append("</script>");
        return sb.toString();
    }
}
