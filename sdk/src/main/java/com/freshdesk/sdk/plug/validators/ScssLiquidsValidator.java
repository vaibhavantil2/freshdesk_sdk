package com.freshdesk.sdk.plug.validators;

import com.freshdesk.sdk.ManifestContents;
import com.freshdesk.sdk.Util;
import com.freshdesk.sdk.validators.BasePrePkgValidator;
import com.freshdesk.sdk.ExitStatus;
import com.freshdesk.sdk.FAException;
import com.freshdesk.sdk.validators.PrePackageValidator;
import com.freshdesk.sdk.SdkException;
import com.freshdesk.sdk.plug.PlugContentUnifier;
import com.freshdesk.sdk.plug.AppFile;
import com.freshdesk.sdk.plug.PlugExecutionContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validates SCSS for allowed Liquids and Liquid Filters.
 * @author subhash
 */
@PrePackageValidator
public class ScssLiquidsValidator extends BasePrePkgValidator {
    
    private static final String[] PLUG_FILES = AppFile.getAllFiles();
    private static final String[] ALLOWED_LIQUIDS = {"app_id"};
    private static final String[] ALLOWED_FILTERS = {"asset_url"};

    private File appDir;
    private File htmlFile;
    private File scssFile;
    private File jsFile;
    private String content;
    private List<Exception> exceptions;
    @Override
    public void validate() {
        final ManifestContents manifest = new ManifestContents(prjDir);
        appDir = new File(prjDir, "app");
        try {
            Util.appDirValidator(appDir);
        }
        catch (FileNotFoundException ex) {
            throw new SdkException(ExitStatus.CMD_FAILED, "Files missing in app dir.");
        }
        catch (FAException ex) {
            throw new SdkException(ExitStatus.CMD_FAILED, ex.getMessage());
        }
        htmlFile = new File(appDir, AppFile.toString(AppFile.HTML));
        scssFile = new File(appDir, AppFile.toString(AppFile.SCSS));
        jsFile = new File(appDir, AppFile.toString(AppFile.JS));
        PlugContentUnifier unifier = new PlugContentUnifier(appDir, manifest, 
                          Collections.EMPTY_MAP, PlugExecutionContext.PACKAGE);
        exceptions = new ArrayList<>();
        try {
            content = unifier.getFileContent(scssFile);
        } catch (IOException e) {
            throw new SdkException(ExitStatus.CMD_FAILED, "Error reading file.");
        }
        Matcher m = Pattern.compile("\\{\\{(.*)\\}\\}").matcher(content);
        try {
            while (m.find()) {
                String[] liqKeyArr = m.group(1).split("\\|");
                if (liqKeyArr.length > 1) {
                    try {
                        checkFilters(liqKeyArr[1].trim());
                    }
                    catch (SdkException ex) {
                        exceptions.add(ex);
                    }
                } else {
                    try {
                        checkLiquids(liqKeyArr[0].trim());
                    }
                    catch (SdkException ex) {
                        exceptions.add(ex);
                    }
                }
            }
        }
        finally {
            if(!exceptions.isEmpty()) {
                StringBuffer exceptionList = new StringBuffer();
                exceptionList.append("SCSS Validations: \n")
                    .append("      ");
                for (int i = 0; i < exceptions.size(); i++ ) {
                    exceptionList.append(Character.toString((char) (i+97)))
                        .append(". ").append(exceptions.get(i).getMessage())
                        .append("\n      ");
                }
                throw new SdkException(ExitStatus.CMD_FAILED, exceptionList.toString());
            }
        }
    }

    public void checkFilters(String input) {
        if (!Arrays.asList(ALLOWED_FILTERS).contains(input)) {
            throw new SdkException(ExitStatus.CMD_FAILED,
                "Unsupported liquid filter in code: " + input);
        }
    }

    public void checkLiquids(String input) {
        if (!Arrays.asList(ALLOWED_LIQUIDS).contains(input)) {
            throw new SdkException(ExitStatus.CMD_FAILED,
                "Unsupported liquid in code: " + input);
        }
    }
}
