package com.freshdesk.sdk;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import liqp.Template;
import liqp.filters.Filter;
import liqp.tags.Tag;
import org.wiztools.commons.FileUtil;

/**
 *
 * @author user
 */
public class TemplateRendererSdk implements TemplateRenderer {
    
    private final List<Filter> filters = new ArrayList<>();
    private final List<Tag> tags = new ArrayList<>();

    @Override
    public TemplateRendererSdk registerFilter(final Filter filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public TemplateRendererSdk registerTag(final Tag tag) {
        tags.add(tag);
        return this;
    }

    @Override
    public String render(final File tmplFile, final Map<String, Object> view, Charset charset) {
        try {
            final String tmplStr = FileUtil.getContentAsString(tmplFile, charset);
            return renderString(tmplStr, view);
        } catch (IOException | RuntimeException ex) {
            throw new TemplateException(ex);
        }
    }

    @Override
    public String renderString(final String tmplStr, final Map<String, Object> view) {
        try {
            final Template tmpl = Template.parse(tmplStr);
            for(Filter f: filters) {
                tmpl.with(f);
            }
            for(Tag t: tags) {
                tmpl.with(t);
            }
            return tmpl.render(view);
        } catch (RuntimeException ex) {
            throw new TemplateException(ex);
        }
    }
}
