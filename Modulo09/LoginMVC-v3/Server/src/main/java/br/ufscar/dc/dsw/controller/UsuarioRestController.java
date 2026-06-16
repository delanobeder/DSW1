package br.ufscar.dc.dsw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.dc.dsw.dto.CriaUsuario;
import br.ufscar.dc.dsw.dto.LoginUsuario;
import br.ufscar.dc.dsw.dto.RecuperaJwtToken;
import br.ufscar.dc.dsw.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioService userService;

    @PostMapping("/login")
    public ResponseEntity<RecuperaJwtToken> authenticateUser(@RequestBody LoginUsuario loginUserDto) {
        RecuperaJwtToken token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CriaUsuario createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/jwt")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/jwt/user")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return new ResponseEntity<>("Usuário autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/jwt/admin")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }

}