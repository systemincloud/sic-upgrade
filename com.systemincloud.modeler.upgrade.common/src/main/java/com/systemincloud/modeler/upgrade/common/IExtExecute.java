package com.systemincloud.modeler.upgrade.common;

import javax.xml.transform.TransformerException;

public interface IExtExecute {
	boolean executeOnRoot(String root);
	String  executeOnFile(String xml);
	String  executeOnPom (String pom) throws TransformerException;
}
