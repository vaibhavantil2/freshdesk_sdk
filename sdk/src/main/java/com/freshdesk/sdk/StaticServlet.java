package com.freshdesk.sdk;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.wiztools.commons.FileUtil;

/**
 *
 * @author user
 */
@WebServlet(urlPatterns = "/assets/*")
public class StaticServlet extends SuperServlet {
    
    private static final String ASSETS_DIR = "assets";

    public StaticServlet(VerboseOptions opts) {
        super(opts);
    }
    
    public static final String PATH = "/" + ASSETS_DIR + "/*";

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        
        final String pathInfo = req.getPathInfo();
        
        // Set content-type:
        final String contentType = Util.getMimeType(pathInfo);
        if (contentType != null) {
            resp.setContentType(contentType);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "File Type Not Supported");
        }
        
        // To avoid SSL mixed-content warning:
        MixedContentUtil.allowedOrigin(resp, "*");

        // Send content:
        final File baseDir = new File(".");
        final File assetFile = new File(baseDir, ASSETS_DIR + pathInfo);
        if (assetFile.canRead()) {
            if(trace || verbose) {
                System.out.println("Serving asset: " + ASSETS_DIR + pathInfo);
            }
            
            final byte[] data = FileUtil.getContentAsBytes(assetFile);
            try (OutputStream os = resp.getOutputStream()) {
                os.write(data);
                os.flush();
            }
        } else {
            if(trace || verbose) {
                System.out.println("Asset not found: " + ASSETS_DIR + pathInfo);
            }
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found.");
        }
    }
}
