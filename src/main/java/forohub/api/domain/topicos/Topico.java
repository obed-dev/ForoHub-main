package forohub.api.domain.topicos;

import forohub.api.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "topico")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaDeCreacion;
    private boolean status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Usuario autor;
    private String curso;


    // Getters y setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(LocalDateTime fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Topico(Long id, String titulo, String mensaje, LocalDateTime fechaDeCreacion, boolean status, Usuario autor, String curso) {
        this.id = id;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaDeCreacion = fechaDeCreacion;
        this.status = status;
        this.autor = autor;
        this.curso = curso;
    }



    public void actualizarDatos(ActualizarTopicoDTO actualizarTopicoDTO) {
        if (actualizarTopicoDTO.titulo() != null) {
            this.titulo = actualizarTopicoDTO.titulo();
        }
        if (actualizarTopicoDTO.mensaje() != null) {
            this.mensaje = actualizarTopicoDTO.mensaje();
        }
        this.fechaDeCreacion = LocalDateTime.now();
        if (actualizarTopicoDTO.curso() != null) {
            this.curso = actualizarTopicoDTO.curso();
        }
    }


    public void desactivarTopico() {
        this.status = false;
    }

    public void activarTopico() {
        this.status = true;
    }

}
