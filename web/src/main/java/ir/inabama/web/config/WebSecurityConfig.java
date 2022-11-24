package ir.inabama.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.passwordEncoder(passwordEncoder())
				.withUser("admin")
				.password(passwordEncoder().encode("asdf/1234"))
				.roles("ADMIN")
				.and()
				.passwordEncoder(passwordEncoder())
				.withUser("user")
				.password(passwordEncoder().encode("pass"))
				.roles("BASIC");
	}

	@Bean
	// Method
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Secure the endpoins with HTTP Basic authentication
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/user/**")
				.hasAnyRole("USER", "ADMIN")
				.antMatchers("/admin/**")
				.hasRole("ADMIN")
				.and()
				.formLogin()
				.permitAll()
				.loginPage("/login")
				.usernameParameter("username")
				.and()
				.logout().logoutSuccessUrl("/")
				.logoutRequestMatcher(
						new AntPathRequestMatcher("/logout"))
				.permitAll();
	}

}