package com.rqms.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "validskillsets")
public class ValidSkillSets {

	private int skillId;
	private String skillType;
	private String skillDescription;
	private int groupId;
	private String stampuser;
	private Date stampdate;
	
	@Id
	@GenericGenerator(name="id" , strategy="increment")
	@GeneratedValue(generator="id")
	@Column(name = "skillId", unique = true, nullable = false)
	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	
	@Column(name = "skillType", nullable = false, length = 100)
	public String getSkillType() {
		return skillType;
	}
	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}
	
	@Column(name = "skillDescription", nullable = false, length = 500)
	public String getSkillDescription() {
		return skillDescription;
	}
	public void setSkillDescription(String skillDescription) {
		this.skillDescription = skillDescription;
	}
	
	@Column(name = "groupId", nullable = true, length = 100)
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	@Column(name = "stampuser", nullable = false, length = 100)
	public String getStampuser() {
		return stampuser;
	}
	public void setStampuser(String stampuser) {
		this.stampuser = stampuser;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "stampdate", nullable = false, length = 100)
	public Date getStampdate() {
		return stampdate;
	}
	public void setStampdate(Date stampdate) {
		this.stampdate = stampdate;
	}
}
