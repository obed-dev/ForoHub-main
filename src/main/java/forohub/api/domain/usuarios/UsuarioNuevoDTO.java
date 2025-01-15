package forohub.api.domain.usuarios;

import jakarta.validation.constraints.NotBlank;

public record UsuarioNuevoDTO(
        @NotBlank(message = "El nombre de usuario es obligatorio")
        String userName,
        @NotBlank(message = "La clave es obligatoria")
        String clave) {
}
