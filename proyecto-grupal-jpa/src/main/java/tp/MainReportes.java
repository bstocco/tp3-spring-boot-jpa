package tp;

import jakarta.persistence.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.List;

public class MainReportes {
    public static void main(String[] args) {
        // Conectamos a la base de datos (Supabase)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto-grupal-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Ejecutando consulta JPQL..");

            // Consulta JPQL optimizada usando el constructor del DTO
            String jpql = "SELECT new tp.FacturaReporteDTO(" +
                    "f.numero, f.fechaEmision, COALESCE(c.denominacion, 'Consumidor Final'), " +
                    "ci.denominacion, pv.descripcion, f.importeTotal, COUNT(d)) " +
                    "FROM tp.FacturaVenta f " +
                    "LEFT JOIN f.cliente c " +
                    "JOIN f.condicionIva ci " +
                    "JOIN f.puntoVenta pv " +
                    "JOIN f.detalles d " +
                    "GROUP BY f.id, f.numero, f.fechaEmision, c.denominacion, ci.denominacion, pv.descripcion, f.importeTotal";

            List<FacturaReporteDTO> reporte = em.createQuery(jpql, FacturaReporteDTO.class).getResultList();

            System.out.println("Se encontraron " + reporte.size() + " facturas. Generando archivos..");

            // Generar Excel (TXT separado por tabulaciones)
            generarExcel(reporte);

            // Generar PDF
            generarPDF(reporte);

            System.out.println("Éxito!. En la carpeta del proyecto están el PDF y el Excel.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void generarExcel(List<FacturaReporteDTO> reporte) throws Exception {
        FileWriter writer = new FileWriter("Reporte_Ventas.txt");
        writer.write("Numero\tFecha\tCliente\tCondicion IVA\tPunto de Venta\tImporte Total\tCant. Items\n");

        for (FacturaReporteDTO dto : reporte) {
            writer.write(dto.getNumeroFactura() + "\t" +
                    dto.getFechaEmision() + "\t" +
                    dto.getClienteDenominacion() + "\t" +
                    dto.getCondicionIva() + "\t" +
                    dto.getPuntoVentaDescripcion() + "\t" +
                    dto.getImporteTotal() + "\t" +
                    dto.getCantidadItems() + "\n");
        }
        writer.close();
    }

    private static void generarPDF(List<FacturaReporteDTO> reporte) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Reporte_Ventas.pdf"));

        document.open();
        document.add(new Paragraph("Reporte Ejecutivo de Ventas"));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);

        table.addCell("Numero");
        table.addCell("Fecha");
        table.addCell("Cliente");
        table.addCell("IVA");
        table.addCell("Pto. Venta");
        table.addCell("Total");
        table.addCell("Items");

        for (FacturaReporteDTO dto : reporte) {
            table.addCell(String.valueOf(dto.getNumeroFactura()));
            table.addCell(dto.getFechaEmision() != null ? dto.getFechaEmision().toString() : "");
            table.addCell(dto.getClienteDenominacion());
            table.addCell(dto.getCondicionIva());
            table.addCell(dto.getPuntoVentaDescripcion());
            table.addCell(String.valueOf(dto.getImporteTotal()));
            table.addCell(String.valueOf(dto.getCantidadItems()));
        }

        document.add(table);
        document.close();
    }
}
