package dev.ia.booking;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;

public record BookingDTO(@Nonnull String destination, @Nonnull  LocalDate startDate, @Nonnull LocalDate endDate,Category category)         {
}
