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

/**
 * Jobdetails generated by hbm2java
 */
@Entity
@Table(name = "jobdetails")
public class Jobdetails implements java.io.Serializable {

	private int jobid;
	private String jobrequesteduserid;
	private String jobtitle;
	private String jobdescription;
	private String skillsets;
	private String companyname;
	private String joblocation;
	private String jobtype;
	private Integer statusid;
	private Date stampdate;
	private Set<Attachment> attachments = new HashSet<Attachment>(0);
	private Set<Applicantfeedback> applicantfeedbacks = new HashSet<Applicantfeedback>(
			0);
	private Set<Jobcandidatemap> jobcandidatemaps = new HashSet<Jobcandidatemap>(
			0);

	public Jobdetails() {
	}

	public Jobdetails(int jobid, String jobtitle, String jobdescription,
			String skillsets, String companyname) {
		this.jobid = jobid;
		this.jobtitle = jobtitle;
		this.jobdescription = jobdescription;
		this.skillsets = skillsets;
		this.companyname = companyname;
	}

	public Jobdetails(int jobid, String jobrequesteduserid, String jobtitle,
			String jobdescription, String skillsets, String companyname,
			String joblocation, String jobtype, Integer statusid,
			Date stampdate, Set<Attachment> attachments,
			Set<Applicantfeedback> applicantfeedbacks,
			Set<Jobcandidatemap> jobcandidatemaps) {
		this.jobid = jobid;
		this.jobrequesteduserid = jobrequesteduserid;
		this.jobtitle = jobtitle;
		this.jobdescription = jobdescription;
		this.skillsets = skillsets;
		this.companyname = companyname;
		this.joblocation = joblocation;
		this.jobtype = jobtype;
		this.statusid = statusid;
		this.stampdate = stampdate;
		this.attachments = attachments;
		this.applicantfeedbacks = applicantfeedbacks;
		this.jobcandidatemaps = jobcandidatemaps;
	}

	@Id
	@Column(name = "jobid", unique = true, nullable = false)
	public int getJobid() {
		return this.jobid;
	}

	public void setJobid(int jobid) {
		this.jobid = jobid;
	}

	@Column(name = "jobrequesteduserid", length = 15)
	public String getJobrequesteduserid() {
		return this.jobrequesteduserid;
	}

	public void setJobrequesteduserid(String jobrequesteduserid) {
		this.jobrequesteduserid = jobrequesteduserid;
	}

	@Column(name = "jobtitle", nullable = false, length = 50)
	public String getJobtitle() {
		return this.jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	@Column(name = "jobdescription", nullable = false, length = 100)
	public String getJobdescription() {
		return this.jobdescription;
	}

	public void setJobdescription(String jobdescription) {
		this.jobdescription = jobdescription;
	}

	@Column(name = "skillsets", nullable = false, length = 50)
	public String getSkillsets() {
		return this.skillsets;
	}

	public void setSkillsets(String skillsets) {
		this.skillsets = skillsets;
	}

	@Column(name = "companyname", nullable = false, length = 50)
	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	@Column(name = "joblocation", length = 50)
	public String getJoblocation() {
		return this.joblocation;
	}

	public void setJoblocation(String joblocation) {
		this.joblocation = joblocation;
	}

	@Column(name = "jobtype", length = 10)
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
	@Column(name = "stampdate", length = 29)
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jobdetails")
	public Set<Applicantfeedback> getApplicantfeedbacks() {
		return this.applicantfeedbacks;
	}

	public void setApplicantfeedbacks(Set<Applicantfeedback> applicantfeedbacks) {
		this.applicantfeedbacks = applicantfeedbacks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jobdetails")
	public Set<Jobcandidatemap> getJobcandidatemaps() {
		return this.jobcandidatemaps;
	}

	public void setJobcandidatemaps(Set<Jobcandidatemap> jobcandidatemaps) {
		this.jobcandidatemaps = jobcandidatemaps;
	}

}