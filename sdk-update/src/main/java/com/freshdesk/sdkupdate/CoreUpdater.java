package com.freshdesk.sdkupdate;

import org.wiztools.appupdate.Version;

import java.io.File;

/**
 * Created by raghu on 17/02/16.
 */
public class CoreUpdater extends AbstractUpdater {

    @Override
    public void update(String url) throws SdkUpdateException {
        System.out.println("New version availability check...");
        WsUtil wsu = new WsUtil(url);

        Version current = SdkUtil.getCurrentVersion(Constants.SDK_DIR);
        Version latest = wsu.getLatest();

        if(current.isLessThan(latest)) {
            System.out.println("New version available: " + latest + ". Downloading...");
            DownloadUtil dlu = new DownloadUtil(wsu.getDlUrl());
            rollbackables.add(dlu);

            File downloaded = dlu.download();
            System.out.println("File downloaded. Installing...");
            InstallUtil inu = new InstallUtil(downloaded, Constants.SDK_DIR, latest);
            rollbackables.add(inu);
            inu.install();

            System.out.println("Successfully installed.");
        }
        else {
            System.out.println("Already at latest version. Quitting...");
        }
    }
}
