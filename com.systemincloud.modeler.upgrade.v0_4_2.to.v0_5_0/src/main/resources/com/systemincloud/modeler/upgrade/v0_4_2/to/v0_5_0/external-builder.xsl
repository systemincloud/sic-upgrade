<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">

	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>
	<xsl:template match="buildCommand[name = 'org.eclipse.m2e.core.maven2Builder']">
		<buildCommand>
			<name>org.eclipse.ui.externaltools.ExternalToolBuilder</name>
			<arguments>
				<dictionary>
					<key>LaunchConfigHandle</key>
					<value>&lt;project&gt;/.externalToolBuilders/org.eclipse.m2e.core.maven2Builder.launch</value>
				</dictionary>
			</arguments>
		</buildCommand>
	</xsl:template>
</xsl:stylesheet>