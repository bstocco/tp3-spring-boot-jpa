package tp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tp.CondicionIva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class CondicionIva extends AuditoriaApp {
    @Column(nullable = false)
    private int codigoAfip;
    @Column(nullable = false)
    private String denominacion;
}