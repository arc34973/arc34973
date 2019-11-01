package myLibrary.com.example.myLibraryRest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import myLibrary.com.example.myLibraryRest.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/")
				.access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")

				.antMatchers("/add-user*/**").access("hasRole('ROLE_ADMIN')").antMatchers("/list-user*/**")
				.access("hasRole('ROLE_ADMIN')").antMatchers("/list-book*/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/list-user*/**").access("hasRole('ROLE_ADMIN')")

				.antMatchers("/list-search*/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')").and().formLogin();

		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

}
