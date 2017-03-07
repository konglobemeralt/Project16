package iMat.controller;

/**
 * Created by Jesper on 2017-02-23.
 */

import iMat.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemController extends AnchorPane implements Initializable {

    private boolean favourited = false;

    @FXML
    private Button favouriteButton;

    @FXML
    private Label itemLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView productImage;

    @FXML
    private Button addToCartButton;

    @FXML
    private Button addButton;

    @FXML
    private TextField textArea;

    @FXML
    private Button subtractButton;

    private BorderPane mainLayout;

    private ShoppingItem shoppingItem;

    //Reference the main application
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public ItemController(ShoppingItem shoppingItem, boolean favourited) {
        //System.out.println("init item");
        AnchorPane itemView;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/item.fxml"));
        loader.setController(this);
        try {
            itemView = loader.load();
            itemLabel.setText(shoppingItem.getProduct().getName());
            priceLabel.setText(shoppingItem.getProduct().getPrice() + " " + shoppingItem.getProduct().getUnit());
            this.productImage.setImage(main.iMat.getFXImage(shoppingItem.getProduct()));
            this.getChildren().add(itemView);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.shoppingItem = shoppingItem;
        this.favourited = favourited;
        initFavourite();
        System.out.println("Name of product: " + shoppingItem.getProduct().getName());
        //this.itemLabel.setText(shoppingItem.getProduct().getName());
        //productImage.setImage(IMatDataHandler.getInstance().getFXImage(shoppingItem.getProduct()));
    }


    @FXML
    private void addButtonPressed(ActionEvent event) {
        shoppingItem.setAmount(shoppingItem.getAmount()+1);
        addToCartButton.setDisable(false);
        subtractButton.setDisable(false);
        updateTextArea();
    }

    @FXML
    private void subtractButtonPressed(ActionEvent event) {
        double newAmount = shoppingItem.getAmount() - 1;
        if (newAmount <= 0){
            newAmount = 0;
            addToCartButton.setDisable(true);
            subtractButton.setDisable(true);
        }
        shoppingItem.setAmount(newAmount);
        updateTextArea();
    }

    @FXML
    private void addToCartButtonPressed(ActionEvent event){

        ShoppingCart cart = Main.iMat.getShoppingCart();
        double amount = shoppingItem.getAmount();
        //shoppingItem.setAmount(0);
        subtractButton.setDisable(true);
        addToCartButton.setDisable(true);

        for (ShoppingItem s: cart.getItems()) {
            if (s.getProduct().equals(shoppingItem.getProduct())){
                s.setAmount(s.getAmount() + amount);
                shoppingItem.setAmount(0);
                main.updateShoppingBag();
                updateTextArea();
                return;
            }
        }

        cart.addItem(new ShoppingItem(shoppingItem.getProduct(), shoppingItem.getAmount()));
        shoppingItem.setAmount(0);
        main.updateShoppingBag();
        updateTextArea();
    }

    private void updateTextArea(){
        textArea.setText(shoppingItem.getAmount() + " " + shoppingItem.getProduct().getUnitSuffix());
    }

    private void amountTextAreaLostFocus() {

        try {
            double newAmount = Double.parseDouble(textArea.getText());
            shoppingItem.setAmount(newAmount);
        } catch (NumberFormatException n) {
        }

        if (shoppingItem.getAmount() > 0) {
            updateTextArea();
        } else {
            shoppingItem.setAmount(0);
        }
    }

    private void initFavourite(){
        if(this.favourited){
            Main.iMat.removeFavorite(shoppingItem.getProduct());
            favouriteButton.getStyleClass().removeAll("favourited");
            favouriteButton.getStyleClass().add("favouriteButton");
            this.favourited = false;
        }
        else{
            Main.iMat.addFavorite(shoppingItem.getProduct());
            favouriteButton.getStyleClass().removeAll("favouriteButton");
            favouriteButton.getStyleClass().add("favourited");
            this.favourited = true;
        }

    }

    private void amountTextAreaClicked() {
        textArea.setText(textArea.getText().split(" ")[0]);
        textArea.selectAll();
    }

    public void favouritedItem(ActionEvent event){
        if(this.favourited){
            Main.iMat.removeFavorite(shoppingItem.getProduct());
            favouriteButton.getStyleClass().removeAll("favourited");
            favouriteButton.getStyleClass().add("favouriteButton");
            this.favourited = false;
        }
        else{
            Main.iMat.addFavorite(shoppingItem.getProduct());
            favouriteButton.getStyleClass().removeAll("favouriteButton");
            favouriteButton.getStyleClass().add("favourited");
            this.favourited = true;
        }


    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textArea.setOnMouseClicked((e) -> amountTextAreaClicked());
        textArea.focusedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    amountTextAreaLostFocus();
                }
            }
        });
    }
}