package iMat;/**
 * Created by Alexandra on 2017-02-22.
 */

import iMat.controller.*;
import iMat.controller.BackButtonHandler.Link;
import iMat.controller.BackButtonHandler.SavedPage;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane mainLayout;

    public static IMatDataHandler iMat = IMatDataHandler.getInstance();

    private ProductViewController productViewController;
    private ScrollPane productPanel;

    private ShoppingBagController shoppingBagController;

    private ProfileViewController profileViewController;

    private MainViewController mainViewController;

    private ConfirmationViewController confirmationViewController;

    private TabPane helpView;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("iMat");
        //this.primaryStage.setHeight(670);
        //this.primaryStage.setWidth(1280);
        this.primaryStage.setResizable(false);
        showMainView();
        showCategoriesView();

        if (iMat.isFirstRun()){
            showFirstStartView();
        }
        else {
            showHomeView();
        }

        Link firstPage = iMat.isFirstRun() ? Link.FIRSTPAGE : Link.HOME;

        historyHandler = new HistoryHandler(mainViewController.getBackButton(), mainViewController.getForwardButton(), firstPage);

        mainViewController.updateShoppingBagCounter();
        iMat.getShoppingCart().addShoppingCartListener(mainViewController);

    }

    private void showMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/MainView.fxml"));
        mainLayout = loader.load();

        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add("CSS/MainStyle.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                iMat.shutDown();
            }
        });

        //Send a reference of main to the controller
        MainViewController controller = loader.getController();
        controller.setMain(this);
        mainViewController = controller;
    }

    public void showHomeView(){
        mainLayout.setRight(null);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/homePage.fxml"));
        try {
            AnchorPane homePanel = loader.load();
            mainLayout.setCenter(homePanel);
        }
        catch (IOException e){

        }

        //Send a reference of main to the controller
        HomeController controller = loader.getController();
        controller.setMain(this);
        controller.update();
    }

    public void showFirstStartView(){
        mainLayout.setRight(null);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/startView.fxml"));
        try {
            AnchorPane firstStartPanel = loader.load();
            mainLayout.setCenter(firstStartPanel);
        }
        catch (IOException e){

        }

        //Send a reference of main to the controller
        FirstStartController controller = loader.getController();
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

    public void showProductView() throws IOException {
        if (productPanel == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ProductView.fxml"));
            productPanel = loader.load();
            mainLayout.setCenter(productPanel);

            //Send a reference of main to the controller
            productViewController = loader.getController();
            productViewController.setMain(this);
        } else {
            mainLayout.setCenter(productPanel);
        }
    }

    public void showPayWizardView() throws IOException {
        mainLayout.setRight(null);

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
        mainLayout.setRight(null);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/profileView.fxml"));
        AnchorPane profileViewPanel = loader.load();
        mainLayout.setCenter(profileViewPanel);

        //Send a reference of main to the controller
        ProfileViewController controller = loader.getController();
        controller.setMain(this);

        profileViewController = controller;
        System.out.println("COTROLLER UPDATE");
        //controller.update();
    }

    public void showHelpView() {
        mainLayout.setRight(null);

        if (helpView == null) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/HelpView.fxml"));
            try {
                helpView = loader.load();
                mainLayout.setCenter(helpView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            mainLayout.setCenter(helpView);
        }
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

        if (productViewController != null){
            productViewController.refresh();
        }
    }

    public void updateShoppingBag() {
        if (shoppingBagController != null) {
            shoppingBagController.updateShoppingBagGrid();
        }
    }

    public void hideShoppingBag() {
        mainLayout.setRight(null);
        if (productViewController != null){
            productViewController.refresh();
        }
    }

    public void showConfirmationView() {
        mainLayout.setRight(null);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/confirmationView.fxml"));
        try {
            AnchorPane confirmationView = loader.load();
            mainLayout.setCenter(confirmationView);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Send a reference of main to the controller
        ConfirmationViewController controller = loader.getController();
        controller.setMain(this);
        confirmationViewController = controller;

    }


    public void updateConfirmationViewText(String time, String date) {
        confirmationViewController.updateText(date, time);
    }

    public void showReceiptView() {
        mainLayout.setRight(null);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/receiptView.fxml"));
        try {
            AnchorPane receiptView = loader.load();
            mainLayout.setCenter(receiptView);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Send a reference of main to the controller
        ReceiptViewController controller = loader.getController();
        controller.setMain(this);
        controller.updateOrders();

    }

    public void showDetailedRecieptView(Order order){
        mainLayout.setRight(null);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/receiptDetailedView.fxml"));
        try {
            AnchorPane receiptDetailedView = loader.load();
            mainLayout.setCenter(receiptDetailedView);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Send a reference of main to the controller
        ReceiptDetailedController controller = loader.getController();
        controller.setMain(this);
        controller.setOrder(order);
    }

    public void showFavourites(){
        try {
            showProductView();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fillProductView(iMat.favorites());
    }

    public void updateShoppingBagCounter(){
        mainViewController.updateShoppingBagCounter();
    }

    public void fillProductView(List<Product> products) {
        productViewController.fillCenterPaneProduct(products);
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

    //------------------------History--------------------------------\\

    public HistoryHandler pageHistory() {
        return historyHandler;
    }

    private HistoryHandler historyHandler;

    public class HistoryHandler {

        private int currentIndex;

        private List<SavedPage> history;

        private Button backButton;

        private Button forwardButton;

        public HistoryHandler(Button backButton, Button forwardButton, Link firstLink) {
            this.backButton = backButton;
            this.forwardButton = forwardButton;
            history = new ArrayList<SavedPage>();
            currentIndex = -1;
            addLink(firstLink);
            backButton.setDisable(true);
        }

        public void addLink(Link link) {
            addedNewLink();
            if (link == Link.PRODUCT || link == Link.DETAILEDRECEIPT) {
                //TODO throw exception
            }
            history.add(new SavedPage(link));
            currentIndex++;
        }

        public void addProductLink(List<Product> productList) {
            addedNewLink();
            history.add(new SavedPage(Link.PRODUCT, productList));
            currentIndex++;
        }

        public void addOrderLink(Order order){
            backButton.setDisable(false);
            if (currentIndex + 1 != history.size()){
                cutOffBranch();
            }
            history.add(new SavedPage(Link.DETAILEDRECEIPT, order));
            currentIndex++;
        }

        private void addedNewLink(){
            backButton.setDisable(false);
            //TODO kolla så att det inte är två samma i rad
            if (currentIndex + 1 != history.size()) {
                cutOffBranch();
            }
        }

        public void goBack() {
            currentIndex--;
            if (currentIndex == 0) {
                backButton.setDisable(true);
            }
            show();
            forwardButton.setDisable(false);
        }

        public void goForwards() {
            currentIndex++;
            if (currentIndex + 1 == history.size()) {
                forwardButton.setDisable(true);
            }
            backButton.setDisable(false);
            //TODO lägg till felhantering här
            show();
        }

        private void show() {
            switch (history.get(currentIndex).getLink()) {
                case CONFIRMEDVIEW:
                    showConfirmationView();
                    break;

                case FAVOURITES:
                    showFavourites();
                    break;

                case HOME:
                    showHomeView();
                    break;

                case FIRSTPAGE:
                    showFirstStartView();
                    break;

                case MYLISTS:
                    break;

                case PRODUCT:
                    try {
                        showProductView();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fillProductView(history.get(currentIndex).getProductList());
                    break;

                case PROFILE:
                    try {
                        showProfileView();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case RECEIPTS:
                    showReceiptView();
                    break;

                case DETAILEDRECEIPT:
                    showDetailedRecieptView(history.get(currentIndex).getOrder());
                    break;

                case WIZARD:
                    try {
                        showPayWizardView();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case HELP:
                    showHelpView();
                    break;
            }
        }

        private void cutOffBranch() {
            for (int i = history.size() - 1; i > currentIndex; i--) {
                history.remove(i);
            }
            forwardButton.setDisable(true);
        }
    }
}
