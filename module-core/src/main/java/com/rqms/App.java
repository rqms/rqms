package com.rqms;

import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rqms.domain.UserProfile;
import com.rqms.service.CRUDService;


public class App {

	public static void main(String[] args) {
		System.out.println("load context");
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		CRUDService CRUDService = (CRUDService) context.getBean("CRUDService");
//
		List<UserProfile> dbUser = CRUDService.getAll(UserProfile.class);
//
//		// Populate the Spring User object with details from the dbUser
//		// Here we just pass the username, password, and access level
//		// getAuthorities() will translate the access level to the correct
//		// role type
         System.out.println(dbUser.size());
//		
		
		context.close();
		
	}

}
