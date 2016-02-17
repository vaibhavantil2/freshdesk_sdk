package com.freshdesk.sdkupdate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Created by raghu on 17/02/16.
 */
public class TmplInstallUtil implements Rollbackable {

        private final File destDir;
        private final File source;

        private final File tmplFile = new File(Constants.FRSH_HOME + "/template/plug-template.zip");
        private final File bakTmplFile = new File(Constants.FRSH_HOME + "/template/plug-template.zip.bak");

        public TmplInstallUtil(File source, File destDir) {
                this.destDir = destDir;
                this.source = source;
        }

        public void install(){
                try{
                        Files.move(tmplFile.toPath(),
                                bakTmplFile.toPath(),
                                StandardCopyOption.REPLACE_EXISTING);
                        Files.move(source.toPath(),
                                destDir.toPath(),
                                StandardCopyOption.REPLACE_EXISTING);
                }
                catch (IOException ex){
                     throw new SdkUpdateException(ex);
                }
        }

        @Override
        public void rollback() {
            try{
                Files.move(bakTmplFile.toPath(),
                        tmplFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException ex)
            {
                    System.err.println("Rollback failed: moving .bak configs failed!");
                    ex.printStackTrace(System.err);
            }

        }
}
