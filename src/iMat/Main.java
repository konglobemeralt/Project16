package iMat;/**
 * Created by Alexandra on 2017-02-22.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {
    private Stage primaryStage;
    public BorderPane mainLayout;


    @Override
    public void start(Stage primaryStage) throws IOException {
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle("iMat");
    showMainView();
    showCategoriesView();
    }

    private void showMainView()throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/MainView.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add("CSS/MainStyle.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showCategoriesView()throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/CategoriesPanel.fxml"));
        AnchorPane categoriesView = loader.load();
        mainLayout.setLeft(categoriesView);
    }





    public static void main(String[] args) {
        launch(args);
    }
}
