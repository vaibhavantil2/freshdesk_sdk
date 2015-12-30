package com.freshdesk.sdk;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import liqp.Template;
import liqp.nodes.LNode;
import liqp.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author subhash
 */
public class TagInclude extends Tag {
    
    private static final Logger LOG = LogManager.getLogger(TagInclude.class);
    
    private final File snippetsDir;
    public static final String extension = ".liquid";
    
    public TagInclude(final File prjDir) {
        super("include");
        this.snippetsDir = new File(new File(prjDir, "template"), "snippets");
    }

    @Override
    public Object render(final Map<String, Object> context, final LNode... nodes) {
        try {
            final String fileNameWithoutExt = super.asString(nodes[0].render(context));
            final File f = new File(snippetsDir, fileNameWithoutExt + extension);
            final Template include = Template.parse(f);

            // check if there's a optional "with expression"
            if(nodes.length > 1) {
                final Object value = nodes[1].render(context);
                context.put(fileNameWithoutExt, value);
            }

            return include.render(context);
        }
        catch(IOException ex) {
            LOG.warn(ex.getLocalizedMessage());
            return "";
        }
    }
    
}
