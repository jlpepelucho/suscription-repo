package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "COBERTURATEXTO")
public class CoberturaTexto {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Lob
    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "COBERTURAID")
    private String coberturaid;

    @Size(max = 16)
    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioactualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDate fechaactualiza;

    @Column(name = "ORDEN")
    private Integer orden;

    @Size(max = 300)
    @Column(name = "TITULO", length = 300)
    private String titulo;

    @Column(name = "ANEXO")
    private Boolean anexo;

}