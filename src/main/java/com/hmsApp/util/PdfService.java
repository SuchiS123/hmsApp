package com.hmsApp.util;


import com.hmsApp.entity.Booking;
import com.hmsApp.entity.Property;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class PdfService {

    public void generateBookingPdf(String filePath, Property property,Booking booking) {
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

            // Add header and footer
            HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            writer.setPageEvent(event);

            document.open();

            // Add logo
            Image logo = Image.getInstance("D:/hmsApp/bnb.jpg");  // Update with your logo path
            logo.scaleToFit(100, 50);  // Adjust size as needed
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);

            // Add table with property details
            PdfPTable table = new PdfPTable(2);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            table.addCell("Id");
            table.addCell(property.getId().toString());

            table.addCell("Name");
            table.addCell(property.getName());

            table.addCell("No of Guest");
            table.addCell(property.getNoOfGuest().toString());

            table.addCell("No of Bedrooms");
            table.addCell(property.getNoOfBedrooms().toString());

            table.addCell("No of Bathrooms");
            table.addCell(property.getNoOfBathrooms().toString());


            // Add payment amount row
            table.addCell("Total Payment Amount (incl. GST)");
//            Double paymentAmount = book.getPaymentAmount();
            table.addCell(booking.getPaymentAmount().toString());


            table.addCell("City");
            table.addCell(property.getCity().getCityName());

            table.addCell("Country");
            table.addCell(property.getCountry().getCountryName());

            document.add(table);

            // Add signature line
            Paragraph signature = new Paragraph("Signature: SuchiSmanta B", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
            signature.setAlignment(Element.ALIGN_RIGHT);
            signature.setSpacingBefore(20f);
            document.add(signature);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // HeaderFooter class to add header and footer
    class HeaderFooterPageEvent extends PdfPageEventHelper {
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            // Add company name in header
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Airbnb", headerFont), 110, 820, 0);
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            // Add footer with page number
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Page " + writer.getPageNumber(), footerFont), 550, 30, 0);
        }

    }
}
