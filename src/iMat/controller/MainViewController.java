package iMat.controller;

import iMat.Main;
import iMat.model.ProductSearch;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.IOException;
import javafx.util.Duration;


/**
 * Created by konglobemeralt on 2017-02-23.
 */
public class MainViewController {


    @FXML
    private Circle shoppingBagCounter;

    @FXML
    private Text itemCounterLable;

    private boolean profilePressed = false;
    @FXML
    private TextField searchBarMain;

    //Reference the main application
    private Main main;

    @FXML
    private void initialize() {
        searchBarMain.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if (searchBarMain.getText().length() > 2) {
                    search();
                }
            }
        });
    }

    public void updateShoppingBagCounter(){
        if(Main.iMat.getShoppingCart().getItems().size() != 0 ) {
            System.out.println("Added item");
            shoppingBagCounter.setVisible(true);
            itemCounterLable.setVisible(true);
            itemCounterLable.toFront();
            itemCounterLable.setText((Integer.toString((int)Math.round(Main.iMat.getShoppingCart().getTotal()))) + ":-");


            ScaleTransition st = new ScaleTransition(Duration.millis(120), shoppingBagCounter);
            st.setByX(1.2f);
            st.setByY(1.2f);
            st.setCycleCount(2);
            st.setAutoReverse(true);

            ScaleTransition st2 = new ScaleTransition(Duration.millis(100), itemCounterLable);
            st2.setByX(1.1f);
            st2.setByY(1.1f);
            st2.setCycleCount(2);
            st2.setAutoReverse(true);

            st.play();
            st2.play();

        }
        else {
            shoppingBagCounter.setVisible(false);
            itemCounterLable.setVisible(false);
            itemCounterLable.setText("");
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void homeButtonPressed(ActionEvent event) {
        System.out.println("Home Button pressed");
    }

    public void helpButtonPressed(ActionEvent event) {
        System.out.println("Help Button pressed");
    }

    public void profileButtonPressed(ActionEvent event) throws IOException {
        System.out.println("profile Button pressed");
        if (!profilePressed) {
            try {
                main.showProfileView();
                profilePressed = !profilePressed;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            main.hideProfileView();
            profilePressed = !profilePressed;
        }


    }

    public void kundkorgButtonPressed(ActionEvent event) {
        System.out.println("kundkorg Button pressed");
        if (main.getMainLayout().getRight() == null) {
            try {
                if (main.getMainLayout().getCenter().getId().equals("tabPane")){
                    ((TabPane)main.getMainLayout().getCenter()).getSelectionModel().select(0);
                }
                else {
                    main.showShoppingBagView();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            main.hideShoppingBag();
        }

    }

    public void searchButtonPressed(ActionEvent event) {

        System.out.print("search Button pressed. ");
        search();
    }

    private void search() {
        main.fillProductView(ProductSearch.search(searchBarMain.getText()));
    }

    public void searchFieldEnterPressed(ActionEvent event) {
        System.out.println("Searched for: " + searchBarMain.getText());

    }

}
