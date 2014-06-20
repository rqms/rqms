package com.rqms.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Tabbox;

import com.rqms.comp.RQMSTabUtil;

public class RQMSActions {
	private static final Logger LOGGER = LoggerFactory.getLogger(RQMSActions.class);
	public static void doAddUser(Tabbox mainTab) {
		LOGGER.debug("Add User method called");
		RQMSTabUtil.openNewTab("User List", "user_profile_list.zul", mainTab);
	}
	
	public static void doAddJob(Tabbox mainTab) {
		LOGGER.debug("Add Job method called");
		RQMSTabUtil.openNewTab("Job List", "add_job_list.zul", mainTab);
	}
	
	public static void doAddFeedback(Tabbox mainTab) {
		LOGGER.debug("Add Job method called");
		RQMSTabUtil.openNewTab("Feedback List", "add_feedback_list.zul", mainTab);
	}
	
	public static void doAddRole(Tabbox mainTab) {
		LOGGER.debug("Add Job method called");
		RQMSTabUtil.openNewTab("Add Role", "valid_role.zul", mainTab);
	}
	
	public static void doAddSkill(Tabbox mainTab) {
		LOGGER.debug("Add Job method called");
		RQMSTabUtil.openNewTab("Add Skill", "valid_skill.zul", mainTab);
	}
	
	public static void doLogOut() {
		LOGGER.debug("Log out method called");
		Executions.sendRedirect("/j_spring_security_logout");
	}
}
