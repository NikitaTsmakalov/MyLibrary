package org.example.mylibrary;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.example.mylibrary.model.Book;
import org.example.mylibrary.service.BookService;

import java.io.IOException;

public class MainController {

    @FXML
    private TabPane mainTabPane;
    
    @FXML
    private Tab bookListTab;
    
    @FXML
    private Tab statisticsTab;

    private BookService bookService;
    private MainApp mainApp;
    private StatisticsController statisticsController;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        initializeTabs();
    }

    private void initializeTabs() {
        try {
            // Загрузка вкладки со списком книг
            FXMLLoader bookListLoader = new FXMLLoader();
            bookListLoader.setLocation(MainApp.class.getResource("view/BookOverview.fxml"));
            bookListTab.setContent(bookListLoader.load());
            
            BookListController bookListController = bookListLoader.getController();
            bookListController.setBookService(bookService);
            bookListController.setMainApp(mainApp);
            
            // Загрузка вкладки со статистикой
            FXMLLoader statisticsLoader = new FXMLLoader();
            statisticsLoader.setLocation(MainApp.class.getResource("view/StatisticsView.fxml"));
            statisticsTab.setContent(statisticsLoader.load());
            
            statisticsController = statisticsLoader.getController();
            statisticsController.setBookService(bookService);
            
            // Добавляем слушатель изменений в списке книг для обновления статистики
            bookService.getBooks().addListener((ListChangeListener.Change<? extends Book> change) -> {
                if (statisticsController != null) {
                    statisticsController.setBookService(bookService);
                }
                
                // Также обновляем таблицу при изменении данных
                while (change.next()) {
                    if (change.wasUpdated()) {
                        // Если были обновления, обновляем таблицу
                        bookListController.setBookService(bookService);
                    }
                }
            });
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText("Учет личной библиотеки");
        alert.setContentText("Приложение для учета личной библиотеки с возможностью добавления, редактирования и удаления книг.");
        alert.showAndWait();
    }

    @FXML
    private void handleClose() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Вы уверены, что хотите закрыть приложение?");
        alert.setContentText("Все несохраненные данные будут потеряны.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response.getText().equals("OK")) {
                Stage stage = (Stage) mainTabPane.getScene().getWindow();
                stage.close();
            }
        });
    }
    
    @FXML
    private void handleSave() {
        try {
            bookService.saveToFile("library.txt");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Сохранение");
            alert.setHeaderText("Данные успешно сохранены");
            alert.setContentText("Книги сохранены в файл library.txt");
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка сохранения");
            alert.setContentText("Не удалось сохранить данные в файл: " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleLoad() {
        try {
            bookService.loadFromFile("library.txt");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Загрузка");
            alert.setHeaderText("Данные успешно загружены");
            alert.setContentText("Книги загружены из файла library.txt");
            alert.showAndWait();
            
            // Обновляем статистику после загрузки
            if (statisticsController != null) {
                statisticsController.setBookService(bookService);
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка загрузки");
            alert.setContentText("Не удалось загрузить данные из файла: " + e.getMessage());
            alert.showAndWait();
        }
    }
}