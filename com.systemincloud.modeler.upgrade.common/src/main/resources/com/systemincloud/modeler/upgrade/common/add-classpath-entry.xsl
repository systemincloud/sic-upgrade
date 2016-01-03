<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd"
                exclude-result-prefixes="xsi">
    <xsl:param name="after"/>
    <xsl:param name="kind"/>
    <xsl:param name="path"/>
    <xsl:param name="excluding"/>

    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
    </xsl:template>

    <xsl:template match="/classpath/classpathentry[path = $after]">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
        <xsl:element name="classpathentry">
            <xsl:attribute name="kind"><xsl:value-of select="$kind"/></xsl:attribute>
            <xsl:attribute name="path"><xsl:value-of select="$path"/></xsl:attribute>
            <xsl:attribute name="excluding"><xsl:value-of select="$excluding"/></xsl:attribute>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
