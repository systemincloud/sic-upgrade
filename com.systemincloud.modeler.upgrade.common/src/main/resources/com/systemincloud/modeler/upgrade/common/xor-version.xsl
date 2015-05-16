<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns:xmi="http://www.omg.org/XMI" 
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
                xmlns:al="http://eclipse.org/graphiti/mm/algorithms" 
                xmlns:dt="http://systemincloud.com/dt" 
                xmlns:pi="http://eclipse.org/graphiti/mm/pictograms" 
                xmlns:sic="http://systemincloud.com/sic"
                xmlns:xor_0_1_0="http://modeler.systemincloud.com/tasks/xor/0_1_0">
    <xsl:param name="version"/>

	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>
	<xsl:template match="@version[parent::task[@xsi:type='xor_0_1_0:Xor']]">
		<xsl:attribute name="version">
    		<xsl:value-of select="$version" />
  		</xsl:attribute>
	</xsl:template>
</xsl:stylesheet>