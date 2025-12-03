package org.example.mylibrary.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.mylibrary.model.Book;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BookService {
    private final ObservableList<Book> books = FXCollections.observableArrayList();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public BookService() {

        initializeTestData();
    }

    private void initializeTestData() {
        addBook(new Book(nextId.getAndIncrement(), "Война и мир", "Лев Толстой", 1869, "Роман", Book.ReadStatus.YES));
        addBook(new Book(nextId.getAndIncrement(), "Преступление и наказание", "Федор Достоевский", 1866, "Роман", Book.ReadStatus.NO));
        addBook(new Book(nextId.getAndIncrement(), "Мастер и Маргарита", "Михаил Булгаков", 1967, "Роман", Book.ReadStatus.IN_PROGRESS));
    }

    public ObservableList<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        if (book.getId() == 0) {
            book.setId(nextId.getAndIncrement());
        }
        books.add(book);
    }

    public void updateBook(Book book) {

    }

    public void deleteBook(Book book) {
        books.remove(book);
    }

    public ObservableList<Book> searchBooks(String query) {
        if (query == null || query.isEmpty()) {
            return books;
        }
        
        ObservableList<Book> filteredBooks = FXCollections.observableArrayList();
        String lowerQuery = query.toLowerCase();
        
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(lowerQuery) ||
                book.getAuthor().toLowerCase().contains(lowerQuery) ||
                book.getGenre().toLowerCase().contains(lowerQuery)) {
                filteredBooks.add(book);
            }
        }
        
        return filteredBooks;
    }

    public void saveToFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Book book : books) {
                // Сохраняем статус как строку
                String statusStr = "NO";
                if (book.getReadStatus() == Book.ReadStatus.YES) {
                    statusStr = "YES";
                } else if (book.getReadStatus() == Book.ReadStatus.IN_PROGRESS) {
                    statusStr = "IN_PROGRESS";
                }
                
                writer.println(book.getId() + ";" + 
                             book.getTitle() + ";" + 
                             book.getAuthor() + ";" + 
                             book.getYear() + ";" + 
                             book.getGenre() + ";" + 
                             statusStr);
            }
        }
    }

    public void loadFromFile(String filename) throws IOException {
        books.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 6) {
                    int id = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String author = parts[2];
                    int year = Integer.parseInt(parts[3]);
                    String genre = parts[4];
                    
                    // Преобразуем строку статуса обратно в enum
                    Book.ReadStatus readStatus = Book.ReadStatus.NO;
                    switch (parts[5]) {
                        case "YES":
                            readStatus = Book.ReadStatus.YES;
                            break;
                        case "IN_PROGRESS":
                            readStatus = Book.ReadStatus.IN_PROGRESS;
                            break;
                        case "NO":
                            readStatus = Book.ReadStatus.NO;
                            break;
                    }
                    
                    Book book = new Book(id, title, author, year, genre, readStatus);
                    books.add(book);
                    
                    // Обновляем счетчик ID
                    if (id >= nextId.get()) {
                        nextId.set(id + 1);
                    }
                }
            }
        }
    }

    public int getTotalBooksCount() {
        return books.size();
    }

    public int getReadBooksCount() {
        return (int) books.stream().filter(book -> book.getReadStatus() == Book.ReadStatus.YES).count();
    }

    public int getInProgressBooksCount() {
        return (int) books.stream().filter(book -> book.getReadStatus() == Book.ReadStatus.IN_PROGRESS).count();
    }

    public double getReadPercentage() {
        if (books.isEmpty()) {
            return 0.0;
        }
        return (double) getReadBooksCount() / getTotalBooksCount() * 100;
    }
}