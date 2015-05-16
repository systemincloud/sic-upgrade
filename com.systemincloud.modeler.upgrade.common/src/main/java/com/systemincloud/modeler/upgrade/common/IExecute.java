package com.systemincloud.modeler.upgrade.common;

import java.io.File;

public interface IExecute {
	boolean execute(String root);
	boolean execute(File file);
}
