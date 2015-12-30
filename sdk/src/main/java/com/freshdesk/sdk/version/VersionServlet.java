package com.freshdesk.sdk.version;

import com.freshdesk.sdk.SuperServlet;
import com.freshdesk.sdk.VerboseOptions;
import com.google.common.net.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author subhash
 */
@WebServlet(urlPatterns = "/version.json")
public class VersionServlet extends SuperServlet {
    
    public static final String PATH = "/version.json";

    public VersionServlet(VerboseOptions opts) {
        super(opts);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(MediaType.JSON_UTF_8.toString());
        try(PrintWriter out = resp.getWriter()) {
            out.printf("{\"sdk-version\":\"%s\",\"platform-version\":\"%s\"}",
                    Versions.SDK_VERSION, Versions.PLATFORM_VERSION);
        }
    }
    
}
