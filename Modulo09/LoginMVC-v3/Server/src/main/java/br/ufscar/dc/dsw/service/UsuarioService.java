package br.ufscar.dc.dsw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufscar.dc.dsw.authentication.JwtTokenService;
import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.dto.CriaUsuario;
import br.ufscar.dc.dsw.dto.LoginUsuario;
import br.ufscar.dc.dsw.dto.RecuperaJwtToken;
import br.ufscar.dc.dsw.security.UsuarioDetails;

@Service
public class UsuarioService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private IUsuarioDAO dao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecuperaJwtToken authenticateUser(LoginUsuario usuario) {

        // Cria um objeto de autenticação com o email e a senha do usuário
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(usuario.username(), usuario.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UsuarioDetails usuarioDetails = (UsuarioDetails) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
       return new RecuperaJwtToken(jwtTokenService.generateToken(usuarioDetails));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método responsável por criar um usuário
    public void createUser(CriaUsuario u) {

        // Cria um novo usuário com os dados fornecidos
        Usuario usuario = new Usuario();
        usuario.setUsername(u.username());
        usuario.setPassword(encoder.encode(u.password()));
        usuario.setRole(u.role());
        usuario.setEnabled(true);
        
        // Salva o novo usuário no banco de dados
        dao.save(usuario);
    }
}
