package com.systemincloud.modeler.upgrade.v0_2_0.to.v0_2_1;

import java.io.File;

import com.systemincloud.modeler.upgrade.common.AbstractExecute;
import com.systemincloud.modeler.upgrade.common.IExecute;

public class Execute extends AbstractExecute implements IExecute {

	@Override
	public boolean execute(File file) {
		String xml;
		try { 
			xml = readFile(file);
			xml = super.executeOnSic(xml);
			//
			//
			//
			super.writeFile(file, xml);
		} catch (Exception e) { return false; }
		return true;
	}
	
	@Override
	public boolean execute(String root) {
		return super.executeOnRoot(root);
	}
	
	@Override
	protected String getVersion() {
		return "0.2.1";
	}
}
