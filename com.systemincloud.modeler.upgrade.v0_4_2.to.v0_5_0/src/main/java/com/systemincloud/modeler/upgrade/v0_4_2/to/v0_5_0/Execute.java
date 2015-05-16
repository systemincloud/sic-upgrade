package com.systemincloud.modeler.upgrade.v0_4_2.to.v0_5_0;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.systemincloud.ext.vip.modeler.upgrade.v0_3_2.to.v0_3_3.VipExecute;
import com.systemincloud.modeler.upgrade.common.AbstractExecute;
import com.systemincloud.modeler.upgrade.common.IExecute;

public class Execute extends AbstractExecute implements IExecute {

	@Override
	public boolean execute(File file) {
		String xml;
		try {
			xml = readFile(file);
			xml = super.executeOnSic(xml);
			xml = updateTaskVerConsole        (xml, "0.1.3");
			xml = updateTaskVerConstant       (xml, "0.2.2");
			xml = updateTaskVerDemux          (xml, "0.1.1");
			xml = updateTaskVerEmbeddedTask   (xml, "0.2.3");
			xml = updateTaskVerAnd            (xml, "0.1.1");
			xml = updateTaskVerOr             (xml, "0.1.1");
			xml = updateTaskVerXor            (xml, "0.1.1");
			xml = updateTaskVerNot            (xml, "0.1.1");
			xml = updateTaskVerJavaTask       (xml, "0.2.5");
			xml = updateTaskVerMux            (xml, "0.1.1");
			xml = updateTaskVerRandomGenerator(xml, "0.2.2");
			xml = updateTaskVerSipo           (xml, "0.1.1");

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
		boolean success = (new File(root + ".externalToolBuilders")).mkdirs();
		if(!success) {
			try {
				InputStream is = Execute.class.getResourceAsStream("org.eclipse.m2e.core.maven2Builder.launch");
				File targetFile = new File(root + ".externalToolBuilders/org.eclipse.m2e.core.maven2Builder.launch");
				FileUtils.copyInputStreamToFile(is, targetFile);
				
				File projectFile = new File(root + ".project");
				String in = IOUtils.toString(new FileInputStream(projectFile));
				String out = transform(in, IOUtils.toString(Execute.class.getResourceAsStream("external-builder.xsl"), "UTF-8"), null);
				FileUtils.write(projectFile, out);
			} catch (IOException | TransformerException e) { }
		}
		return super.executeOnRoot(root);
	}

	@Override
	protected String executeOnPom(String pom) throws TransformerException {
		return pom;
	}

	@Override
	protected String getVersion() {
		return "0.5.0";
	}
}
