package com.epam.service;

import com.epam.model.Book;

public class BookFactory {

    public static Book createBook(String id, String title) {
        return new Book(id, title);
    }
}

