package forohub.api.domain.topicos;

import forohub.api.domain.usuarios.Usuario;
import forohub.api.domain.usuarios.UsuarioDetalleDTO;

import java.time.LocalDateTime;

public record ListadoTopicoDTO(Long id, String titulo, String mensaje, LocalDateTime fechaDeCreacion, UsuarioDetalleDTO  autor, String curso) {

    public ListadoTopicoDTO(Topico topico) {
        this(topico.getId(),topico.getTitulo(),topico.getMensaje(),topico.getFechaDeCreacion(),new UsuarioDetalleDTO(topico.getAutor().getUsername()),topico.getCurso() );
    }
}
