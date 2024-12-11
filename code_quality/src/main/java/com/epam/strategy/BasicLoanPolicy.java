package com.epam.strategy;

public class BasicLoanPolicy  implements LoanPolicy {
    @Override
    public boolean canLoan(String userId) {
        // Simple policy: always allow loan
        return true;
    }
}
