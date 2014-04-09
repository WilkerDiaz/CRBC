/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : EntregaEditor.java
 * Creado por : irojas
 * Creado en  : 03-jun-04 7:24:31
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 03-jun-04 7:24:31
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
import java.awt.event.ActionEvent;
import java.util.EventObject;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.TableCellEditor;

import com.becoblohm.cr.manejadorsesion.Sesion;

public class EntregaEditor extends JComboBox implements TableCellEditor {
	protected EventListenerList listenerList = new EventListenerList();
	protected ChangeEvent changeEvent = new ChangeEvent(this);
	private int[] colorCombo = new int[3];
	private ScanerSwitch parent = null;
	
	public EntregaEditor(ScanerSwitch sw) {
		super();
		parent = sw;
		initialize();
		if (Sesion.entregaCaja)
			addItem(Sesion.ENTREGA_CAJA);
		if(Sesion.entregaDespacho)
			addItem(Sesion.ENTREGA_DESPACHO);
		if(Sesion.entregaDomicilio)
			addItem(Sesion.ENTREGA_DOMICILIO);
		if (Sesion.entregaCteRetira)
			addItem(Sesion.ENTREGA_CLIENTE_RETIRA);
		setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
		getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "stop");
		getActionMap().put("stop", new StopAction());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		String[] xColorFondo = Sesion.colorCombo.split(",");
		this.setBackground(new java.awt.Color(Integer.parseInt(xColorFondo[0]),Integer.parseInt(xColorFondo[1]),Integer.parseInt(xColorFondo[2])));
        //this.setBackground(new java.awt.Color(69,170,252));
			
	}
	public void addCellEditorListener(CellEditorListener listener) {
		listenerList.add(CellEditorListener.class, listener);
		this.requestFocus();
		parent.setPermitirEscaner(false);
	}

	public void removeCellEditorListener(CellEditorListener listener) {
		listenerList.remove(CellEditorListener.class, listener);
		parent.setPermitirEscaner(true);
	}

	protected void fireEditingStopped() {
		CellEditorListener listener;
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] == CellEditorListener.class) {
				listener = (CellEditorListener) listeners[i + 1];
				listener.editingStopped(changeEvent);
			}
		}
	}
	
	protected void fireEditingCanceled() {
		CellEditorListener listener;
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] == CellEditorListener.class) {
				listener = (CellEditorListener) listeners[i + 1];
				listener.editingCanceled(changeEvent);
			}
		}
	}
	
	public void cancelCellEditing() {
		fireEditingCanceled();
	}
	
	public boolean stopCellEditing() {
		fireEditingStopped();
		parent.setPermitirEscaner(true);
		return true;
	}
	
	public boolean isCellEditable(EventObject event) {
		return true;
	}

	public boolean shouldSelectCell(EventObject event) {
		return true;
	}
	
	public Object getCellEditorValue() {
		return getSelectedItem();
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return this;
	}

	class StopAction extends AbstractAction {
		StopAction() {
			super("stop");
		}
		
		public void actionPerformed(ActionEvent e){
			stopCellEditing();
		}
	}
}
