package com.freshdesk.sdk.plug;

import com.freshdesk.sdk.Constants;
import com.freshdesk.sdk.FilterAssetURL;
import java.io.File;

/**
 *
 * @author subhash
 */
public class FilterAssetURLPlug extends FilterAssetURL {
    
    private static final String URL_PREFIX = Constants.URL_SCHEME + "://" +
                    Constants.LOCAL_SERVER_URL + ":" + Constants.SERVER_PORT + "/";

    public FilterAssetURLPlug(File f) {
        super(f);
    }
    
    @Override
    public String getAssetPrefix() {
        return URL_PREFIX;
    }
    
}
