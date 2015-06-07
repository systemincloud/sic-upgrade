package com.systemincloud.modeler.upgrade.v0_5_3.to.v0_6_0;

import java.io.File;

import javax.xml.transform.TransformerException;

import com.systemincloud.modeler.upgrade.common.AbstractExecute;

public class Execute extends AbstractExecute {

	@Override
	public boolean execute(File file) {
		String xml;
		try {
			xml = readFile(file);
			xml = super.executeOnSic(xml);
			
			xml = updateTaskVerEmbeddedTask(xml, "0.3.0");
			xml = updateTaskVerJavaTask    (xml, "0.3.0");
			
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
		return "0.6.0";
	}
}
