package com.epam.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable Subject (LibraryNotifier)
 * */
public class LibraryNotifier {
    private final List<NotificationObserver> observers = new ArrayList<>();

    public void addObserver(NotificationObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) {
        for (NotificationObserver observer : observers) {
            observer.notify(message);
        }
    }
}
