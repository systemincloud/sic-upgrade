package com.systemincloud.modeler.upgrade.v0_4_1.to.v0_4_2;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class ExecuteTest {

	@Test
	public void testRemoveGateStyle1() throws IOException, TransformerException {
		String in = IOUtils.toString(ExecuteTest.class.getResourceAsStream("And.sict"));
		String out = new Execute().removeGateStyle(in);
		String expected = IOUtils.toString(ExecuteTest.class.getResourceAsStream("And.out.sict"));
		assertEquals(expected, out);
	}

	@Test
	public void testRemoveGateStyle2() throws IOException, TransformerException {
		String in = IOUtils.toString(ExecuteTest.class.getResourceAsStream("Simple.sict"));
		String out = new Execute().removeGateStyle(in);
		String expected = IOUtils.toString(ExecuteTest.class.getResourceAsStream("Simple.out.sict"));
		assertEquals(expected, out);
	}
	
	@Test
	public void testRemoveGateStyle3() throws IOException, TransformerException {
		String in = IOUtils.toString(ExecuteTest.class.getResourceAsStream("OnInputGenerator.sict"));
		String out = new Execute().removeGateStyle(in);
		String expected = IOUtils.toString(ExecuteTest.class.getResourceAsStream("OnInputGenerator.out.sict"));
		assertEquals(expected, out);
	}
}
