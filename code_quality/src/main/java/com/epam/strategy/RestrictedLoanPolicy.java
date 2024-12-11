package com.epam.strategy;

import com.epam.service.LibraryManagementSystem;

public class RestrictedLoanPolicy implements LoanPolicy {
    private final LibraryManagementSystem library;

    public RestrictedLoanPolicy(LibraryManagementSystem library) {
        this.library = library;
    }

    @Override
    public boolean canLoan(String userId) {
        // Disallow loan if user has outstanding fines
        return library.getFine(userId) == 0;
    }
}
