package com.dk.server;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dk.server.models.User;
import com.dk.server.models.Theme;
import com.dk.server.models.Token;
import com.dk.server.repositories.GameRepository;
import com.dk.server.repositories.TokenRepository;
import com.dk.server.repositories.UserRepository;

@SpringBootApplication
@EnableMongoRepositories
@ComponentScan
public class ServerApplication implements CommandLineRunner {
	
	@Autowired
    GameRepository gameRepo;
	@Autowired
    TokenRepository tokenRepo;
	@Autowired
    UserRepository userRepo;
	@Autowired
	private PasswordEncoder encoder;


	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
	
	/**
	 * Method used to generate initial data into database
	 * @param args
	 */
	public void run(String... args) {
        
        System.out.println("-------------CREATE USERS, TOKENS, THEMES---------------------------\n");
        
        createData();
        
        System.out.println("\n------------------SHOW ALL USERS-----------------------------\n");
        
        showAllUsers();
        
        System.out.println("\n--------------GET USER BY USERNAME-------------------------------\n");
        
        getUsersByUsername("dakota@test.com");      
                
        
        System.out.println("\n------------FINAL COUNT OF USERS-------------------------\n");
        
        findCountOfUsers();
        
        System.out.println("\n------------FINAL COUNT OF GAMES-------------------------\n");
        
        findCountOfGames();
        
        System.out.println("\n-------------------THANK YOU---------------------------");
        
    }
	
	void createData() {		
		System.out.println("Clearing...");
		this.userRepo.deleteAll();
		this.tokenRepo.deleteAll();
		this.gameRepo.deleteAll();

        System.out.println("Data creation started...");
        String hashedPassword = encoder.encode("123");
        
        tokenRepo.save(new Token.Builder()
        		.name("Monkey")
        		.url("./assets/monkey.gif")
        		.build());
        
        tokenRepo.save(new Token.Builder()
        		.name("Elvis")
        		.url("./assets/elvis.gif")
        		.build());
        
        tokenRepo.save(new Token.Builder()
        		.name("Bullet Train")
        		.url("./assets/bullet_train.gif")
        		.build());
        
        tokenRepo.save(new Token.Builder()
        		.name("Western")
        		.url("./assets/western.gif")
        		.build());
        
        Theme theme = new Theme.Builder()
        		.color("#ff0000")
        		.playerToken(tokenRepo.findByName("Monkey"))
        		.computerToken(tokenRepo.findByName("Elvis"))
        		.build();
        
        userRepo.save(new User.Builder()
				.roles( Arrays.asList( "USER", "ADMIN" ) )
				.password( hashedPassword )
				.username( "dakota@test.com" )
				.defaults(theme)
				.isEnabled( true )
				.isAccountNonExpired( true )
				.isAccountNonLocked(true)
				.isCredentialsNonExpired(true)
				.build());
        
        userRepo.save(new User.Builder()
				.roles( Arrays.asList( "USER" ) )
				.password( hashedPassword )
				.username( "other@test.com" )
				.defaults(theme)
				.isEnabled( true )
				.isAccountNonExpired( true )
				.isAccountNonLocked(true)
				.isCredentialsNonExpired(true)
				.build());
        
        userRepo.save(new User.Builder()
				.roles( Arrays.asList( "USER", "ADMIN" ) )
				.password( encoder.encode("111111111") )
				.username( "bilbo@mordor.org" )
				.defaults(theme)
				.isEnabled( true )
				.isAccountNonExpired( true )
				.isAccountNonLocked(true)
				.isCredentialsNonExpired(true)
				.build());
        
        userRepo.save(new User.Builder()
				.roles( Arrays.asList( "USER", "ADMIN" ) )
				.password( encoder.encode("222222222") )
				.username( "frodo@mordor.org" )
				.defaults(theme)
				.isEnabled( true )
				.isAccountNonExpired( true )
				.isAccountNonLocked(true)
				.isCredentialsNonExpired(true)
				.build());
        
        userRepo.save(new User.Builder()
				.roles( Arrays.asList( "USER", "ADMIN" ) )
				.password( encoder.encode("333333333") )
				.username( "samwise@mordor.org" )
				.defaults(theme)
				.isEnabled( true )
				.isAccountNonExpired( true )
				.isAccountNonLocked(true)
				.isCredentialsNonExpired(true)
				.build());

        System.out.println("Data creation complete...");
    }
	
	public void showAllUsers() {
        
        userRepo.findAll().forEach(user -> System.out.println(getUserDetails(user)));
    }
	
	public void getUsersByUsername(String username) {
        System.out.println("Getting user by username: " + username);
        User user = userRepo.findByUsername(username);
        System.out.println(getUserDetails(user));
    }
    
    public void findCountOfUsers() {
        long count = userRepo.count();
        System.out.println("Number of documents in the collection: " + count);
    }
    
    public void findCountOfGames() {
        long count = gameRepo.count();
        System.out.println("Number of documents in the collection: " + count);
    }
	
	public String getUserDetails(User user) {

        System.out.println(
        "Username: " + user.getUsername() + 
        ", \nDefaults | PT:" + user.getDefaults().getPlayerToken().getName() + " | CT: " + user.getDefaults().getComputerToken().getName() +
        ", \nPassword: " + user.getPassword()
        );
        
        return "";
    }
	
	public void updateDefaults(String username) {
        
        // Change to this new value
		Theme theme = new Theme.Builder()
        		.color("#fff000")
        		.playerToken(tokenRepo.findByName("Western"))
        		.computerToken(tokenRepo.findByName("Bullet Train"))
        		.build();
        
        // Find all the items with the category snacks
        User user = userRepo.findByUsername(username);
        user.setDefaults(theme);
        
        // Save all the items in database
        User userUpdated = userRepo.save(user);
        
        if(userUpdated != null)
            System.out.println("Successfully updated " + userUpdated.getUsername() + ".");         
    }

}
