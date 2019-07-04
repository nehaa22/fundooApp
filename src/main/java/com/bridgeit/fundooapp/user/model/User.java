package com.bridgeit.fundooapp.user.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.bridgeit.fundooapp.note.model.Label;
import com.bridgeit.fundooapp.note.model.Note;
import com.fasterxml.jackson.annotation.JsonIgnore;

/* Table name = user created
 * --------------------------------------------------------------------------*/

@Entity
@Table(name = "user")
public class User
{

/* Validation
 * --------------------------------------------------------------------------*/

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userId")
	private long userId;
	
	@NotEmpty(message = "please provide your name")
	@NotNull(message = "please provide your name")
	@Column(name = "name")
	private String name;
	
	@Email(regexp =  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org))+$")
	@NotEmpty(message = "Please provide valid email")
	@NotNull(message = "Please provide valid email")
	@Column(name = "email",unique = true)
	private String email;
	
	@NotEmpty(message = "Please provide password")
	@NotNull(message = "Please provide password")
	@Column(name = "password")
	private String password;
	
	@Pattern(regexp = "[0-9]{10}" , message = "provide valid mobile number")
	@NotEmpty(message = "please provide your mobile number")
	@NotNull(message = "please provide your mobile number")
	@Column(name = "mobileNumber")
	private String mobileNumber;
	
	@Column(name = "isVerified")
	boolean isVarified;
	
	@Column(name = "registeredDate")
	private LocalDate registeredDate;
	
	@Column(name = "updatedDate")
	private LocalDate updatedDate;
	
	@Column(name = "profilePic")
	private String profilePic;
	
	
	/* Relationship mapping
	 * --------------------------------------------------------------------------*/
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Note> notes;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Label> label;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Note> collaboratedNotes;
	

/* Getter-setters
 * --------------------------------------------------------------------------*/

	public User() {    super();    }

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

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

	public boolean isVarified() {
		return isVarified;
	}

	public void setVarified(boolean isVarified) {
		this.isVarified = isVarified;
	}

	public LocalDate getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(LocalDate registeredDate) {
		this.registeredDate = registeredDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public List<Label> getLabel() {
		return label;
	}

	public void setLabel(List<Label> label) {
		this.label = label;
	}

	public Set<Note> getCollaboratedNotes() {
		return collaboratedNotes;
	}

	public void setCollaboratedNotes(Set<Note> collaboratedNotes) {
		this.collaboratedNotes = collaboratedNotes;
	}
	
	
	
	
	
}
