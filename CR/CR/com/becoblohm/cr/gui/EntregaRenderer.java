/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : EntregaRenderer.java
 * Creado por : irojas
 * Creado en  : 03-jun-04 7:47:31
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 03-jun-04 7:47:31
 * Analista    : irojas
 * Descripción : Implementación inicial.
 * =============================================================================
 */

/**
 * Descripción:
 * 
 */

package com.becoblohm.cr.gui;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.becoblohm.cr.manejadorsesion.Sesion;

public class EntregaRenderer extends JComboBox implements TableCellRenderer {
	public EntregaRenderer() {
		super();
		if (Sesion.entregaCaja)
			addItem(Sesion.ENTREGA_CAJA);
		if(Sesion.entregaDespacho)
			addItem(Sesion.ENTREGA_DESPACHO);
		if(Sesion.entregaDomicilio)
			addItem(Sesion.ENTREGA_DOMICILIO);
		if (Sesion.entregaCteRetira)
			addItem(Sesion.ENTREGA_CLIENTE_RETIRA);
	}
	
	public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row,int column) {
		if (isSelected) {
			setForeground(table.getForeground());
			super.setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(new java.awt.Color(226,226,222));
		}
		setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
		setSelectedItem(value);
		return this;
	}

}