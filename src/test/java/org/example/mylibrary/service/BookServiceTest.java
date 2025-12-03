package org.example.mylibrary.service;

import javafx.collections.ObservableList;
import org.example.mylibrary.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
    }

    @Test
    void testGetBooks() {
        ObservableList<Book> books = bookService.getBooks();
        assertNotNull(books);
        // Проверяем, что сервис инициализируется с тестовыми данными
        assertTrue(books.size() > 0);
    }

    @Test
    void testAddBook() {
        int initialSize = bookService.getBooks().size();
        Book book = new Book(0, "Тестовая книга", "Тестовый автор", 2025, "Тест", Book.ReadStatus.NO);
        bookService.addBook(book);
        
        assertEquals(initialSize + 1, bookService.getBooks().size());
        assertTrue(bookService.getBooks().contains(book));
        // Проверяем, что ID был назначен автоматически
        assertNotEquals(0, book.getId());
    }

    @Test
    void testDeleteBook() {
        // Создаем новую книгу для теста, чтобы не влиять на существующие данные
        Book book = new Book(0, "Тестовая книга для удаления", "Тестовый автор", 2025, "Тест", Book.ReadStatus.NO);
        bookService.addBook(book);
        
        int initialSize = bookService.getBooks().size();
        bookService.deleteBook(book);
        
        assertEquals(initialSize - 1, bookService.getBooks().size());
        assertFalse(bookService.getBooks().contains(book));
    }

    @Test
    void testSearchBooks() {
        // Очищаем существующие данные для точного тестирования
        bookService.getBooks().clear();
        
        Book book1 = new Book(0, "Война и мир", "Лев Толстой", 1869, "Роман", Book.ReadStatus.YES);
        Book book2 = new Book(0, "Преступление и наказание", "Федор Достоевский", 1866, "Роман", Book.ReadStatus.NO);
        bookService.addBook(book1);
        bookService.addBook(book2);
        
        // Поиск по названию
        ObservableList<Book> results1 = bookService.searchBooks("Война");
        assertEquals(1, results1.size());
        assertEquals("Война и мир", results1.get(0).getTitle());
        
        // Поиск по автору
        ObservableList<Book> results2 = bookService.searchBooks("Достоевский");
        assertEquals(1, results2.size());
        assertEquals("Преступление и наказание", results2.get(0).getTitle());
        
        // Поиск по жанру
        ObservableList<Book> results3 = bookService.searchBooks("Роман");
        assertEquals(2, results3.size());
        
        // Поиск без результатов
        ObservableList<Book> results4 = bookService.searchBooks("Не существующая книга");
        assertEquals(0, results4.size());
        
        // Пустой поиск
        ObservableList<Book> results5 = bookService.searchBooks("");
        assertEquals(2, results5.size());
    }

    @Test
    void testSaveAndLoadFile() throws IOException {
        // Создаем новый сервис без тестовых данных
        BookService testService = new BookService();
        testService.getBooks().clear(); // Очищаем тестовые данные
        
        Book book1 = new Book(1, "Война и мир", "Лев Толстой", 1869, "Роман", Book.ReadStatus.YES);
        Book book2 = new Book(2, "Преступление и наказание", "Федор Достоевский", 1866, "Роман", Book.ReadStatus.IN_PROGRESS);
        testService.addBook(book1);
        testService.addBook(book2);
        
        String filename = "test_library.txt";
        
        // Сохраняем в файл
        testService.saveToFile(filename);
        
        // Создаем новый сервис и загружаем из файла
        BookService loadedService = new BookService();
        loadedService.getBooks().clear(); // Очищаем тестовые данные
        loadedService.loadFromFile(filename);
        
        assertEquals(2, loadedService.getBooks().size());
        assertEquals("Война и мир", loadedService.getBooks().get(0).getTitle());
        assertEquals(Book.ReadStatus.YES, loadedService.getBooks().get(0).getReadStatus());
        assertEquals("Преступление и наказание", loadedService.getBooks().get(1).getTitle());
        assertEquals(Book.ReadStatus.IN_PROGRESS, loadedService.getBooks().get(1).getReadStatus());
        
        // Удаляем тестовый файл
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testStatistics() {
        // Создаем новый сервис без тестовых данных
        BookService testService = new BookService();
        testService.getBooks().clear(); // Очищаем тестовые данные
        
        Book book1 = new Book(1, "Война и мир", "Лев Толстой", 1869, "Роман", Book.ReadStatus.YES);
        Book book2 = new Book(2, "Преступление и наказание", "Федор Достоевский", 1866, "Роман", Book.ReadStatus.NO);
        Book book3 = new Book(3, "Мастер и Маргарита", "Михаил Булгаков", 1967, "Роман", Book.ReadStatus.YES);
        testService.addBook(book1);
        testService.addBook(book2);
        testService.addBook(book3);
        
        assertEquals(3, testService.getTotalBooksCount());
        assertEquals(2, testService.getReadBooksCount());
        assertEquals(1, testService.getTotalBooksCount() - testService.getReadBooksCount()); // 1 непрочитанная
        assertEquals(66.7, testService.getReadPercentage(), 0.1);
    }
}