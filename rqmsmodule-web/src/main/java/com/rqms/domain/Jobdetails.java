package com.rqms.domain;

// default package
// Generated May 28, 2014 12:44:16 PM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;



/**
 * Jobdetails generated by hbm2java
 */
@Entity
@Table(name = "jobdetails")
public class Jobdetails implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1708539924648057423L;
	@Id
	@GeneratedValue(generator = "JobId")
	@GenericGenerator(name = "JobId", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "JobId"),
			@Parameter(name = "initial_value", value = "1"),
			@Parameter(name = "increment_size", value = "1") })
	private int jobid;
	private String jobrequesteduserid;
	private String jobtitle;
	private String jobdescription;
	private String skillsets;

	private String joblocation;
	private String jobtype;
	private Integer statusid;
	private Date stampdate;

	@Enumerated(EnumType.STRING)
	private JobState jobstate;

	@Column(length = 1000)
	private String companyName;

	@Column(length = 1000)
	private String companyUrl;

	private BigDecimal jobsalary;

	@Column(length = 500)
	private String jobstreetaddress;

	@Column(length = 15)
	private String jobzipCode;

	public String getJobstreetaddress() {
		return jobstreetaddress;
	}

	public void setJobstreetaddress(String jobstreetaddress) {
		this.jobstreetaddress = jobstreetaddress;
	}

	public String getJobzipCode() {
		return jobzipCode;
	}

	public void setJobzipCode(String jobzipCode) {
		this.jobzipCode = jobzipCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}

	public JobState getJobstate() {
		return jobstate;
	}

	public void setJobstate(JobState jobstate) {
		this.jobstate = jobstate;
	}

	private Set<Attachment> attachments = new HashSet<Attachment>(0);
	 
	private Set<Candidateprofile> candidates = new HashSet<Candidateprofile>(0);

	public Jobdetails() {
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "job_candidate", joinColumns = { 
			@JoinColumn(name = "JobId", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "candidateid", 
					nullable = false, updatable = false) })
	public Set<Candidateprofile> getCandidates() {
		return candidates;
	}

	public void setCandidates(Set<Candidateprofile> candidates) {
		this.candidates = candidates;
	}

	public Jobdetails(String jobrequesteduserid, String jobtitle,
			String jobdescription, String skillsets, String companyname,
			String joblocation, String jobtype) {
		this.jobid = jobid;
		this.jobrequesteduserid = jobrequesteduserid;
		this.jobtitle = jobtitle;
		this.jobdescription = jobdescription;
		this.skillsets = skillsets;
		this.companyName = companyname;
		this.joblocation = joblocation;
		this.jobtype = jobtype;
	}

	public Jobdetails(int jobid, String jobrequesteduserid, String jobtitle,
			String jobdescription, String skillsets, String companyname,
			String joblocation, String jobtype, Integer statusid,
			Date stampdate, Set<Attachment> attachments,
			Set<Candidateprofile> candidates) {
		this.jobid = jobid;
		this.jobrequesteduserid = jobrequesteduserid;
		this.jobtitle = jobtitle;
		this.jobdescription = jobdescription;
		this.skillsets = skillsets;
		this.companyName = companyname;
		this.joblocation = joblocation;
		this.jobtype = jobtype;
		this.statusid = statusid;
		this.stampdate = stampdate;
		this.attachments = attachments;
		this.candidates= candidates;
		
	}

	@Id
	@GenericGenerator(name = "id", strategy = "increment")
	@GeneratedValue(generator = "id")
	@Column(name = "jobid", unique = true, nullable = false)
	public int getJobid() {
		return this.jobid;
	}

	public void setJobid(int jobid) {
		this.jobid = jobid;
	}

	@Column(name = "jobrequesteduserid", length = 100)
	public String getJobrequesteduserid() {
		return this.jobrequesteduserid;
	}

	public void setJobrequesteduserid(String jobrequesteduserid) {
		this.jobrequesteduserid = jobrequesteduserid;
	}

	@Column(name = "jobtitle", nullable = false, length = 1000)
	public String getJobtitle() {
		return this.jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	@Column(name = "jobdescription", nullable = false, length = 10000)
	public String getJobdescription() {
		return this.jobdescription;
	}

	public void setJobdescription(String jobdescription) {
		this.jobdescription = jobdescription;
	}

	@Column(name = "skillsets", nullable = false, length = 200)
	public String getSkillsets() {
		return this.skillsets;
	}

	public void setSkillsets(String skillsets) {
		this.skillsets = skillsets;
	}

	@Column(name = "joblocation", length = 200)
	public String getJoblocation() {
		return this.joblocation;
	}

	public void setJoblocation(String joblocation) {
		this.joblocation = joblocation;
	}

	@Column(name = "jobtype", length = 200)
	public String getJobtype() {
		return this.jobtype;
	}

	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
	}

	@Column(name = "statusid")
	public Integer getStatusid() {
		return this.statusid;
	}

	public void setStatusid(Integer statusid) {
		this.statusid = statusid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "stampdate", length = 100)
	public Date getStampdate() {
		return this.stampdate;
	}

	public void setStampdate(Date stampdate) {
		this.stampdate = stampdate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jobdetails")
	public Set<Attachment> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}

	
	public BigDecimal getJobsalary() {
		return jobsalary;
	}

	public void setJobsalary(BigDecimal jobsalary) {
		this.jobsalary = jobsalary;
	}

}