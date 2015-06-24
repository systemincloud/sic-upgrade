package com.systemincloud.modeler.upgrade.v0_6_1.to.v0_7_0;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import com.systemincloud.ext.vip.modeler.upgrade.v0_3_5.to.v0_3_6.VipExecute;
import com.systemincloud.modeler.upgrade.common.AbstractExecute;

public class Execute extends AbstractExecute {

	@Override
	public boolean execute(File file) {
		String xml;
		try {
			xml = readFile(file);
			xml = super.executeOnSic(xml);
			
			xml = updateTaskVerConsole        (xml, "0.1.6");
			xml = updateTaskVerConstant       (xml, "0.2.5");
			xml = updateTaskVerDemux          (xml, "0.2.0");
			xml = updateTaskVerEmbeddedTask   (xml, "0.3.1");
			xml = updateTaskVerAnd            (xml, "0.1.4");
			xml = updateTaskVerNot            (xml, "0.1.4");
			xml = updateTaskVerXor            (xml, "0.1.4");
			xml = updateTaskVerOr             (xml, "0.1.4");
			xml = updateTaskVerInspect        (xml, "0.1.2");
			xml = updateTaskVerJavaTask       (xml, "0.4.0");
			xml = updateTaskVerMux            (xml, "0.2.0");
			xml = updateTaskVerRandomGenerator(xml, "0.2.5");
			xml = updateTaskVerSipo           (xml, "0.1.4");
			
			xml = muxAddGroup(xml);
			xml = demuxAddGroup(xml);
			
			xml = new VipExecute().executeOnFile(xml);
			//
			//
			//
			super.writeFile(file, xml);
		} catch (Exception e) { return false; }
		return true;
	}
	
	public String muxAddGroup(String xml) throws TransformerException, IOException {
		return transform(xml, IOUtils.toString(Execute.class.getResourceAsStream("mux-group.xsl"), "UTF-8"), null);
	}
	
	public String demuxAddGroup(String xml) throws TransformerException, IOException {
		return transform(xml, IOUtils.toString(Execute.class.getResourceAsStream("demux-group.xsl"), "UTF-8"), null);
	}
	
	@Override
	public boolean execute(String root) {
		return super.executeOnRoot(root);
	}

	@Override
	protected String executeOnPom(String pom) throws TransformerException {
		return updateDependencyVersion(pom, "com.systemincloud.modeler.tasks.javatask"
				                         + ":com.systemincloud.modeler.tasks.javatask.api", 
				                            "0.5.0");
	}

	@Override
	protected String getVersion() {
		return "0.7.0";
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected Document updateJavaTask(Document doc) throws IOException,
	                                                       JavaModelException,
	                                                       IllegalArgumentException,
	                                                       MalformedTreeException,
	                                                       BadLocationException {
		final CompilationUnit acu = getCompilationUnit(doc);
		if(acu == null) return null;

		final AST        ast      = acu.getAST();
		final ASTRewrite rewriter = ASTRewrite.create(ast);

		acu.recordModifications();

		acu.accept(new ASTVisitor() {
			@Override public boolean visit(final MethodDeclaration node) {
				if(node.getName().getIdentifier().equals("execute")) {
					SingleVariableDeclaration svd = ast.newSingleVariableDeclaration();
					svd.setType(ast.newPrimitiveType(PrimitiveType.INT));
					svd.setName(ast.newSimpleName("grp"));
					node.parameters().add(svd);
					return false;
				}
				return false;
		}});

		try{
		TextEdit edits = rewriter.rewriteAST(doc, null);
		edits.apply(doc);
		} catch(Exception e) { 
			e.getMessage();
		}
		return doc;
	}
}
