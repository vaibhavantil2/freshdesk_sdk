package com.freshdesk.sdk;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.wiztools.commons.FileUtil;

/**
 *
 * @author raghav
 */
public class DigestUtil {

    private static String getHashCode(InputStream is) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String content = getStringFromInputStream(is);
            byte[] array = md.digest(content.getBytes()); 
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new SdkException(ExitStatus.CMD_FAILED, "Unable to generate hash.");
        }
    }

    public static String getHashCodeForFiles(List<File> files, ManifestContents manifest) {
        StringBuilder content = new StringBuilder();
        try {
            for (File f : files) {
                content.append(FileUtil.getContentAsString(f, manifest.getCharset()));
            }
            return getHashCode(new ByteArrayInputStream(content.toString().getBytes()));
        }
        catch (IOException ex) {
            throw new FAException("Error while generating Digest.");
        }
    }
    
    private static String getStringFromInputStream(InputStream is) {
        try {
            StringBuilder out =new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String newLine = System.getProperty("line.separator");
            String line;
            while((line = br.readLine()) != null) {
                out.append(line);
                out.append(newLine);
            }
            // remove the last newline element.
            out.deleteCharAt(out.toString().length() - 1);
            return out.toString();
        } catch (IOException ex) {
            throw new FAException("Error while reading content from file.");
        }
    }

}
