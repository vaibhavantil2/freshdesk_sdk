package com.freshdesk.sdkupdate;

import org.wiztools.appupdate.Version;

import java.io.File;

/**
 * Created by raghu on 17/02/16.
 */
public class TmplUpdater extends AbstractUpdater {
    @Override
    public boolean update(String url) throws SdkUpdateException {
        System.out.println("New template version availability check...");
        WsUtil wsu = new WsUtil(url);

        System.out.println("Downloading template...");
        DownloadUtil dlu = new DownloadUtil(wsu.getDlUrl());
        rollbackables.add(dlu);

        File downloaded = dlu.download();
        System.out.println("Template downloaded. Installing...");
        TmplInstallUtil inu = new TmplInstallUtil(downloaded, Constants.TMPL_FILE);
        rollbackables.add(inu);
        inu.install();

        System.out.println("Template successfully installed.");
        return true;

    }
}
