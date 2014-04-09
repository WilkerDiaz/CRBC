package com.becoblohm.cr;
import ve.com.megasoft.universal.VPosUniversal;
import ve.com.megasoft.universal.error.VposUniversalException;

public class Test {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        VPosUniversal vpos = null;
        
        try {
           // vpos = new VPosUniversal("C:/vPos/conf/"); 
            vpos = new VPosUniversal("/opt/CR/vPos/conf/"); 
            //vpos.tarjeta("","");
            //vpos.miscelaneos();
           
            
            // En este punto pueden ser obtenidos los valores de los atributos
            // que contienen datos de la transacción.
            System.out.println(" COD. RESPUESTA: " + vpos.getNumSeq());
            System.out.println("DESC. RESPUESTA: " + vpos.getMensajeRespuesta());

        } catch (VposUniversalException e) {
            // Manejo de errores
            System.out.println(e.getMessage());
        } finally {
            
            System.out.println(System.getProperty("java.library.path"));
            System.exit(0);
        }
    }
}
