package iMat.controller;

import iMat.Main;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.Product;

/**
 * Created by Kraft on 2017-03-09.
 */
public class FeaturedItemController extends AnchorPane {

    @FXML
    private ImageView imageView;

    private Product product;

    //Reference the main application
    private Main main;

    public FeaturedItemController(Main main, Product product){
        this.main = main;
        this.product = product;

        imageView.setImage(main.iMat.getFXImage(product, 258, 258));
    }

}
