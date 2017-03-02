package iMat.controller;

import iMat.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.util.List;
import javafx.geometry.Insets;
public class ShoppingBagController {



    @FXML
    private GridPane shoppingBagGrid;

    @FXML
    private Label numberOfItemsLabel;

    @FXML
    private Label totalPriceLabel;


    //Reference the main application
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void payButtonPressed(ActionEvent event) {
        try {
            main.showPayWizardView();
            main.hideShoppingBag();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateShoppingBagGrid() {

        numberOfItemsLabel.setText(""+main.iMat.getShoppingCart().getItems().size());
        totalPriceLabel.setText(""+main.iMat.getShoppingCart().getTotal());

        //Clear grid
        shoppingBagGrid.getChildren().clear();
        shoppingBagGrid.addColumn(0);
        shoppingBagGrid.getColumnConstraints().set(0, new ColumnConstraints(84));
        shoppingBagGrid.addColumn(1);
        shoppingBagGrid.getColumnConstraints().set(1, new ColumnConstraints(32));
        shoppingBagGrid.addColumn(2);
        shoppingBagGrid.getColumnConstraints().set(2, new ColumnConstraints(75));
        shoppingBagGrid.addColumn(3);
        shoppingBagGrid.getColumnConstraints().set(3, new ColumnConstraints(32));
        shoppingBagGrid.addColumn(4);
        shoppingBagGrid.getColumnConstraints().set(4, new ColumnConstraints(45));
        shoppingBagGrid.addColumn(5);
        shoppingBagGrid.getColumnConstraints().set(5, new ColumnConstraints(32));

        List<ShoppingItem> shoppingItems = main.iMat.getShoppingCart().getItems();

        for (int index = 0; index < shoppingItems.size(); index++) {

            //Initialize all components
            Button subtractButton = new Button("");

            subtractButton.setPrefWidth(32);
            subtractButton.setMaxWidth(32);
            subtractButton.setMinWidth(32);
            subtractButton.setPrefHeight(32);
            subtractButton.setMaxHeight(32);
            subtractButton.setMinHeight(32);
            subtractButton.setId(index+"subtractButton");
            subtractButton.getStyleClass().add("subtractButton");
            subtractButton.setOnAction((e) -> addOrSubtractButtonPressed(false));


            Button addButton = new Button("");
            addButton.setPrefWidth(32);
            addButton.setMaxWidth(32);
            addButton.setMinWidth(32);
            addButton.setPrefHeight(32);
            addButton.setMaxHeight(32);
            addButton.setMinHeight(32);
            addButton.setId(index+"addButton");
            addButton.getStyleClass().add("addButton");
            addButton.setOnAction((e) -> addOrSubtractButtonPressed(true));

            Button removeButton = new Button("");
            removeButton.setPrefHeight(32);
            removeButton.setMinHeight(32);
            removeButton.setMaxHeight(32);
            removeButton.setPrefWidth(32);
            removeButton.setMinWidth(32);
            removeButton.setMaxWidth(32);
            removeButton.setId(index+"removeButton");
            removeButton.getStyleClass().add("deleteButton");
            removeButton.setOnAction((e) -> removeButtonPressed());


            TextArea amountTextArea = new TextArea("st");
            amountTextArea.setPrefHeight(32);
            amountTextArea.setMinHeight(32);
            amountTextArea.setMaxHeight(32);
            amountTextArea.setId(index+"amountTextArea");
            amountTextArea.setOnInputMethodTextChanged((e) -> amountTextAreaChanged());

            shoppingBagGrid.addRow(index);
            ShoppingItem s = shoppingItems.get(index);
            shoppingBagGrid.add(new Label(s.getProduct().getName()), 0, index);
            shoppingBagGrid.add(subtractButton, 1, index);
            amountTextArea.setText(s.getAmount() + " " + s.getProduct().getUnitSuffix());
            shoppingBagGrid.add(amountTextArea, 2, index);
            shoppingBagGrid.add(addButton, 3, index);
            shoppingBagGrid.add(new Label("" + s.getTotal() + " kr"), 4, index);

            shoppingBagGrid.add(removeButton, 5, index);
        }


    }

    private void removeButtonPressed(){
        for (Node n: shoppingBagGrid.getChildren()) {
            if (n.isFocused()){
                int removeIndex = Character.getNumericValue((n.getId()).charAt(0));
                main.iMat.getShoppingCart().removeItem(removeIndex);
            }
        }
        updateShoppingBagGrid();
    }

    private void addOrSubtractButtonPressed(boolean add){
        for (Node n: shoppingBagGrid.getChildren()) {
            if (n.isFocused()){
                int index = Character.getNumericValue((n.getId()).charAt(0));
                double newAmount = add ? main.iMat.getShoppingCart().getItems().get(index).getAmount() + 1:  main.iMat.getShoppingCart().getItems().get(index).getAmount() - 1;
                if (newAmount > 0){
                    main.iMat.getShoppingCart().getItems().get(index).setAmount(newAmount);
                }
                else {
                    main.iMat.getShoppingCart().removeItem(index);
                }
            }
        }
        updateShoppingBagGrid();
    }

    private void amountTextAreaChanged(){
        //TODO

        /*for (Node n: shoppingBagGrid.getChildren()) {
            if (n.isFocused()){
                int index = Character.getNumericValue((n.getId()).charAt(0));
                //main.iMat.getShoppingCart().removeItem(removeIndex);
            }
        }
        updateShoppingBagGrid();*/
    }




}

