<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:param name="version"/>

	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>
	<xsl:template match="path[parent::pydev_pathproperty[@name='org.python.pydev.PROJECT_EXTERNAL_SOURCE_PATH']]">
    	<path>${M2_HOME}/com/systemincloud/modeler/tasks/pythontask/com.systemincloud.modeler.tasks.pythontask.process/0.2.0/com.systemincloud.modeler.tasks.pythontask.process-0.2.0.jar</path>
	</xsl:template>
</xsl:stylesheet>