package com.systemincloud.modeler.upgrade.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Scanner;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;

public abstract class AbstractExecute implements IExecute {

	public boolean upgrade(String root) {
		boolean result = true;
		Collection<File> files = FileUtils.listFiles(new File(root), new RegexFileFilter("^(.*?(\\.sic|\\.sict))"), DirectoryFileFilter.DIRECTORY);
		for(File f : files)
			result = result | execute(f);
		result = result | execute(root);
		return result;
	}
	
	private static String mainTaskVersionXsl;
	private static String consoleVersionXsl;
	private static String constantVersionXsl;
	private static String demuxVersionXsl;
	private static String embeddedTaskVersionXsl;
	private static String andVersionXsl;
	private static String orVersionXsl;
	private static String xorVersionXsl;
	private static String notVersionXsl;
	private static String javaTaskVersionXsl;
	private static String muxVersionXsl;
	private static String randomGeneratorVersionXsl;
	private static String sipoVersionXsl;

	private static String updateDependencyVersionXsl;

	static {
		try {
			mainTaskVersionXsl         = IOUtils.toString(AbstractExecute.class.getResourceAsStream("main-task-version.xsl"), "UTF-8");

			consoleVersionXsl          = IOUtils.toString(AbstractExecute.class.getResourceAsStream("console-version.xsl"), "UTF-8");
			constantVersionXsl         = IOUtils.toString(AbstractExecute.class.getResourceAsStream("constant-version.xsl"), "UTF-8");
			demuxVersionXsl            = IOUtils.toString(AbstractExecute.class.getResourceAsStream("demux-version.xsl"), "UTF-8");
			embeddedTaskVersionXsl     = IOUtils.toString(AbstractExecute.class.getResourceAsStream("embedded-task-version.xsl"), "UTF-8");
			andVersionXsl              = IOUtils.toString(AbstractExecute.class.getResourceAsStream("and-version.xsl"), "UTF-8");
			orVersionXsl               = IOUtils.toString(AbstractExecute.class.getResourceAsStream("or-version.xsl"), "UTF-8");
			xorVersionXsl              = IOUtils.toString(AbstractExecute.class.getResourceAsStream("xor-version.xsl"), "UTF-8");
			notVersionXsl              = IOUtils.toString(AbstractExecute.class.getResourceAsStream("not-version.xsl"), "UTF-8");
			javaTaskVersionXsl         = IOUtils.toString(AbstractExecute.class.getResourceAsStream("java-task-version.xsl"), "UTF-8");
			muxVersionXsl              = IOUtils.toString(AbstractExecute.class.getResourceAsStream("mux-version.xsl"), "UTF-8");
			randomGeneratorVersionXsl  = IOUtils.toString(AbstractExecute.class.getResourceAsStream("random-generator-version.xsl"), "UTF-8");
			sipoVersionXsl             = IOUtils.toString(AbstractExecute.class.getResourceAsStream("sipo-version.xsl"), "UTF-8");

			updateDependencyVersionXsl = IOUtils.toString(AbstractExecute.class.getResourceAsStream("update-dependency-version.xsl"), "UTF-8");
		} catch (IOException e) { }
	}

	private static TransformerFactory factory = TransformerFactory.newInstance();

	protected boolean executeOnRoot(String root) {
		try {
			setProjectVersion(root, getVersion());

			String pom = getPom(root);
			pom = executeOnPom(pom);
			savePom(root, pom);

			updateJavaTasks(root);
		} catch (Exception e) { return false; }
		return true;
	}

	private void setProjectVersion(String root, String version) throws IOException {
		FileInputStream in = new FileInputStream(root + "/.settings/com.systemincloud.modeler.project.prefs");
		Properties prefs = new Properties();
		prefs.load(in);
		in.close();

		FileOutputStream out = new FileOutputStream(root + "/.settings/com.systemincloud.modeler.project.prefs");
		prefs.setProperty("modeler.version", version);
		prefs.store(out, null);
		out.close();
	}

	private String getPom(String root) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(root + "/pom.xml"));
		return new String(encoded, "UTF-8");
	}

	private void savePom(String root, String pom) throws IOException {
		FileUtils.writeStringToFile(new File(root + "/pom.xml"), pom);
	}

	private void updateJavaTasks(String root) {
		Collection<File> files = FileUtils.listFiles(new File(root),
				                                     new RegexFileFilter("^.*\\.java$"),
				                                     DirectoryFileFilter.DIRECTORY);
		try {
			for(File f :files) {
				Scanner scanner = new Scanner(f);
				while(scanner.hasNextLine()){
					if(scanner.nextLine().trim().contains("@JavaTaskInfo")) {
						Document document = new Document(FileUtils.readFileToString(f));
				    	Document ret      = updateJavaTask(document);
				    	if(ret != null) FileUtils.write(f, ret.get());
				        break;
				     }
				}
				scanner.close();
			}
		} catch (IOException | JavaModelException | IllegalArgumentException | MalformedTreeException | BadLocationException e) {
			e.printStackTrace();
		}
	}

	protected Document updateJavaTask(Document document) throws IOException, JavaModelException, IllegalArgumentException, MalformedTreeException, BadLocationException { return null; }

	protected CompilationUnit getCompilationUnit(Document document) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    parser.setSource(document.get().toCharArray());
	    parser.setResolveBindings(true);

        return (CompilationUnit) parser.createAST(null); // parse
	}

	public static String updateTaskVerConsole        (String xml, String version) throws TransformerException { return transform(xml, consoleVersionXsl,         "version", version); }
	public static String updateTaskVerConstant       (String xml, String version) throws TransformerException { return transform(xml, constantVersionXsl,        "version", version); }
	public static String updateTaskVerDemux          (String xml, String version) throws TransformerException { return transform(xml, demuxVersionXsl,           "version", version); }
	public static String updateTaskVerEmbeddedTask   (String xml, String version) throws TransformerException { return transform(xml, embeddedTaskVersionXsl,    "version", version); }
	public static String updateTaskVerAnd            (String xml, String version) throws TransformerException { return transform(xml, andVersionXsl,             "version", version); }
	public static String updateTaskVerOr             (String xml, String version) throws TransformerException { return transform(xml, orVersionXsl,              "version", version); }
	public static String updateTaskVerXor            (String xml, String version) throws TransformerException { return transform(xml, xorVersionXsl,             "version", version); }
	public static String updateTaskVerNot            (String xml, String version) throws TransformerException { return transform(xml, notVersionXsl,             "version", version); }
	public static String updateTaskVerJavaTask       (String xml, String version) throws TransformerException { return transform(xml, javaTaskVersionXsl,        "version", version); }
	public static String updateTaskVerMux            (String xml, String version) throws TransformerException { return transform(xml, muxVersionXsl,             "version", version); }
	public static String updateTaskVerRandomGenerator(String xml, String version) throws TransformerException { return transform(xml, randomGeneratorVersionXsl, "version", version); }
	public static String updateTaskVerSipo           (String xml, String version) throws TransformerException { return transform(xml, sipoVersionXsl,            "version", version); }

	public static String updateDependencyVersion     (String pom, String dependency, final String version) throws TransformerException {
		final String groupId    = dependency.substring(0, dependency.indexOf(":"));
		final String artifactId = dependency.substring(dependency.indexOf(":") + 1, dependency.length());
		return transform(pom, updateDependencyVersionXsl, new HashMap<String, String>() { private static final long serialVersionUID = 1L;
			{
				put("groupId",    groupId);
				put("artifactId", artifactId);
				put("version",    version);
			}
		});
	}

	protected String executeOnSic(String xml) throws TransformerException {
		String result = transform(xml, mainTaskVersionXsl, "version", getVersion());
		return result;
	}

	public static String transform(String xml, String xsl, final String key, final String value) throws TransformerException {
		return transform(xml, xsl, new HashMap<String, String>() { private static final long serialVersionUID = 1L;
			{ put(key, value); }
		});
	}

	public static String transform(String xml, String xsl, Map<String, String> params) throws TransformerException {
		String resultXml = xml;
		StringWriter writer = new StringWriter();
		Source xmlDoc = new StreamSource(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
		Source xslDoc = new StreamSource(new ByteArrayInputStream(xsl.getBytes(StandardCharsets.UTF_8)));
		Result result = new StreamResult(writer);
		Transformer trans = factory.newTransformer(xslDoc);
		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		if(params != null)
			for(Entry<String, String> es : params.entrySet())
				trans.setParameter(es.getKey(), es.getValue());
		trans.transform(xmlDoc, result);
		resultXml = writer.toString();
		return resultXml;
	}

	public static String transform2(String xml, String xsl, final String key, final String value) throws SaxonApiException {
		return transform2(xml, xsl, new HashMap<String, String>() { private static final long serialVersionUID = 1L;
			{ put(key, value); }
		});
	}
	
	public static String transform2(String xml, String xsl, Map<String, String> params) throws SaxonApiException {
        Processor      proc   = new Processor(false);
        XsltCompiler   comp   = proc.newXsltCompiler();
        XsltExecutable exp    = comp.compile(new StreamSource(new StringReader(xsl)));
        XdmNode        source = proc.newDocumentBuilder().build(new StreamSource(new StringReader(xml)));
        Serializer     out    = proc.newSerializer();
        out.setOutputProperty(Serializer.Property.METHOD, "text");
        XsltTransformer t  = exp.load();
        StringWriter    sw = new StringWriter();
        out.setOutputWriter(sw);
        
		if(params != null)
			for(Entry<String, String> es : params.entrySet())
				t.setParameter(new QName(es.getKey()), new XdmAtomicValue(es.getValue()));
        
		t.setInitialContextNode(source);
        t.setDestination(out);
        t.transform();
        
		return sw.toString();
	}
	
	protected static String readFile(File file) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(file.toURI()));
		return new String(encoded, Charset.defaultCharset());
	}

	protected static void writeFile(File file, String content) {
		try {
			FileWriter fileWriter = new FileWriter(file, false);
			fileWriter.write(content);
			fileWriter.close();
		} catch (IOException e) { }
	}

	protected abstract String getVersion();
	protected abstract String executeOnPom(String pom) throws TransformerException;

	public static String executeTransform(Class<?> clazz, String xml, String xslName) throws TransformerException {
		return executeTransform(clazz, xml, xslName, null);
	}

	public static String executeTransform(Class<?> clazz, String xml, String xslName, Map<String, String> params) throws TransformerException {
		String xsl = null;
		try {
			xsl = IOUtils.toString(clazz.getResourceAsStream(xslName), "UTF-8");
		} catch (IOException e) { }
		return transform(xml, xsl, null);
	}

	public static final String DEP_JAVA_API = "com.systemincloud.modeler.tasks.javatask:com.systemincloud.modeler.tasks.javatask.api";
}
