package iMat.controller;

import iMat.Main;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.Product;

import java.util.Random;

/**
 * Created by Kraft on 2017-03-08.
 */
public class HomeController {

    @FXML
    private GridPane featuredItemsGrid;

    //Reference the main application
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void initialize(){
        Random random = new Random();
        for (int columnIndex = 0; columnIndex < 4; columnIndex++){
            Product product = main.iMat.getProduct(random.nextInt(main.iMat.getProducts().size()));

            FeaturedItemController controller = new FeaturedItemController(main, product);

            featuredItemsGrid.add(controller, columnIndex, 0);
        }
    }
}
