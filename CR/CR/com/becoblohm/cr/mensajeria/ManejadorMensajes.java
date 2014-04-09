package com.becoblohm.cr.mensajeria;

import java.sql.Date;

import com.becoblohm.cr.manejadorsesion.Sesion;

public class ManejadorMensajes {
	

	
	
	public static void mensajeErrorCarga (String entidad)
	{
		String mensaje = "Se ha producido un error en la carga de datos de la entidad "+
				entidad +" en la tienda "+Sesion.getTienda().getNumero() +" caja "+
						Sesion.getCaja().getNumero()+" . No fue posible" +
						" encontrar ningún DataFile para realizar la carga de datos, " +
						"verifique que colascr esté corriendo.";
		try{
			EMail email = new EMail(Sesion.getSmtpHost(), Sesion.getSmtpPort(), true, 
						Sesion.getMailRemitente(), "Soporte", "wdiaz@beco.com.ve", "Wilker Diaz", "Error en la carga de productos CR", mensaje, null);
					email.createAuthenticator(Sesion.getMailRemitente(), Sesion.getPasswordRemitente());
			email.send();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void mensajeErrorCarga(String entidadPrimera, Date ultAct,
			String entidadTomada) {
		String mensaje = "Se ha producido un error en la carga de datos de la entidad "+
						entidadPrimera+" en la tienda "+Sesion.getTienda().getNumero() +
						" caja "+Sesion.getCaja().getNumero()+". " +
						"No fue posible obtener el DataFile de fecha "+ultAct+", en su " +
						"lugar se obtuvo el DataFile "+entidadTomada+".";
		try{
			EMail email = new EMail(Sesion.getSmtpHost(), Sesion.getSmtpPort(), true, 
				Sesion.getMailRemitente(), "Soporte", "wdiaz@beco.com.ve", "Wilker Diaz", "Error en la carga de productos CR", mensaje, null);
			email.createAuthenticator(Sesion.getMailRemitente(), Sesion.getPasswordRemitente());
			email.send();
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}   

}