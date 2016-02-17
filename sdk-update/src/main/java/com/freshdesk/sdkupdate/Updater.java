package com.freshdesk.sdkupdate;

import java.util.List;

/**
 * Created by raghu on 17/02/16.
 */
public interface Updater {
	void setRollbackables(List<Rollbackable> r);
	void setCleanables(List<Cleanable> c);
	void update();
}
