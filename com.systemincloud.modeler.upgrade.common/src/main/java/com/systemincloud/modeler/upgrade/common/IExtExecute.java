package com.systemincloud.modeler.upgrade.common;

import net.sf.saxon.s9api.SaxonApiException;

public interface IExtExecute {
    boolean executeOnRoot(String root);
    String  executeOnFile(String xml);
    String  executeOnPom (String pom) throws SaxonApiException;
}
