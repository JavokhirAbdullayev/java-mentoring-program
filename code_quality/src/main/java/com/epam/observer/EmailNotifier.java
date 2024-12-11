package com.epam.observer;

/**
 * Concrete Observer (e.g., EmailNotifier)
 * */
public class EmailNotifier implements NotificationObserver {
    private final String email;

    public EmailNotifier(String email) {
        this.email = email;
    }

    @Override
    public void notify(String message) {
        System.out.println("Email sent to " + email + ": " + message);
    }
}