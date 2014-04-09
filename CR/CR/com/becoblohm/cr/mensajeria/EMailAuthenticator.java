package com.becoblohm.cr.mensajeria;

import java.io.*;
import java.util.logging.*;

import javax.mail.*;

/**
 *  Email User Authentication
 *
 *  @author Jorg Janke
 *  @version $Id: EMailAuthenticator.java 8244 2009-12-04 23:25:29Z freyes $
 */
public class EMailAuthenticator extends Authenticator implements Serializable
{
	/** */
    private static final long serialVersionUID = -1453447115062744524L;

	/**
	 * 	Constructor
	 * 	@param username user name
	 * 	@param password user password
	 */
	public EMailAuthenticator (String username, String password)
	{
		m_pass = new PasswordAuthentication (username, password);
		if (username == null || username.length() == 0)
		{
			//log.log(Level.SEVERE, "Username is NULL");
			Thread.dumpStack();
		}
		if (password == null || password.length() == 0)
		{
			//log.log(Level.SEVERE, "Password is NULL");
			Thread.dumpStack();
		}
	}	//	EMailAuthenticator

	/**	Password		*/
	private PasswordAuthentication 	m_pass = null;
	/**	Logger			*/
	//private static CLogger log = CLogger.getCLogger(EMailAuthenticator.class);

	/**
	 *	Get PasswordAuthentication
	 * 	@return Password Authentication
	 */
	
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return m_pass;
	}	//	getPasswordAuthentication

	/**
	 * 	Get String representation
	 * 	@return info
	 */
	
	/*public String toString()
	{
		if (m_pass == null)
			return "EMailAuthenticator[]";
		if (CLogMgt.isLevelFinest())
			return "EMailAuthenticator["
				+ m_pass.getUserName() + "/" + m_pass.getPassword() + "]";
		return "EMailAuthenticator["
			+ m_pass.getUserName() + "/************]";
	}*/	//	toString

}	//	EMailAuthenticator
