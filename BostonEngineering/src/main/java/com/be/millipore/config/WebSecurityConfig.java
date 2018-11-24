package com.be.millipore.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.be.millipore.security.JwtAuthenticationEntryPoint;
import com.be.millipore.security.JwtAuthenticationFilter;

@Configuration

//Enable security configuration. This annotation denotes configuration for spring security.
@EnableWebSecurity

/*
 * In this line, we
 * activate @PreFilter, @PreAuthorize, @PostFilter, @PostAuthorize annotations
 * on any spring beans in the context.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource(name = "userService")
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

	@Bean
	public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationFilter();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * We do not need csrf(Cross-site request forgery) protection because our tokens
		 * are immune to it.
		 */
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
				/*
				 * make sure we use stateless session; session won't be used to store user's
				 * state.
				 */
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
				.antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources",
						"/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**")
				.permitAll()
				.antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
						"/**/*.css", "/**/*.js")
				.permitAll()
				.antMatchers("/boston/user/email/**", "/login/**", "/boston/", "/bootstrap/**", "/jquery/**", "/css/**",
						" /js/**", "/images/**", "/boston/user", "/boston/user/**")
				.permitAll().antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated().and()
				.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}