package com.bridgeit.fundooapp.user.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class UserDTO
{
	/*Validation
	 * ----------------------------------------------------------------*/
	
	@Column(name = "name" , nullable = false)
	@NotEmpty(message = "please provide your name")
	private String name;
	
	@Column(name = "email" , nullable = false)
	@NotEmpty(message="Please provide valid email")
	@Email(regexp =  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org))+$")
	private String email;
	
	@Column(name = "password")
	@NotEmpty(message="Please provide password")
	@Length(min=6 , max = 50, message = "password must be at least 6 character and max 50 character")
	private String password;
	
	@Column(name = "mobileNumber")
	@Pattern(regexp = "[0-9]{10}" , message = "provide valid mobile number")
	private String mobileNumber;
	
	/*Constructor
	 * ----------------------------------------------------------------*/
	
	public UserDTO() {   }
	
	public UserDTO( @NotEmpty(message = "please provide your name") 
	         String name,
			@NotEmpty(message="Please provide valid email")
			@Email(regexp =  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org))+$")
	         String email,
			@NotEmpty(message="Please provide password")
			@Length(min=6 , max = 50, message = "password must be at least 6 character and max 50 character")
	         String password, 
	        @Pattern(regexp = "[0-9]{10}" , message = "provide valid mobile number")
	         String mobileNumber) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.mobileNumber = mobileNumber;
	}
	
	/*Getter-setters
	 * ----------------------------------------------------------------*/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	/* to-string
	 * ----------------------------------------------------------------*/

	@Override
	public String toString() {
		return "UserDTO [name=" + name + ", email=" + email + ", password=" + password + ", mobileNumber="
				+ mobileNumber + "]";
	}
	
	
	
	

}
