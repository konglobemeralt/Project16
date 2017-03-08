package iMat.controller;

import iMat.Main;
import iMat.controller.BackButtonHandler.Link;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Created by Kraft on 2017-03-07.
 */
public class ConfirmationViewController {

    //Reference the main application
    private Main main;

    @FXML
    private Label timeLabel;

    @FXML
    private Label dateLabel;

    @FXML
    void receiptsButtonPressed(ActionEvent event) {
        main.showReceiptView();
        main.pageHistory().addLink(Link.RECEIPTS);
    }

    @FXML
    void returnHomePressed(ActionEvent event) {
        main.showHomeView();
        main.pageHistory().addLink(Link.HOME);
    }

    public void updateText(String date, String time){
        timeLabel.setText(time);
        dateLabel.setText(date);
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
