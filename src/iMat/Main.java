package iMat;/**
 * Created by Alexandra on 2017-02-22.
 */

import iMat.controller.*;
import iMat.controller.BackButtonHandler.Link;
import iMat.controller.BackButtonHandler.SavedPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane mainLayout;

    public static IMatDataHandler iMat = IMatDataHandler.getInstance();

    private ProductViewController productViewController;

    private ShoppingBagController shoppingBagController;

    private ProfileViewController profileViewController;

    private MainViewController mainViewController;

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

        historyHandler = new HistoryHandler(mainViewController.getBackButton());

        iMat.getCustomer().setFirstName("Kalle");
        iMat.getCustomer().setLastName("Moraeus");

    }

    private void showMainView() throws IOException {

        if (mainViewController == null){
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
            mainViewController = controller;
        }


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

    public void showProductView()throws IOException{
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

    public void showProfileView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/profileView.fxml"));
        AnchorPane profileViewPanel = loader.load();
        mainLayout.setCenter(profileViewPanel);

        //Send a reference of main to the controller
        ProfileViewController controller = loader.getController();
        controller.setMain(this);

        profileViewController = controller;

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
        mainViewController.updateShoppingBagCounter();
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


    public HistoryHandler pageHistory() {return historyHandler; }

    private HistoryHandler historyHandler;

    public class HistoryHandler {

        private int currentIndex;

        private List<SavedPage> history;

        private Button backButton;

        public HistoryHandler(Button backButton){
            this.backButton = backButton;
            history = new ArrayList<SavedPage>();
            currentIndex = -1;
            addLink(Link.HOME);
            backButton.setDisable(true);
        }

        public void addLink(Link link){
            //TODO lägg till så att en inte kan lägga till två likadan i rad
            backButton.setDisable(false);
            if (currentIndex + 1 != history.size()){
                cutOffBranch();
            }
            if (link == Link.PRODUCT){
                //TODO throw exception
            }
            history.add(new SavedPage(link));
            currentIndex++;
        }

        public void addProductLink(List<Product> productList){
            backButton.setDisable(false);
            //TODO lägg till så att en inte kan lägga till två likadan i rad
            if (currentIndex + 1 != history.size()){
                cutOffBranch();
            }
            history.add(new SavedPage(Link.PRODUCT, productList));
            currentIndex++;
        }

        public void goBack(){
            currentIndex--;
            if (currentIndex == 0){
                backButton.setDisable(true);
            }
            show();
        }

        public void goForwards(){
            currentIndex++;
            //TODO lägg till felhantering här
            show();
        }

        private void show(){
            switch (history.get(currentIndex).getLink()){
                case CONFIRMEDVIEW:
                    break;

                case FAVOURITES:
                    try { showProductView(); }  catch (IOException e){ e.printStackTrace(); }
                    fillProductView(history.get(currentIndex).getProductList());
                    break;

                case HOME:
                    mainLayout.setCenter(null);
                    break;

                case MYLISTS:
                    break;

                case PRODUCT:
                    try { showProductView(); }  catch (IOException e){ e.printStackTrace(); }
                    fillProductView(history.get(currentIndex).getProductList());
                    break;

                case PROFILE:
                    try { showProfileView(); }  catch (IOException e){ e.printStackTrace(); }
                    break;

                case RECEIPTS:
                    break;

                case WIZARD:
                    try { showPayWizardView(); }  catch (IOException e){ e.printStackTrace(); }
                    break;

            }
        }

        private void cutOffBranch(){
            for (int i = history.size() - 1; i > currentIndex; i--){
                history.remove(i);
            }
        }
    }
}
