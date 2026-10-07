package tp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Date;
import java.util.List;

public class MainConsultas {
        public static void main(String[] args) {

                EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto-grupal-jpa");
                EntityManager em = emf.createEntityManager();

                // Consulta 1
                List<FacturaVenta> facturas = em
                                .createQuery("SELECT fv FROM tp.FacturaVenta fv", FacturaVenta.class)
                                .getResultList();

                System.out.println("Cantidad de facturas: " + facturas.size());

                for (FacturaVenta factura : facturas) {
                        System.out.println(
                                        "Factura: " + factura.getNumero()
                                                        + " Estado: " + factura.getEstado()
                                                        + " Importe: " + factura.getImporteTotal());
                }

                // Consulta 2
                List<Object[]> resultados = em
                                .createQuery("SELECT fv.numero, fv.fechaEmision, fv.importeTotal "
                                                + "FROM tp.FacturaVenta fv",
                                                Object[].class)
                                .getResultList();

                for (Object[] resultado : resultados) {
                        System.out.println(
                                        "Número: " + resultado[0]
                                                        + " Fecha: " + resultado[1]
                                                        + " Importe: " + resultado[2]);
                }

                // Consulta 3
                List<Articulo> articulos = em
                                .createQuery("SELECT a FROM tp.Articulo a " + "WHERE a.rubro.denominacion = :denominacion",
                                                Articulo.class)
                                .setParameter("denominacion", "Bebidas")
                                .getResultList();

                for (Articulo articulo : articulos) {
                        System.out.println(
                                        "Código: " + articulo.getCodigo()
                                                        + " Artículo: " + articulo.getDenominacion());
                }

                // Consulta 4
                Date fechaDesde = java.sql.Date.valueOf("2026-09-01");
                Date fechaHasta = java.sql.Date.valueOf("2026-09-30");

                List<FacturaVenta> facturasFecha = em
                                .createQuery(
                                                "SELECT fv FROM tp.FacturaVenta fv "
                                                                + "WHERE fv.fechaEmision BETWEEN :fechaDesde AND :fechaHasta",
                                                FacturaVenta.class)
                                .setParameter("fechaDesde", fechaDesde)
                                .setParameter("fechaHasta", fechaHasta)
                                .getResultList();

                for (FacturaVenta factura : facturasFecha) {
                        System.out.println(
                                        "Factura: " + factura.getNumero()
                                                        + " Fecha: " + factura.getFechaEmision()
                                                        + " Importe: " + factura.getImporteTotal());
                }

                // Consulta 5
                List<FacturaVenta> facturasEmitidas = em
                                .createQuery(
                                                "SELECT fv FROM tp.FacturaVenta fv " + "WHERE fv.estado = :estado "
                                                                + "AND fv.importeTotal > :importe "
                                                                + "AND fv.fechaAnulacion IS NULL",
                                                FacturaVenta.class)
                                .setParameter("estado", "EMITIDA")
                                .setParameter("importe", 10000.0)
                                .getResultList();

                for (FacturaVenta factura : facturasEmitidas) {
                        System.out.println(
                                        "Factura: " + factura.getNumero()
                                                        + " Estado: " + factura.getEstado()
                                                        + " Importe: " + factura.getImporteTotal());
                }

                // Consulta 6
                String texto = "juan";

                List<Cliente> clientes = em
                                .createQuery(
                                                "SELECT c FROM tp.Cliente c "
                                                                + "WHERE LOWER(c.denominacion) LIKE LOWER(:texto) "
                                                                + "OR c.cuitCuil LIKE :cuit",
                                                Cliente.class)
                                .setParameter("texto", "%" + texto + "%")
                                .setParameter("cuit", "20-%")
                                .getResultList();

                for (Cliente cliente : clientes) {
                        System.out.println(
                                        "tp.Cliente: " + cliente.getDenominacion()
                                                        + " CUIT/CUIL: " + cliente.getCuitCuil());
                }

                // Consulta 7
                List<String> estados = em
                                .createQuery(
                                                "SELECT DISTINCT fv.estado " + "FROM tp.FacturaVenta fv "
                                                                + "ORDER BY fv.estado ASC",
                                                String.class)
                                .getResultList();

                for (String estado : estados) {
                        System.out.println("Estado: " + estado);
                }

                // Consulta 8
                Object[] resultado = em
                                .createQuery(
                                                "SELECT COUNT(fv), SUM(fv.importeTotal), AVG(fv.importeTotal) "
                                                                + "FROM tp.FacturaVenta fv "
                                                                + "WHERE fv.estado = :estado",
                                                Object[].class)
                                .setParameter("estado", "EMITIDA")
                                .getSingleResult();

                System.out.println(
                                "Cantidad: " + resultado[0]
                                                + " Suma: " + resultado[1]
                                                + " Promedio: " + resultado[2]);

                // Consulta 9
                List<Integer> numeros = List.of(1, 2, 5);

                List<PuntoVenta> puntosVenta = em
                                .createQuery("SELECT pv FROM tp.PuntoVenta pv " + "WHERE pv.numero IN :numeros",
                                                PuntoVenta.class)
                                .setParameter("numeros", numeros)
                                .getResultList();

                for (PuntoVenta puntoVenta : puntosVenta) {
                        System.out.println(
                                        "Punto de venta: " + puntoVenta.getNumero());
                }

                // Consulta 10
                List<FacturaVenta> facturasUsuario = em
                                .createQuery("SELECT fv FROM tp.FacturaVenta fv "
                                                + "WHERE fv.usuarioCarga.usuario = :usuario",
                                                FacturaVenta.class)
                                .setParameter("usuario", "jgatica")
                                .getResultList();

                for (FacturaVenta factura : facturasUsuario) {
                        System.out.println(
                                        "Factura: " + factura.getNumero()
                                                        + " tp.Usuario: " + factura.getUsuarioCarga().getUsuario()
                                                        + " Importe: " + factura.getImporteTotal());
                }

                // Consulta 11
                List<FacturaVentaDetalle> detalles = em
                                .createQuery(
                                                "SELECT d FROM tp.FacturaVentaDetalle d " + "INNER JOIN d.factura f "
                                                                + "WHERE f.puntoVenta.numero = :numeroPuntoVenta",
                                                FacturaVentaDetalle.class)
                                .setParameter("numeroPuntoVenta", 1)
                                .getResultList();

                for (FacturaVentaDetalle detalle : detalles) {
                        System.out.println(
                                        "Detalle: " + detalle.getId()
                                                        + " Cantidad: " + detalle.getCantidad()
                                                        + " Subtotal: " + detalle.getImporteSubtotal());
                }

                // Consulta 12
                List<Object[]> articulosMarca = em
                                .createQuery("SELECT a.denominacion, m.denominacion " + "FROM tp.Articulo a "
                                                + "LEFT JOIN a.marca m",
                                                Object[].class)
                                .getResultList();

                for (Object[] resultadoss : articulosMarca) {
                        System.out.println(
                                        "Artículo: " + resultadoss[0]
                                                        + " tp.Marca: " + resultadoss[1]);
                }

                // Consulta 13
                List<FacturaVenta> facturasMarca = em
                                .createQuery(
                                                "SELECT DISTINCT f " +
                                                                "FROM tp.FacturaVenta f " +
                                                                "JOIN f.detalles d " +
                                                                "JOIN d.listaPrecioArticulo lpa " +
                                                                "WHERE lpa.articulo.marca.denominacion = :marca",
                                                FacturaVenta.class)
                                .setParameter("marca", "Coca Cola")
                                .getResultList();

                for (FacturaVenta factura : facturasMarca) {
                        System.out.println(
                                        "Factura: " + factura.getNumero() + " | Importe: " + factura.getImporteTotal());
                }

                // Consulta 14
                List<FacturaVenta> facturasMayorPromedio = em
                                .createQuery(
                                                "SELECT fv FROM tp.FacturaVenta fv " + "WHERE fv.importeTotal > "
                                                                + "(SELECT AVG(fv2.importeTotal) FROM tp.FacturaVenta fv2)",
                                                FacturaVenta.class)
                                .getResultList();

                for (FacturaVenta factura : facturasMayorPromedio) {
                        System.out.println(
                                        "Factura: " + factura.getNumero()
                                                        + " Importe: " + factura.getImporteTotal());
                }

                // Consulta 15
                List<Object[]> resultados15 = em
                                .createQuery(
                                                "SELECT pv.descripcion, COUNT(fv), SUM(fv.importeTotal) " +
                                                                "FROM tp.FacturaVenta fv " +
                                                                "JOIN fv.puntoVenta pv " +
                                                                "WHERE fv.estado = :estado " +
                                                                "GROUP BY pv.id, pv.descripcion",
                                                Object[].class)
                                .setParameter("estado", "EMITIDA")
                                .getResultList();

                for (Object[] result : resultados15) {
                        System.out.println("Punto de venta: " + result[0] + " | Cantidad de facturas: " + result[1]
                                        + " Total facturado: " + result[2]);
                }

                // Consulta 16
                List<Object[]> resultados16 = em
                                .createQuery(
                                                "SELECT fv.usuarioCarga.usuario, COUNT(fv) " +
                                                                "FROM tp.FacturaVenta fv " +
                                                                "GROUP BY fv.usuarioCarga.usuario " +
                                                                "HAVING COUNT(fv) > 5",
                                                Object[].class)
                                .getResultList();

                for (Object[] resultad : resultados16) {
                        System.out.println(
                                        "tp.Usuario: " + resultad[0]
                                                        + " Cantidad de facturas: " + resultad[1]);
                }

                // Consulta 17
                List<Object[]> resultados17 = em
                                .createQuery(
                                                "SELECT m.denominacion, " +
                                                                "SUM(d.cantidad), " +
                                                                "SUM(d.importeSubtotal) " +
                                                                "FROM tp.FacturaVentaDetalle d " +
                                                                "INNER JOIN d.listaPrecioArticulo lpa " +
                                                                "INNER JOIN lpa.articulo a " +
                                                                "INNER JOIN a.marca m " +
                                                                "GROUP BY m.denominacion " +
                                                                "ORDER BY m.denominacion",
                                                Object[].class)
                                .getResultList();

                for (Object[] resultadosss : resultados17) {
                        System.out.println(
                                        "tp.Marca: " + resultadosss[0]
                                                        + " Unidades vendidas: " + resultadosss[1]
                                                        + " Subtotal acumulado: " + resultadosss[2]);
                }

                // Consulta 18
                List<String> marcas = em
                                .createQuery(
                                                "SELECT m.denominacion " +
                                                                "FROM tp.Marca m " +
                                                                "WHERE EXISTS (" +
                                                                "SELECT d " +
                                                                "FROM tp.FacturaVentaDetalle d " +
                                                                "WHERE d.listaPrecioArticulo.articulo.marca = m" +
                                                                ")",
                                                String.class)
                                .getResultList();

                for (String marca : marcas) {
                        System.out.println(
                                        "tp.Marca: " + marca);
                }

                // Consulta 19
                List<Articulo> articulosNuncaFacturados = em
                                .createQuery(
                                                "SELECT a " +
                                                                "FROM tp.Articulo a " +
                                                                "WHERE NOT EXISTS (" +
                                                                "SELECT d " +
                                                                "FROM tp.FacturaVentaDetalle d " +
                                                                "WHERE d.listaPrecioArticulo.articulo = a" +
                                                                ")",
                                                Articulo.class)
                                .getResultList();

                for (Articulo articulo : articulosNuncaFacturados) {
                        System.out.println(
                                        "Artículo: " + articulo.getDenominacion()
                                                        + " Código: " + articulo.getCodigo());
                }

                // Consulta 20
                List<Object[]> resultados20 = em
                                .createQuery(
                                                "SELECT fv.numero, fv.importeTotal, " +
                                                                "CASE " +
                                                                "  WHEN fv.importeTotal > 50000 THEN 'ALTO VALOR' " +
                                                                "  WHEN fv.importeTotal BETWEEN 10000 AND 50000 THEN 'MEDIO VALOR' "
                                                                +
                                                                "  ELSE 'BAJO VALOR' " +
                                                                "END " +
                                                                "FROM tp.FacturaVenta fv " +
                                                                "ORDER BY fv.importeTotal DESC",
                                                Object[].class)
                                .getResultList();

                for (Object[] results : resultados20) {
                        System.out.println("Factura: " + results[0] + " | Importe: " + results[1] + " | Categoría: "
                                        + results[2]);
                }

                em.close();
                emf.close();
        }
}
