package iMat.controller;

/**
 * Created by Jesper on 2017-02-23.
 */

 import javafx.fxml.FXML;
 import javafx.fxml.FXMLLoader;
 import javafx.fxml.Initializable;
 import javafx.scene.image.ImageView;
 import javafx.scene.layout.AnchorPane;
 import javafx.scene.layout.BorderPane;
 import se.chalmers.ait.dat215.project.IMatDataHandler;
 import se.chalmers.ait.dat215.project.ShoppingItem;

 import java.io.IOException;
 import java.net.URL;
 import java.util.ResourceBundle;

public class itemController extends AnchorPane implements Initializable {
    private BorderPane mainLayout;

    @FXML
    ShoppingItem shoppingItem;

    @FXML
    ImageView productImage;

    public itemController(ShoppingItem shoppingItem) {
        System.out.println("init item");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/item.fxml"));
            loader.setController(this);
            AnchorPane anchorPane = loader.load();
            mainLayout.setLeft(anchorPane);

        } catch (IOException exc) {
            System.out.println(exc.toString());
        }
        System.out.println("Loading ItemController...");
        this.shoppingItem = shoppingItem;
        System.out.println("Name of product: "  + shoppingItem.getProduct().getName());

        productImage.setImage(IMatDataHandler.getInstance().getFXImage(shoppingItem.getProduct()));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
