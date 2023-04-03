package com.dk.server.services;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dk.server.models.User;
import com.dk.server.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if( user == null ) {
			throw new UsernameNotFoundException("no such user");
		} else {
			System.out.println( user.getUsername() );
			System.out.println( user.getPassword() );
			return user;
		}
	}
	
	@PostConstruct
	public void makeMockUsers() {
		if( this.userRepository.count() > 0 ) return;		
		this.userRepository.deleteAll();
		String hashedPassword = encoder.encode("123");
		User tu = new User.Builder()
				.roles( Arrays.asList( "USER", "ADMIN" ) )
				.password( hashedPassword )
				.username( "kenny" )
				.isEnabled( true )
				.isAccountNonExpired( true )
				.isAccountNonLocked(true)
				.isCredentialsNonExpired(true)
				.build();
		userRepository.save( tu );
	
		User tu2 = new User.Builder()
				.roles( Arrays.asList( "USER" ) )
				.password( hashedPassword )
				.username( "hunt" )
				.isEnabled( true )
				.isAccountNonExpired( true )
				.isAccountNonLocked(true)
				.isCredentialsNonExpired(true)
				.build();
		userRepository.save( tu2 );
	}

}

