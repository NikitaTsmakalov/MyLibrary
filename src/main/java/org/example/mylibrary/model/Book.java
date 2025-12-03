package org.example.mylibrary.model;

import javafx.beans.property.*;

public class Book {
    private final IntegerProperty id;
    private final StringProperty title;
    private final StringProperty author;
    private final IntegerProperty year;
    private final StringProperty genre;
    private final ObjectProperty<ReadStatus> readStatus;

    public enum ReadStatus {
        YES("Да"),
        IN_PROGRESS("В процессе"),
        NO("Нет");

        private final String displayName;

        ReadStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public Book() {
        this.id = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.author = new SimpleStringProperty();
        this.year = new SimpleIntegerProperty();
        this.genre = new SimpleStringProperty();
        this.readStatus = new SimpleObjectProperty<>();
    }

    public Book(int id, String title, String author, int year, String genre, ReadStatus readStatus) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.year = new SimpleIntegerProperty(year);
        this.genre = new SimpleStringProperty(genre);
        this.readStatus = new SimpleObjectProperty<>(readStatus);
    }


    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }


    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }


    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public StringProperty authorProperty() {
        return author;
    }


    public int getYear() {
        return year.get();
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public IntegerProperty yearProperty() {
        return year;
    }


    public String getGenre() {
        return genre.get();
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public StringProperty genreProperty() {
        return genre;
    }


    public ReadStatus getReadStatus() {
        return readStatus.get();
    }

    public void setReadStatus(ReadStatus readStatus) {
        this.readStatus.set(readStatus);
    }

    public ObjectProperty<ReadStatus> readStatusProperty() {
        return readStatus;
    }

    // Методы для обратной совместимости
    public boolean isRead() {
        return ReadStatus.YES == getReadStatus();
    }
    public void setRead(boolean read) {
        setReadStatus(read ? ReadStatus.YES : ReadStatus.NO);
    }

    @Override
    public String toString() {
        return getTitle() + " by " + getAuthor();
    }
}