package iMat.controller;

/**
 * Created by Jesper on 2017-02-23.
 */

import iMat.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class itemController extends AnchorPane implements Initializable {

    @FXML
    private Label itemLabel;

    @FXML
    private ImageView productImage;

    //Reference the main application
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    private BorderPane mainLayout;

    ShoppingItem shoppingItem;

    public itemController(ShoppingItem shoppingItem)  {
        System.out.println("init item");
        AnchorPane itemView;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/item.fxml"));
        loader.setController(this);
        try {
            itemView = loader.load();
            itemLabel.setText(shoppingItem.getProduct().getName());
            this.productImage.setImage(IMatDataHandler.getInstance().getFXImage(shoppingItem.getProduct()));
            this.getChildren().add(itemView);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        this.shoppingItem = shoppingItem;
        System.out.println("Name of product: "  + shoppingItem.getProduct().getName());
        //this.itemLabel.setText(shoppingItem.getProduct().getName());
        //productImage.setImage(IMatDataHandler.getInstance().getFXImage(shoppingItem.getProduct()));
    }


    public void favouritedItem(ActionEvent event){

        this.setStyle( "-fx-background-image: url(\"img/ic_favorite_black_24dp_1x.png\");");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}