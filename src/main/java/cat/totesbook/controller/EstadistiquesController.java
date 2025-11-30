package cat.totesbook.controller;

import cat.totesbook.domain.Rol;
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.dto.AutorEstadisticaDTO;
import cat.totesbook.dto.LlibreEstadisticaDTO;
import cat.totesbook.dto.UsuariEstadisticaDTO;
import cat.totesbook.service.PrestecService;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EstadistiquesController {

    @Autowired
    private PrestecService prestecService;

    @GetMapping("/admin/estadistiques")
    public String veureEstadistiquesLlibres(
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String categoria,
            Model model, HttpSession session) {
        
        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null || sessio.getRol() != Rol.ADMIN) return "redirect:/login";

        List<LlibreEstadisticaDTO> stats = prestecService.getEstadistiquesLlibres(autor, categoria);
        List<UsuariEstadisticaDTO> statsUsuaris = prestecService.getEstadistiquesUsuaris();
        
        model.addAttribute("stats", stats);
        model.addAttribute("statsUsuaris", statsUsuaris);
        model.addAttribute("filtreAutor", autor);
        model.addAttribute("filtreCategoria", categoria);
        
        return "admin_estadistiques";
    }

    @GetMapping("/bibliotecari/autors-populars")
    public String veureAutorsPopulars(Model model, HttpSession session) {
        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null || (sessio.getRol() != Rol.BIBLIOTECARI && sessio.getRol() != Rol.ADMIN)) {
            return "redirect:/login";
        }

        List<AutorEstadisticaDTO> stats = prestecService.getEstadistiquesAutors();
        model.addAttribute("stats", stats);
        
        return "bibliotecari_autors_populars";
    }

    @GetMapping("/admin/estadistiques/excel")
    public void exportarExcel(@RequestParam(required = false) String autor,
                              @RequestParam(required = false) String categoria,
                              HttpServletResponse response) throws IOException {
        
        List<LlibreEstadisticaDTO> statsLlibres = prestecService.getEstadistiquesLlibres(autor, categoria);
        List<UsuariEstadisticaDTO> statsUsuaris = prestecService.getEstadistiquesUsuaris();
        
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=estadistiques_global.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            
            // --- FULL 1: LLIBRES ---
            Sheet sheetLlibres = workbook.createSheet("Top Llibres");
            Row headerRow = sheetLlibres.createRow(0);
            headerRow.createCell(0).setCellValue("Titol");
            headerRow.createCell(1).setCellValue("Autor");
            headerRow.createCell(2).setCellValue("Total Prestecs");
            
            int rowIdx = 1;
            for (LlibreEstadisticaDTO dto : statsLlibres) {
                Row row = sheetLlibres.createRow(rowIdx++);
                row.createCell(0).setCellValue(dto.getLlibre().getTitol());
                row.createCell(1).setCellValue(dto.getLlibre().getAutor());
                row.createCell(2).setCellValue(dto.getTotalPrestecs());
            }

            // --- FULL 2: USUARIS ---
            Sheet sheetUsuaris = workbook.createSheet("Top Lectors");
            Row headerRow2 = sheetUsuaris.createRow(0);
            headerRow2.createCell(0).setCellValue("Nom");
            headerRow2.createCell(1).setCellValue("Cognoms");
            headerRow2.createCell(2).setCellValue("Email");
            headerRow2.createCell(3).setCellValue("Total Prestecs");

            rowIdx = 1;
            for (UsuariEstadisticaDTO dto : statsUsuaris) {
                Row row = sheetUsuaris.createRow(rowIdx++);
                row.createCell(0).setCellValue(dto.getUsuari().getNom());
                row.createCell(1).setCellValue(dto.getUsuari().getCognoms());
                row.createCell(2).setCellValue(dto.getUsuari().getEmail());
                row.createCell(3).setCellValue(dto.getTotalPrestecs());
            }

            workbook.write(response.getOutputStream());
        }
    }

    @GetMapping("/admin/estadistiques/pdf")
    public void exportarPdf(@RequestParam(required = false) String autor,
                            @RequestParam(required = false) String categoria,
                            HttpServletResponse response) throws IOException {
        
        List<LlibreEstadisticaDTO> statsLlibres = prestecService.getEstadistiquesLlibres(autor, categoria);
        List<UsuariEstadisticaDTO> statsUsuaris = prestecService.getEstadistiquesUsuaris();
        
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=informe_biblioteca.pdf");

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font fontTitol = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titol = new Paragraph("Informe TotEsBook", fontTitol);
            titol.setAlignment(Element.ALIGN_CENTER);
            document.add(titol);
            document.add(new Paragraph(" "));

            // --- SECCIÓ 1: LLIBRES ---
            document.add(new Paragraph("1. Llibres Mes Prestats", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            document.add(new Paragraph(" "));
            
            PdfPTable tableL = new PdfPTable(3);
            tableL.setWidthPercentage(100);
            
            tableL.addCell("Titol");
            tableL.addCell("Autor");
            tableL.addCell("Cant.");

            for (LlibreEstadisticaDTO dto : statsLlibres) {
                tableL.addCell(dto.getLlibre().getTitol());
                tableL.addCell(dto.getLlibre().getAutor());
                tableL.addCell(String.valueOf(dto.getTotalPrestecs()));
            }
            document.add(tableL);

            document.add(new Paragraph(" "));

            // --- SECCIÓ 2: USUARIS ---
            document.add(new Paragraph("2. Lectors Mes Actius", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            document.add(new Paragraph(" "));

            PdfPTable tableU = new PdfPTable(3);
            tableU.setWidthPercentage(100);

            tableU.addCell("Nom");
            tableU.addCell("Email");
            tableU.addCell("Prestecs");

            for (UsuariEstadisticaDTO dto : statsUsuaris) {
                tableU.addCell(dto.getUsuari().getNom() + " " + dto.getUsuari().getCognoms());
                tableU.addCell(dto.getUsuari().getEmail());
                tableU.addCell(String.valueOf(dto.getTotalPrestecs()));
            }
            document.add(tableU);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}