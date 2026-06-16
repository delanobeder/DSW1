package br.ufscar.dc.dsw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginMVCApplication {

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(LoginMVCApplication.class, args);
	}

	/* @Bean
	public CommandLineRunner demo(IUsuarioDAO dao, BCryptPasswordEncoder encoder) {
		return (args) -> {
			
			Usuario u1 = new Usuario();
			u1.setUsername("user");
			u1.setPassword(encoder.encode("user"));
			u1.setRole("ROLE_USER");
			u1.setEnabled(true);
			dao.save(u1);
			
			Usuario u2 = new Usuario();
			u2.setUsername("admin");
			u2.setPassword(encoder.encode("admin"));
			u2.setRole("ROLE_ADMIN");
			u2.setEnabled(true);
			dao.save(u2);			 
		};
	} */
}
