package com.systemincloud.modeler.upgrade.v0_4_1.to.v0_4_2;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;

import com.systemincloud.modeler.upgrade.common.AbstractExecute;
import com.systemincloud.modeler.upgrade.common.IExecute;

public class Execute extends AbstractExecute implements IExecute {

	@Override
	public boolean execute(File file) {
		String xml;
		try {
			xml = readFile(file);
			xml = super.executeOnSic(xml);

			xml = fixSkipFirst(xml);
			xml = removeGateStyle(xml);
			xml = removeSync(xml);
			xml = xml.replaceAll("BPMN-POLYGON-ARROW", "POLYGON-ARROW");
			//
			//
			//
			super.writeFile(file, xml);
		} catch (Exception e) { return false; }
		return true;
	}

	public String fixSkipFirst(String xml) throws TransformerException, IOException {
		return transform(xml, IOUtils.toString(Execute.class.getResourceAsStream("fix-skipFirst.xsl"), "UTF-8"), null);
    }
	
	public String removeGateStyle(String xml) throws TransformerException, IOException {
		return transform(xml, IOUtils.toString(Execute.class.getResourceAsStream("remove-style.xsl"), "UTF-8"), "name", "GATE");
    }
	
	public String removeSync(String xml) throws TransformerException, IOException {
		return transform(xml, IOUtils.toString(Execute.class.getResourceAsStream("remove-sync.xsl"), "UTF-8"), null);
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
		return "0.4.2";
	}
}
