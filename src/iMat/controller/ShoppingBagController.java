package iMat.controller;

import iMat.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateShoppingBagGrid() {

        numberOfItemsLabel.setText("" + main.iMat.getShoppingCart().getItems().size());
        totalPriceLabel.setText("" + main.iMat.getShoppingCart().getTotal());

        //Clear grid
        shoppingBagGrid.getChildren().clear();
        shoppingBagGrid.addColumn(0);
        shoppingBagGrid.getColumnConstraints().set(0, new ColumnConstraints(129));

        shoppingBagGrid.addColumn(1);
        shoppingBagGrid.getColumnConstraints().set(1, new ColumnConstraints(32));
        shoppingBagGrid.addColumn(2);
        shoppingBagGrid.getColumnConstraints().set(2, new ColumnConstraints(100));
        shoppingBagGrid.addColumn(3);
        shoppingBagGrid.getColumnConstraints().set(3, new ColumnConstraints(32));
        shoppingBagGrid.addColumn(4);
        shoppingBagGrid.getColumnConstraints().set(4, new ColumnConstraints(75));
        shoppingBagGrid.addColumn(5);
        shoppingBagGrid.getColumnConstraints().set(5, new ColumnConstraints(32));

        List<ShoppingItem> shoppingItems = main.iMat.getShoppingCart().getItems();

        for (int index = 0; index < shoppingItems.size() && index < 15; index++) {

            shoppingBagGrid.addRow(index);
            ShoppingItem shoppingItem = shoppingItems.get(index);

            //Initialize all components
            Label productLabel = new Label(" " + shoppingItem.getProduct().getName());


            Button subtractButton = new Button("");
            subtractButton.setPrefWidth(32);
            subtractButton.setMaxWidth(32);
            subtractButton.setMinWidth(32);
            subtractButton.setPrefHeight(32);
            subtractButton.setMaxHeight(32);
            subtractButton.setMinHeight(32);

            subtractButton.setId(index + "_subtractButton");
            subtractButton.getStyleClass().add("subtractButton");
            subtractButton.setOnAction((e) -> addOrSubtractButtonPressed(false));


            Button addButton = new Button("");
            addButton.setPrefWidth(32);
            addButton.setMaxWidth(32);
            addButton.setMinWidth(32);
            addButton.setPrefHeight(32);
            addButton.setMaxHeight(32);
            addButton.setMinHeight(32);
            addButton.setId(index + "addButton");
            addButton.getStyleClass().add("addButton");
            addButton.setOnAction((e) -> addOrSubtractButtonPressed(true));

            Button removeButton = new Button("");
            removeButton.setPrefHeight(32);
            removeButton.setMinHeight(32);
            removeButton.setMaxHeight(32);
            removeButton.setPrefWidth(32);
            removeButton.setMinWidth(32);
            removeButton.setMaxWidth(32);
            removeButton.setId(index + "_removeButton");
            removeButton.getStyleClass().add("deleteButton");
            removeButton.setOnAction((e) -> removeButtonPressed());

            Label priceLabel = new Label("  " + shoppingItem.getTotal() + " kr");

            TextArea amountTextArea = new TextArea("st");
            amountTextArea.setPrefHeight(32);
            amountTextArea.setMinHeight(32);
            amountTextArea.setMaxHeight(32);
            amountTextArea.setId(index + "_amountTextArea");
            amountTextArea.setOnMouseClicked((e) -> amountTextAreaClicked(amountTextArea));
            amountTextArea.focusedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                    if (!newPropertyValue) {
                        amountTextAreaLostFocus(amountTextArea, priceLabel);
                    }
                }
            });
            amountTextArea.setText(shoppingItem.getAmount() + " " + shoppingItem.getProduct().getUnitSuffix());


            if (index % 2 == 1) { // Add a pane to every other product so that we may colour it
                AnchorPane productPane = new AnchorPane(productLabel);
                productPane.autosize();
                productPane.setPrefHeight(32);
                productPane.getStyleClass().add("paneStyle");
                shoppingBagGrid.add(productPane, 0, index);

                AnchorPane subtractPane = new AnchorPane(subtractButton);
                subtractPane.autosize();
                subtractPane.setPrefHeight(32);
                subtractPane.getStyleClass().add("paneStyle");
                shoppingBagGrid.add(subtractPane, 1, index);

                AnchorPane amountTextPane = new AnchorPane(amountTextArea);
                amountTextPane.autosize();
                amountTextPane.setPrefHeight(32);
                amountTextPane.getStyleClass().add("paneStyle");
                shoppingBagGrid.add(amountTextPane, 2, index);

                AnchorPane addPane = new AnchorPane(addButton);
                addPane.autosize();
                addPane.setPrefHeight(32);
                addPane.getStyleClass().add("paneStyle");
                shoppingBagGrid.add(addPane, 3, index);

                AnchorPane pricePane = new AnchorPane(priceLabel);
                pricePane.autosize();
                pricePane.setPrefHeight(32);
                pricePane.getStyleClass().add("paneStyle");
                shoppingBagGrid.add(pricePane, 4, index);

                shoppingBagGrid.add(removeButton, 5, index);
            } else {


                shoppingBagGrid.add(productLabel, 0, index);
                shoppingBagGrid.add(subtractButton, 1, index);
                amountTextArea.setText(shoppingItem.getAmount() + " " + shoppingItem.getProduct().getUnitSuffix());
                shoppingBagGrid.add(amountTextArea, 2, index);
                shoppingBagGrid.add(addButton, 3, index);

                shoppingBagGrid.add(priceLabel, 4, index);
                shoppingBagGrid.add(removeButton, 5, index);
            }

        }


    }

    private void removeButtonPressed() {
        for (Node n : shoppingBagGrid.getChildren()) {
            if (n.isFocused()) {
                int removeIndex = Integer.parseInt((n.getId().split("_")[0]));
                main.iMat.getShoppingCart().removeItem(removeIndex);
            }
        }
        updateShoppingBagGrid();
    }

    private void addOrSubtractButtonPressed(boolean add) {
        for (Node n : shoppingBagGrid.getChildren()) {
            if (n.isFocused()) {
                int index = Integer.parseInt((n.getId().split("_")[0]));
                double newAmount = add ? main.iMat.getShoppingCart().getItems().get(index).getAmount() + 1 : main.iMat.getShoppingCart().getItems().get(index).getAmount() - 1;
                if (newAmount > 0) {
                    main.iMat.getShoppingCart().getItems().get(index).setAmount(newAmount);
                } else {
                    main.iMat.getShoppingCart().removeItem(index);
                }
            }
        }
        updateShoppingBagGrid();
    }

    private void amountTextAreaLostFocus(TextArea amount, Label price) {
        int index = Integer.parseInt((amount.getId().split("_")[0]));
        ShoppingItem item = main.iMat.getShoppingCart().getItems().get(index);

        try {
            double newAmount = Double.parseDouble(amount.getText());
            item.setAmount(newAmount);
        } catch (NumberFormatException n) {
        }

        if (item.getAmount() > 0) {
            amount.setText(" " + item.getAmount() + " " + item.getProduct().getUnitSuffix());
            price.setText("  " + item.getTotal() + " kr");
        } else {
            main.iMat.getShoppingCart().removeItem(index);
            updateShoppingBagGrid();
        }
    }

    private void amountTextAreaClicked(TextArea amount) {
        amount.setText(amount.getText().split(" ")[0]);
        amount.selectAll();
    }

}

