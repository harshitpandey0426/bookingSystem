package com.lld.system.design.bookingmyshow.api;

import com.lld.system.design.bookingmyshow.service.BookingService;
import com.lld.system.design.bookingmyshow.service.PaymentsService;
import lombok.NonNull;

public class PaymentsController {
    private final PaymentsService paymentsService;
    private final BookingService bookingService;

    public PaymentsController(PaymentsService paymentsService, BookingService bookingService) {
        this.paymentsService = paymentsService;
        this.bookingService = bookingService;
    }

    public void paymentFailed(String bookingId, String user) {
        paymentsService.processPaymentFailed(bookingService.getBooking(bookingId), user);
    }

    public void paymentSuccess(String bookingId, String user) {
        bookingService.confirmBooking(bookingService.getBooking(bookingId), user);
    }
}
