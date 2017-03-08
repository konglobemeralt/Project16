package iMat.controller;

import iMat.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Kraft on 2017-03-07.
 */
public class ReceiptViewController {

    @FXML
    private ListView<AnchorPane> receiptList;

    //Reference the main application
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }
}
