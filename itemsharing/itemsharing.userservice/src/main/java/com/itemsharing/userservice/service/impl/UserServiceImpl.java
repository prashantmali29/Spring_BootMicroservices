package com.itemsharing.userservice.service.impl;

import java.security.Security;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.catalina.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.itemsharing.userservice.model.Role;
import com.itemsharing.userservice.model.User;
import com.itemsharing.userservice.model.UserRole;
import com.itemsharing.userservice.repository.UserRepository;
import com.itemsharing.userservice.service.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserRepository repository;

	@Override
	public User createUser(User user) {
		
		User localUser = repository.findByUsername(user.getUsername());
		
		if(localUser != null) {
			logger.info("User with username {} already exist",user.getUsername());
		}else {
			Set<UserRole> userRole = new HashSet<>();
			Role role = new Role();
			role.setId(1);
			userRole.add(new UserRole(user, role));
			user.getUserRoles().addAll(userRole);
			
			Date date = new Date();
			user.setJoinDate(date);
			
			String password= new BCryptPasswordEncoder().encode(user.getPassword());
			user.setPassword(password);
			localUser = repository.save(user);
		}
		
		return localUser;
	}

	@Override
	public User getUserByName(String userName) {
		return repository.findByUsername(userName);
	}

}
