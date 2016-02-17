package com.freshdesk.sdkupdate;

import org.wiztools.appupdate.Version;
import java.io.File;

/**
 * Created by raghu on 17/02/16.
 */
public class TmplUpdater extends AbstractUpdater {
	@Override
	public void update() throws SdkUpdateException {
		System.out.println("New version availability check...");
		WsUtil wsu = new WsUtil(Constants.VER_WS_ENDPT);

		System.out.println("Downloading Template...");
		DownloadUtil dlu = new DownloadUtil(wsu.getDlUrl());
		rollbackables.add(dlu);

		File downloaded = dlu.download();
		System.out.println("Template downloaded. Installing...");
		TmplInstallUtil inu = new TmplInstallUtil(downloaded, Constants.TMPL_DIR);
		rollbackables.add(inu);
		inu.install();

		System.out.println("Successfully installed.");

	}
}
