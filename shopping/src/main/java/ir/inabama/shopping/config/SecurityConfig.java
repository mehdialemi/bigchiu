package ir.inabama.shopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.formLogin()
				.loginPage("/auth/login.html")
				.failureUrl("/auth/login-error.html")
				.and()
				.logout()
				.logoutSuccessUrl("/index.html")
				.and()
				.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/user/**").hasRole("USER")
				.antMatchers("/shared/**").hasAnyRole("ADMIN", "USER")
				.and()
				.exceptionHandling()
				.accessDeniedPage("/403.html");
	}


	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication()
				.withUser(User.withDefaultPasswordEncoder().username("mehdi").password("123456").roles("ADMIN").build())
				.withUser(User.withDefaultPasswordEncoder().username("maryam").password("123456").roles("USER").build())
				.withUser(User.withDefaultPasswordEncoder().username("ted").password("demo").roles("USER", "ADMIN").build());
	}


}