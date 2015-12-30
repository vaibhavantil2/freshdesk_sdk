package com.freshdesk.sdk;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Map;
import liqp.filters.Filter;
import liqp.tags.Tag;

/**
 *
 * @author subhash
 */
public interface TemplateRenderer {
    String render(File tmpl, Map<String, Object> view, Charset charset) throws TemplateException;
    String renderString(String tmplStr, Map<String, Object> view) throws TemplateException;
    TemplateRenderer registerFilter(Filter filter) throws TemplateException;
    TemplateRenderer registerTag(Tag tag) throws TemplateException;
}
