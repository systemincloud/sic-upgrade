package com.systemincloud.modeler.upgrade.v0_6_1.to.v0_7_0;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.junit.Test;

public class ExecuteTest {

	@Test
	public void test() {
		try {
			Document document = new Document(IOUtils.toString(ExecuteTest.class.getResourceAsStream("NAtomic1SIn")));
			Document ret      = new Execute().updateJavaTask(document);
			System.out.println(ret.get());
		} catch (IOException | JavaModelException | IllegalArgumentException | MalformedTreeException | BadLocationException e) { fail(e.getMessage()); }
	}
	
	@Test
	public void testMuxGroup() throws IOException, TransformerException {
		String in = IOUtils.toString(ExecuteTest.class.getResourceAsStream("Simple.mux.sic"));
		String out = new Execute().muxAddGroup(in);
		String expected = IOUtils.toString(ExecuteTest.class.getResourceAsStream("Simple.mux.out.sic"));
		assertEquals(expected, out);
	}
	
	@Test
	public void testDemuxGroup() throws IOException, TransformerException {
		String in = IOUtils.toString(ExecuteTest.class.getResourceAsStream("Simple.demux.sic"));
		String out = new Execute().demuxAddGroup(in);
		String expected = IOUtils.toString(ExecuteTest.class.getResourceAsStream("Simple.demux.out.sic"));
		assertEquals(expected, out);
	}
}
