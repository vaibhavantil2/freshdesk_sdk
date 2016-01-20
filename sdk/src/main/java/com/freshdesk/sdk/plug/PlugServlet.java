package com.freshdesk.sdk.plug;

import com.freshdesk.sdk.TemplateCtxBuilder;
import com.freshdesk.sdk.FAException;
import com.freshdesk.sdk.ManifestContents;
import com.freshdesk.sdk.JsonUtil;
import com.freshdesk.sdk.IParamContents;
import com.freshdesk.sdk.MixedContentUtil;
import com.freshdesk.sdk.SuperServlet;
import com.freshdesk.sdk.TemplateRendererSdk;
import com.freshdesk.sdk.VerboseOptions;
import com.freshdesk.sdk.plug.run.UserBean;
import com.freshdesk.sdk.plug.run.UserLiquefier;
import com.freshdesk.sdk.plug.run.UserType;
import com.freshdesk.sdk.version.Versions;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.wiztools.appupdate.Version;
import org.wiztools.appupdate.VersionImpl;
import static java.nio.charset.StandardCharsets.*;
import org.wiztools.commons.StreamUtil;
import org.wiztools.commons.StringUtil;

/**
 *
 * @author user
 */

@WebServlet(urlPatterns = "/plug/*")
public class PlugServlet extends SuperServlet {
    
    private static final String EXTN_VERSION_HEADER = "FAExtnVersion";
    
    public static final String PATH = "/plug/*";
    
    public PlugServlet(VerboseOptions opts) {
        super(opts);
    }
    
    private void process(final HttpServletRequest req,
            final HttpServletResponse resp) throws IOException, ServletException {
        
        // Version header validation:
        String verStr = req.getHeader(EXTN_VERSION_HEADER);
        if(StringUtil.isEmpty(verStr)) {
            String msg = "Version header `" + EXTN_VERSION_HEADER + "' not available in request.";
            System.err.println(msg);
            throw new ServletException(msg);
        }
        try {
            final Version extnVer = new VersionImpl(verStr);
            if(!Versions.isCompatible(extnVer)) {
                String msg = "Request from incompatible browser extn: " + extnVer;
                System.err.println(msg);
                throw new ServletException(msg);
            }
        }
        catch(IllegalArgumentException ex) {
            String msg = "Version header has invalid value: " + verStr;
            System.err.println(msg);
            if(verboseException) {
                ex.printStackTrace(System.err);
            }
            throw new ServletException(msg, ex);
        }

        final File prjDir = new File(".");
        final File libDir = new File(prjDir, "lib");
        final ManifestContents manifest = new ManifestContents(prjDir);

        String body = StreamUtil.inputStream2String(req.getInputStream(), UTF_8);
        if(trace) {
            System.out.println("### Plug request:\n");
            System.out.println(StringUtil.isEmpty(body)? "<EMPTY>": body);
            System.out.println();
        }
        
        if(StringUtil.isEmpty(body)) {
            System.err.println("Corrupt request (empty request body). Failing request.");
            throw new ServletException("Request body is empty!");
        }
        
        try {
            Map<String, Object> urlBody = JsonUtil.jsonToMap(body);
            Map<String, Object> pageParams = (Map<String, Object>)urlBody
                    .get("pageParams");
            Map<String, Object> requester = (Map<String, Object>)urlBody
                    .get("requester");
            String pgStr = (String) urlBody.get("page");
            if(pgStr == null) {
                throw new FAException("`page' missing in request.");
            }
            
            String pgUrl = (String) urlBody.get("url");
            if (pgUrl == null) {
                throw new FAException("`url' missing in request.");
            }
            PlugPages plugpage = PlugPages.fromString(pgStr);
            
            IParamContents ipc = new IParamContents(prjDir, manifest.getCharset());
            
            PlugUtil pu = new PlugUtil(pageParams, requester, plugpage, pgUrl);
            Map<String, Object> params =  pu.getParams();
            Map<String, Object> currentUserDetails = UserLiquefier.getUserObject(
                                                     UserType.CURRENT_USER,
                                                     new UserBean((Map<String, Object>)urlBody.get("current_user")));

            Map<String, Object> renderParams = new TemplateCtxBuilder()
                    .addExisting(params)
                    .addExisting(currentUserDetails)
                    .addInstallationParams(ipc.getIParams()).build();

            String consolidatedResponse = new PlugResponse(libDir, manifest).getPlugResponse();
            TemplateRendererSdk renderer = new TemplateRendererSdk()
                    .registerFilter(new FilterAssetURLPlug(prjDir));
            String finalOutput = renderer.renderString(consolidatedResponse, renderParams);
            if(trace) {
                System.out.println("### Plug response:\n");
                System.out.println(finalOutput);
                System.out.println();
            }

            // To avoid SSL mixed-content warning:
            MixedContentUtil.allowedOrigin(resp, "*");

            try(OutputStream os = resp.getOutputStream()) {
                os.write(finalOutput.getBytes());
                os.flush();
            }
        }
        catch(FAException ex) {
            System.err.println(ex.getMessage());
            if(verboseException) {
                ex.printStackTrace(System.err);
            }
            throw new ServletException(ex);
        }
    }
    
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }
    
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }
}
