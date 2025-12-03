package org.example.mylibrary;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.mylibrary.service.BookService;

public class StatisticsController {

    @FXML
    private Label totalBooksLabel;
    
    @FXML
    private Label readBooksLabel;
    
    @FXML
    private Label unreadBooksLabel;
    
    @FXML
    private Label inProgressBooksLabel;
    
    @FXML
    private Label readPercentageLabel;
    
    private BookService bookService;
    

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
        updateStatistics();
    }
    

    private void updateStatistics() {
        if (bookService != null && totalBooksLabel != null) {
            int totalBooks = bookService.getTotalBooksCount();
            int readBooks = bookService.getReadBooksCount();
            int inProgressBooks = bookService.getInProgressBooksCount();
            int unreadBooks = totalBooks - readBooks - inProgressBooks;
            double readPercentage = bookService.getReadPercentage();
            
            totalBooksLabel.setText(String.valueOf(totalBooks));
            readBooksLabel.setText(String.valueOf(readBooks));
            unreadBooksLabel.setText(String.valueOf(unreadBooks));
            inProgressBooksLabel.setText(String.valueOf(inProgressBooks));
            readPercentageLabel.setText(String.format("%.1f%%", readPercentage));
        }
    }
}