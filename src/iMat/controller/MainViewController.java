package iMat.controller;
import iMat.Main;
import iMat.model.ProductSearch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;


/**
 * Created by konglobemeralt on 2017-02-23.
 */
public class MainViewController {
    @FXML
    private TextField searchBarMain;

    //Reference the main application
    private Main main;

    @FXML
    private void initialize(){
        searchBarMain.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if (searchBarMain.getText().length() > 2){
                    search();
                }
            }
        });
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void homeButtonPressed(ActionEvent event){
        System.out.println("Home Button pressed");
    }

    public void helpButtonPressed(ActionEvent event){
        System.out.println("Help Button pressed");
    }
    public void profileButtonPressed(ActionEvent event){
        System.out.println("profile Button pressed");
        if (main.getMainLayout().getRight() == null) {
            try {
                main.showProfileView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            main.hideProfileView();

        }


    }
    public void kundkorgButtonPressed(ActionEvent event) {
        System.out.println("kundkorg Button pressed");
        if(main.getMainLayout().getRight() == null){
            try{
                main.showShoppingBagView();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            main.hideShoppingBag();
        }

    }
    public void searchButtonPressed(ActionEvent event){

        System.out.print("search Button pressed. ");
        search();
    }

    private void search(){
        main.fillProductView(ProductSearch.search(searchBarMain.getText()));
    }

    public void searchFieldEnterPressed(ActionEvent event){
        System.out.println("Searched for: " + searchBarMain.getText());

    }

}
