/**
 * 
 */
package com.ecowheel.system.managedbean;

import java.io.Serializable;

/**
 * 
 * @author David Andres Betancourth Botero
 *
 */
public class Datos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5994929999374958023L;

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Datos [username=" + username + ", password=" + password + "]";
	}

}
