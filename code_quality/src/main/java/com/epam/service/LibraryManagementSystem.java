package com.epam.service;

import com.epam.exceptions.BookAlreadyLoanedException;
import com.epam.exceptions.BookNotFoundException;
import com.epam.exceptions.ReservationException;
import com.epam.exceptions.UserNotAllowedToLoanException;
import com.epam.model.Book;
import com.epam.observer.LibraryNotifier;
import com.epam.observer.NotificationObserver;
import com.epam.strategy.BasicLoanPolicy;
import com.epam.strategy.LoanPolicy;

import java.util.HashMap;
import java.util.Map;

public class LibraryManagementSystem {
    private final HashMap<String, Book> books = new HashMap<>();
    private final HashMap<String, String> loanedBooks = new HashMap<>(); // Book ID -> User ID
    private final HashMap<String, Integer> overdueFines = new HashMap<>(); // User ID -> Fine Amount
    private final LibraryNotifier notifier = new LibraryNotifier();
    private LoanPolicy loanPolicy = new BasicLoanPolicy();

    public void setLoanPolicy(LoanPolicy policy) {
        this.loanPolicy = policy;
    }

    public void addObserver(NotificationObserver observer) {
        notifier.addObserver(observer);
    }

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
            throw new BookNotFoundException("Book with ID " + bookId + " not found.");
        }

        if (book.isLoaned()) {
            throw new BookAlreadyLoanedException("Book with ID " + bookId + " is already loaned out.");
        }

        if (!loanPolicy.canLoan(userId)) {
            throw new UserNotAllowedToLoanException("User with ID " + userId + " is not allowed to loan books.");
        }

        book.loan();
        loanedBooks.put(bookId, userId);
        notifier.notifyObservers("Book " + book.getTitle() + " loaned to user " + userId);

    }

    public void returnBook(String bookId) {
        Book book = books.get(bookId);
        if (book == null || !loanedBooks.containsKey(bookId)) {
            throw new BookNotFoundException("Book with ID " + bookId + " is not currently loaned.");
        }

        book.returnBook();
        loanedBooks.remove(bookId);
        notifier.notifyObservers("Book " + book.getTitle() + " has been returned.");
    }

    public void reserveBook(String bookId, String userId) {
        Book book = books.get(bookId);
        if (book == null) {
            throw new ReservationException("Book with ID " + bookId + " not found.");
        }

        if (book.getReservedBy() != null) {
            throw new ReservationException("Book with ID " + bookId + " is already reserved.");
        }

        book.reserve(userId);
        notifier.notifyObservers("Book " + book.getTitle() + " reserved by user " + userId);

    }

    public void imposeFine(String userId, int amount) {
        overdueFines.put(userId, overdueFines.getOrDefault(userId, 0) + amount);
        notifier.notifyObservers("Fine imposed on user " + userId + ": " + amount + " units.");
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
