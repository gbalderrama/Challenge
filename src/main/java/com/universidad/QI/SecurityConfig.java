package com.universidad.QI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.universidad.QI.services.UserService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	UserService userService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	SecurityFilterChain web(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/panel","/panel/**").hasRole("ADMIN"));
		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers(new AntPathRequestMatcher("/panel/edit?", "GET")).hasRole("ADMIN"));
		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers(new AntPathRequestMatcher("/panel/delete?", "GET")).hasRole("ADMIN"));

		http.authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll());
		
		http.formLogin(login -> login.loginPage("/login")
				.loginProcessingUrl("/loginchk")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/index")
				.permitAll());
        
		http.logout(logout -> logout.logoutUrl("/logout")
                .logoutSuccessUrl("/index")
                .permitAll());
        
		http.csrf(csrf -> csrf.disable());

		return http.build();

	}

}
