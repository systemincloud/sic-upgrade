package com.systemincloud.modeler.upgrade.v0_4_0.to.v0_4_1;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import com.systemincloud.modeler.upgrade.common.AbstractExecute;
import com.systemincloud.modeler.upgrade.common.IExecute;
import com.systemincloud.ext.vip.modeler.upgrade.v0_3_1.to.v0_3_2.VipExecute;

public class Execute extends AbstractExecute implements IExecute {

	@Override
	public boolean execute(File file) {
		String xml;
		try {
			xml = readFile(file);
			xml = super.executeOnSic(xml);
			xml = updateTaskVerJavaTask(xml, "0.2.4");

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
		return super.executeOnRoot(root);
	}

	@Override
	public String executeOnPom(String pom) throws TransformerException {
		String ret = updateDependencyVersion(pom, DEP_JAVA_API, "0.3.1");
		return ret;
	}

	@Override
	protected String getVersion() {
		return "0.4.1";
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
		@Override public boolean visit(final NormalAnnotation node) {
			if(node.getTypeName().isSimpleName())
				if(node.getTypeName().toString().equals("SicParameters")) {

					final SingleMemberAnnotation sicParametersA = ast.newSingleMemberAnnotation();
					sicParametersA.setTypeName(ast.newSimpleName("SicParameters"));

					ListRewrite lrw = rewriter.getListRewrite(acu, CompilationUnit.IMPORTS_PROPERTY);
					ImportDeclaration id7 = ast.newImportDeclaration();
					id7.setName(ast.newName("com.systemincloud.modeler.tasks.javatask.api.annotations.SicParameter"));
					lrw.insertLast(id7, null);

					ArrayInitializer array = ast.newArrayInitializer();

					if(node.values().size() > 0) {
						Object o = node.values().get(0);
						if(o instanceof MemberValuePair) {
							Expression e1 = ((MemberValuePair) o).getValue();
							if(e1 instanceof ArrayInitializer) {
								for(Object e2 : ((ArrayInitializer) e1).expressions()) {
									NormalAnnotation sicParameterA = ast.newNormalAnnotation();
									sicParameterA.setTypeName(ast.newSimpleName("SicParameter"));

									MemberValuePair mvpName = ast.newMemberValuePair();
									mvpName.setName(ast.newSimpleName("name"));
									if(e2 instanceof QualifiedName) {
										SimpleName n  = ast.newSimpleName(((QualifiedName) e2).getName().getIdentifier());
										Name q = ast.newName(((QualifiedName) e2).getQualifier().toString());
										mvpName.setValue(ast.newQualifiedName(q, n));
									} else if(e2 instanceof StringLiteral) {
										StringLiteral sl = ast.newStringLiteral();
										sl.setLiteralValue(((StringLiteral) e2).getLiteralValue());
										mvpName.setValue(sl);
									}
									sicParameterA.values().add(mvpName);
									array.expressions().add(sicParameterA);
								}
							}
						}
					}

					sicParametersA.setValue(array);
					acu.accept(new ASTVisitor() { @Override public boolean visit(TypeDeclaration nodeType) {
						rewriter.getListRewrite(nodeType, nodeType.getModifiersProperty()).insertAt(sicParametersA, 1, null);
						rewriter.getListRewrite(nodeType, nodeType.getModifiersProperty()).remove(node, null);
						return false;
					}});
				}
			return false;
		}});

		TextEdit edits = rewriter.rewriteAST(doc, null);
		edits.apply(doc);
		return doc;
	}
}
