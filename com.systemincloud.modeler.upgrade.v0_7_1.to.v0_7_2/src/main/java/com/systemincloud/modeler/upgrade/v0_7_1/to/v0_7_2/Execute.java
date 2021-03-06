package com.systemincloud.modeler.upgrade.v0_7_1.to.v0_7_2;

import java.io.File;

import com.systemincloud.modeler.upgrade.common.AbstractExecute;

public class Execute extends AbstractExecute {

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
    protected String getVersion() {
        return "0.7.2";
    }

    @Override
    protected String executeOnPom(String pom) {
        return pom;
    }
}
