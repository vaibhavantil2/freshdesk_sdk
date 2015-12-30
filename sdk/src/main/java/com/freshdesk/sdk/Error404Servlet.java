package com.freshdesk.sdk;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(urlPatterns = "/error/404.json")
public class Error404Servlet extends SuperServlet {
    
    public static final String PATH = "/error/404.json";

    public Error404Servlet(VerboseOptions opts) {
        super(opts);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response headers:
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");
        
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("uri", requestUri);
        JsonObject jsonObj = builder.build();
        // Write error body:
        try(PrintWriter out = resp.getWriter()) {
            try(JsonWriter writer = Json.createWriter(out)) {
                writer.writeObject(jsonObj);
            }
        }
    }
}
