package br.ufscar.dc.dsw.domain;

public class Usuario {

	private Long id;
	
	private String username;
	
	private String password;

	private String role;

	private JwtToken token;
		
	public Usuario(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.token = new JwtToken("");
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public JwtToken getToken() {
		return token;
	}

	public void setToken(JwtToken token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return username + ", " + password;
	}
}