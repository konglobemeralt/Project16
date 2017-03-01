package iMat.controller;

import iMat.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class PayWizardViewController {

    @FXML
    private AnchorPane payViewPane;

    @FXML
    private Label totItemsPayView;

    @FXML
    private Label totPricePayView;

    @FXML
    private ToggleGroup payMethodRadioButtons;

    //Reference the main application
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

}
