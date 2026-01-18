package dev.ia.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "tb_reservas")
public class Booking extends PanacheEntity {
    public String userId;
    public String customerName;
   public String destination ;
   public LocalDate startDate;
   public LocalDate endDate;
   public BookingStatus status;
   public Category category;





    @Override
    public String toString() {
        return "Booking{" +
                "userId='" + userId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", destination='" + destination + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", category=" + category +
                '}';
    }
}
