package com.systemincloud.modeler.upgrade.v0_5_2.to.v0_5_3;

import java.io.File;

import javax.xml.transform.TransformerException;

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
	protected String executeOnPom(String pom) throws TransformerException {
		return pom;
	}

	@Override
	protected String getVersion() {
		return "0.5.3";
	}
}
