package com.systemincloud.modeler.upgrade.v0_8_0.to.v0_9_0;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.systemincloud.ext.vip.modeler.upgrade.v0_3_6.to.v0_3_7.VipExecute;
import com.systemincloud.modeler.upgrade.common.AbstractExecute;

import net.sf.saxon.s9api.SaxonApiException;

public class Execute extends AbstractExecute {

    @Override
    public boolean execute(File file) {
        String xml;
        try {
            xml = readFile(file);
            xml = super.executeOnSic(xml);

            xml = updateTaskVerConsole        (xml, "0.2.0");
            xml = updateTaskVerConstant       (xml, "0.3.0");
            xml = updateTaskVerDemux          (xml, "0.4.0");
            xml = updateTaskVerEmbeddedTask   (xml, "0.4.0");
            xml = updateTaskVerAnd            (xml, "0.2.0");
            xml = updateTaskVerNot            (xml, "0.2.0");
            xml = updateTaskVerOr             (xml, "0.2.0");
            xml = updateTaskVerXor            (xml, "0.2.0");
            xml = updateTaskVerInspect        (xml, "0.2.0");
            xml = updateTaskVerJavaTask       (xml, "0.5.0");
            xml = updateTaskVerMux            (xml, "0.3.0");
            xml = updateTaskVerPythonTask     (xml, "0.2.0");
            xml = updateTaskVerRandomGenerator(xml, "0.3.0");
            xml = updateTaskVerSipo           (xml, "0.2.0");

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
        try {
            String pyproject = IOUtils.toString(Execute.class.getResourceAsStream(".pydevproject"));
            pyproject = new VipExecute().executeOnPyDevProjectFile(pyproject);
            createFile(root, "src/main/r/PLACEHOLDER_FOR_R", "");
            createFile(root, "src/test/r/PLACEHOLDER_FOR_R", "");

        } catch (IOException e) { }
        return super.executeOnRoot(root);
    }

    @Override
    protected String getVersion() {
        return "0.9.0";
    }

    @Override
    public String executeOnProjectFile(String pfile) throws SaxonApiException {
        String ret;
        ret = addProjectNature(pfile, "de.walware.statet.base.StatetNature");
        ret = addProjectNature(ret, "de.walware.statet.r.RNature");
        ret = addProjectNature(ret, "de.walware.docmlet.wikitext.natures.Wikitext");
        ret = addBuildCommand(ret, "de.walware.docmlet.wikitext.builders.Wikitext");

        return ret;
    }

    @Override
    public String executeOnClassPathFile(String file) throws SaxonApiException {
        String ret;
        ret = addClasspathEntry(file, "src/test/resources", "src", "src/main/r", null);
        ret = addClasspathEntry(ret,  "src/test/resources", "src", "src/test/r", null);
        return ret;
    }

    @Override
    public String executeOnPom(String pom) throws SaxonApiException {
        String ret;
        ret = updateDependencyVersion(pom, DEP_JAVA_API, "0.7.0");
        ret = updateDependencyVersion(ret, DEP_PYTHON_API, "0.2.0");

        ret = new VipExecute().executeOnPom(ret);
        return ret;
    }

    @Override
    public String executeOnPyDevProject(String file) throws SaxonApiException {
    	String result = file;
		try {
			String xsl = IOUtils.toString(AbstractExecute.class.getResourceAsStream("update-pythontask-process-version.xsl"), "UTF-8");
	        result = transform2(file, xsl, "version", "0.2.0");
	        result = new VipExecute().executeOnPom(result);
		} catch (IOException e) {
		}

        return result;
    }
}
