package forohub.api.controller;

import forohub.api.domain.usuarios.AutenticacionUsuarioDTO;
import forohub.api.domain.usuarios.Usuario;
import forohub.api.infraestructure.security.DatosJWTToken;
import forohub.api.infraestructure.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid AutenticacionUsuarioDTO autenticacionUsuarioDTO) {
        try {
            Authentication authToken = new UsernamePasswordAuthenticationToken(autenticacionUsuarioDTO.userName(), autenticacionUsuarioDTO.clave());
            var usuarioAutenticado = authenticationManager.authenticate(authToken);
            var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
            return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body("El usuario no existe");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

}
