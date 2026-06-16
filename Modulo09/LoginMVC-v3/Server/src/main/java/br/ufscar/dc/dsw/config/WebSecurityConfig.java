package br.ufscar.dc.dsw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.ufscar.dc.dsw.authentication.UsuarioAuthenticationFilter;
import br.ufscar.dc.dsw.security.UsuarioDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
    private UsuarioAuthenticationFilter userAuthenticationFilter;

	public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/usuarios/login", // Url que usaremos para fazer login
            "/usuarios", // Url que usaremos para criar um usuário
    };

    // Endpoints que requerem autenticação para serem acessados
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/usuarios/jwt", 
    };

    // Endpoints que só podem ser acessador por usuários (não administradores)
    public static final String [] ENDPOINTS_USER = {
            "/usuarios/jwt/user"
    };

    // Endpoints que só podem ser acessador por usuários com permissão de administrador
    public static final String [] ENDPOINTS_ADMIN = {
            "/usuarios/jwt/admin"
    };

	@Bean
	public UserDetailsService userDetailsService() {
		return new UsuarioDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((authz) -> authz
						.requestMatchers("/", "/index", "/error").permitAll()
						.requestMatchers("/login/**", "/js/**", "/css/**", "/image/**", "/webjars/**").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/user/**").hasRole("USER")
						.requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
						.requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
						.requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMIN")
						.requestMatchers(ENDPOINTS_USER).hasRole("USER")
						.anyRequest().authenticated()
				).formLogin((form) -> form
				.loginPage("/login")
				.permitAll()
				).logout((logout) -> logout
				.logoutSuccessUrl("/").
				permitAll()
		).addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		http.csrf(csrf -> csrf.disable());

		return http.build();
	}
}
