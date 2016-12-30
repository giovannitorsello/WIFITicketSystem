<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
  <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>


  <xsl:template match="SerieTicket">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">

      <fo:layout-master-set>
        <fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm" margin-top="1cm" margin-bottom="0.5cm" margin-left="0.5cm" margin-right="0.5cm">
           <fo:region-body margin-top="1cm" margin-bottom="1cm"/>
           <fo:region-before region-name="content-header" extent="1cm"/>
           <fo:region-after region-name="content-footer" extent="1cm"/>

        </fo:simple-page-master>
      </fo:layout-master-set>

      <fo:page-sequence master-reference="simpleA4" id="seqPage1">
        <fo:static-content flow-name="content-header">
            <fo:block text-align="right" border-top="1pt dashed silver">
                Pagina <fo:page-number /> di <fo:page-number-citation  ref-id="theEnd" />
            </fo:block>
        </fo:static-content>

        <fo:flow flow-name="xsl-region-body">
          <fo:block font-size="14pt" font-weight="bold" space-after="5mm">Azienda: <xsl:value-of select="DenominazioneAzienda"/>
          </fo:block>
          <fo:block font-size="11pt" space-after="5mm">Serie Ticket<xsl:value-of select="SerieTicket"/>
          </fo:block>

<xsl:for-each select="Page">
          <fo:block font-size="10pt">
            <fo:table table-layout="fixed" width="100%" border-collapse="separate">
              <fo:table-column column-width="4cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="1cm"/>
              <fo:table-column column-width="2cm"/>
              <fo:table-column column-width="2cm"/>              
              <fo:table-body>                   
                <xsl:apply-templates select="Ticket"/>
              </fo:table-body>
            </fo:table>
          </fo:block>
          
<fo:block break-after="page"/>
</xsl:for-each>

         

          <!--fo:block font-size="10pt"><xsl:template match="TotalePagina">TP</xsl:template></fo:block>
          <fo:block font-size="10pt"><xsl:template match="TotaleProgressivo">TP</xsl:template></fo:block-->
         
          <fo:block id="theEnd" />
        </fo:flow>                

      </fo:page-sequence>          

    </fo:root>
  </xsl:template>




  <!-- ========================= -->
  <!-- child element: member     -->
  <!-- ========================= -->
  <xsl:template match="Ticket">
    <fo:table-row>
      <xsl:if test="function = 'lead'">
        <xsl:attribute name="font-weight">bold</xsl:attribute>
      </xsl:if>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="seriale"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="user"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="password"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="duratagiorni"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="dataemissione"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="datascadenza"/>
        </fo:block>
      </fo:table-cell>
    </fo:table-row>
  </xsl:template>


</xsl:stylesheet>
