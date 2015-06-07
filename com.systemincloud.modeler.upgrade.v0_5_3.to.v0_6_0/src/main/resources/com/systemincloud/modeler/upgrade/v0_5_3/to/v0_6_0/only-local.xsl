<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns:xmi="http://www.omg.org/XMI" 
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
                xmlns:al="http://eclipse.org/graphiti/mm/algorithms" 
                xmlns:dt="http://systemincloud.com/dt" 
                xmlns:pi="http://eclipse.org/graphiti/mm/pictograms" 
                xmlns:sic="http://systemincloud.com/sic">

	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>
	<xsl:template match="task[@bundleName='com.systemincloud.modeler.tasks.console' and not(@onlyLocal)]">
		<xsl:attribute name="onlyLocal">
    		<xsl:value-of select="true" />
  		</xsl:attribute>
	</xsl:template>
	<xsl:template match="task[@bundleName='com.systemincloud.modeler.tasks.inspect' and not(@onlyLocal)]">
		<xsl:attribute name="onlyLocal">
    		<xsl:value-of select="true" />
  		</xsl:attribute>
	</xsl:template>
</xsl:stylesheet>