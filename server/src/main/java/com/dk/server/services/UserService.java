package com.dk.server.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dk.server.models.Theme;
import com.dk.server.models.Token;
import com.dk.server.models.User;
import com.dk.server.repositories.TokenRepository;
import com.dk.server.repositories.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if( user == null ) {
			throw new UsernameNotFoundException("no such user");
		} else {
			return user;
		}
	}
	
	@PostConstruct
	public void makeMockData() {
		if( this.tokenRepository.count() == 0 ) {
			makeMockTokens();
		}
		if( this.userRepository.count() == 0 ) {
			makeMockUsers();
		}
	}
	
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	private void makeMockTokens() {
		this.tokenRepository.deleteAll();

		Token monkey = new Token.Builder()
							  .name("Monkey")
							  .url("https://charity.cs.uwlax.edu/assets/avatars/avatar143.gif")
							  .build();
		tokenRepository.save(monkey);
		
		Token scienceBoy = new Token.Builder()
							  .name("Science Boy")
							  .url("https://charity.cs.uwlax.edu/assets/avatars/avatar124.gif")
							  .build();
		tokenRepository.save(scienceBoy);

		Token captainAmerica = new Token.Builder()
							  .name("Captain America")
							  .url("https://charity.cs.uwlax.edu/assets/avatars/avatar57.gif")
							  .build();
		tokenRepository.save(captainAmerica);

		Token waterWheel = new Token.Builder()
							  .name("Water Wheel")
							  .url("https://charity.cs.uwlax.edu/assets/avatars/avatar92.gif")
							  .build();
		tokenRepository.save(waterWheel);
	}

	private void makeMockUsers() {	
		this.userRepository.deleteAll();
		Theme theme = new Theme.Builder()
				.color("#FF0000")
				.computerToken(this.tokenRepository.findByName("Monkey"))
				.playerToken(this.tokenRepository.findByName("Science Boy"))
				.build();

		String hashedPassword = encoder.encode("123");
		//String hashedPassword = "123";
		User tu = new User.Builder()
				.roles( Arrays.asList( "USER", "ADMIN" ) )
				.password( hashedPassword )
				.username( "dakota@test.com" )
				.defaults(theme)
				.isEnabled( true )
				.isAccountNonExpired( true )
				.isAccountNonLocked(true)
				.isCredentialsNonExpired(true)
				.build();
		userRepository.save( tu );
	
		User tu2 = new User.Builder()
				.roles( Arrays.asList( "USER" ) )
				.password( hashedPassword )
				.username( "other@test.com" )
				.defaults(theme)
				.isEnabled( true )
				.isAccountNonExpired( true )
				.isAccountNonLocked(true)
				.isCredentialsNonExpired(true)
				.build();
		userRepository.save( tu2 );
	}

}

