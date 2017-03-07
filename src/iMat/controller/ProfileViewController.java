package iMat.controller;

import iMat.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Created by konglobemeralt on 2017-03-07.
 */
public class ProfileViewController {

    //Reference the main application
    private Main main;

    @FXML
    Button saveButton;

    public void saveButtonPressed() throws IOException{
        System.out.println("SaveButton pressed");
        main.pageHistory().goBack();
    }

    public void setMain(Main main) {
        this.main = main;
    }

}
