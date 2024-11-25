package com.epam.jmp.dto;

import java.time.LocalDate;

public record Subscription(BankCard bankcard, LocalDate startDate) {
}
