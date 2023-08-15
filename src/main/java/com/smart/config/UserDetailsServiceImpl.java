package com.smart.config;





import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.smart.dao.UserRepository;
import com.smart.entites.User;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
     
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//fetching user from database
		
		User user = userRepository.GetUserByUserName(username);
		
		if(user ==null) {
			
			throw new UsernameNotFoundException("COULD NOT FOUND USER ||");
		}
		
		UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);
		
		return userDetailsImpl;
		
	}
	
	

}

  