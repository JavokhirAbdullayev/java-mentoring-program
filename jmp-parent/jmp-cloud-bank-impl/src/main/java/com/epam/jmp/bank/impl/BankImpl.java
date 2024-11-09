package com.epam.jmp.bank.impl;

import com.epam.jmp.dto.*;
import com.epam.jmp.api.Bank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class BankImpl implements Bank {
    private final List<BankCard> cards = new ArrayList<>();

    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        var card = switch(cardType) {
            case DEBIT -> new DebitBankCard(UUID.randomUUID().toString(), user, 0);
            case CREDIT -> new CreditBankCard(UUID.randomUUID().toString(), user, 0);
        };
        cards.add(card);
        return card;
    }
}
