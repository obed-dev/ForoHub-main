package forohub.api.domain.topicos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record RegistroTopicoDTO(
        @NotBlank
        @NotNull
        String titulo,
        @NotBlank
        @NotNull
        String mensaje,
        @NotBlank
        @NotNull
        String curso
) {
}
