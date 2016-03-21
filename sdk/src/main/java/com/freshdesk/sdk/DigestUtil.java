package com.freshdesk.sdk;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.wiztools.commons.HexEncodeUtil;

/**
 *
 * @author raghav
 */
public class DigestUtil {
    
    public static String getHashCodeForFiles(List<File> files, ManifestContents manifest) {
        DigestCompute dc = new DigestCompute();
        for (File f : files) {
            try(
                    RandomAccessFile aFile = new RandomAccessFile(f, "r");
                    FileChannel inChannel = aFile.getChannel();) {
                MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
                buffer.load();
                dc.update(buffer);
                buffer.clear();
            }
            catch (IOException ex) {
                throw new SdkException(ExitStatus.CMD_FAILED, "Error while generating Digest.");
            }   
        }
        return dc.getHexHash();
    }
    
    static class DigestCompute {
        
        private final MessageDigest md;
        
        DigestCompute() {
            try {
                md = MessageDigest.getInstance("MD5");
            }
            catch(NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        }
        
        void update(ByteBuffer bb) throws IOException {
            md.update(bb);
        }
        
        String getHexHash() {
            return HexEncodeUtil.bytesToHex(md.digest());
        }
    }
}
