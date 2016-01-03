<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd"
                exclude-result-prefixes="xsi">
    <xsl:param name="nature"/>

    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
    </xsl:template>

    <xsl:template match="/projectDescription/natures/nature[last()]">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
        <xsl:element name="nature"><xsl:value-of select="$nature" /></xsl:element>
    </xsl:template>

</xsl:stylesheet>
