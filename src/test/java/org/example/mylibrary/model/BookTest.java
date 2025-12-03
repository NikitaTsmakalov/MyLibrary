package org.example.mylibrary.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testBookCreation() {
        Book book = new Book(1, "Война и мир", "Лев Толстой", 1869, "Роман", Book.ReadStatus.YES);
        
        assertEquals(1, book.getId());
        assertEquals("Война и мир", book.getTitle());
        assertEquals("Лев Толстой", book.getAuthor());
        assertEquals(1869, book.getYear());
        assertEquals("Роман", book.getGenre());
        assertEquals(Book.ReadStatus.YES, book.getReadStatus());
        assertTrue(book.isRead());
    }

    @Test
    void testBookProperties() {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Война и мир");
        book.setAuthor("Лев Толстой");
        book.setYear(1869);
        book.setGenre("Роман");
        book.setReadStatus(Book.ReadStatus.IN_PROGRESS);
        
        assertEquals(1, book.getId());
        assertEquals("Война и мир", book.getTitle());
        assertEquals("Лев Толстой", book.getAuthor());
        assertEquals(1869, book.getYear());
        assertEquals("Роман", book.getGenre());
        assertEquals(Book.ReadStatus.IN_PROGRESS, book.getReadStatus());
        assertFalse(book.isRead());
    }

    @Test
    void testDefaultConstructor() {
        Book book = new Book();
        
        assertNotNull(book);
        assertEquals(0, book.getId());
        assertNull(book.getTitle());
        assertNull(book.getAuthor());
        assertEquals(0, book.getYear());
        assertNull(book.getGenre());
        assertNull(book.getReadStatus());
        assertFalse(book.isRead());
    }

    @Test
    void testToString() {
        Book book = new Book(1, "Война и мир", "Лев Толстой", 1869, "Роман", Book.ReadStatus.YES);
        
        assertEquals("Война и мир by Лев Толстой", book.toString());
    }

    @Test
    void testReadStatusEnum() {
        assertEquals("Да", Book.ReadStatus.YES.getDisplayName());
        assertEquals("В процессе", Book.ReadStatus.IN_PROGRESS.getDisplayName());
        assertEquals("Нет", Book.ReadStatus.NO.getDisplayName());
        
        assertEquals("Да", Book.ReadStatus.YES.toString());
        assertEquals("В процессе", Book.ReadStatus.IN_PROGRESS.toString());
        assertEquals("Нет", Book.ReadStatus.NO.toString());
    }
}