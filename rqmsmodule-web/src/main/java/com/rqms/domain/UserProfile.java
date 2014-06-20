package com.rqms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.rqms.utilities.FieldMatch;



@Entity
@Table(name = "userprofile")
@NamedQuery(name = "UserProfile.findUserByUserID", query = "SELECT usr  FROM UserProfile  as usr WHERE usr.userLoginID = ?")
@FieldMatch.List({ @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"), })
public class UserProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	private long id;

	@Column(length = 1000)
	@NotBlank(message = "First Name cannot be empty")
	@Size(min = 2, message = "First name is too small")
	private String firstName;

	@Column(length = 1000)
	@NotBlank(message = "Last Name cannot be empty")
	@Length(min = 2, max = 50, message = "LastName should be 2 to 50 Characters size")
	private String lastName;

	@Column(length = 1000)
	private String middleName;

	@Column(length = 1000)
	@Pattern(regexp = "[0-9]{3}-[0-9]{2}-[0-9]{4}", message = "Invalid SSN format")
	private String SSN;

	@Column(length = 1000)
	@NotBlank(message = "Address1 cannot be empty")
	private String address1;

	@Column(length = 1000)
	private String address2;

	@Column(length = 1000)
	@NotBlank(message = "City cannot be empty")
	private String city;

	@Column(length = 1000)
	@NotBlank(message = "State cannot be empty")
	@Length(min = 2, max = 100, message = "State should be 2   Characters size")
	private String State;

	@Column(length = 1000)
	@NotBlank(message = "ZipCode cannot be empty")
	private String zipCode;
	
	@Column(length = 15)
	private String phoneNumber;

	@Column(length = 1000)
	@NotBlank(message = "Email cannot be empty")
	@org.hibernate.validator.constraints.Email(message = "Invalid Email Format")
	private String email;

	@Column(length = 1000)
	@NotBlank(message = "Login Name cannot be empty")
	private String userLoginID;

	@Column(length = 1000)
	@NotBlank(message = "Password cannot be empty")
	private String password;

	@Column(length = 1000)
	@Transient
	private String confirmPassword;

	private Integer system;

	@Column(length = 1000)
	@NotBlank(message = "Theme cannot be empty")
	private String theme;

	@Column(name = "userPhoto")
	@Lob
	private byte[] userPhoto;

	@Temporal(TemporalType.DATE)
	@Past(message = "Date of birth should be past date")
	private Date DOB;
   private Integer roleid;
  
   
   public Integer getRoleid() {
	return roleid;
}

public void setRoleid(Integer roleid) {
	this.roleid = roleid;
}

public Date getStampdate() {
	return stampdate;
}

public void setStampdate(Date stampdate) {
	this.stampdate = stampdate;
}

public String getWebsite() {
	return website;
}

public void setWebsite(String website) {
	this.website = website;
}

private Date stampdate;
   @Column(length = 1000)
private String website;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	public Date getDOB() {
		return DOB;
	}

	public void setDOB(Date dOB) {
		DOB = dOB;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserLoginID() {
		return userLoginID;
	}

	public void setUserLoginID(String userLoginID) {
		this.userLoginID = userLoginID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSystem() {
		return system;
	}

	public void setSystem(Integer system) {
		this.system = system;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public byte[] getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(byte[] userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/*public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}*/

}
