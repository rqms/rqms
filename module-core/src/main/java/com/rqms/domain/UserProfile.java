package com.rqms.domain;

// default package
// Generated May 28, 2014 12:44:16 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * UserProfile generated by hbm2java
 */
@Entity
@Table(name = "userprofile", uniqueConstraints = @UniqueConstraint(columnNames = "userloginid"))
public class UserProfile implements java.io.Serializable {

	private long id;
	private Date dob;
	private String ssn;
	private String state;
	private String address1;
	private String address2;
	private String city;
	private String email;
	private String firstname;
	private String lastname;
	private String middlename;
	private String password;
	private Integer roleid;
	private Date stampdate;
	private Integer system;
	private String theme;
	private String userloginid;
	private Long userphoto;
	private String website;
	private String zipcode;
	private Set<Applicantfeedback> applicantfeedbacks = new HashSet<Applicantfeedback>(
			0);

	public UserProfile() {
	}

	public UserProfile(long id, String state, String address1, String city,
			String email, String firstname, String lastname, String password,
			String theme, String userloginid, String zipcode) {
		this.id = id;
		this.state = state;
		this.address1 = address1;
		this.city = city;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.theme = theme;
		this.userloginid = userloginid;
		this.zipcode = zipcode;
	}

	public UserProfile(long id, Date dob, String ssn, String state,
			String address1, String address2, String city, String email,
			String firstname, String lastname, String middlename,
			String password, Integer roleid, Date stampdate, Integer system,
			String theme, String userloginid, Long userphoto, String website,
			String zipcode, Set<Applicantfeedback> applicantfeedbacks) {
		this.id = id;
		this.dob = dob;
		this.ssn = ssn;
		this.state = state;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.middlename = middlename;
		this.password = password;
		this.roleid = roleid;
		this.stampdate = stampdate;
		this.system = system;
		this.theme = theme;
		this.userloginid = userloginid;
		this.userphoto = userphoto;
		this.website = website;
		this.zipcode = zipcode;
		this.applicantfeedbacks = applicantfeedbacks;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dob", length = 13)
	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Column(name = "ssn")
	public String getSsn() {
		return this.ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	@Column(name = "state", nullable = false, length = 50)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "address1", nullable = false)
	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(name = "address2")
	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Column(name = "city", nullable = false)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "email", nullable = false)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "firstname", nullable = false)
	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Column(name = "lastname", nullable = false, length = 50)
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name = "middlename")
	public String getMiddlename() {
		return this.middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "roleid")
	public Integer getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "stampdate", length = 29)
	public Date getStampdate() {
		return this.stampdate;
	}

	public void setStampdate(Date stampdate) {
		this.stampdate = stampdate;
	}

	@Column(name = "system")
	public Integer getSystem() {
		return this.system;
	}

	public void setSystem(Integer system) {
		this.system = system;
	}

	@Column(name = "theme", nullable = false)
	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Column(name = "userloginid", unique = true, nullable = false)
	public String getUserloginid() {
		return this.userloginid;
	}

	public void setUserloginid(String userloginid) {
		this.userloginid = userloginid;
	}

	@Column(name = "userphoto")
	public Long getUserphoto() {
		return this.userphoto;
	}

	public void setUserphoto(Long userphoto) {
		this.userphoto = userphoto;
	}

	@Column(name = "website")
	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Column(name = "zipcode", nullable = false)
	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userprofile")
	public Set<Applicantfeedback> getApplicantfeedbacks() {
		return this.applicantfeedbacks;
	}

	public void setApplicantfeedbacks(Set<Applicantfeedback> applicantfeedbacks) {
		this.applicantfeedbacks = applicantfeedbacks;
	}

}
