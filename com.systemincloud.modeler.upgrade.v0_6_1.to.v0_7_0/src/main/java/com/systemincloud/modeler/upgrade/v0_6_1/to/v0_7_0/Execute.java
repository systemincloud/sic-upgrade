package com.systemincloud.modeler.upgrade.v0_6_1.to.v0_7_0;

import java.io.File;

import javax.xml.transform.TransformerException;

import com.systemincloud.ext.vip.modeler.upgrade.v0_3_5.to.v0_3_6.VipExecute;
import com.systemincloud.modeler.upgrade.common.AbstractExecute;

public class Execute extends AbstractExecute {

	@Override
	public boolean execute(File file) {
		String xml;
		try {
			xml = readFile(file);
			xml = super.executeOnSic(xml);
			
			xml = updateTaskVerConsole        (xml, "0.1.6");
			xml = updateTaskVerConstant       (xml, "0.2.5");
			xml = updateTaskVerDemux          (xml, "0.2.0");
			xml = updateTaskVerEmbeddedTask   (xml, "0.3.1");
			xml = updateTaskVerAnd            (xml, "0.1.4");
			xml = updateTaskVerNot            (xml, "0.1.4");
			xml = updateTaskVerXor            (xml, "0.1.4");
			xml = updateTaskVerOr             (xml, "0.1.4");
			xml = updateTaskVerInspect        (xml, "0.1.2");
			xml = updateTaskVerJavaTask       (xml, "0.4.0");
			xml = updateTaskVerMux            (xml, "0.2.0");
			xml = updateTaskVerRandomGenerator(xml, "0.2.5");
			xml = updateTaskVerSipo           (xml, "0.1.4");
			
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
		return updateDependencyVersion(pom, "com.systemincloud.modeler.tasks.javatask"
				                         + ":com.systemincloud.modeler.tasks.javatask.api", 
				                            "0.5.0");
	}

	@Override
	protected String getVersion() {
		return "0.7.0";
	}
}
