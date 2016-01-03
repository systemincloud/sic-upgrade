package com.systemincloud.modeler.upgrade.v0_7_2.to.v0_8_0;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.systemincloud.modeler.upgrade.common.AbstractExecute;

import net.sf.saxon.s9api.SaxonApiException;

public class Execute extends AbstractExecute {

    @Override
    public boolean execute(File file) {
        String xml;
        try {
            xml = readFile(file);
            xml = super.executeOnSic(xml);

            xml = updateTaskVerConsole        (xml, "0.1.8");
            xml = updateTaskVerConstant       (xml, "0.2.6");
            xml = updateTaskVerDemux          (xml, "0.3.1");
            xml = updateTaskVerEmbeddedTask   (xml, "0.3.2");
            xml = updateTaskVerAnd            (xml, "0.1.5");
            xml = updateTaskVerNot            (xml, "0.1.5");
            xml = updateTaskVerOr             (xml, "0.1.5");
            xml = updateTaskVerXor            (xml, "0.1.5");
            xml = updateTaskVerInspect        (xml, "0.1.3");
            xml = updateTaskVerJavaTask       (xml, "0.4.1");
            xml = updateTaskVerMux            (xml, "0.2.1");
            xml = updateTaskVerRandomGenerator(xml, "0.2.6");
            xml = updateTaskVerSipo           (xml, "0.1.6");
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
            FileUtils.writeStringToFile(new File(root + "/.pydevproject"), IOUtils.toString(Execute.class.getResourceAsStream(".pydevproject")));
            createFile(root, "src/main/py/PLACEHOLDER_FOR_PY", "");
            createFile(root, "src/test/py/PLACEHOLDER_FOR_PY", "");
        } catch (IOException e) { }
        return super.executeOnRoot(root);
    }

    @Override
    protected String getVersion() {
        return "0.8.0";
    }

    @Override
    public String executeOnProjectFile(String pfile) throws SaxonApiException {
        String ret;
        ret = addProjectNature(pfile, "org.python.pydev.pythonNature");
        ret = addBuildCommand(ret, "org.python.pydev.PyDevBuilder");
        return ret;
    }

    @Override
    public String executeOnClassPathFile(String file) throws SaxonApiException {
        String ret;
        ret = addClasspathEntry(file, "src/test/java", "src", "src/main/py", "**/__pycache__/**");
        ret = addClasspathEntry(ret,  "src/main/py",   "src", "src/test/py", "**/__pycache__/**");
        return ret;
    }

    @Override
    public String executeOnPom(String pom) throws SaxonApiException {
        String ret;
        ret = updateDependencyVersion(pom, DEP_JAVA_API, "0.6.0");
        ret = addDependency(ret, DEP_PYTHON_API, "0.1.0");
        return ret;
    }
}
