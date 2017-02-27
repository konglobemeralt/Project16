package iMat.controller;

/**
 * Created by Jesper on 2017-02-23.
 */

 import iMat.Main;
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
    private BorderPane mainLayout;


    ShoppingItem shoppingItem;

    @FXML
    Label itemLabel;

    @FXML
    ImageView productImage;

    public itemController(ShoppingItem shoppingItem) {
        System.out.println("init item");

        ImageView image = new ImageView(IMatDataHandler.getInstance().getFXImage(shoppingItem.getProduct()));
        image.setFitHeight(100);
        image.setFitWidth(100);
        Button tempButton = new Button(shoppingItem.getProduct().getName(), image);
        tempButton.setMaxWidth(200);
        tempButton.setMaxHeight(200);
        tempButton.setStyle("-fx-font: 22 arial; fx-background-size: 30px;");
        this.getChildren().add(tempButton);

            //this.getChildren().add(anchorPane);



        this.shoppingItem = shoppingItem;
        System.out.println("Name of product: "  + shoppingItem.getProduct().getName());
        //this.itemLabel.setText(shoppingItem.getProduct().getName());
        //productImage.setImage(IMatDataHandler.getInstance().getFXImage(shoppingItem.getProduct()));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
