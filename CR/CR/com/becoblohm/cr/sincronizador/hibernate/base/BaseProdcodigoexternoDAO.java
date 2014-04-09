/*
 * $$Id: BaseProdcodigoexternoDAO.java,v 1.2 2005/03/10 15:54:39 programa8 Exp $$
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Creado por	: Superferretería EPA / Hibernate Synchronizer
 * Creado en 	: Tue Feb 15 08:03:32 GMT-04:00 2005
 * (C) Copyright SuperFerretería EPA C.A. Todos los Derechos Reservados
 * 
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $$Log: BaseProdcodigoexternoDAO.java,v $
 * $Revision 1.2  2005/03/10 15:54:39  programa8
 * $CR al 10/03/2005. Merging desde branch STABLE
 * $
 * $Revision 1.1.2.2  2005/03/07 12:54:19  programa8
 * $Integración Versiones Estable e Inestable al 07/03/2005
 * $
 * $Revision 1.1.4.1  2005/02/28 18:39:11  programa8
 * $Version Inestable al 28/02/2005
 * $    *-Preparación para trabajar sin administrador de ventanas.
 * $    *-Reordenamiento de GUI
 * $    *-Mejoras en scroll y pantallas de pagos
 * $    *- Mantenimiento de estado en Cliente en Espera
 * $    *- Avisos del sistema manejados por la aplicacion
 * $    *- Desbloqueo de caja por otros usuarios reparado.
 * $
 * $Revision 1.1.2.1  2005/02/15 17:43:39  acastillo
 * $Ajuste de estructura prodcodigoexterno
 * $$
 * ===========================================================================
 */

package com.becoblohm.cr.sincronizador.hibernate.base;






import net.sf.hibernate.Hibernate;
import net.sf.hibernate.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.becoblohm.cr.sincronizador.hibernate.dao.ProdcodigoexternoDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 *
 * @author Hibernate Synchronizer - $$Author: programa8 $$
 * @version $$Revision: 1.2 $$<br>$$Date: 2005/03/10 15:54:39 $$
 * @since Tue Feb 15 08:03:32 GMT-04:00 2005
 */
/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 */
public abstract class BaseProdcodigoexternoDAO extends com.becoblohm.cr.sincronizador.hibernate.dao._RootDAO {
	/** Log para este objeto. */
	private static Log log = LogFactory.getLog(BaseProdcodigoexternoDAO.class);


	/** 
	 * singleton instance of the DAO 
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public static ProdcodigoexternoDAO instance;

	/**
	 * Return a singleton of the DAO
	 * @return ProdcodigoexternoDAO instance
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public static ProdcodigoexternoDAO getInstance () {
		if (null == instance) instance = new ProdcodigoexternoDAO();
		if (log.isDebugEnabled()) {
			log.debug("Returning a sigleton instance of: ProdcodigoexternoDAO");
		}
		return instance;
	}

	/**
	 * com.becoblohm.cr.sincronizador.hibernate.dao._RootDAO _RootDAO.getReferenceClass()
	 * @return Class the com.becoblohm.cr.sincronizador.hibernate.dao._RootDAO
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	/*
	* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó la clase
	* Fecha: agosto 2011
	*/
	public Class<com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno> getReferenceClass () {
		return com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno.class;
	}
	


	/**
      * Get an com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno instance 
	  * @param key the key or id of the object
 	  * @return com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	  */
	public com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno get(java.lang.String key)
		throws net.sf.hibernate.HibernateException {
		return (com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno) get(getReferenceClass(), key);
	}

	/**
      * Get an com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno instance 
	  * @param key the key or id of the object
	  * @param s the session tu use
 	  * @return com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	  */
	public com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno get(java.lang.String key, Session s)
		throws net.sf.hibernate.HibernateException {
		return (com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno) get(getReferenceClass(), key, s);
	}

	/**
      * Get and initialize an com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno instance 
	  * @param key the key or id of the object
	  * @param s the session tu use
 	  * @return com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	  */
	public com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno getInitialize(java.lang.String key, Session s) 
			throws net.sf.hibernate.HibernateException { 
		com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno obj = get(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}
	
	/**
      * Load an com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno instance 
	  * @param key the key or id of the object
 	  * @return com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	  */
	public com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno load(java.lang.String key)
		throws net.sf.hibernate.HibernateException {
		return (com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno) load(getReferenceClass(), key);
	}

	/**
      * Load an com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno instance 
	  * @param key the key or id of the object
	  * @param s the session tu use
 	  * @return com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	  */
	public com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno load(java.lang.String key, Session s)
		throws net.sf.hibernate.HibernateException {
		return (com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno) load(getReferenceClass(), key, s);
	}

	/**
      * Load and initialize an com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno instance 
	  * @param key the key or id of the object
	  * @param s the session tu use
 	  * @return com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	  */
	public com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno loadInitialize(java.lang.String key, Session s) 
			throws net.sf.hibernate.HibernateException { 
		com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param prodcodigoexterno a transient instance of a persistent class 
	 * @return the class identifier
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public java.lang.String save(com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno prodcodigoexterno)
		throws net.sf.hibernate.HibernateException {
		return (java.lang.String) super.save(prodcodigoexterno);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param prodcodigoexterno a transient instance of a persistent class
	 * @param s the Session
	 * @return java.lang.String the class identifier
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public java.lang.String save(com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno prodcodigoexterno, Session s)
		throws net.sf.hibernate.HibernateException {
		return (java.lang.String) super.save(prodcodigoexterno, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param prodcodigoexterno a transient instance containing new or updated state 
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public void saveOrUpdate(com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno prodcodigoexterno)
		throws net.sf.hibernate.HibernateException {
		super.saveOrUpdate(prodcodigoexterno);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param prodcodigoexterno a transient instance containing new or updated state.
	 * @param s the Session.
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public void saveOrUpdate(com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno prodcodigoexterno, Session s)
		throws net.sf.hibernate.HibernateException {
		super.saveOrUpdate(prodcodigoexterno, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param prodcodigoexterno a transient instance containing updated state
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public void update(com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno prodcodigoexterno) 
		throws net.sf.hibernate.HibernateException {
		super.update(prodcodigoexterno);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param prodcodigoexterno a transient instance containing updated state
	 * @param s the Session
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public void update(com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno prodcodigoexterno, Session s)
		throws net.sf.hibernate.HibernateException {
		super.update(prodcodigoexterno, s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public void delete(java.lang.String id)
		throws net.sf.hibernate.HibernateException {
		super.delete(load(id));
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param id the instance ID to be removed
	 * @param s the Session
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public void delete(java.lang.String id, Session s)
		throws net.sf.hibernate.HibernateException {
		super.delete(load(id, s), s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param prodcodigoexterno the instance to be removed
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public void delete(com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno prodcodigoexterno)
		throws net.sf.hibernate.HibernateException {
		super.delete(prodcodigoexterno);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param prodcodigoexterno the instance to be removed
	 * @param s the Session
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public void delete(com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno prodcodigoexterno, Session s)
		throws net.sf.hibernate.HibernateException {
		super.delete(prodcodigoexterno, s);
	}
	
	/**
	 * Re-read the state of the given instance from the underlying database. It is inadvisable to use this to implement
	 * long-running sessions that span many business tasks. This method is, however, useful in certain special circumstances.
	 * For example 
	 * <ul> 
	 * <li>where a database trigger alters the object state upon insert or update</li>
	 * <li>after executing direct SQL (eg. a mass update) in the same session</li>
	 * <li>after inserting a Blob or Clob</li>
	 * </ul>
	 * @param prodcodigoexterno the com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno to refresh
	 * @param s the Session to use
 	  * @throws net.sf.hibernate.HibernateException
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public void refresh (com.becoblohm.cr.sincronizador.hibernate.Prodcodigoexterno prodcodigoexterno, Session s)
		throws net.sf.hibernate.HibernateException {
		super.refresh(prodcodigoexterno, s);
	}

	/**
	 * Returns the default order property
	 * @return String the property name
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
    public String getDefaultOrderProperty () {
		return null;
    }
    


}