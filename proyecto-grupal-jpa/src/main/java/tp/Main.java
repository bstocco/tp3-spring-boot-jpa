package tp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto-grupal-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Crear Usuario
            Usuario usuarioMock = new Usuario();
            usuarioMock.setUsuario("admin");
            usuarioMock.setClave("1234");
            usuarioMock.setNombre("Juan");
            usuarioMock.setApellido("Perez");
            em.persist(usuarioMock);

            // Crear CondicionIva
            CondicionIva condicionIva = new CondicionIva();
            condicionIva.setDenominacion("Consumidor Final");
            condicionIva.setFechaAlta(new Date());
            condicionIva.setFechaModificacion(new Date());
            condicionIva.setUsuarioCarga(usuarioMock);
            condicionIva.setUsuarioModificacion(usuarioMock);
            em.persist(condicionIva);

            // Crear PuntoVenta
            PuntoVenta puntoVenta = new PuntoVenta();
            puntoVenta.setNumero(10);
            puntoVenta.setDescripcion("Local Mendoza");
            puntoVenta.setTipoEmision("Factura Electronica");
            puntoVenta.setDomicilioComercial("San Martin 500");
            puntoVenta.setFechaAlta(new Date());
            puntoVenta.setFechaModificacion(new Date());
            puntoVenta.setUsuarioCarga(usuarioMock);
            puntoVenta.setUsuarioModificacion(usuarioMock);
            em.persist(puntoVenta);

            // Crear Articulo
            Articulo articulo = new Articulo();
            articulo.setCodigo("A1");
            articulo.setDenominacion("Producto Test");
            articulo.setFechaAlta(new Date());
            articulo.setFechaModificacion(new Date());
            articulo.setUsuarioCarga(usuarioMock);
            articulo.setUsuarioModificacion(usuarioMock);
            em.persist(articulo);

            // Crear ListaPrecio
            ListaPrecio lista = new ListaPrecio();
            lista.setCodigo("L1");
            lista.setDenominacion("Lista General");
            lista.setFechaAlta(new Date());
            lista.setFechaModificacion(new Date());
            lista.setUsuarioCarga(usuarioMock);
            lista.setUsuarioModificacion(usuarioMock);
            em.persist(lista);

            // Crear ListaPrecioArticulo
            ListaPrecioArticulo lpa = new ListaPrecioArticulo();
            lpa.setArticulo(articulo);
            lpa.setListaPrecio(lista);
            lpa.setPrecioVenta(12500.0);
            lpa.setFechaAlta(new Date());
            lpa.setFechaModificacion(new Date());
            lpa.setUsuarioCarga(usuarioMock);
            lpa.setUsuarioModificacion(usuarioMock);
            em.persist(lpa);

            // Crear FacturaVenta
            FacturaVenta factura = new FacturaVenta();
            factura.setNumero(1001L);
            factura.setFechaEmision(new Date());
            factura.setPuntoVenta(puntoVenta);
            factura.setCondicionIva(condicionIva);

            factura.setImporteTotal(25000.0);
            factura.setImporteCobrado(25000.0);
            factura.setImporteSaldo(0.0);

            factura.setEstado("PAGADA");
            factura.setFechaAlta(new Date());
            factura.setFechaModificacion(new Date());
            factura.setUsuarioCarga(usuarioMock);
            factura.setUsuarioModificacion(usuarioMock);
            factura.setDetalles(new ArrayList<>());

            // Crear Detalles
            FacturaVentaDetalle detalle1 = new FacturaVentaDetalle();
            detalle1.setCantidad(2.0);
            detalle1.setPrecioUnitario(12500.0);
            detalle1.setImporteSubtotal(25000.0);
            detalle1.setListaPrecioArticulo(lpa);

            detalle1.setFactura(factura);
            factura.getDetalles().add(detalle1);

            em.persist(factura);

            em.getTransaction().commit();
            System.out.println("¡Éxito! Factura y detalles guardados en cascada.");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}