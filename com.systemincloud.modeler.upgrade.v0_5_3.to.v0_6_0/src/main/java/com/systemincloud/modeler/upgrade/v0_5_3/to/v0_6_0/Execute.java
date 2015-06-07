package com.systemincloud.modeler.upgrade.v0_5_3.to.v0_6_0;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import com.systemincloud.ext.vip.modeler.upgrade.v0_3_4.to.v0_3_5.VipExecute;
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
			
			xml = addOnlyLocalAttribute(xml);
			
			xml = new VipExecute().executeOnFile(xml);
			//
			//
			//
			super.writeFile(file, xml);
		} catch (Exception e) { return false; }
		return true;
	}

	public String addOnlyLocalAttribute(String xml) throws TransformerException, IOException {
		return transform(xml, IOUtils.toString(Execute.class.getResourceAsStream("only-local.xsl"), "UTF-8"), null);
	}

	@Override
	public boolean execute(String root) {
		super.executeOnRoot(root);
		Collection<File> files = FileUtils.listFiles(new File(root), new RegexFileFilter("^(.*?(\\.sict))"), DirectoryFileFilter.DIRECTORY);
		for(File f : files) {
			String p = f.getAbsolutePath();
			f.renameTo(new File(p.substring(0, p.length() - 5) + ".sic"));
		}
		return true;
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
