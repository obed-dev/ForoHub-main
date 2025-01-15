package forohub.api.controller;

import forohub.api.domain.ValidacionException;
import forohub.api.domain.topicos.*;
import forohub.api.domain.usuarios.Usuario;
import forohub.api.domain.usuarios.UsuarioDetalleDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
@Transactional
public class TopicoController {

    @Autowired
    TopicoRepository topicoRepository;


    @PostMapping
    public ResponseEntity createTopico(@RequestBody @Valid RegistroTopicoDTO registroTopicoDTO) {
        // Verificar si ya existe un tópico con el mismo título y mensaje
        boolean exists = topicoRepository.existsByTituloAndMensaje(
                registroTopicoDTO.titulo(),
                registroTopicoDTO.mensaje()
        );

        if (exists) {
            return ResponseEntity.status(409).body("Ya existe un tópico con el mismo título y mensaje");
        }

        // Obtener el autor del contexto de seguridad
        var usuarioAutenticado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Crear una nueva entidad Topico con los datos del DTO
        Topico topico = new Topico(
                null, // ID se genera automáticamente
                registroTopicoDTO.titulo(),
                registroTopicoDTO.mensaje(),
                LocalDateTime.now(),
                true,
                usuarioAutenticado, // Autor autenticado
                registroTopicoDTO.curso()
        );

        // Guardar el nuevo tópico en la base de datos
        topicoRepository.save(topico);

        // Retornar una respuesta de éxito
        return ResponseEntity.ok("Tópico creado con éxito");
    }

    @GetMapping
    ResponseEntity getTopicos(@PageableDefault(size = 2) Pageable paginacion) {
        var listTopicos = topicoRepository.findByStatusTrue(paginacion);
        return ResponseEntity.ok(listTopicos.map(ListadoTopicoDTO::new));
    }

    @GetMapping("/{id}")
    ResponseEntity getTopicoByID(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new ActualizarTopicoDTO(topico.getId(),topico.getTitulo(),topico.getMensaje(),topico.getFechaDeCreacion(),topico.getStatus(),new UsuarioDetalleDTO(topico.getAutor().getUsername()),topico.getCurso()));
    }

    @PutMapping
    ResponseEntity updateTopico(@RequestBody @Valid ActualizarTopicoDTO actualizarTopicoDTO) {
        var topico = topicoRepository.getReferenceById(actualizarTopicoDTO.id());

        // Validar si el tópico está activo
        if (!topico.getStatus()) {
            throw new ValidacionException("No se puede actualizar un tópico inactivo");
        }

        topico.actualizarDatos(actualizarTopicoDTO);
        return ResponseEntity.ok(new ActualizarTopicoDTO(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaDeCreacion(), topico.getStatus(), new UsuarioDetalleDTO(topico.getAutor().getUsername()), topico.getCurso()));
    }


    @DeleteMapping("/{id}")
    ResponseEntity deleteTopico(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activar")
    ResponseEntity activateTopico(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        topico.activarTopico();
        topicoRepository.save(topico);
        return ResponseEntity.ok().build();
    }

}
