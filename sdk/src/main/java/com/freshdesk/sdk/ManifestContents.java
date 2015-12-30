package com.freshdesk.sdk;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wiztools.appupdate.Version;
import org.wiztools.appupdate.VersionImpl;
import org.wiztools.commons.Charsets;

/**
 *
 * @author subhash
 */
public class ManifestContents {
    private Charset charset;
    
    private static final String PF_VER_KEY = "platform-version";
    private static final String PKG_TYPE_KEY = "pkg-type";
    
    private final Version pfVersion;
    private final ExtnType pkgType;

    private static final Logger LOG = LogManager.getLogger(ManifestContents.class);
    
    private static final String[] MANDATORY_PARAMS = new String[]{
        PF_VER_KEY,
        PKG_TYPE_KEY
    };

    public ManifestContents(final File prjDir) throws FAException {
        final File manifest = new File(prjDir, "manifest.yml");
        
        final Map<String, Object> yaml;
        
        try {
            yaml = YamlUtil.getYaml(manifest, Charsets.UTF_8);
        }
        catch (IOException ex) {
            throw new FAException("Failed to read from manifest.", ex);
        }
        
        try {
            String cs = (String) yaml.get("source-charset");
            charset = Charset.forName(cs);
            LOG.info(String.format("Using charset: %s.", charset));
        }
        catch (IllegalArgumentException ex) {
            LOG.warn("Missing source charset in manifest. Using default UTF-8.");
            charset = Charsets.UTF_8;
        }
        
        // Mandatory package validation:
        for(String param: MANDATORY_PARAMS) {
            try {
                String v = (String) yaml.get(param);
            }
            catch(IllegalArgumentException ex) {
                throw new FAException(String.format("`%s' missing in manifest.", param));
            }
        }
        
        // Instantiate instance variables:
        pfVersion = new VersionImpl((String) yaml.get(PF_VER_KEY));
        pkgType = ExtnType.fromString((String) yaml.get(PKG_TYPE_KEY));
        
        // Version validation:
        if(!PlatformVersion.isSupported(pfVersion)) {
            throw new FAException(
                    String.format("Unsupported platform in manifest: %s.", pfVersion));
        }
    }

    public Charset getCharset() {
        return charset;
    }

    public Version getPlatformVersion() {
        return pfVersion;
    }
    
    public ExtnType getPackageType() {
        return pkgType;
    }
}
