package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "VARIABLESSISTEMA")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VariablesSistema implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "NOMBRE", length = 100)
    private String nombre;

    @Column(name = "CONTENIDO", length = 3000)
    private String contenido;

}