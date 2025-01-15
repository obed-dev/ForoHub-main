package forohub.api.infraestructure.security;

import forohub.api.domain.usuarios.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtener el token del header
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            var token = authHeader.replace("Bearer ", "");
            try {
                // Extraer el nombre de usuario desde el token
                var nombreUsuario = tokenService.getSubject(token);

                if (nombreUsuario != null) {
                    // Buscar el usuario en el repositorio
                    var usuario = usuarioRepository.findByUserName(nombreUsuario)
                            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

                    // Autenticar al usuario
                    var authentication = new UsernamePasswordAuthenticationToken(
                            usuario,
                            usuario.getPassword(),
                            usuario.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (UsernameNotFoundException e) {
                // Responder con un 404 si el usuario no es encontrado
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
                response.getWriter().write("Usuario no encontrado");
                return;
            } catch (Exception e) {
                // Responder con un 401 si ocurre un problema con el token
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                response.getWriter().write("Token no válido o ausente");
                return;
            }
        }

        // Continuar con el filtro si no hay errores
        filterChain.doFilter(request, response);
    }

}
