package com.epam.service;

import com.epam.model.Book;

import java.util.HashMap;
import java.util.Map;

public class LibraryManagementSystem {
    private final HashMap<String, Book> books = new HashMap<>();
    private final HashMap<String, String> loanedBooks = new HashMap<>(); // Book ID -> User ID
    private final HashMap<String, Integer> overdueFines = new HashMap<>(); // User ID -> Fine Amount

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    public Map<String, Book> getBooks() {
        return books;
    }

    public HashMap<String, String> getLoanedBooks() {
        return loanedBooks;
    }

    public void checkOutBook(String bookId, String userId) {
        Book book = books.get(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.isLoaned()) {
            System.out.println("Book is currently unavailable.");
        } else {
            book.loan();
            loanedBooks.put(bookId, userId);
            System.out.println("Book checked out by " + userId);
        }
    }

    public void returnBook(String bookId) {
        Book book = books.get(bookId);
        if (book == null || !loanedBooks.containsKey(bookId)) {
            System.out.println("This book was not checked out.");
            return;
        }

        book.returnBook();
        loanedBooks.remove(bookId);
        System.out.println("Book returned.");
    }

    public void reserveBook(String bookId, String userId) {
        Book book = books.get(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.getReservedBy() != null) {
            System.out.println("Book is already reserved.");
        } else {
            book.reserve(userId);
            System.out.println("Book reserved by " + userId);
        }
    }

    public void imposeFine(String userId, int amount) {
        overdueFines.put(userId, overdueFines.getOrDefault(userId, 0) + amount);
        System.out.println("Fine imposed on " + userId + ": " + amount + " units.");
    }

    public int getFine(String userId) {
        return overdueFines.getOrDefault(userId, 0);
    }

    public void displayBooks() {
        for (Book book : books.values()) {
            System.out.println(book.getId() + " - " + book.getTitle() +
                    " (Loaned: " + book.isLoaned() + ", Reserved by: " + (book.getReservedBy() == null ? "None" : book.getReservedBy()) + ")");
        }
    }
}
