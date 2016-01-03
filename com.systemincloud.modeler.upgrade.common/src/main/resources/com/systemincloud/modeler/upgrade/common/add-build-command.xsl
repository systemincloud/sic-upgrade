<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd"
                exclude-result-prefixes="xsi">
    <xsl:param name="name"/>

    <xsl:output indent="yes"/>

    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
    </xsl:template>

    <xsl:template match="/projectDescription/buildSpec/buildCommand[last()]">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
        <xsl:element name="buildCommand">
            <xsl:element name="name"><xsl:value-of select="$name" /></xsl:element>
            <xsl:element name="arguments"/>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
