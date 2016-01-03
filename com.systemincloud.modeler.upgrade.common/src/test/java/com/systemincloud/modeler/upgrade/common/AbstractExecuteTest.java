package com.systemincloud.modeler.upgrade.common;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import net.sf.saxon.s9api.SaxonApiException;

public class AbstractExecuteTest {

    @Test
    public void addDependencyTest() {
        try {
            AbstractExecute.addDependency(IOUtils.toString(AbstractExecuteTest.class.getResourceAsStream("pom.xml")), AbstractExecute.DEP_PYTHON_API, "0.1.0");
        } catch (SaxonApiException | IOException e) {
            fail();
        }
    }

}
