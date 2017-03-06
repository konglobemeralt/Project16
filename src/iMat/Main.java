package iMat;/**
 * Created by Alexandra on 2017-02-22.
 */

import iMat.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane mainLayout;

    public static IMatDataHandler iMat = IMatDataHandler.getInstance();

    private ProductViewController productViewController;

    private ShoppingBagController shoppingBagController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("iMat");
        //this.primaryStage.setHeight(670);
        //this.primaryStage.setWidth(1280);
        this.primaryStage.setResizable(false);
        showMainView();
        showProductView();
        showCategoriesView();


        //Test
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(1), 1));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(2), 4));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(3), 3));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(4), 5));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(5), 3.8));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(6), 2.5));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(7), 9));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(8), 1));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(80), 1));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(40), 1));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(55), 1));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(66), 1));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(77), 1));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(88), 1));
        iMat.getShoppingCart().addItem(new ShoppingItem(iMat.getProduct(99), 1));

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
        categoriesView.setStyle("-fx-box-border: transparent;");
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
        productViewController = controller;

        //controller.fillCenterPane(iMat.getProducts(ProductCategory.HERB));

    }

    public void showPayWizardView()throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/PayWizard2.0.fxml"));
        TabPane tabPane = loader.load();
        mainLayout.setCenter(tabPane);

        //Send a reference of main to the controller
        PayWizardViewController controller = loader.getController();
        controller.setMain(this);
        controller.showOverviewTab();

    }

    public void showShoppingBagView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ShoppingBag.fxml"));
        AnchorPane shoppingBagPanel = loader.load();
        mainLayout.setRight(shoppingBagPanel);

        //Send a reference of main to the controller
        ShoppingBagController controller = loader.getController();
        controller.setMain(this);
        controller.updateShoppingBagGrid();

        shoppingBagController = controller;
    }

    public void updateShoppingBag(){
        if (shoppingBagController != null)
        {
            shoppingBagController.updateShoppingBagGrid();
        }
    }

    public void hideShoppingBag(){
        mainLayout.setRight(null);
    }

    public void fillProductView(List<Product> products){
        productViewController.fillCenterPane(products);
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
