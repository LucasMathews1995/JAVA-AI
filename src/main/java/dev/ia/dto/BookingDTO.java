package dev.ia.dto;

import dev.ia.model.Category;
import jakarta.annotation.Nonnull;

import java.time.LocalDate;

public record BookingDTO(@Nonnull String destination, @Nonnull  LocalDate startDate, @Nonnull LocalDate endDate,Category category)         {
}
