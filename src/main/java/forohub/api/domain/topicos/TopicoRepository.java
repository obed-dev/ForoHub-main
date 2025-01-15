package forohub.api.domain.topicos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TopicoRepository  extends JpaRepository<Topico, Long> {
    Optional<Topico> findByTitulo(String titulo);

    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    Page<Topico> findByStatusTrue(Pageable paginacion);
}
