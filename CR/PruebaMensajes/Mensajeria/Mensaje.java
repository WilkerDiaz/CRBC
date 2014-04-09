package Mensajeria;


import java.util.Vector;


/**
 * Clase abstracta de envío de mensajes.
 * Al extender de ella debe implementarse el método "construirContenido" y agregarse a la tabla
 * INTRANET.MENSAJE
 * Adicionalmente completar los destinatarios y horarios de envío en el mismo esquema
 * @author jgraterol
 *
 */
public class Mensaje {

	private int id;
	private Vector<String> destinatario;
	private String remitente;
	private String nombreRemitente;
	private String asunto;
	private String contenido;
	private String contenidoHTML;
		
	public Mensaje(int id , Vector<String> destinatario, String remitente, String nombreRemitente, String asunto, String contenido) {
		super();
		this.id = id;
		this.destinatario = destinatario;
		this.remitente = remitente;
		this.nombreRemitente = nombreRemitente;
		this.asunto = asunto;
		this.contenido = contenido;
	}
	
	
	

		
	public String getAsunto() {
		return asunto;
	}
	
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	
	public String getContenido() {
		return contenido;
	}
	
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	public Vector<String> getDestinatario() {
		return destinatario;
	}
	
	public void setDestinatario(Vector<String> destinatario) {
		this.destinatario = destinatario;
	}
	
	public String getNombreRemitente() {
		return nombreRemitente;
	}
	
	public void setNombreRemitente(String nombreRemitente) {
		this.nombreRemitente = nombreRemitente;
	}
	
	public String getRemitente() {
		return remitente;
	}
	
	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}
	

	
	/**
	 * Envía el mensaje a los destinatarios
	 *
	 */
	public void enviar(){
		for(int i=0;i<this.destinatario.size();i++){
			try{
				String asunto =this.asunto;
		/*		String textoContenido =null;
				String textoContenidoHTML =null;
				if(contenido!=null && contenido.size()!=0){
					asunto+=(String)contenido.elementAt(1);
					textoContenido=(String)contenido.elementAt(0);
				}
				if(contenidoHTML!=null && contenidoHTML.size()!=0){ 
					asunto+=(String)contenidoHTML.elementAt(1);
					textoContenidoHTML=(String)contenidoHTML.elementAt(0);
				}*/
				String emailDestinatario = (String)this.destinatario.elementAt(i);
				EMail email = new EMail ("gmail.com", 0, true, 
						this.remitente, this.nombreRemitente, emailDestinatario, emailDestinatario, 
						asunto,contenido, contenidoHTML);
					
				email.createAuthenticator(remitente, "1052020931");
				email.send();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContenidoHTML() {
		return contenidoHTML;
	}

	public void setContenidoHTML(String contenidoHTML) {
		this.contenidoHTML = contenidoHTML;
	}
	
}
