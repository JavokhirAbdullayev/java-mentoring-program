package com.epam.model;

public class Book {
    private final String id;
    private final String title;
    private String reservedBy;
    private boolean isLoaned;

    public Book(String id, String title) {
        this.id = id;
        this.title = title;
        this.isLoaned = false;
        this.reservedBy = null;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public boolean isLoaned() { return isLoaned; }
    public String getReservedBy() { return reservedBy; }

    public void loan() { this.isLoaned = true; }
    public void returnBook() { this.isLoaned = false; this.reservedBy = null; }
    public void reserve(String userId) { this.reservedBy = userId; }
}
