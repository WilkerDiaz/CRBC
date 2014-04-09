/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.verificador
 * Programa   : SimpleLinkListener.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 27/11/2003 04:11:59 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.5 (según CVS)
 * Fecha       : 11/02/2004 08:46 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Actualización para uso del método setUbicacion de la clase Sesion.
 * =============================================================================
 */
package com.becoblohm.cr.verificador;

import java.io.FileNotFoundException;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

import org.apache.log4j.Logger;

/** 
 * Descripción: 
 * 	Clase implementa los hipervínculos existentes al documento HTML asociadoa a un
 * JEditorPane, en este caso al VerificadorDePrecios, cambiando al documento HTML al
 * ser activados. 
 * 
 */
public class SimpleLinkListener implements HyperlinkListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(SimpleLinkListener.class);

  private JEditorPane pane;       // The pane we're using to display HTML

  private JTextField  urlField;   // An optional text field for showing
                                  // the current URL being displayed

  private JLabel statusBar;       // An optional label for showing where
                                  // a link would take you

  private String mensajeBarra;       // An optional label for showing where
                                  // a link would take you

  public SimpleLinkListener(JEditorPane jep, JTextField jtf, JLabel jl) {
    pane = jep;
    urlField = jtf;
    statusBar = jl;
    mensajeBarra = statusBar.getText();
  }

  public SimpleLinkListener(JEditorPane jep) {
    this(jep, null, null);
  }

  public SimpleLinkListener(JEditorPane jep, JLabel jl) {
    this(jep, null, jl);
  }

  public void hyperlinkUpdate(HyperlinkEvent he) {
	if (logger.isDebugEnabled()) {
		logger.debug("hyperlinkUpdate(HyperlinkEvent) - start");
	}

    HyperlinkEvent.EventType type = he.getEventType();
    if (type == HyperlinkEvent.EventType.ENTERED) {
      // Enter event.  Fill in the status bar.
      if (statusBar != null) {
        statusBar.setText(he.getURL().toString());
      }
    }
    else if (type == HyperlinkEvent.EventType.EXITED) {
      // Exit event.  Clear the status bar.
      if (statusBar != null) {
        statusBar.setText(mensajeBarra); // Must be a space or it disappears
      }
    }
    else if (type == HyperlinkEvent.EventType.ACTIVATED) {
      // Jump event.  Get the URL, and, if it's not null, switch to that
      // page in the main editor pane and update the "site url" label.
      if (he instanceof HTMLFrameHyperlinkEvent) {
        // Ahh, frame event; handle this separately.
        HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)he;
        HTMLDocument doc = (HTMLDocument)pane.getDocument();
        doc.processHTMLFrameHyperlinkEvent(evt);
      } else {
        try {
          pane.setPage(he.getURL());
          if (urlField != null) {
            urlField.setText(he.getURL().toString());
          }
        }
        catch (FileNotFoundException fnfe) {
			logger.error("hyperlinkUpdate(HyperlinkEvent)", fnfe);

          pane.setText("Could not open file: <tt>" + he.getURL() + 
                       "</tt>.<hr>");
        }
        catch (Exception e) {
		logger.error("hyperlinkUpdate(HyperlinkEvent)", e);
        }
      }
    }

	if (logger.isDebugEnabled()) {
		logger.debug("hyperlinkUpdate(HyperlinkEvent) - end");
	}
  }
}
