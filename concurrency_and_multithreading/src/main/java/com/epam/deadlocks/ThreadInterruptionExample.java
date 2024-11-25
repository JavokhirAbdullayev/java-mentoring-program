package com.epam.deadlocks;

class Worker implements Runnable {
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Working...");
                Thread.sleep(500); // Simulate work
            }
        } catch (InterruptedException e) {
            System.out.println("Thread was interrupted! Cleaning up...");
        } finally {
            System.out.println("Thread exiting gracefully.");
        }
    }
}

public class ThreadInterruptionExample {
    public static void main(String[] args) {
        Thread workerThread = new Thread(new Worker());
        workerThread.start();

        try {
            Thread.sleep(2400); // Let the worker run for 3 seconds
        } catch (InterruptedException e) {
            System.out.println("after worker");
//            Thread.currentThread().interrupt(); // Restore interrupted status
        }

        workerThread.interrupt(); // Signal the thread to stop
    }
}

