package com.systemincloud.modeler.upgrade.v0_4_0.to.v0_4_1;

import static org.junit.Assert.fail;

import java.io.IOException;

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
			Document document = new Document(IOUtils.toString(ExecuteTest.class.getResourceAsStream("Parameters")));
			Document ret = new Execute().updateJavaTask(document);
			System.out.println(ret.get());
		} catch (IOException | JavaModelException | IllegalArgumentException | MalformedTreeException | BadLocationException e) { fail(e.getMessage()); }
	}
}
