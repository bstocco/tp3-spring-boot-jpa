package tp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tp.Contacto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Contacto extends EntityId {
    private String email;
    private String telefono;
    private String celular;
}
