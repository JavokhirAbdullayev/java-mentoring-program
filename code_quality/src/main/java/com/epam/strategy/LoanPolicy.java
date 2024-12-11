package com.epam.strategy;

public interface LoanPolicy {
    boolean canLoan(String userId);
}
