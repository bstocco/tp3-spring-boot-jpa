package tp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tp.Rubro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Rubro extends AuditoriaApp {
    @Column(nullable = false)
    private String denominacion;
    @Column(nullable = false)
    private Integer codigo;
}
