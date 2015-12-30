package com.freshdesk.sdkupdate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import me.tongfei.progressbar.ProgressBar;
import org.wiztools.commons.SystemProperty;

/**
 *
 * @author subhash
 */
public class DownloadUtil implements Rollbackable {
    
    private final String url;
    private final File dlFile;
    
    public DownloadUtil(String url) {
        this.url = url;
        final String fileName = url.substring(url.lastIndexOf('/'));
        dlFile = new File(
                    new File(SystemProperty.tmpDir),
                    fileName);
    }
    
    public File download() {
        try {
            URLConnection con = new URL(url).openConnection();
            con.connect();
            final int totalLen = con.getContentLength();
            ProgressBar pb = new ProgressBar("Downloading", totalLen);
            pb.start();
            try(
                    InputStream is = con.getInputStream();
                    OutputStream os = new FileOutputStream(dlFile);
                    ) {
                byte[] buf = new byte[1024*5];
                int len;
                while((len=is.read(buf)) != -1) {
                    os.write(buf, 0, len);
                    
                    pb.stepBy(len);
                }
                pb.stop();
            }
            return dlFile;
        }
        catch(MalformedURLException ex) {
            throw new SdkUpdateException(ex);
        }
        catch(IOException ex) {
            throw new SdkUpdateException(ex);
        }
    }

    @Override
    public void rollback() {
        if(dlFile != null && dlFile.exists()) {
            dlFile.delete();
        }
    }
}
