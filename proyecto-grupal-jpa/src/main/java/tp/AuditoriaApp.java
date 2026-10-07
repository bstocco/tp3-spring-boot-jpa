package tp;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public abstract class AuditoriaApp extends EntityId {
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaAlta;
    protected Date fechaBaja;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaModificacion;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    protected Usuario usuarioCarga;
    
    @ManyToOne
    protected Usuario usuarioBaja;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    protected Usuario usuarioModificacion;

    @PrePersist
    protected void prePersist() {
        Date ahora = new Date();
        if (fechaAlta == null) fechaAlta = ahora;
        if (fechaModificacion == null) fechaModificacion = ahora;
    }

    @PreUpdate
    protected void preUpdate() {
        fechaModificacion = new Date();
    }
}