package com.epam.jmp;

import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.User;
import com.epam.jmp.bank.impl.BankImpl;
import com.epam.jmp.api.Bank;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BankImplTest {

    Bank bank = new BankImpl();

    @Test
    void testCreateBankCard() {
        var user = user("John", "Doe", LocalDate.of(1998, 01, 01));
        var bankCard = bank.createBankCard(user, BankCardType.DEBIT);
        assertNotNull(bankCard.getNumber());
    }

    private User user(String name, String surname, LocalDate birthDay) {
        return new User(name, surname, birthDay);
    }
}
