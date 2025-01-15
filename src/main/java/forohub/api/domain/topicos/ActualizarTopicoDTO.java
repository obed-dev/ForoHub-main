package forohub.api.domain.topicos;

import forohub.api.domain.usuarios.Usuario;
import forohub.api.domain.usuarios.UsuarioDetalleDTO;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ActualizarTopicoDTO(
        @NotNull
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        boolean status,
        UsuarioDetalleDTO autor,
        String curso) {
}
