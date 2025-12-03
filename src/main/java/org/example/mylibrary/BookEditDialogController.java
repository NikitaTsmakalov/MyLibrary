package org.example.mylibrary;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.mylibrary.model.Book;

public class BookEditDialogController {

    @FXML
    private TextField titleField;
    
    @FXML
    private TextField authorField;
    
    @FXML
    private TextField yearField;
    
    @FXML
    private ComboBox<String> genreComboBox;
    
    @FXML
    private ComboBox<String> readComboBox;
    
    @FXML
    private Button okButton;
    
    @FXML
    private Button cancelButton;
    
    private Stage dialogStage;
    private Book book;
    private boolean okClicked = false;
    
    @FXML
    private void initialize() {
        // Инициализация ComboBox для жанров
        genreComboBox.getItems().addAll(
            "Роман", "Детектив", "Фантастика", "Фэнтези", 
            "Биография", "Историческая литература", "Поэзия",
            "Научная литература", "Учебная литература"
        );
        
        // Инициализация ComboBox для статуса прочтения с новыми значениями
        readComboBox.getItems().addAll("Да", "В процессе", "Нет");
        readComboBox.setPromptText("Выберите статус");
    }
    

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    

    public void setBook(Book book) {
        this.book = book;
        
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        yearField.setText(Integer.toString(book.getYear()));
        genreComboBox.setValue(book.getGenre());
        

        if (book.getReadStatus() != null) {
            readComboBox.setValue(book.getReadStatus().getDisplayName());
        } else {
            readComboBox.setValue("Нет"); // Значение по умолчанию
        }
    }
    

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            book.setTitle(titleField.getText());
            book.setAuthor(authorField.getText());
            book.setYear(Integer.parseInt(yearField.getText()));
            book.setGenre(genreComboBox.getValue());
            
            // Преобразуем строковое значение обратно в enum
            String readStatus = readComboBox.getValue();
            if ("Да".equals(readStatus)) {
                book.setReadStatus(Book.ReadStatus.YES);
            } else if ("В процессе".equals(readStatus)) {
                book.setReadStatus(Book.ReadStatus.IN_PROGRESS);
            } else {
                book.setReadStatus(Book.ReadStatus.NO);
            }
            
            okClicked = true;
            dialogStage.close();
        }
    }
    

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    

    public boolean isOkClicked() {
        return okClicked;
    }
    

    private boolean isInputValid() {
        String errorMessage = "";
        
        if (titleField.getText() == null || titleField.getText().length() == 0) {
            errorMessage += "Не введено название книги!\n";
        }
        if (authorField.getText() == null || authorField.getText().length() == 0) {
            errorMessage += "Не введен автор книги!\n";
        }
        if (yearField.getText() == null || yearField.getText().length() == 0) {
            errorMessage += "Не введен год издания!\n";
        } else {

            try {
                Integer.parseInt(yearField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Год издания должен быть целым числом!\n";
            }
        }
        
        if (genreComboBox.getValue() == null) {
            errorMessage += "Не выбран жанр книги!\n";
        }
        
        if (readComboBox.getValue() == null) {
            errorMessage += "Не выбран статус прочтения книги!\n";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Некорректные поля");
            alert.setHeaderText("Пожалуйста, исправьте некорректные поля");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }
}