package tp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tp.Domicilio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Domicilio extends EntityId {
    private String nombreCalle;
    private String numeroCalle;
}