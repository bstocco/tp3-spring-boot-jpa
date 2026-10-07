package tp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tp.PuntoVenta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class PuntoVenta extends AuditoriaApp {
    @Column(nullable = false)
    private int numero;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private String tipoEmision;
    @Column(nullable = false)
    private String domicilioComercial;
}
