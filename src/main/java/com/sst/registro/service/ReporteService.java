package com.sst.registro.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sst.registro.model.entity.Evento;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class ReporteService {

    public void generarReporteEventos(HttpServletResponse response, java.util.List<Evento> eventos) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(46, 134, 171));
        Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);
        Font normalFont = new Font(Font.HELVETICA, 10, Font.NORMAL, Color.BLACK);

        Paragraph title = new Paragraph("Reporte de Eventos de Seguridad", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        Paragraph fecha = new Paragraph("Fecha de generación: " + 
            java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        fecha.setSpacingAfter(20);
        document.add(fecha);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{15, 20, 20, 25, 20});

        PdfPCell cell = new PdfPCell(new Phrase("Código", headerFont));
        cell.setBackgroundColor(new Color(46, 134, 171));
        cell.setPadding(8);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Tipo", headerFont));
        cell.setBackgroundColor(new Color(46, 134, 171));
        cell.setPadding(8);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Fecha", headerFont));
        cell.setBackgroundColor(new Color(46, 134, 171));
        cell.setPadding(8);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Lugar", headerFont));
        cell.setBackgroundColor(new Color(46, 134, 171));
        cell.setPadding(8);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Estado", headerFont));
        cell.setBackgroundColor(new Color(46, 134, 171));
        cell.setPadding(8);
        table.addCell(cell);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Evento evento : eventos) {
            table.addCell(new PdfPCell(new Phrase(evento.getCodigo() != null ? evento.getCodigo() : "-", normalFont)));
            table.addCell(new PdfPCell(new Phrase(evento.getTipo() != null ? evento.getTipo().getDescripcion() : "-", normalFont)));
            table.addCell(new PdfPCell(new Phrase(evento.getFechaHora() != null ? evento.getFechaHora().format(formatter) : "-", normalFont)));
            table.addCell(new PdfPCell(new Phrase(evento.getLugar() != null ? evento.getLugar() : "-", normalFont)));
            table.addCell(new PdfPCell(new Phrase(evento.getEstado() != null ? evento.getEstado().getDescripcion() : "-", normalFont)));
        }

        document.add(table);

        Paragraph total = new Paragraph("\nTotal de eventos: " + eventos.size(), normalFont);
        total.setSpacingBefore(20);
        document.add(total);

        document.close();
    }

    public void generarReporteEventoIndividual(HttpServletResponse response, Evento evento) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(46, 134, 171));
        Font headerFont = new Font(Font.HELVETICA, 14, Font.BOLD, new Color(46, 134, 171));
        Font normalFont = new Font(Font.HELVETICA, 11, Font.NORMAL, Color.BLACK);

        Paragraph title = new Paragraph("Detalle del Evento", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        agregarCampo(document, "Código", evento.getCodigo(), headerFont, normalFont);
        agregarCampo(document, "Tipo", evento.getTipo().getDescripcion(), headerFont, normalFont);
        agregarCampo(document, "Gravedad", evento.getGravedad().getDescripcion(), headerFont, normalFont);
        agregarCampo(document, "Estado", evento.getEstado().getDescripcion(), headerFont, normalFont);
        agregarCampo(document, "Fecha", evento.getFechaHora().format(formatter), headerFont, normalFont);
        agregarCampo(document, "Lugar", evento.getLugar(), headerFont, normalFont);
        agregarCampo(document, "Personas Involucradas", evento.getPersonasInvolucradas(), headerFont, normalFont);
        agregarCampo(document, "Descripción", evento.getDescripcion(), headerFont, normalFont);
        agregarCampo(document, "Consecuencias", evento.getConsecuencias() != null ? evento.getConsecuencias() : "No especificadas", headerFont, normalFont);

        if (evento.getInvestigacion() != null) {
            document.add(new Paragraph("\n"));
            Paragraph investigacionTitle = new Paragraph("Investigación", headerFont);
            investigacionTitle.setSpacingBefore(15);
            document.add(investigacionTitle);
            
            agregarCampo(document, "Causa Inmediata", evento.getInvestigacion().getCausaInmediata(), headerFont, normalFont);
            agregarCampo(document, "Causas Básicas", evento.getInvestigacion().getCausaBasica(), headerFont, normalFont);
            agregarCampo(document, "Factores Contributorios", 
                evento.getInvestigacion().getFactoresContribuitorios() != null ? 
                evento.getInvestigacion().getFactoresContribuitorios() : "No aplica", headerFont, normalFont);
        }

        document.close();
    }

    private void agregarCampo(Document document, String label, String valor, Font headerFont, Font normalFont) throws Exception {
        Paragraph labelP = new Paragraph(label + ":", headerFont);
        labelP.setSpacingBefore(10);
        document.add(labelP);
        
        Paragraph valorP = new Paragraph(valor != null ? valor : "-", normalFont);
        document.add(valorP);
    }
}