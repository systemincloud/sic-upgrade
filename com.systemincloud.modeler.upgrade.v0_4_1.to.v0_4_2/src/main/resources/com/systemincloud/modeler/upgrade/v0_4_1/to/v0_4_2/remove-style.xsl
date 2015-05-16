<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns:xmi="http://www.omg.org/XMI" 
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
                xmlns:al="http://eclipse.org/graphiti/mm/algorithms" 
                xmlns:dt="http://systemincloud.com/dt" 
                xmlns:pi="http://eclipse.org/graphiti/mm/pictograms" 
                xmlns:sic="http://systemincloud.com/sic">
    <xsl:param name="name"/>

	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>
	<xsl:template match="@style">
		<xsl:variable name="isGate" select="/xmi:XMI/pi:Diagram/styles[@id=$name]"/>
		<xsl:choose>
        	<xsl:when test="$isGate">
				<xsl:variable name="i" select="count(/xmi:XMI/pi:Diagram/styles[@id=$name]/preceding-sibling::styles)"/>
				<xsl:variable name="j" select="number(substring-after(.,'.'))"/>
				<xsl:variable name="k" select="count(/xmi:XMI/pi:Diagram/styles[@id='TASK']/preceding-sibling::styles)"/>
<!-- 				<xsl:message terminate="no"><xsl:value-of select="$i"/></xsl:message> -->
<!-- 				<xsl:message terminate="no"><xsl:value-of select="$j"/></xsl:message> -->
<!-- 				<xsl:message terminate="no"><xsl:value-of select="$k"/></xsl:message> -->
				<xsl:if test="$j &lt; $i">
<!-- 					<xsl:message terminate="no"><xsl:value-of select="." /></xsl:message> -->
					<xsl:attribute name="style">
			    		<xsl:value-of select="." />
			  		</xsl:attribute>
				</xsl:if>
				<xsl:if test="$j > $i">
<!-- 					<xsl:message terminate="no"><xsl:value-of select="concat('/0/@styles.', $j - 1)" /></xsl:message> -->
					<xsl:attribute name="style">
			    		<xsl:value-of select="concat('/0/@styles.', $j - 1)" />
			  		</xsl:attribute>
				</xsl:if>
				<xsl:if test="$j = $i and $i > $k">
<!-- 					<xsl:message terminate="no"><xsl:value-of select="concat('/0/@styles.', $k)" /></xsl:message> -->
					<xsl:attribute name="style">
			    		<xsl:value-of select="concat('/0/@styles.', $k)" />
			  		</xsl:attribute>
				</xsl:if>
				<xsl:if test="$j = $i and $i &lt;  $k">
<!-- 					<xsl:message terminate="no"><xsl:value-of select="concat('/0/@styles.', $k - 1)" /></xsl:message> -->
					<xsl:attribute name="style">
			    		<xsl:value-of select="concat('/0/@styles.', $k - 1)" />
			  		</xsl:attribute>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="style">
<!-- 					<xsl:message terminate="no"><xsl:value-of select="." /></xsl:message> -->
		    		<xsl:value-of select="." />
		  		</xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="styles[@id=$name]">
	</xsl:template>
</xsl:stylesheet>