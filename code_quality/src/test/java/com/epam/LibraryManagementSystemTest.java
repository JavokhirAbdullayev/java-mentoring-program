package com.epam;


import com.epam.model.Book;
import com.epam.service.BookFactory;
import com.epam.service.LibraryManagementSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;



public class LibraryManagementSystemTest {

    private LibraryManagementSystem library;

    @BeforeEach
    void setUp() {
        library = new LibraryManagementSystem();
        library.addBook(BookFactory.createBook("BK001", "Java Programming"));
        library.addBook(BookFactory.createBook("BK002", "Data Structures"));
    }

    @Test
    void testCheckOutBook_Success() {
        library.checkOutBook("BK001", "USR001");
        Book book = library.getBooks().get("BK001");
        assertTrue(book.isLoaned());
        assertEquals("USR001", library.getLoanedBooks().get("BK001"));
    }

    @Test
    void testCheckOutBook_AlreadyLoaned() {
        library.checkOutBook("BK001", "USR001");
        library.checkOutBook("BK001", "USR002");
        assertTrue(library.getBooks().get("BK001").isLoaned());
        assertEquals("USR001", library.getLoanedBooks().get("BK001")); // Original user remains
    }

    @Test
    void testReturnBook_Success() {
        library.checkOutBook("BK001", "USR001");
        library.returnBook("BK001");
        Book book = library.getBooks().get("BK001");
        assertFalse(book.isLoaned());
        assertNull(library.getLoanedBooks().get("BK001"));
    }

    @Test
    void testReturnBook_NotCheckedOut() {
        library.returnBook("BK001");
        assertFalse(library.getBooks().get("BK001").isLoaned());
    }

    @Test
    void testReserveBook_Success() {
        library.reserveBook("BK001", "USR002");
        Book book = library.getBooks().get("BK001");
        assertEquals("USR002", book.getReservedBy());
    }

    @Test
    void testReserveBook_AlreadyReserved() {
        library.reserveBook("BK001", "USR002");
        library.reserveBook("BK001", "USR003");
        assertEquals("USR002", library.getBooks().get("BK001").getReservedBy()); // First reservation persists
    }

    @Test
    void testImposeFine() {
        library.imposeFine("USR001", 100);
        assertEquals(100, library.getFine("USR001"));
        library.imposeFine("USR001", 50);
        assertEquals(150, library.getFine("USR001"));
    }

    @Test
    void testGetFine_NoFineImposed() {
        assertEquals(0, library.getFine("USR002"));
    }

    @Test
    void testDisplayBooks() {
        library.checkOutBook("BK001", "USR001");
        library.reserveBook("BK002", "USR002");

        library.displayBooks();

        assertTrue(library.getBooks().get("BK001").isLoaned());
        assertEquals("USR002", library.getBooks().get("BK002").getReservedBy());
    }

    @Test
    void testAddBook() {
        library.addBook(BookFactory.createBook("BK003", "Algorithms"));
        assertNotNull(library.getBooks().get("BK003"));
        assertEquals("Algorithms", library.getBooks().get("BK003").getTitle());
    }
}
