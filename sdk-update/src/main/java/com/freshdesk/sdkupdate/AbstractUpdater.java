package com.freshdesk.sdkupdate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghu on 17/02/16.
 */
public abstract class AbstractUpdater implements Updater {

	protected List<Rollbackable> rollbackables = new ArrayList<>();
	protected List<Cleanable> cleanables = new ArrayList<>();

	@Override
	public void setRollbackables(List<Rollbackable> r) {
		this.rollbackables = r;
	}

	@Override
	public void setCleanables(List<Cleanable> c) {
		this.cleanables = c;
	}
}
