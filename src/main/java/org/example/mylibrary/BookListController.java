package org.example.mylibrary;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.mylibrary.model.Book;
import org.example.mylibrary.service.BookService;

import java.io.IOException;

public class BookListController {

    @FXML
    private TableView<Book> bookTable;
    
    @FXML
    private TableColumn<Book, String> titleColumn;
    
    @FXML
    private TableColumn<Book, String> authorColumn;
    
    @FXML
    private TableColumn<Book, Integer> yearColumn;
    
    @FXML
    private TableColumn<Book, String> genreColumn;
    
    @FXML
    private TableColumn<Book, String> readColumn;
    
    @FXML
    private TextField searchField;
    
    private BookService bookService;
    
    private MainApp mainApp;
    
    @FXML
    private void initialize() {

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        

        readColumn.setCellValueFactory(cellData -> {
            Book.ReadStatus status = cellData.getValue().getReadStatus();
            return new javafx.beans.property.SimpleStringProperty(status != null ? status.getDisplayName() : "");
        });
        

        bookTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                handleEditBook();
            }
        });
    }
    
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
        bookTable.setItems(bookService.getBooks());
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    private void handleAddBook() {
        Book tempBook = new Book();
        tempBook.setReadStatus(Book.ReadStatus.NO); // Установим начальное значение
        boolean okClicked = mainApp.showBookEditDialog(tempBook);
        if (okClicked) {
            bookService.addBook(tempBook);
        }
    }
    
    @FXML
    private void handleEditBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            Book tempBook = new Book(
                selectedBook.getId(),
                selectedBook.getTitle(),
                selectedBook.getAuthor(),
                selectedBook.getYear(),
                selectedBook.getGenre(),
                selectedBook.getReadStatus()
            );
            
            boolean okClicked = mainApp.showBookEditDialog(tempBook);
            if (okClicked) {
                // Обновляем выбранный объект данными из временного
                selectedBook.setTitle(tempBook.getTitle());
                selectedBook.setAuthor(tempBook.getAuthor());
                selectedBook.setYear(tempBook.getYear());
                selectedBook.setGenre(tempBook.getGenre());
                selectedBook.setReadStatus(tempBook.getReadStatus());
                

                bookTable.refresh();
            }
        } else {
            // Ничего не выбрано
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Нет выбора");
            alert.setHeaderText("Не выбрана книга");
            alert.setContentText("Пожалуйста, выберите книгу в таблице.");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleDeleteBook() {
        int selectedIndex = bookTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            // Подтверждение удаления
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText("Вы уверены, что хотите удалить эту книгу?");
            alert.setContentText("Это действие нельзя будет отменить.");
            
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Book book = bookTable.getItems().remove(selectedIndex);
                    bookService.deleteBook(book);
                }
            });
        } else {
            // Ничего не выбрано
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Нет выбора");
            alert.setHeaderText("Не выбрана книга");
            alert.setContentText("Пожалуйста, выберите книгу в таблице.");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleSearch() {
        String query = searchField.getText();
        ObservableList<Book> filteredBooks = bookService.searchBooks(query);
        bookTable.setItems(filteredBooks);
    }
    
    @FXML
    private void handleClearSearch() {
        searchField.clear();
        bookTable.setItems(bookService.getBooks());
    }
}