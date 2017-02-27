package iMat.controller;
import iMat.Main;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCartListener;
import se.chalmers.ait.dat215.project.ShoppingItem;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Created by konglobemeralt on 2017-02-23.
 */
public class itemViewController extends AnchorPane implements Initializable, ShoppingCartListener {
    @FXML
    TextField searchBarMain;

    @FXML
    GridPane centerPaneMain;

    IMatDataHandler iMatHandler;
    List<ShoppingItem> shoppingItems;



    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init itemView");
        iMatHandler = IMatDataHandler.getInstance();
        shoppingItems = iMatHandler.getShoppingCart().getItems();
        iMatHandler.getShoppingCart().addShoppingCartListener(this);


       // Button tempButton1 = new Button("Button_1");
       // Button tempButton2 = new Button("Button_2");

       // centerPaneMain.add(tempButton1, 0, 0);
       // centerPaneMain.add(tempButton2, 0, 1);

        centerPaneMain.add(new itemController(new ShoppingItem(iMatHandler.getProduct(1), 2)), 0, 1);
        centerPaneMain.add(new itemController(new ShoppingItem(iMatHandler.getProduct(2), 2)), 0, 2);
        centerPaneMain.add(new itemController(new ShoppingItem(iMatHandler.getProduct(3), 2)), 0, 3);
        centerPaneMain.add(new itemController(new ShoppingItem(iMatHandler.getProduct(4), 2)), 1, 1);
        centerPaneMain.add(new itemController(new ShoppingItem(iMatHandler.getProduct(5), 2)), 1, 2);
        centerPaneMain.add(new itemController(new ShoppingItem(iMatHandler.getProduct(6), 2)), 1, 3);
        centerPaneMain.add(new itemController(new ShoppingItem(iMatHandler.getProduct(7), 2)), 2, 1);
        centerPaneMain.add(new itemController(new ShoppingItem(iMatHandler.getProduct(8), 2)), 2, 2);
        centerPaneMain.add(new itemController(new ShoppingItem(iMatHandler.getProduct(9), 2)), 2, 3);

        iMatHandler.getShoppingCart().getTotal();
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
    }



}
