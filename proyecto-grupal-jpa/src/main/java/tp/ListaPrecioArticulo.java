package tp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tp.ListaPrecioArticulo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class ListaPrecioArticulo extends AuditoriaApp {
    @ManyToOne
    @JoinColumn(nullable = false)
    private ListaPrecio listaPrecio;
    @Column(nullable = false)
    private double precioVenta;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Articulo articulo;
}