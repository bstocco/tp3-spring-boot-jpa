package tp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tp.FacturaVentaDetalle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class FacturaVentaDetalle extends EntityId {
    @ManyToOne
    @JoinColumn(nullable = false)
    private FacturaVenta factura;
    @ManyToOne
    @JoinColumn(nullable = false)
    private ListaPrecioArticulo listaPrecioArticulo;
    private String descripcion;
    @Column(nullable = false)
    private double cantidad;
    @Column(nullable = false)
    private double precioUnitario;
    private double porcentajeBonificacion;
    private double importeNeto;
    private double importeIva;
    @Column(nullable = false)
    private double importeSubtotal;
}