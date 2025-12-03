package org.example.mylibrary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.mylibrary.model.Book;
import org.example.mylibrary.service.BookService;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private BookService bookService;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Личная библиотека");
        

        bookService = new BookService();
        
        initRootLayout();
        showBookOverview();
    }
    

    public void initRootLayout() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            

            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setScene(scene);
            

            MainController controller = loader.getController();
            controller.setBookService(bookService);
            controller.setMainApp(this);
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public void showBookOverview() {
        try {
            // Загружаем обзор книг
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BookOverview.fxml"));
            AnchorPane bookOverview = (AnchorPane) loader.load();
            
            // Помещаем обзор книг в центр корневого макета
            rootLayout.setCenter(bookOverview);
            
            // Передаем сервис и ссылку на главное приложение в контроллер
            BookListController controller = loader.getController();
            controller.setBookService(bookService);
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public boolean showBookEditDialog(Book book) {
        try {
            // Загружаем fxml файл и создаем новую сцену для всплывающего окна
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BookEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Создаем диалоговое окно Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактировать книгу");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            dialogStage.setScene(scene);
            
            // Передаем книгу в контроллер
            BookEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setBook(book);
            
            // Отображаем диалоговое окно и ждем, пока пользователь его не закроет
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}