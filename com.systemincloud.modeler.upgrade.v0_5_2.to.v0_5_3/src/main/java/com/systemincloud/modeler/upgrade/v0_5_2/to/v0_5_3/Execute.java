package com.systemincloud.modeler.upgrade.v0_5_2.to.v0_5_3;

import java.io.File;

import javax.xml.transform.TransformerException;

import com.systemincloud.ext.vip.modeler.upgrade.v0_3_3.to.v0_3_4.VipExecute;
import com.systemincloud.modeler.upgrade.common.AbstractExecute;

public class Execute extends AbstractExecute {

	@Override
	public boolean execute(File file) {
		String xml;
		try {
			xml = readFile(file);
			xml = super.executeOnSic(xml);

			xml = updateTaskVerConsole(xml, "0.1.4");
			
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
		return super.executeOnRoot(root);
	}

	@Override
	protected String executeOnPom(String pom) throws TransformerException {
		return pom;
	}

	@Override
	protected String getVersion() {
		return "0.5.3";
	}
}
