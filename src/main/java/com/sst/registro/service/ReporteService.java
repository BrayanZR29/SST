package com.sst.registro.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sst.registro.model.entity.Evento;
import com.sst.registro.model.entity.Investigacion;
import com.sst.registro.model.entity.AccionCorrectiva;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class ReporteService {

    private static final Color COLOR_PRIMARY = new Color(46, 134, 171);
    private static final Color COLOR_SECONDARY = new Color(162, 59, 114);
    private static final Color COLOR_FONDO = new Color(245, 245, 245);
    private static final Color COLOR_BORDE = new Color(200, 200, 200);

    public void generarReporteEventos(HttpServletResponse response, List<Evento> eventos) throws Exception {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        agregarEncabezadoCompleto(document, "REPORTE GENERAL DE EVENTOS", "Sistema de Gestión de Seguridad y Salud en el Trabajo", eventos);
        
        agregarEstadisticasResumen(document, eventos);

        agregarTablaEventosCompleta(document, eventos);

        agregarResumenEventos(document, eventos);

        agregarPie(document);
        document.close();
    }

    private void agregarEncabezadoCompleto(Document document, String titulo, String subtitulo, List<Evento> eventos) throws Exception {
        Paragraph title = new Paragraph(titulo, new Font(Font.HELVETICA, 20, Font.BOLD, COLOR_PRIMARY));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(5);
        document.add(title);

        Paragraph sub = new Paragraph(subtitulo, new Font(Font.HELVETICA, 12, Font.ITALIC, Color.GRAY));
        sub.setAlignment(Element.ALIGN_CENTER);
        sub.setSpacingAfter(10);
        document.add(sub);

        int total = eventos != null ? eventos.size() : 0;
        Paragraph fecha = new Paragraph("Fecha de generación: " + 
            LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " | Total: " + total + " eventos",
            new Font(Font.HELVETICA, 10, Font.BOLD, Color.BLACK));
        fecha.setAlignment(Element.ALIGN_CENTER);
        fecha.setSpacingAfter(15);
        document.add(fecha);
    }

    private void agregarEstadisticasResumen(Document document, List<Evento> eventos) throws Exception {
        long total = eventos.size();
        long accidentes = eventos.stream().filter(e -> e.getTipo() != null && e.getTipo().name().equals("ACCIDENTE")).count();
        long incidentes = eventos.stream().filter(e -> e.getTipo() != null && e.getTipo().name().equals("INCIDENTE")).count();
        long enfermedades = eventos.stream().filter(e -> e.getTipo() != null && e.getTipo().name().equals("ENFERMEDAD_PROFESIONAL")).count();
        long abiertos = eventos.stream().filter(e -> e.getEstado() != null && e.getEstado().name().equals("ABIERTO")).count();
        long enProceso = eventos.stream().filter(e -> e.getEstado() != null && e.getEstado().name().equals("EN_PROCESO")).count();
        long cerrados = eventos.stream().filter(e -> e.getEstado() != null && e.getEstado().name().equals("CERRADO")).count();
        long leves = eventos.stream().filter(e -> e.getGravedad() != null && e.getGravedad().name().equals("LEVE")).count();
        long graves = eventos.stream().filter(e -> e.getGravedad() != null && e.getGravedad().name().equals("GRAVE")).count();
        long mortales = eventos.stream().filter(e -> e.getGravedad() != null && e.getGravedad().name().equals("MORTAL")).count();

        Paragraph titulo = new Paragraph("RESUMEN ESTADÍSTICO", new Font(Font.HELVETICA, 14, Font.BOLD, COLOR_SECONDARY));
        titulo.setSpacingBefore(20);
        titulo.setSpacingAfter(10);
        document.add(titulo);

        PdfPTable tabla = new PdfPTable(9);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{11, 11, 11, 11, 11, 11, 11, 11, 12});

        String[] labels = {"Total", "Accidentes", "Incidentes", "Enfermedades", "Abiertos", "En Proceso", "Cerrados", "Leves", "Graves/Mortales"};
        String[] valores = {String.valueOf(total), String.valueOf(accidentes), String.valueOf(incidentes), 
            String.valueOf(enfermedades), String.valueOf(abiertos), String.valueOf(enProceso), 
            String.valueOf(cerrados), String.valueOf(leves), String.valueOf(graves + mortales)};

        Font headerFont = new Font(Font.HELVETICA, 9, Font.BOLD, Color.WHITE);
        Font valueFont = new Font(Font.HELVETICA, 10, Font.BOLD, COLOR_PRIMARY);

        for (int i = 0; i < 9; i++) {
            PdfPCell celdaHeader = new PdfPCell(new Phrase(labels[i], headerFont));
            celdaHeader.setBackgroundColor(COLOR_PRIMARY);
            celdaHeader.setPadding(6);
            celdaHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(celdaHeader);
        }

        for (int i = 0; i < 9; i++) {
            PdfPCell celda = new PdfPCell(new Phrase(valores[i], valueFont));
            celda.setPadding(6);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(celda);
        }

        document.add(tabla);
    }

    private void agregarTablaEventosCompleta(Document document, List<Evento> eventos) throws Exception {
        document.add(new Paragraph("\n"));
        
        Paragraph titulo = new Paragraph("LISTADO DETALLADO DE EVENTOS", new Font(Font.HELVETICA, 14, Font.BOLD, COLOR_SECONDARY));
        titulo.setSpacingBefore(20);
        titulo.setSpacingAfter(10);
        document.add(titulo);

        PdfPTable tabla = new PdfPTable(10);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{8, 10, 8, 8, 12, 10, 14, 14, 10, 10});

        String[] headers = {"Código", "Tipo", "Gravedad", "Estado", "Fecha", "Lugar", "Personas", "Descripción", "Consecuencias", "Registrado"};
        Font headerFont = new Font(Font.HELVETICA, 8, Font.BOLD, Color.WHITE);

        for (String h : headers) {
            PdfPCell celda = new PdfPCell(new Phrase(h, headerFont));
            celda.setBackgroundColor(COLOR_PRIMARY);
            celda.setPadding(4);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(celda);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        Font normalFont = new Font(Font.HELVETICA, 7, Font.NORMAL, Color.BLACK);

        for (Evento e : eventos) {
            tabla.addCell(crearCeldaCorta(e.getCodigo(), normalFont));
            tabla.addCell(crearCeldaCorta(e.getTipo() != null ? e.getTipo().getDescripcion() : "-", normalFont));
            tabla.addCell(crearCeldaColor(e.getGravedad() != null ? e.getGravedad().getDescripcion() : "-", 
                obtenerColorGravedad(e.getGravedad()), normalFont));
            tabla.addCell(crearCeldaColor(e.getEstado() != null ? e.getEstado().getDescripcion() : "-",
                obtenerColorEstado(e.getEstado()), normalFont));
            tabla.addCell(crearCeldaCorta(e.getFechaHora() != null ? e.getFechaHora().format(formatter) : "-", normalFont));
            tabla.addCell(crearCeldaCorta(truncarTexto(e.getLugar(), 15), normalFont));
            tabla.addCell(crearCeldaCorta(truncarTexto(e.getPersonasInvolucradas(), 20), normalFont));
            tabla.addCell(crearCeldaCorta(truncarTexto(e.getDescripcion(), 20), normalFont));
            tabla.addCell(crearCeldaCorta(truncarTexto(e.getConsecuencias(), 20), normalFont));
            tabla.addCell(crearCeldaCorta(e.getUsuarioRegistra() != null ? e.getUsuarioRegistra().getNombre() : "-", normalFont));
        }

        document.add(tabla);
    }

    private void agregarResumenEventos(Document document, List<Evento> eventos) throws Exception {
        document.add(new Paragraph("\n"));
        
        Paragraph notas = new Paragraph("NOTAS:", new Font(Font.HELVETICA, 10, Font.BOLD, COLOR_SECONDARY));
        notas.setSpacingBefore(15);
        notas.setSpacingAfter(5);
        document.add(notas);

        Font notaFont = new Font(Font.HELVETICA, 9, Font.NORMAL, Color.BLACK);
        
        Paragraph nota1 = new Paragraph("• Los eventos mostrados incluyen todos los registros encontrados según los filtros aplicados.", notaFont);
        document.add(nota1);
        
        Paragraph nota2 = new Paragraph("• Para ver el detalle completo de cada evento, consulte el reporte individual.", notaFont);
        document.add(nota2);
        
        Paragraph nota3 = new Paragraph("• Las columnas de Personas y Descripción han sido truncadas para ajustar en esta tabla.", notaFont);
        document.add(nota3);
    }

    public void generarReporteEventoIndividual(HttpServletResponse response, Evento evento) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = new Font(Font.HELVETICA, 22, Font.BOLD, COLOR_PRIMARY);
        Font sectionFont = new Font(Font.HELVETICA, 16, Font.BOLD, COLOR_SECONDARY);
        Font labelFont = new Font(Font.HELVETICA, 12, Font.BOLD, COLOR_PRIMARY);
        Font strongFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.BLACK);
        Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.BLACK);

        agregarBloqueTitulo(document, "DETALLE COMPLETO DEL EVENTO", 
            evento.getCodigo() != null ? evento.getCodigo() : "SIN CÓDIGO", titleFont);

        agregarSeccion(document, "1. INFORMACIÓN BÁSICA", sectionFont);
        agregarTabla2Columnas(document, evento, labelFont, normalFont);

        agregarSeccion(document, "2. DESCRIPCIÓN DETALLADA", sectionFont);
        agregarDescripcionDetallada(document, evento, labelFont, normalFont);

        agregarSeccion(document, "3. INFORMACIÓN DE REGISTRO", sectionFont);
        agregarInfoRegistro(document, evento, labelFont, normalFont);

        if (evento.getInvestigacion() != null) {
            agregarSeccion(document, "4. INVESTIGACIÓN DEL EVENTO", sectionFont);
            agregarInvestigacion(document, evento.getInvestigacion(), labelFont, normalFont);
        }

        agregarPie(document);
        document.close();
    }

    private void agregarBloqueTitulo(Document document, String titulo, String codigo, Font font) throws Exception {
        Paragraph title = new Paragraph(titulo, font);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(5);
        document.add(title);

        Paragraph code = new Paragraph("Código: " + codigo, new Font(Font.HELVETICA, 14, Font.BOLD, COLOR_SECONDARY));
        code.setAlignment(Element.ALIGN_CENTER);
        code.setSpacingAfter(10);
        document.add(code);

        Paragraph fecha = new Paragraph("Generado: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
            new Font(Font.HELVETICA, 10, Font.ITALIC, Color.GRAY));
        fecha.setAlignment(Element.ALIGN_CENTER);
        fecha.setSpacingAfter(20);
        document.add(fecha);
    }

    private void agregarSeccion(Document document, String titulo, Font font) throws Exception {
        document.add(new Paragraph("\n"));
        Paragraph section = new Paragraph(titulo, font);
        section.setSpacingBefore(15);
        section.setSpacingAfter(10);
        document.add(section);
    }

    private void agregarTabla2Columnas(Document document, Evento evento, Font labelFont, Font normalFont) throws Exception {
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{30, 70});

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        agregarFila(tabla, "Tipo de Evento", evento.getTipo() != null ? evento.getTipo().getDescripcion() : "-", labelFont, normalFont);
        agregarFila(tabla, "Gravedad", evento.getGravedad() != null ? evento.getGravedad().getDescripcion() : "-", labelFont, 
            obtenerFontColor(evento.getGravedad()));
        agregarFila(tabla, "Estado", evento.getEstado() != null ? evento.getEstado().getDescripcion() : "-",
            labelFont, obtenerFontColorEstado(evento.getEstado()));
        agregarFila(tabla, "Fecha y Hora del Evento", evento.getFechaHora() != null ? evento.getFechaHora().format(formatter) : "-", 
            labelFont, normalFont);
        agregarFila(tabla, "Lugar/Área", evento.getLugar() != null ? evento.getLugar() : "-", labelFont, normalFont);
        agregarFila(tabla, "Personas Involucradas", evento.getPersonasInvolucradas() != null ? evento.getPersonasInvolucradas() : "No especificadas",
            labelFont, normalFont);

        document.add(tabla);
    }

    private void agregarDescripcionDetallada(Document document, Evento evento, Font labelFont, Font normalFont) throws Exception {
        PdfPTable tabla = new PdfPTable(1);
        tabla.setWidthPercentage(100);

        PdfPCell celdaLabel = new PdfPCell(new Phrase("Descripción de los hechos:", labelFont));
        celdaLabel.setBackgroundColor(COLOR_FONDO);
        celdaLabel.setPadding(10);
        celdaLabel.setBorder(Rectangle.BOX);
        celdaLabel.setBorderColor(COLOR_BORDE);
        tabla.addCell(celdaLabel);

        PdfPCell celdaDesc = new PdfPCell(new Phrase(evento.getDescripcion() != null ? evento.getDescripcion() : "Sin descripción",
            normalFont));
        celdaDesc.setPadding(10);
        celdaDesc.setBorder(Rectangle.BOX);
        celdaDesc.setBorderColor(COLOR_BORDE);
        tabla.addCell(celdaDesc);

        document.add(tabla);

        document.add(new Paragraph("\n"));

        PdfPTable tablaCons = new PdfPTable(1);
        tablaCons.setWidthPercentage(100);

        PdfPCell celdaLabelCons = new PdfPCell(new Phrase("Consecuencias:", labelFont));
        celdaLabelCons.setBackgroundColor(COLOR_FONDO);
        celdaLabelCons.setPadding(10);
        celdaLabelCons.setBorder(Rectangle.BOX);
        celdaLabelCons.setBorderColor(COLOR_BORDE);
        tablaCons.addCell(celdaLabelCons);

        PdfPCell celdaCons = new PdfPCell(new Phrase(evento.getConsecuencias() != null ? evento.getConsecuencias() : "Sin consecuencias registradas",
            normalFont));
        celdaCons.setPadding(10);
        celdaCons.setBorder(Rectangle.BOX);
        celdaCons.setBorderColor(COLOR_BORDE);
        tablaCons.addCell(celdaCons);

        document.add(tablaCons);
    }

    private void agregarInfoRegistro(Document document, Evento evento, Font labelFont, Font normalFont) throws Exception {
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{30, 70});

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        agregarFila(tabla, "Registrado por", evento.getUsuarioRegistra() != null ? evento.getUsuarioRegistra().getNombre() : "-", 
            labelFont, normalFont);
        agregarFila(tabla, "Fecha de registro", evento.getFechaCreacion() != null ? evento.getFechaCreacion().format(formatter) : "-",
            labelFont, normalFont);
        agregarFila(tabla, "Última actualización", evento.getFechaActualizacion() != null ? evento.getFechaActualizacion().format(formatter) : "-",
            labelFont, normalFont);

        document.add(tabla);
    }

    private void agregarInvestigacion(Document document, Investigacion inv, Font labelFont, Font normalFont) throws Exception {
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{30, 70});

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        agregarFila(tabla, "Investigador", inv.getInvestigador() != null ? inv.getInvestigador().getNombre() : "-", labelFont, normalFont);
        agregarFila(tabla, "Fecha de investigación", inv.getFechaInvestigacion() != null ? inv.getFechaInvestigacion().format(formatter) : "-",
            labelFont, normalFont);
        agregarFila(tabla, "Causa Inmediata", inv.getCausaInmediata() != null ? inv.getCausaInmediata() : "-", labelFont, normalFont);
        agregarFila(tabla, "Causas Básicas", inv.getCausaBasica() != null ? inv.getCausaBasica() : "-", labelFont, normalFont);
        agregarFila(tabla, "Factores Contributorios", inv.getFactoresContribuitorios() != null ? inv.getFactoresContribuitorios() : "No aplica",
            labelFont, normalFont);

        document.add(tabla);

        if (inv.getAccionesCorrectivas() != null && !inv.getAccionesCorrectivas().isEmpty()) {
            document.add(new Paragraph("\n"));
            
            Paragraph accionesTitle = new Paragraph("4.1 ACCIONES CORRECTIVAS", new Font(Font.HELVETICA, 14, Font.BOLD, COLOR_SECONDARY));
            accionesTitle.setSpacingBefore(15);
            accionesTitle.setSpacingAfter(10);
            document.add(accionesTitle);

            PdfPTable tablaAcciones = new PdfPTable(5);
            tablaAcciones.setWidthPercentage(100);
            tablaAcciones.setWidths(new float[]{35, 15, 15, 15, 20});

            String[] headers = {"Descripción", "Responsable", "Fecha Límite", "Estado", "Completada"};
            Font headerFont = new Font(Font.HELVETICA, 10, Font.BOLD, Color.WHITE);
            
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
                cell.setBackgroundColor(COLOR_PRIMARY);
                cell.setPadding(6);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablaAcciones.addCell(cell);
            }

            for (AccionCorrectiva accion : inv.getAccionesCorrectivas()) {
                tablaAcciones.addCell(crearCelda(accion.getDescripcion() != null ? accion.getDescripcion() : "-", normalFont));
                tablaAcciones.addCell(crearCelda(accion.getResponsable() != null ? accion.getResponsable() : "-", normalFont));
                tablaAcciones.addCell(crearCelda(accion.getFechaLimite() != null ? accion.getFechaLimite().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "-", normalFont));
                tablaAcciones.addCell(crearCeldaColor(accion.getEstadoAccion() != null ? accion.getEstadoAccion().getDescripcion() : "-",
                    obtenerColorEstadoAccion(accion.getEstadoAccion()), normalFont));
                tablaAcciones.addCell(crearCelda(accion.getFechaCompletado() != null ? accion.getFechaCompletado().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Pendiente", normalFont));
            }

            document.add(tablaAcciones);
        }
    }

    public void generarReporteEstadisticas(HttpServletResponse response, Map<String, Long> resumen, 
            List<Evento> eventosRecientes) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = new Font(Font.HELVETICA, 22, Font.BOLD, COLOR_PRIMARY);
        Font sectionFont = new Font(Font.HELVETICA, 16, Font.BOLD, COLOR_SECONDARY);
        Font valueFont = new Font(Font.HELVETICA, 28, Font.BOLD, COLOR_PRIMARY);

        agregarBloqueTituloEstadisticas(document, "REPORTE ESTADÍSTICO", titleFont);

        agregarSeccion(document, "RESUMEN GENERAL", sectionFont);
        agregarEstadisticasGrid(document, resumen);

        if (eventosRecientes != null && !eventosRecientes.isEmpty()) {
            agregarSeccion(document, "EVENTOS RECIENTES", sectionFont);
            agregarTablaEventosRecientes(document, eventosRecientes);
        }

        agregarPie(document);
        document.close();
    }

    private void agregarBloqueTituloEstadisticas(Document document, String titulo, Font font) throws Exception {
        Paragraph title = new Paragraph(titulo, font);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(5);
        document.add(title);

        Paragraph fecha = new Paragraph("Generado: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
            new Font(Font.HELVETICA, 10, Font.ITALIC, Color.GRAY));
        fecha.setAlignment(Element.ALIGN_CENTER);
        fecha.setSpacingAfter(20);
        document.add(fecha);
    }

    private void agregarEstadisticasGrid(Document document, Map<String, Long> resumen) throws Exception {
        long totalAccidentes = resumen.getOrDefault("totalAccidentes", 0L);
        long totalIncidentes = resumen.getOrDefault("totalIncidentes", 0L);
        long totalEnfermedades = resumen.getOrDefault("totalEnfermedades", 0L);
        long totalAbiertos = resumen.getOrDefault("totalAbiertos", 0L);
        long totalEnProceso = resumen.getOrDefault("totalEnProceso", 0L);
        long totalCerrados = resumen.getOrDefault("totalCerrados", 0L);
        long total = totalAccidentes + totalIncidentes + totalEnfermedades;

        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{60, 40});

        Font labelFont = new Font(Font.HELVETICA, 14, Font.BOLD, Color.GRAY);
        Font valueFont = new Font(Font.HELVETICA, 28, Font.BOLD, COLOR_PRIMARY);

        agregarCeldaEstadistica(tabla, "Total de Eventos", String.valueOf(total), valueFont);
        agregarCeldaEstadistica(tabla, "Eventos Abiertos", String.valueOf(totalAbiertos), 
            new Font(Font.HELVETICA, 28, Font.BOLD, new Color(220, 53, 69)));
        agregarCeldaEstadistica(tabla, "Accidentes", String.valueOf(totalAccidentes), 
            new Font(Font.HELVETICA, 28, Font.BOLD, new Color(220, 53, 69)));
        agregarCeldaEstadistica(tabla, "En Proceso", String.valueOf(totalEnProceso), 
            new Font(Font.HELVETICA, 28, Font.BOLD, new Color(255, 193, 7)));
        agregarCeldaEstadistica(tabla, "Incidentes", String.valueOf(totalIncidentes), 
            new Font(Font.HELVETICA, 28, Font.BOLD, new Color(255, 193, 7)));
        agregarCeldaEstadistica(tabla, "Cerrados", String.valueOf(totalCerrados), 
            new Font(Font.HELVETICA, 28, Font.BOLD, new Color(40, 167, 69)));

        document.add(tabla);
    }

    private void agregarTablaEventosRecientes(Document document, List<Evento> eventosRecientes) throws Exception {
        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{15, 20, 20, 22, 23});

        String[] headers = {"Código", "Tipo", "Fecha", "Estado", "Lugar"};
        Font headerFont = new Font(Font.HELVETICA, 10, Font.BOLD, Color.WHITE);

        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
            cell.setBackgroundColor(COLOR_PRIMARY);
            cell.setPadding(6);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(cell);
        }

        Font normalFont = new Font(Font.HELVETICA, 10, Font.NORMAL, Color.BLACK);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Evento e : eventosRecientes) {
            tabla.addCell(crearCelda(e.getCodigo() != null ? e.getCodigo() : "-", normalFont));
            tabla.addCell(crearCelda(e.getTipo() != null ? e.getTipo().getDescripcion() : "-", normalFont));
            tabla.addCell(crearCelda(e.getFechaHora() != null ? e.getFechaHora().format(formatter) : "-", normalFont));
            tabla.addCell(crearCelda(e.getEstado() != null ? e.getEstado().getDescripcion() : "-", normalFont));
            tabla.addCell(crearCelda(e.getLugar() != null ? e.getLugar() : "-", normalFont));
        }

        document.add(tabla);
    }

    private void agregarPie(Document document) throws Exception {
        Paragraph pie = new Paragraph("\n\nSistema de Gestión de Seguridad y Salud en el Trabajo",
            new Font(Font.HELVETICA, 9, Font.ITALIC, Color.GRAY));
        pie.setAlignment(Element.ALIGN_CENTER);
        pie.setSpacingBefore(40);
        document.add(pie);
    }

    private void agregarFila(PdfPTable tabla, String label, String valor, Font labelFont, Font valorFont) {
        PdfPCell celdaLabel = new PdfPCell(new Phrase(label, labelFont));
        celdaLabel.setBackgroundColor(COLOR_FONDO);
        celdaLabel.setPadding(8);
        celdaLabel.setBorder(Rectangle.BOX);
        celdaLabel.setBorderColor(COLOR_BORDE);
        tabla.addCell(celdaLabel);

        PdfPCell celdaValor = new PdfPCell(new Phrase(valor != null ? valor : "-", valorFont));
        celdaValor.setPadding(8);
        celdaValor.setBorder(Rectangle.BOX);
        celdaValor.setBorderColor(COLOR_BORDE);
        tabla.addCell(celdaValor);
    }

    private void agregarCeldaEstadistica(PdfPTable tabla, String label, String valor, Font valorFont) {
        PdfPCell celdaLabel = new PdfPCell(new Phrase(label, new Font(Font.HELVETICA, 16, Font.BOLD, Color.GRAY)));
        celdaLabel.setBorder(Rectangle.NO_BORDER);
        celdaLabel.setPadding(12);
        tabla.addCell(celdaLabel);

        PdfPCell celdaValor = new PdfPCell(new Phrase(valor, valorFont));
        celdaValor.setBorder(Rectangle.NO_BORDER);
        celdaValor.setPadding(12);
        celdaValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(celdaValor);
    }

    private PdfPCell crearCelda(String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(5);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    private PdfPCell crearCeldaCorta(String texto, Font font) {
        String textoCorto = truncarTexto(texto, 20);
        PdfPCell cell = new PdfPCell(new Phrase(textoCorto, font));
        cell.setPadding(3);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    private PdfPCell crearCeldaColor(String texto, Color bgColor, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBackgroundColor(bgColor);
        cell.setPadding(5);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private String truncateString(String text, int maxLength) {
        if (text == null) return "-";
        return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
    }

    private String truncarTexto(String texto, int maxChars) {
        if (texto == null) return "-";
        if (texto.length() <= maxChars) return texto;
        return texto.substring(0, maxChars - 3) + "...";
    }

    private Color obtenerColorGravedad(Object gravedad) {
        if (gravedad == null) return Color.LIGHT_GRAY;
        String g = gravedad.toString();
        return switch (g) {
            case "LEVE" -> new Color(40, 167, 69);
            case "GRAVE" -> new Color(255, 193, 7);
            case "MORTAL" -> new Color(220, 53, 69);
            default -> Color.LIGHT_GRAY;
        };
    }

    private Color obtenerColorEstado(Object estado) {
        if (estado == null) return Color.LIGHT_GRAY;
        String e = estado.toString();
        return switch (e) {
            case "ABIERTO" -> new Color(220, 53, 69);
            case "EN_PROCESO" -> new Color(255, 193, 7);
            case "CERRADO" -> new Color(40, 167, 69);
            default -> Color.LIGHT_GRAY;
        };
    }

    private Color obtenerColorEstadoAccion(Object estado) {
        if (estado == null) return Color.LIGHT_GRAY;
        String e = estado.toString();
        return switch (e) {
            case "PENDIENTE" -> new Color(255, 193, 7);
            case "COMPLETADA" -> new Color(40, 167, 69);
            case "VENCIDA" -> new Color(220, 53, 69);
            default -> Color.LIGHT_GRAY;
        };
    }

    private Font obtenerFontColor(Object gravedad) {
        if (gravedad == null) return new Font(Font.HELVETICA, 12, Font.NORMAL, Color.BLACK);
        String g = gravedad.toString();
        return switch (g) {
            case "LEVE" -> new Font(Font.HELVETICA, 12, Font.BOLD, new Color(40, 167, 69));
            case "GRAVE" -> new Font(Font.HELVETICA, 12, Font.BOLD, new Color(255, 193, 7));
            case "MORTAL" -> new Font(Font.HELVETICA, 12, Font.BOLD, new Color(220, 53, 69));
            default -> new Font(Font.HELVETICA, 12, Font.NORMAL, Color.BLACK);
        };
    }

    private Font obtenerFontColorEstado(Object estado) {
        if (estado == null) return new Font(Font.HELVETICA, 12, Font.NORMAL, Color.BLACK);
        String e = estado.toString();
        return switch (e) {
            case "ABIERTO" -> new Font(Font.HELVETICA, 12, Font.BOLD, new Color(220, 53, 69));
            case "EN_PROCESO" -> new Font(Font.HELVETICA, 12, Font.BOLD, new Color(255, 193, 7));
            case "CERRADO" -> new Font(Font.HELVETICA, 12, Font.BOLD, new Color(40, 167, 69));
            default -> new Font(Font.HELVETICA, 12, Font.NORMAL, Color.BLACK);
        };
    }
}