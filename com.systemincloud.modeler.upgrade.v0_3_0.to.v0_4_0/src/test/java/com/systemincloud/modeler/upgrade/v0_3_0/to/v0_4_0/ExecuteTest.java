package com.systemincloud.modeler.upgrade.v0_3_0.to.v0_4_0;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.systemincloud.modeler.upgrade.common.AbstractExecute;

public class ExecuteTest {

	@Test
	public void test() {
		try {
			String pom = IOUtils.toString(ExecuteTest.class.getResourceAsStream("pom.xml"), "UTF-8");
			String ret = AbstractExecute.updateDependencyVersion(pom, AbstractExecute.DEP_JAVA_API, "0.3.0");
			assertTrue(ret.contains("<version>0.3.0</version>"));
		} catch (IOException | TransformerException e) { fail(e.getMessage()); }
	}
}
