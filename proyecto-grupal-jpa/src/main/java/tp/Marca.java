package tp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tp.Marca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Marca extends AuditoriaApp {
    @Column(nullable = false)
    private String denominacion;
    @Column(nullable = false)
    private Integer codigo;
}