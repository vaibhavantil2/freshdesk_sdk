package com.freshdesk.sdk;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author subhash
 */
@WebServlet(urlPatterns = "/error/exception.json")
public class ErrorServlet extends SuperServlet {
    
    public static final String PATH = "/error/exception.json";

    public ErrorServlet(VerboseOptions opts) {
        super(opts);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Set response headers:
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        // Get the exception object:
        Throwable ex = (Throwable) req.getAttribute(
                "javax.servlet.error.exception");
        String msg = null;
        String stackTrace = null;
        if(ex != null) {
            // Message:
            msg = ex.getMessage();
            
            // stack trace:
            StringWriter writer = new StringWriter();
            PrintWriter pw = new PrintWriter(writer);
            ex.printStackTrace(pw);
            stackTrace = writer.toString();
            
            // Verbose:
            if(!verboseException) { // avoid displaying redundant error message!
                System.out.println("Exception: " + msg);
            }
            if(verboseException) {
                System.out.print("Exception: ");
                System.out.println(stackTrace);
            }
        }
        
        // Prepare JSON:
        JsonObjectBuilder builder = Json.createObjectBuilder();
        if(msg != null) {
            builder.add("message", msg);
        }
        if(stackTrace != null) {
            builder.add("stackTrace", stackTrace);
        }
        JsonObject jsonObj = builder.build();
        
        // Write error body:
        try(PrintWriter out = resp.getWriter()) {
            try(JsonWriter writer = Json.createWriter(out)) {
                writer.writeObject(jsonObj);
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }
}
