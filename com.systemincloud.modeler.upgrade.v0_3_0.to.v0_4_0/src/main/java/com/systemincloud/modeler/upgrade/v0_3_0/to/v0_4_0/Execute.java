package com.systemincloud.modeler.upgrade.v0_3_0.to.v0_4_0;

import java.io.File;

import javax.xml.transform.TransformerException;

import com.systemincloud.ext.vip.modeler.upgrade.v0_3_0.to.v0_3_1.VipExecute;
import com.systemincloud.modeler.upgrade.common.AbstractExecute;
import com.systemincloud.modeler.upgrade.common.IExecute;

public class Execute extends AbstractExecute implements IExecute {

	@Override
	public boolean execute(File file) {
		String xml;
		try { 
			xml = readFile(file);
			xml = super.executeOnSic(xml);
			xml = updateTaskVerConsole        (xml, "0.1.2");
			xml = updateTaskVerConstant       (xml, "0.2.1");
			xml = updateTaskVerEmbeddedTask   (xml, "0.2.2");
			xml = updateTaskVerJavaTask       (xml, "0.2.3");
			xml = updateTaskVerRandomGenerator(xml, "0.2.1");
			
			xml = executeTransform(this.getClass(), xml, "random-generator-remove-generator.xsl");
			
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
	public String executeOnPom(String pom) throws TransformerException {
		String ret = updateDependencyVersion(pom, DEP_JAVA_API, "0.3.0");
		ret = new VipExecute().executeOnPom(ret);
		return ret;
	}
	
	@Override
	protected String getVersion() {
		return "0.4.0";
	}
}
