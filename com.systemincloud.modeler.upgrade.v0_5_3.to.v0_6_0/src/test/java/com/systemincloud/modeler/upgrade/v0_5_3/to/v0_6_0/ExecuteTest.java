package com.systemincloud.modeler.upgrade.v0_5_3.to.v0_6_0;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class ExecuteTest {

	@Test
	public void testAddOnlyLocalAttribute() throws IOException, TransformerException {
		String in = IOUtils.toString(ExecuteTest.class.getResourceAsStream("Cascade.sict"));
		String out = new Execute().addOnlyLocalAttribute(in);
		String expected = IOUtils.toString(ExecuteTest.class.getResourceAsStream("Cascade.out.sict"));
		assertEquals(expected, out);
	}
}
