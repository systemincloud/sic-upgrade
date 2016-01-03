<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd"
                xmlns:maven="http://maven.apache.org/POM/4.0.0"
                exclude-result-prefixes="xsi maven">
    <xsl:param name="groupId"/>
    <xsl:param name="artifactId"/>
    <xsl:param name="version"/>

    <xsl:output indent="yes"/>

    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
    </xsl:template>

    <xsl:template match="/maven:project/maven:dependencies/maven:dependency[last()]">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
        <xsl:element name="dependency" namespace="http://maven.apache.org/POM/4.0.0">
            <xsl:element name="groupId" namespace="http://maven.apache.org/POM/4.0.0"><xsl:value-of select="$groupId" /></xsl:element>
            <xsl:element name="artifactId" namespace="http://maven.apache.org/POM/4.0.0"><xsl:value-of select="$artifactId" /></xsl:element>
            <xsl:element name="version" namespace="http://maven.apache.org/POM/4.0.0"><xsl:value-of select="$version" /></xsl:element>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
