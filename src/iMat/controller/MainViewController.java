package iMat.controller;

import iMat.Main;
import iMat.controller.BackButtonHandler.Link;
import iMat.model.ProductSearch;
import javafx.animation.ScaleTransition;
import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.IOException;
import javafx.util.Duration;
import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.ShoppingCartListener;


/**
 * Created by konglobemeralt on 2017-02-23.
 */
public class MainViewController implements ShoppingCartListener{


    @FXML
    private Circle shoppingBagCounter;

    @FXML
    private Text itemCounterLable;

    @FXML
    private TextField searchBarMain;

    @FXML
    private Button backButton;

    @FXML
    private Button forwardButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button kundkorgButton;

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

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        updateShoppingBagCounter();
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

            RotateTransition rt = new RotateTransition(Duration.millis(100), itemCounterLable);
            rt.setByAngle(30);
            rt.setCycleCount(2);
            rt.setAutoReverse(true);

            st.play();
            st2.play();
            rt.play();

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
        main.showHomeView();
        main.pageHistory().addLink(Link.HOME);
    }

    public void helpButtonPressed(ActionEvent event) {
        System.out.println("Help Button pressed");
    }

    public void profileButtonPressed(ActionEvent event) throws IOException {
        if (main.getMainLayout().getCenter() == null || !main.getMainLayout().getCenter().getId().equals("profilePane")) {
            try {
                main.showProfileView();
                main.pageHistory().addLink(Link.PROFILE);


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            main.pageHistory().goBack();
        }


    }

    public void kundkorgButtonPressed(ActionEvent event) {
        System.out.println("kundkorg Button pressed");
        if (main.getMainLayout().getRight() == null) {
            try {
                if (main.getMainLayout().getCenter() != null && main.getMainLayout().getCenter().getId().equals("tabPane")){
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

    private void search() {
        main.fillProductView(ProductSearch.search(searchBarMain.getText()));
    }

    public void searchFieldEnterPressed(ActionEvent event) {
        main.pageHistory().addProductLink(ProductSearch.search(searchBarMain.getText()));
        search();
    }

    @FXML
    void backButtonPressed(ActionEvent event) {
        main.pageHistory().goBack();
    }

    @FXML
    void forwardButtonPressed(ActionEvent event) {
        main.pageHistory().goForwards();
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getForwardButton() {return forwardButton;}
}
