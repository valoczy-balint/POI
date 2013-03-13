package org.mercury.poi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@Column(name = "username")
	private String username;
	
	/**
	 * The password as an SHA value
	 */
	@Column(name = "password")
	private String password;
	
	/**
	 * Access level of the user. 
	 * 1 = Admin user
	 * 2 = Regular user
	 */
	@Column(name = "access")
	private Integer access;
	
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
	public Integer getAccess() {
		return access;
	}
	public void setAccess(Integer access) {
		this.access = access;
	}
}
