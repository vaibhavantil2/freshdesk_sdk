package com.freshdesk.sdk.version;

import com.freshdesk.sdk.SuperServlet;
import com.freshdesk.sdk.VerboseOptions;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.wiztools.appupdate.Version;
import org.wiztools.appupdate.VersionImpl;
import com.freshdesk.sdkcommon.Versions;

/**
 *
 * @author subhash
 */
@WebServlet(urlPatterns = "/version/compatible/*")
public class VersionCompatibilityCheckServlet extends SuperServlet {
    
    public static final String PATH = "/version/compatible/*";

    public VersionCompatibilityCheckServlet(VerboseOptions opts) {
        super(opts);
    }
    
    private void noOp(int status, HttpServletResponse resp) throws ServletException, IOException {
        noOp(status, null, resp);
    }
    
    private void noOp(int status, String statusMsg, HttpServletResponse resp) throws ServletException, IOException {
        if(statusMsg != null) {
            resp.setStatus(status, statusMsg);
        }
        else {
            resp.setStatus(status);
        }
        try(PrintWriter out = resp.getWriter()) {
            // just open and close the stream!
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if(pathInfo != null && pathInfo.startsWith("/")) {
            try {
                Version browserVersion = new VersionImpl(pathInfo.replaceFirst("/", ""));
                if(Versions.isCompatible(browserVersion)) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
                else {
                    noOp(HttpServletResponse.SC_NOT_FOUND,
                            String.format("Incompat versions: sdk %s; browser extn %s",Versions.SDK_VERSION, browserVersion),
                            resp);
                }
            }
            catch(IllegalArgumentException ex) {
                noOp(HttpServletResponse.SC_BAD_REQUEST, resp);
            }
        }
        else {
            noOp(HttpServletResponse.SC_NOT_FOUND, resp);
        }
    }
    
}
