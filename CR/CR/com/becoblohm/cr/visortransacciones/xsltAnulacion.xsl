<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="1.0"
    xmlns:xalan="http://xml.apache.org/xslt">
	<xsl:output method="html"/>
    
    <xsl:template match="/">
		<HTML>
			<BODY>
				<xsl:apply-templates select="anulacion"/>
			</BODY>
		</HTML>    
    </xsl:template>
    
   <xsl:template match="anulacion">
		<TABLE border="0">
		<TBODY>
			<TR>
				<TD align="center" width="425">
				<HR noshade=""/>
				<FONT size="5" face="Arial"><B> </B></FONT><FONT size="6"
					face="Arial"><B>A N U L A C I O N</B></FONT>
				<HR noshade=""/>
				</TD>
			</TR>			
			<TR>
				<TD align="center" width="425"><FONT size="3" face="Arial"><FONT
					size="6" face="Arial"><B>FERRETERIA EPA, C.A.</B></FONT></FONT><HR/></TD>
			</TR>
			<TR>
				<xsl:apply-templates select="cliente"/>
			</TR>
			<TR>
				<TD width="425"><FONT size="3" face="Arial"></FONT>
					<TABLE border="0" width="100%">
					<TBODY>
						<TR>
							<TD><TABLE width="100%" border="0">
							<TBODY>
								<TR>
									<TD><FONT size="3" face="Arial">Cajero: </FONT>  <FONT size="2" face="Arial" color="#000066"><b><xsl:value-of select="./codCajero"/> </b></FONT> </TD>
									<TD align="right"><FONT size="3" face="Arial">POS: </FONT> <FONT size="2" face="Arial" color="#000066"><b><xsl:value-of select="format-number(./numCajaFinaliza, '000')"/> </b></FONT> </TD>
								</TR>
							</TBODY>
							</TABLE>
							</TD>
						</TR>
						<TR>
							<TD align="center" height="65">
							<TABLE width="100%" border="0">
							<TBODY>
								<TR>
									<TD><FONT size="3" face="Arial">Hora: </FONT>  <FONT size="2" face="Arial" color="#000066"><b><xsl:value-of select="./horaFin"/> </b></FONT></TD>
									<TD><FONT size="3" face="Arial">Fecha: </FONT> <FONT size="2" face="Arial" color="#000066"><b><xsl:value-of select="./fechaTrans"/> </b></FONT></TD>
									<TD><FONT size="3" face="Arial">Tr: </FONT> <FONT size="2" face="Arial" color="#000066"><b><xsl:value-of select="format-number(./numTransaccion ,'#')"/> </b></FONT></TD>
								</TR>
							</TBODY>
							</TABLE>
							<FONT size="5" face="Arial"><B>ANULA TR. <xsl:value-of select="format-number(./ventaOriginal/numTransaccion, '00000000000')"/></B></FONT><HR/></TD>
						</TR>
					</TBODY>
					</TABLE>
			</TD>
			</TR>
			<TR>
				<TD width="425">
					<CENTER><TABLE border="1" width="100%">
					<TBODY>
						<TR>
							<TD height="36"><TABLE width="100%" border="1" cellpadding="0" cellspacing="1" bgcolor="white">
							<TBODY>
								<TR>
									<TD height="27" width="58"><B><FONT size="2" face="Arial">Código</FONT></B></TD>
									<TD height="27" width="172"><B><FONT size="2" face="Arial">Descripción</FONT></B></TD>
									<TD height="27" width="88"><B><FONT size="2" face="Arial">Precio N.</FONT></B></TD>
									<TD height="27" width="85"><B><FONT size="2" face="Arial">SubTotal</FONT></B></TD>
								</TR>
								<TR>
									<TD height="23" width="58"><B><FONT size="2" face="Arial">Cantidad</FONT></B></TD>
									<TD height="23" width="172"><B><FONT size="2" face="Arial">Unidad Venta</FONT></B></TD>
									<TD height="23" width="88"><B><FONT size="2" face="Arial">Precio Venta</FONT></B></TD>
									<TD height="23" width="85">
										<CENTER><TABLE width="100%" border="1">
										<TBODY>
											<TR>
												<TD width="34"><B><FONT size="2" face="Arial">Cond</FONT></B></TD>
												<TD width="32"><B><FONT size="2" face="Arial">Ent</FONT></B></TD>
											</TR>
										</TBODY>
										</TABLE></CENTER>
									</TD>
								</TR>
							</TBODY>
							</TABLE>
							</TD>
						</TR>
						<xsl:apply-templates select="//detallesTransaccion/detalletransaccion"/>
					</TBODY>
					</TABLE></CENTER>
				</TD>
			</TR>
			<TR>
						<TD width="425">
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TBODY>
								<TR>
									<TD width="153">
										<FONT size="3" face="Arial">Total Art: </FONT> 
										<FONT size="2" face="Arial" color="#000066">
											<b><xsl:value-of select="format-number(sum(//detallesTransaccion/detalletransaccion/cantidad), '#')"/></b>
										</FONT>
									</TD>
									<TD width="270">
										<TABLE border="0" width="100%">
										<TBODY>
											<TR>
												<TD width="101"><FONT size="3" face="Arial">Subtotal</FONT></TD>
												<TD width="163"><FONT size="2" face="Arial" color="#000066"><b><xsl:value-of select="format-number(./montoBase, '###,###.00')"/></b></FONT></TD>
												
											</TR>
											<TR>
												<TD width="101"><FONT size="3" face="Arial">IVA</FONT></TD>
												<TD width="163"><FONT size="2" face="Arial" color="#000066"><b><xsl:value-of select="format-number(./montoImpuesto, '###,###.00')"/></b></FONT></TD>
											</TR>
											<TR>
												<TD width="101"><FONT size="3" face="Arial">Total</FONT></TD>
												<TD width="163"><FONT size="2" face="Arial" color="#000066"><b><xsl:value-of select="format-number(./montoBase + ./montoImpuesto, '###,###.00')"/></b></FONT></TD>
											</TR>
											<xsl:apply-templates select="//ventaoriginal/pagos/pago"/>

										</TBODY>
										</TABLE>
									</TD>
								</TR>
							</TBODY>
							</TABLE><HR/>
						</TD>
					</TR>
					<TR>
						<TD width="425" height="43"><p align="center"><FONT size="6" face="Arial"><B>A N U L A C I O N</B></FONT></p><BR/>
							
						</TD>
					</TR>
					<TR>
						<TD width="425" height="26"></TD>
					</TR>
				</TBODY>
				</TABLE>
				<P></P>
   
   </xsl:template>
   <xsl:template match="cliente">
		<TD width="425">
			<FONT size="3" face="Arial">
					Cliente: <FONT size="2" face="Arial" color="#000066"><b><xsl:value-of select="./nombre"/> </b></FONT><BR/>
					Dirección: <FONT size="2" face="Arial" color="#000066"><b><xsl:value-of select="./dirFiscal"/></b></FONT><BR/>
					CI/RIF: <FONT size="2" face="Arial" color="#000066"><b><xsl:value-of select="./codCliente"/></b></FONT><HR/>
			</FONT>
		</TD>   
   </xsl:template>
   
   <xsl:template match="detalletransaccion">
		<TR>
			<TD height="44">
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TBODY>
					<TR>
						<TD width="60"><FONT size="2" face="Arial"><xsl:value-of select="substring(./producto/codProducto, 6)"/> </FONT></TD>
							<TD width="173"><FONT size="2" face="Arial"><xsl:value-of select="./producto/descripcionLarga"/></FONT></TD>
						<TD width="89"><FONT size="2" face="Arial"><xsl:value-of select="format-number(./producto/precioRegular, '###,###.00')"/></FONT></TD>
						<TD width="85"><FONT size="2" face="Arial"><xsl:value-of select="format-number(./cantidad * ./precioFinal, '###,###.00')"/></FONT></TD>
					</TR>	
						<TR>
						<TD width="60"><FONT size="2" face="Arial"><xsl:value-of select="format-number(./cantidad, '#,##0')"/></FONT></TD>
						<TD width="173"><FONT size="2" face="Arial"><xsl:value-of select="./producto/abreviadoUnidadVenta"/></FONT></TD>
						<TD width="89"><FONT size="2" face="Arial"><xsl:value-of select="format-number(./precioFinal, '###,###.00')"/></FONT></TD>
						<TD width="85">
							<TABLE width="100%" border="0">
							<TBODY>
								<TR>
									<TD width="37"><FONT size="2" face="Arial"><xsl:value-of select="./condicionVenta"/></FONT></TD>
									<TD width="33"><FONT size="2" face="Arial">
										<xsl:choose>
											<xsl:when test="./tipoEntrega[string() = 'Caja']">C</xsl:when>
											<xsl:when test="./tipoEntrega[string() = 'Cliente Retira']">R</xsl:when>
											<xsl:when test="./tipoEntrega[string() = 'Despacho']">D</xsl:when>
											<xsl:when test="./tipoEntrega[string() = 'Domicilio']">F</xsl:when>
										</xsl:choose> 
									</FONT></TD>
								</TR>
							</TBODY>
							</TABLE>
						</TD>
					</TR>
				</TBODY>
				</TABLE>
			</TD>
		</TR>
   </xsl:template>
   <xsl:template match="pago">
		<TR>
			<TD width="101"><FONT size="3" face="Arial"><xsl:value-of select="./formaPago/nombre"/> </FONT></TD>
			<TD width="163"><FONT size="2" face="Arial" color="#000066"><b><xsl:value-of select="format-number(./monto, '###,###.00')"/></b> </FONT></TD>
		</TR>   
   </xsl:template>
</xsl:stylesheet>
