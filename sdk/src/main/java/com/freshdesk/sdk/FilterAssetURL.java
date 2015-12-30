package com.freshdesk.sdk;

import java.io.File;
import liqp.filters.Filter;

/**
 *
 * @author user
 */
public abstract class FilterAssetURL extends Filter {

    private final File baseDir;
    @Override
    public Object apply(final Object value, final Object... notUsed) {
        final String assetPath = super.asString(value);
        
        final String path = "assets/" + assetPath;
        final File assetFile = new File(baseDir, path);
        if(assetFile.exists()) {
            return getAssetPrefix() + path;
        }
        else {
            throw new FAException("Not Found: " + assetFile.getAbsolutePath());
        }
    }
    public FilterAssetURL(final File f) {
        super(LiquidFilterName.ASSET);
        baseDir = f;
    }
    
    public abstract String getAssetPrefix();
}
