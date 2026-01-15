package dev.ia.model;

import dev.ia.BookingStatus;
import dev.ia.Category;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import java.time.LocalDate;
import java.util.List;

public class Booking extends PanacheEntity {
    public String userId;
    String customerName;
    String destination ;
    LocalDate startDate;
    LocalDate endDate;
    BookingStatus status;
    Category category;


}
