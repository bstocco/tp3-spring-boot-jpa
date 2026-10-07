package tp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tp.ListaPrecio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class ListaPrecio extends AuditoriaApp {
    @Column(nullable = false)
    private String codigo;
    @Column(nullable = false)
    private String denominacion;
}