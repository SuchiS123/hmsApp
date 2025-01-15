package com.hmsApp.controller;


import com.hmsApp.entity.Booking;
import com.hmsApp.entity.Property;
import com.hmsApp.entity.RoomsAvailability;
import com.hmsApp.repository.BookingRepository;
import com.hmsApp.repository.PropertyRepository;
import com.hmsApp.repository.RoomsAvailabilityRepository;
import com.hmsApp.service.EmailService;
import com.hmsApp.service.TwilioService;
import com.hmsApp.service.TwilioWhatsAppService;
import com.hmsApp.util.PdfService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")

public class BookingsController {

    private RoomsAvailabilityRepository roomsAvailabilityRepository;
    private PropertyRepository propertyRepository;
    private BookingRepository bookingRepository;
    private PdfService pdfService;
    private TwilioService twilioService;
    private TwilioWhatsAppService twilioWhatsAppService;
    private EmailService emailService;


    public BookingsController(RoomsAvailabilityRepository roomsAvailabilityRepository,
                              PropertyRepository propertyRepository, BookingRepository bookingRepository,
                              PdfService pdfService, TwilioService twilioService,
                              TwilioWhatsAppService twilioWhatsAppService,
                              EmailService emailService
    ) {
        this.roomsAvailabilityRepository = roomsAvailabilityRepository;
        this.propertyRepository = propertyRepository;
        this.bookingRepository = bookingRepository;
        this.pdfService = pdfService;
        this.twilioService = twilioService;
        this.twilioWhatsAppService = twilioWhatsAppService;
        this.emailService = emailService;
    }

    @GetMapping("/search/rooms")
    public ResponseEntity<?> searchRoomsAndBook(@RequestParam LocalDate fromDate,
                                                @RequestParam LocalDate toDate,
                                                @RequestParam String roomType,
                                                @RequestParam long propertyId,
                                                @RequestBody Booking bookings) {

        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RuntimeException("Could not find property " + propertyId));
        List<RoomsAvailability> availableRooms =
                roomsAvailabilityRepository.findAvailableRooms(fromDate, toDate, roomType, propertyId);

        for (RoomsAvailability r : availableRooms) {
            if (r.getTotal_rooms() == 0) {
                return new ResponseEntity<>("No Rooms Available", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        bookings.setProperty(property);

        // Payment Calculation Logic (stay duration * nightly price + GST)
        if (availableRooms != null && !availableRooms.isEmpty()) {
            Long nightlyPrice = availableRooms.get(0).getNightlyPrice();

            if (nightlyPrice != null) {
                long duration = java.time.temporal.ChronoUnit.DAYS.between(fromDate, toDate);
                Double totalAmount = duration * nightlyPrice + (duration * nightlyPrice * 0.18); // Adding 18% GST
                // Total payment after GST

                System.out.println("Calculated Total Amount: " + totalAmount);

                bookings.setPaymentAmount(totalAmount);

                Booking savedBookings = bookingRepository.save(bookings);
                pdfService.generateBookingPdf(
                        "D:\\hmsApp\\hms_bookings\\confirmation-order " + savedBookings.getId() + ".pdf", property,savedBookings);

                String pdfPath = "D:\\hmsApp\\hms_bookings\\confirmation-order " + savedBookings.getId() + ".pdf";

                // Send thank you email with PDF attachment
                try {
                    emailService.sendThankYouEmailWithAttachment(savedBookings.getEmail(), pdfPath);
                } catch (Exception e) {
                    return new ResponseEntity<>("Booking created successfully, but failed to send email: " + e.getMessage(), HttpStatus.OK);
                }


                twilioService.sendSms("+919827773965", "Your Booking Confirmed" +
                        "Your Booking Id is :" + savedBookings.getId());
                twilioWhatsAppService.sendWhatsAppMessage("+919827773965", "Your Booking Confirmed" + "Your Booking Id is :" + savedBookings.getId());


                return new ResponseEntity<>("Bookings Created SuccessFully ", HttpStatus.OK);
            }


        }
        return null;
    }
}
