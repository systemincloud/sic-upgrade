package com.systemincloud.modeler.upgrade.v0_2_3.to.v0_3_0;

import java.io.File;

import com.systemincloud.ext.vip.modeler.upgrade.v0_2_0.to.v0_3_0.VipExecute;
import com.systemincloud.modeler.upgrade.common.AbstractExecute;
import com.systemincloud.modeler.upgrade.common.IExecute;

public class Execute extends AbstractExecute implements IExecute {

	@Override
	public boolean execute(File file) {
		String xml;
		try { 
			xml = readFile(file);
			xml = super.executeOnSic(xml);
			xml = new VipExecute().executeOnFile(xml);
			//
			//
			//
			super.writeFile(file, xml);
		} catch (Exception e) { return false; }
		return true;
	}
	
	@Override
	public boolean execute(String root) {
		if(!new VipExecute().executeOnRoot(root)) return false;
		return super.executeOnRoot(root);
	}
	
	@Override
	protected String getVersion() {
		return "0.3.0";
	}
}
