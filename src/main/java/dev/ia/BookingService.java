package dev.ia;


import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class BookingService {


    private final Map<Long,Booking> bookings = new HashMap<>();

    public BookingService() {
        bookings.put(12345L ,new Booking(12345L,"John Doe","Tesouros do Egito", LocalDate.now().plusMonths(2),LocalDate.now().plusMonths(2).plusDays(2),BookingStatus.PENDING));
        bookings.put(67890L ,new Booking(67890L,"Jane Smith","Aventura Amazonia", LocalDate.now().plusMonths(3),LocalDate.now().plusMonths(3).plusDays(3),BookingStatus.PENDING));
    }

    public Optional<Booking> getBookingDetails(Long id) {
        return Optional.ofNullable(bookings.get(id));
    }

    public Optional<Booking> cancelBooking(Long bookingId,String customerName) {

        if(bookings.containsKey(bookingId)) {
            Booking booking = bookings.get(bookingId);
            if(booking.customerName().endsWith(customerName)) {
                Booking cancelledBooking = new Booking(booking.id(),booking.customerName(), booking.destination(),  booking.startDate(), booking.endDate(), BookingStatus.CANCELLED);

                bookings.put(bookingId,cancelledBooking);
                return Optional.of(cancelledBooking);
            }

        }
        return Optional.empty();
    }
}
