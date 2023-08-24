package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "DOCUMENTOXDIRECCION", schema = "SA")
public class DocumentoXDireccion {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "DOCUMENTOID")
    private String documentoId;

    @Column(name = "DIRECCIONID")
    private String direccionId;

    @Column(name = "DIRECCIONCOMPLETA", length = 2000)
    private String direccionCompleta;

    @Column(name = "TELEFONO", length = 20)
    private String telefono;
}
