package com.bridgeit.fundooapp.user.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class LoginDTO 
{
	/*Validation
	 * ------------------------------------------------------------------*/
	
	@Column(name = "email", nullable = false)
	@Email(regexp =  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org))+$")
	@NotEmpty(message="Please provide valid email")
	private String email;
	

	@NotEmpty(message="Please provide password")
	@Column(name="password")
	private String password;
	
	/*Constructor
	 * ------------------------------------------------------------------*/
	
	public LoginDTO() {   }
	
	public LoginDTO( @Email(
			regexp =  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org))+$")
			@NotEmpty(message="Please provide valid email")
			String email, 
			@NotEmpty(message="Please provide password")
			String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	/*Getter-Setters
	 * ------------------------------------------------------------------*/
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

	/*to-String
	 * ------------------------------------------------------------------*/
	@Override
	public String toString() {
		return "LoginDTO [email=" + email + ", password=" + password + "]";
	}
	
	

}
