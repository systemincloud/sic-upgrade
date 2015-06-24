<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns:xmi="http://www.omg.org/XMI" 
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
                xmlns:al="http://eclipse.org/graphiti/mm/algorithms" 
                xmlns:dt="http://systemincloud.com/dt" 
                xmlns:pi="http://eclipse.org/graphiti/mm/pictograms" 
                xmlns:sic="http://systemincloud.com/sic"
                xmlns:mux_0_1_0="http://modeler.systemincloud.com/tasks/mux/0_1_0">

	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>
	<xsl:template match="inputPort[parent::task[@xsi:type='mux_0_1_0:Mux']]">
		<xsl:copy>
			<xsl:choose>
				<xsl:when test="@id='Idx'">
					<xsl:attribute name="group">0</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="group">
						<xsl:value-of select="number(substring(@id,4)) + 1"/>
					</xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
      		<xsl:apply-templates select="@*[name()!='asynchronous']|node()" />
    	</xsl:copy>
	</xsl:template>
</xsl:stylesheet>