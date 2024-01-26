package kapsalon.nl.services;


import kapsalon.nl.models.dto.AppointmentDTO;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.swing.text.Document;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class PdfService {

    public byte[] generatePdf(AppointmentDTO appointmentDTO) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            addAppointmentDetailsToStream(contentStream, appointmentDTO);

            contentStream.endText();
            contentStream.close();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            document.close();


            return byteArrayOutputStream.toByteArray();
        }
    }
    private void addAppointmentDetailsToStream(PDPageContentStream contentStream, AppointmentDTO appointmentDTO) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(100, 700);
        String paymentHasBeenMade = appointmentDTO.isPaid() ? "Ja" : "Nee";
        float leading = 20;
        contentStream.setLeading(leading);

        contentStream.showText("Afspraakgegevens:");
        contentStream.newLine();
        contentStream.showText("ID: " + appointmentDTO.getId());
        contentStream.newLine();
        contentStream.showText("Kapsalon: " + appointmentDTO.getSelectedKapsalon().getName());
        contentStream.newLine();
        contentStream.showText("Kapper: " + appointmentDTO.getSelectedBarber().getName());
        contentStream.newLine();
        contentStream.showText("Dienst: " + appointmentDTO.getSelectedDienst().getCategory() + appointmentDTO.getSelectedDienst().getTitle());
        contentStream.newLine();
        contentStream.showText("Klant: " + appointmentDTO.getCustomer().getFirstName() + " " + appointmentDTO.getCustomer().getSecondName());
        contentStream.newLine();
        contentStream.showText("Datum: " + appointmentDTO.getAppointmentDate());
        contentStream.newLine();
        contentStream.showText("Tijd: " + appointmentDTO.getAppointmentTime());
        contentStream.newLine();
        contentStream.showText("Prijs: " + "€" + appointmentDTO.getSelectedDienst().getPrice());
        contentStream.newLine();
        contentStream.showText("is betaald?: " + paymentHasBeenMade);
    }
}