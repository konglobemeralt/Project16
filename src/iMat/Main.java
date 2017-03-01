package iMat;/**
 * Created by Alexandra on 2017-02-22.
 */

import iMat.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane mainLayout;

    public static IMatDataHandler iMat = IMatDataHandler.getInstance();


    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("iMat");
        showMainView();
        showCategoriesView();
        showProductView();

        //Test
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(1), 1));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(2), 4));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(3), 3));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(4), 5));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(5), 3.8));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(6), 2.5));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(7), 9));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(8), 1));

        iMat.getCustomer().setFirstName("Kalle");
        iMat.getCustomer().setLastName("Moraeus");
    }

    private void showMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/MainView.fxml"));
        mainLayout = loader.load();

        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add("CSS/MainStyle.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Send a reference of main to the controller
        MainViewController controller = loader.getController();
        controller.setMain(this);
    }

    private void showCategoriesView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/CategoriesPanel.fxml"));
        AnchorPane categoriesView = loader.load();

        mainLayout.setLeft(categoriesView);

        //Send a reference of main to the controller
        CategoriesPanelController controller = loader.getController();
        controller.setMain(this);
    }

    private void showProductView()throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ProductView.fxml"));
        ScrollPane productPanel = loader.load();
        mainLayout.setCenter(productPanel);

        //Send a reference of main to the controller
        ProductViewController controller = loader.getController();
        controller.setMain(this);

    }

    public void showPayWizardView()throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/PayWizardView.fxml"));
        TabPane tabPane = loader.load();
        mainLayout.setCenter(tabPane);

        //Send a reference of main to the controller
        PayWizardViewController controller = loader.getController();
        controller.setMain(this);
        controller.initialize();

    }

    public void showShoppingBagView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ShoppingBag.fxml"));
        ScrollPane shoppingBagPanel = loader.load();
        mainLayout.setRight(shoppingBagPanel);

        //Send a reference of main to the controller
        ShoppingBagController controller = loader.getController();
        controller.setMain(this);
        controller.updateShoppingBagGrid();
    }

    public void hideShoppingBag(){
        mainLayout.setRight(null);
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public BorderPane getMainLayout() {
        return mainLayout;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
