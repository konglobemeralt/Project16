package iMat.controller;

import iMat.Main;
import iMat.controller.BackButtonHandler.Link;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PayWizardViewController {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab overview;

    @FXML
    private Tab credentials;

    @FXML
    private Tab deliveryPayment;

    @FXML
    private Tab confirmation;


    //Reference the main application
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void showOverviewTab() {
        tabPane.getSelectionModel().select(overview);

        updateTabEnabledStatus();
        updateShoppingBagGrid();
    }

    @FXML
    void goBack(ActionEvent event) {
        main.pageHistory().goBack();
    }

    @FXML
    public void initialize() {
        Customer customer = main.iMat.getCustomer();

        firstNameArea.setText(customer.getFirstName());
        lastNameArea.setText(customer.getLastName());
        phoneArea.setText(customer.getPhoneNumber());
        addressArea.setText(customer.getAddress());
        postalCodeArea.setText(customer.getPostCode());
        postAddressArea.setText(customer.getPostAddress());

        CreditCard card = main.iMat.getCreditCard();
        cardOwnerArea.setText(card.getHoldersName());
        if (card.getCardNumber().length() == 16){
            cardNumberArea1.setText(card.getCardNumber().substring(0, 4));
            cardNumberArea2.setText(card.getCardNumber().substring(4, 8));
            cardNumberArea3.setText(card.getCardNumber().substring(8, 12));
            cardNumberArea4.setText(card.getCardNumber().substring(12, 16));
        }
        if (card.getValidMonth() != -1){
            cardMonthArea.setText(""+card.getValidMonth());
        }
        if (card.getValidYear() != -1){
            cardYearArea.setText(""+card.getValidYear());
        }

        if (card.getVerificationCode() < 10 && card.getVerificationCode() != -1 ){
            cardCVCArea.setText("00"+card.getVerificationCode());
        }
        else if (card.getVerificationCode() < 100 && card.getVerificationCode() != -1){
            cardCVCArea.setText("0"+card.getVerificationCode());
        }
        else if (card.getVerificationCode() != -1){
            cardCVCArea.setText(""+card.getVerificationCode());
        }

        cardNumberArea1.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if(!newValue.matches("\\d{0,4}"))
                {
                    cardNumberArea1.setText(oldValue);
                }

                if(newValue.length() == 4)
                {
                    cardNumberArea2.requestFocus();
                }
            }
        });


        cardNumberArea2.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if(!newValue.matches("\\d{0,4}"))
                {
                    cardNumberArea2.setText(oldValue);
                }

                if(newValue.length() == 4)
                {
                    cardNumberArea3.requestFocus();
                }
            }
        });

        cardNumberArea3.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if(!newValue.matches("\\d{0,4}"))
                {
                    cardNumberArea3.setText(oldValue);
                }

                if(newValue.length() == 4)
                {
                    cardNumberArea4.requestFocus();
                }
            }
        });

        cardNumberArea4.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if(!newValue.matches("\\d{0,4}"))
                {
                    cardNumberArea4.setText(oldValue);
                }
            }
        });

        cardMonthArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if(!newValue.matches("\\d{0,2}"))
                {
                    cardMonthArea.setText(oldValue);
                }
            }
        });

        cardYearArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if(!newValue.matches("\\d{0,2}"))
                {
                    cardYearArea.setText(oldValue);
                }
            }
        });

        cardCVCArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if(!newValue.matches("\\d{0,3}"))
                {
                    cardCVCArea.setText(oldValue);
                }
            }
        });


        firstNameArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if (saveCredentialsCheckBox.isSelected()){
                    customer.setFirstName(firstNameArea.getText());
                }
                updateTabEnabledStatus();
            }
        });

        lastNameArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if (saveCredentialsCheckBox.isSelected()){
                    customer.setLastName(lastNameArea.getText());
                }
                updateTabEnabledStatus();
            }
        });

        phoneArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if (saveCredentialsCheckBox.isSelected()){
                    customer.setPhoneNumber(phoneArea.getText());
                }
                updateTabEnabledStatus();
            }
        });

        addressArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if (saveCredentialsCheckBox.isSelected()){
                    customer.setAddress(addressArea.getText());
                }
                updateTabEnabledStatus();
            }
        });

        postalCodeArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if(!newValue.matches("\\d{0,5}"))
                {
                    postalCodeArea.setText(oldValue);
                }
                if (saveCredentialsCheckBox.isSelected()){
                    customer.setPostCode(postalCodeArea.getText());
                }
                updateTabEnabledStatus();
            }
        });

        postAddressArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if (saveCredentialsCheckBox.isSelected()){
                    customer.setPostAddress(postAddressArea.getText());
                }
                updateTabEnabledStatus();
            }
        });

        LocalDate today = LocalDate.now();

        int dayIndex = 0, monthIndex = 0, dayNumber = today.getDayOfMonth();

        String[] weekdays = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
        String[] translatedWeekdays = {"Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag", "Söndag"};

        for (int i = 0; i < weekdays.length; i++) {
            if (today.getDayOfWeek().toString().equals(weekdays[i])) {
                dayIndex = i;
                break;
            }
        }

        String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        String[] translatedMonths = {"Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"};

        for (int i = 0; i < months.length; i++) {
            if (today.getMonth().toString().equals(months[i])) {
                monthIndex = i;
                break;
            }
        }


        String[] displayDates = new String[7];

        for (int i = 0; i < 5; i++) {
            dayIndex = (dayIndex + 1) % 7;
            if (dayNumber + 1 >= today.lengthOfMonth()) {
                dayNumber = 0;
                monthIndex = (monthIndex + 1) % 12;
            }
            dayNumber++;
            if (i == 0) {
                displayDates[i] = "Imorgon" + " " + dayNumber + " " + translatedMonths[monthIndex];
            } else  {
                displayDates[i] = translatedWeekdays[dayIndex] + " " + dayNumber + " " + translatedMonths[monthIndex];
            }
        }

        ObservableList<String> times = FXCollections.observableArrayList("08:00 - 10:00", "10:00 - 12:00", "12:00 - 14:00", "14:00 - 16:00", "16:00 - 18:00", "18:00 - 20:00");
        ObservableList<String> dates = FXCollections.observableArrayList(displayDates[0], displayDates[1], displayDates[2], displayDates[3], displayDates[4], displayDates[5]);

        dateComboBox.setItems(dates);
        timeComboBox.setItems(times);

        cardPanel.setVisible(false);

        TextField[] textFields = {cardNumberArea1,cardNumberArea2,cardNumberArea3,cardNumberArea4};

        for (TextField t : textFields) {
            t.textProperty().addListener(new ChangeListener<String>() {
                public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                    updateTabEnabledStatus();
                }
            });
        }

        cardOwnerArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if (saveCardCheckBox.isSelected()){
                    card.setHoldersName(cardOwnerArea.getText());
                }
                updateTabEnabledStatus();
            }
        });

        cardMonthArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if(!newValue.matches("\\d{0,5}"))
                {
                    cardMonthArea.setText(oldValue);
                }
                try{
                    if (saveCardCheckBox.isSelected()){
                        card.setValidMonth(Integer.parseInt(cardMonthArea.getText()));
                    }
                }
                catch (NumberFormatException e) {}
                updateTabEnabledStatus();
            }
        });

        cardYearArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if(!newValue.matches("\\d{0,5}"))
                {
                    cardYearArea.setText(oldValue);
                }
                try{
                    if (saveCardCheckBox.isSelected()){
                        card.setValidYear(Integer.parseInt(cardMonthArea.getText()));
                    }
                }
                catch (NumberFormatException e) {}
                updateTabEnabledStatus();
            }
        });

        cardCVCArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if(!newValue.matches("\\d{0,5}"))
                {
                    cardCVCArea.setText(oldValue);
                }
                try{
                    if (saveCardCheckBox.isSelected()){
                        card.setVerificationCode(Integer.parseInt(cardMonthArea.getText()));
                    }
                }
                catch (NumberFormatException e) {}
                updateTabEnabledStatus();
            }
        });



    }

    @FXML
    private void updateTabEnabledStatus(ActionEvent e) {
        updateTabEnabledStatus();
    }

    private void updateTabEnabledStatus() {

        if (isOverviewFinished()) {
            overviewNextButton.setDisable(false);
            credentials.setDisable(false);
        } else {
            overviewNextButton.setDisable(true);
            credentials.setDisable(true);
        }

        if (isCredentialsFinished() && !credentials.isDisabled()) {
            credentialsNextButton.setDisable(false);
            deliveryPayment.setDisable(false);
        } else {
            credentialsNextButton.setDisable(true);
            deliveryPayment.setDisable(true);
        }

        if (isDeliveryPaymentFinished() && !deliveryPayment.isDisabled()) {
            deliveryNextButton.setDisable(false);
            confirmation.setDisable(false);
            updateConfirmationLabels();
            updateConfirmationList();
        } else {
            deliveryNextButton.setDisable(true);
            confirmation.setDisable(true);
        }
    }

    @FXML
    private void nextButtonPressed(ActionEvent a) {

        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();


        if (currentTab.equals(overview)) {
            currentTab = credentials;
        } else if (currentTab.equals(credentials)) {
            currentTab = deliveryPayment;
        } else if (currentTab.equals(deliveryPayment)) {
            currentTab = confirmation;
        }

        updateTabEnabledStatus();
        tabPane.getSelectionModel().select(currentTab);

    }

    @FXML
    private void backButtonPressed(ActionEvent a) {

        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();

        if (currentTab.equals(confirmation)) {
            currentTab = deliveryPayment;
        } else if (currentTab.equals(deliveryPayment)) {
            currentTab = credentials;
        } else if (currentTab.equals(credentials)) {
            currentTab = overview;
        }

        tabPane.getSelectionModel().select(currentTab);

    }

    //----------------Overview----------------\\
    @FXML
    private Button overviewNextButton;

    @FXML
    private GridPane shoppingBagGrid;

    @FXML
    private AnchorPane payViewPane;

    @FXML
    private Label numberOfItemsLabelOverview;

    @FXML
    private Label totalPriceLabelOverview;

    public boolean isOverviewFinished() {
        return main.iMat.getShoppingCart().getItems().size() > 0;
    }

    public void updateShoppingBagGrid() {

        numberOfItemsLabelOverview.setText("" + main.iMat.getShoppingCart().getItems().size());
        confirmNumberOfItemsLabel.setText("Antal varor: " + main.iMat.getShoppingCart().getItems().size());
        totalPriceLabelOverview.setText("" + Math.round(main.iMat.getShoppingCart().getTotal() * 100) / 100.0);
        confirmTotalPriceLabel.setText("Totalt: " + Math.round(main.iMat.getShoppingCart().getTotal() * 100) / 100.0 + " kr");

        //Clear grid
        shoppingBagGrid.getChildren().clear();
        shoppingBagGrid.addColumn(0);
        shoppingBagGrid.getColumnConstraints().set(0, new ColumnConstraints(10, 400, 400));
        shoppingBagGrid.addColumn(1);
        shoppingBagGrid.getColumnConstraints().set(1, new ColumnConstraints(30));
        shoppingBagGrid.addColumn(2);
        //shoppingBagGrid.getColumnConstraints().set(2, new ColumnConstraints(60, 160, 160));
        shoppingBagGrid.getColumnConstraints().set(2, new ColumnConstraints(120));
        shoppingBagGrid.addColumn(3);
        shoppingBagGrid.getColumnConstraints().set(3, new ColumnConstraints(30));
        shoppingBagGrid.addColumn(4);
        shoppingBagGrid.getColumnConstraints().set(4, new ColumnConstraints(500, 500, 500));
        shoppingBagGrid.addColumn(5);
        shoppingBagGrid.getColumnConstraints().set(5, new ColumnConstraints(30));

        List<ShoppingItem> shoppingItems = main.iMat.getShoppingCart().getItems();

        for (int index = 0; index < shoppingItems.size(); index++) {

            ShoppingItem shoppingItem = shoppingItems.get(index);
            shoppingBagGrid.addRow(index);

            boolean unitIsDouble = shoppingItem.getProduct().getUnitSuffix().matches("l")
                    || shoppingItem.getProduct().getUnitSuffix().matches("kg");

            //Initialize all components
            Label productLabel = new Label("  " + shoppingItem.getProduct().getName());

            Button subtractButton = new Button("");
            subtractButton.setPrefWidth(30);
            subtractButton.setMaxWidth(30);
            subtractButton.setMinWidth(30);
            subtractButton.setPrefHeight(30);
            subtractButton.setMaxHeight(30);
            subtractButton.setMinHeight(30);
            subtractButton.setId(index + "subtractButton");
            subtractButton.getStyleClass().add("subtractButton");
            subtractButton.setOnAction((e) -> addOrSubtractButtonPressed(false));

            Button addButton = new Button("");
            addButton.setPrefWidth(30);
            addButton.setMaxWidth(30);
            addButton.setMinWidth(30);
            addButton.setPrefHeight(30);
            addButton.setMaxHeight(30);
            addButton.setMinHeight(30);
            addButton.setId(index + "addButton");
            addButton.getStyleClass().add("addButton");
            addButton.setOnAction((e) -> addOrSubtractButtonPressed(true));

            Button removeButton = new Button("");
            removeButton.setPrefHeight(30);
            removeButton.setMinHeight(30);
            removeButton.setMaxHeight(30);
            removeButton.setPrefWidth(30);
            removeButton.setMinWidth(30);
            removeButton.setMaxWidth(30);
            removeButton.setId(index + "removeButton");
            removeButton.getStyleClass().add("deleteButton");
            removeButton.setOnAction((e) -> removeButtonPressed());

            Label priceLabel = new Label("  " + Math.round(shoppingItem.getTotal() * 100) / 100.0 + " kr");
            priceLabel.paddingProperty().set(new Insets(0, 0, 0, 200));

            TextArea amountTextArea = new TextArea("st");

            final ChangeListener<String> inputFilter = new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
                {
                    System.out.println("inputFilter");
                    boolean inValidDoubleInput = !newValue.matches("\\d*" + "\\." + "\\d{0,2}") && !newValue.matches("\\d*");
                    boolean inValidIntInput = !newValue.matches("\\d*");

                    if (unitIsDouble && inValidDoubleInput)
                    {
                        amountTextArea.setText(oldValue);
                    }
                    else if (!unitIsDouble && inValidIntInput)
                    {
                        amountTextArea.setText(oldValue);
                    }
                }
            };

            amountTextArea.setPrefHeight(30);
            amountTextArea.setMinHeight(30);
            amountTextArea.setMaxHeight(30);
            amountTextArea.setId(index + "amountTextArea");
            amountTextArea.setOnMouseClicked((e) -> amountTextAreaClicked(amountTextArea));
            amountTextArea.focusedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                    amountTextArea.textProperty().addListener(inputFilter);
                    if (!newPropertyValue) {
                        amountTextArea.textProperty().removeListener(inputFilter);
                        amountTextArea.textProperty().removeListener(inputFilter);  // vet ej varför detta funkar.
                        amountTextAreaLostFocus(amountTextArea, priceLabel);
                    }
                }
            });

            if (shoppingItem.getAmount() % 1 == 0) {
                amountTextArea.setText((int) (shoppingItem.getAmount()) + " " + shoppingItem.getProduct().getUnitSuffix());
            } else {
                amountTextArea.setText(shoppingItem.getAmount() + " " + shoppingItem.getProduct().getUnitSuffix());
            }

            if (index % 2 == 1) { // Add a pane to every other product so that we may colour it
                AnchorPane productPane = new AnchorPane(productLabel);
                productPane.autosize();
                productPane.setPrefHeight(32);
                productLabel.getStyleClass().add("textFix");
                productPane.getStyleClass().add("paneStyle");
                shoppingBagGrid.add(productPane, 0, index);

                shoppingBagGrid.add(subtractButton, 1, index);

                shoppingBagGrid.add(amountTextArea, 2, index);

                shoppingBagGrid.add(addButton, 3, index);

                AnchorPane pricePane = new AnchorPane(priceLabel);
                pricePane.autosize();
                pricePane.setPrefHeight(32);
                priceLabel.getStyleClass().add("textFix");
                pricePane.getStyleClass().add("paneStyle");
                shoppingBagGrid.add(pricePane, 4, index);

                shoppingBagGrid.add(removeButton, 5, index);
            } else {


                shoppingBagGrid.add(productLabel, 0, index);
                shoppingBagGrid.add(subtractButton, 1, index);
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
                int removeIndex = Character.getNumericValue((n.getId()).charAt(0));
                main.iMat.getShoppingCart().removeItem(removeIndex);
            }
        }
        updateShoppingBagGrid();
        updateTabEnabledStatus();

    }

    private void addOrSubtractButtonPressed(boolean add) {

        for (Node n : shoppingBagGrid.getChildren()) {
            if (n.isFocused()) {
                int index = Character.getNumericValue((n.getId()).charAt(0));
                double newAmount = add ? main.iMat.getShoppingCart().getItems().get(index).getAmount() + 1 : main.iMat.getShoppingCart().getItems().get(index).getAmount() - 1;
                if (newAmount > 0) {
                    main.iMat.getShoppingCart().getItems().get(index).setAmount(newAmount);
                } else {
                    main.iMat.getShoppingCart().removeItem(index);
                }
            }
        }
        updateShoppingBagGrid();
        updateTabEnabledStatus();

        totalPriceLabelOverview.setText("" + Math.round(main.iMat.getShoppingCart().getTotal() * 100) / 100.0);
        confirmTotalPriceLabel.setText("Totalt: " + Math.round(main.iMat.getShoppingCart().getTotal() * 100) / 100.0 + " kr");
        main.updateShoppingBagCounter();

    }

    private void amountTextAreaLostFocus(TextArea amount, Label price) {
        int index = Character.getNumericValue((amount.getId().charAt(0)));
        ShoppingItem item = main.iMat.getShoppingCart().getItems().get(index);

        try {
            double newAmount = Double.parseDouble(amount.getText());
            if (!(item.getProduct().getUnitSuffix().equals("kg") || item.getProduct().getUnitSuffix().equals("l"))) {
                newAmount = Math.round(newAmount);
            }
            item.setAmount(newAmount);
        } catch (NumberFormatException n) {
        }

        if (item.getAmount() > 0) {
            if (item.getAmount() % 1 == 0) {
                amount.setText(" " + (int) item.getAmount() + " " + item.getProduct().getUnitSuffix());
            } else {
                amount.setText(" " + item.getAmount() + " " + item.getProduct().getUnitSuffix());
            }
            price.setText("  " + Math.round(item.getTotal() * 100) / 100.0 + " kr");
        } else {
            main.iMat.getShoppingCart().removeItem(index);
            updateShoppingBagGrid();
        }
        totalPriceLabelOverview.setText("" + Math.round(main.iMat.getShoppingCart().getTotal() * 100) / 100.0);
        confirmTotalPriceLabel.setText("Totalt: " + Math.round(main.iMat.getShoppingCart().getTotal() * 100) / 100.0 + " kr");
        main.updateShoppingBagCounter();

    }

    private void amountTextAreaClicked(TextArea amount) {
        amount.setText(amount.getText().split(" ")[0]);
        amount.selectAll();
    }

    //----------------Credentials----------------\\

    @FXML
    private Button credentialsNextButton;

    @FXML
    private TextField firstNameArea;

    @FXML
    private TextField lastNameArea;

    @FXML
    private TextField phoneArea;

    @FXML
    private TextField addressArea;

    @FXML
    private TextField postalCodeArea;

    @FXML
    private TextField postAddressArea;

    @FXML
    private ImageView firstNameFeedbackImage;

    @FXML
    private ImageView lastNameFeedbackImage;

    @FXML
    private ImageView phoneFeedbackImage;

    @FXML
    private ImageView postalCodeFeedbackImage;

    @FXML
    private ImageView postAddressFeedbackImage;

    @FXML
    private ImageView addressFeedbackImage;

    @FXML
    private Label firstNameFeedbackLabel;

    @FXML
    private Label lastNameFeedbackLabel;

    @FXML
    private Label phoneFeedbackLabel;

    @FXML
    private Label addressFeedbackLabel;

    @FXML
    private Label postalCodeFeedbackLabel;

    @FXML
    private Label postAddressFeedbackLabel;

    @FXML
    private CheckBox saveCredentialsCheckBox;

    private boolean isCredentialsFinished() {
        String[] inputs = {firstNameArea.getText(), lastNameArea.getText(), phoneArea.getText(), addressArea.getText(), postalCodeArea.getText(), postAddressArea.getText()};
        ImageView[] feedbackImages = {firstNameFeedbackImage, lastNameFeedbackImage, phoneFeedbackImage, addressFeedbackImage, postalCodeFeedbackImage, postAddressFeedbackImage};
        Label[] feedbackLabels = {firstNameFeedbackLabel, lastNameFeedbackLabel, phoneFeedbackLabel, addressFeedbackLabel, postalCodeFeedbackLabel, postAddressFeedbackLabel};

        boolean returnBool = true;

        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i].equals("")) {
                feedbackImages[i].getStyleClass().removeAll("correct", "error");
                feedbackLabels[i].setText("");
                returnBool = false;
                //return false;
            } else {

                switch (i) {

                    default:
                        feedbackImages[i].getStyleClass().add("correct");
                        feedbackLabels[i].setText("");
                        break;

                    case 2: // Phone number
                        try {
                            long number = Long.parseLong(inputs[i].replace(' ', '0').replace('+', '0').replace('-', '0'));
                            feedbackImages[i].getStyleClass().add("correct");
                            feedbackLabels[i].setText("");
                        } catch (NumberFormatException e) {
                            feedbackImages[i].getStyleClass().removeAll("correct");
                            feedbackImages[i].getStyleClass().add("error");
                            feedbackLabels[i].setText("Får endast innehålla siffror");
                            returnBool = false;
                            //return false;
                        }
                        break;

                    case 4: // Postal Code
                        try {
                            String[] numbers = inputs[i].split(" ");
                            String number = "";
                            for (String s : numbers) {
                                number += s;
                            }
                            if (number.length() != 5) {
                                throw new NumberFormatException();
                            }
                            int testIfNumber = Integer.parseInt(number);
                            feedbackImages[i].getStyleClass().add("correct");
                            feedbackLabels[i].setText("");
                        } catch (NumberFormatException e) {
                            //feedbackImages[i].getStyleClass().removeAll();
                            feedbackImages[i].getStyleClass().removeAll("correct");
                            feedbackImages[i].getStyleClass().add("error");
                            feedbackLabels[i].setText("Måste innehålla 5 siffror");
                            returnBool = false;
                            //return false;
                        }
                        break;
                }
            }
        }
        return returnBool;
    }

    @FXML
    private void clearCredentialsPressed(ActionEvent event) {
        TextField[] credentialFields = {firstNameArea, lastNameArea, phoneArea, addressArea, postAddressArea, postalCodeArea};

        for (TextField t : credentialFields){
            t.setText("");
        }

        credentialsNextButton.setDisable(true);
    }

    @FXML
    private void saveCredentialsCheckBoxPressed(ActionEvent event){
        Customer customer = main.iMat.getCustomer();
        if (!saveCredentialsCheckBox.isSelected()){
            customer.setAddress("");
            customer.setPostAddress("");
            customer.setFirstName("");
            customer.setLastName("");
            customer.setPhoneNumber("");
            customer.setPostCode("");
        }
        else {
            customer.setFirstName(firstNameArea.getText());
            customer.setLastName(lastNameArea.getText());
            customer.setPhoneNumber(phoneArea.getText());
            customer.setAddress(addressArea.getText());
            customer.setPostCode(postalCodeArea.getText());
            customer.setPostAddress(postAddressArea.getText());
        }
    }


    //----------------Delivery & Payment----------------\\

    @FXML
    private ComboBox<String> dateComboBox;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private Button deliveryNextButton;

    @FXML
    private ToggleGroup payMethodRadioButtons;

    @FXML
    private RadioButton cardRadioButton;

    @FXML
    private AnchorPane cardPanel;

    @FXML
    private TextField cardOwnerArea;

    @FXML
    private TextField cardNumberArea1;

    @FXML
    private TextField cardNumberArea2;

    @FXML
    private TextField cardNumberArea3;

    @FXML
    private TextField cardNumberArea4;

    @FXML
    private TextField cardMonthArea;

    @FXML
    private TextField cardYearArea;

    @FXML
    private TextField cardCVCArea;

    @FXML
    private ImageView cardOwnerFeedbackImage;

    @FXML
    private ImageView cardNumberFeedbackImage;

    @FXML
    private ImageView cardDateFeedbackImage;

    @FXML
    private ImageView cardCVCFeedbackImage;

    @FXML
    private CheckBox saveCardCheckBox;

    @FXML
    void cardRadioButtonPressed(ActionEvent event) {
        cardPanel.setVisible(true);
        cardPanel.setDisable(false);

        if (cardOwnerArea.getText().equals("")) {
            cardOwnerArea.setText(main.iMat.getCustomer().getFirstName() + " " + main.iMat.getCustomer().getLastName());
        }

        updateTabEnabledStatus();
    }

    @FXML
    void notCardRadioButtonPressed(ActionEvent event) {
        cardPanel.setVisible(false);
        cardPanel.setDisable(true);

        updateTabEnabledStatus();
    }

    private boolean checkCardNumberArea(){
        TextField[] cardNumberAreas = {cardNumberArea1, cardNumberArea2, cardNumberArea3, cardNumberArea4};

        for (TextField t: cardNumberAreas){
            if (t.getText().equals("")){
                cardNumberFeedbackImage.getStyleClass().removeAll("correct", "error");
                return false;
            }
        }

        for (TextField t: cardNumberAreas){
            try {
                int number = Integer.parseInt(t.getText());
                if (t.getText().length() != 4){
                    throw new NumberFormatException();
                }
            }
            catch (NumberFormatException e) {
                cardNumberFeedbackImage.getStyleClass().removeAll("correct");
                cardNumberFeedbackImage.getStyleClass().add("error");
                return false;
            }
        }

        String number = cardNumberArea1.getText() + cardNumberArea2.getText() + cardNumberArea3.getText() + cardNumberArea4.getText();

        if (saveCardCheckBox.isSelected()){
            main.iMat.getCreditCard().setCardNumber(number);
        }

        cardNumberFeedbackImage.getStyleClass().removeAll("error");
        cardNumberFeedbackImage.getStyleClass().add("correct");

        return true;
    }

    private boolean isDeliveryPaymentFinished() {
        boolean returnBool = true;


        int d = dateComboBox.getSelectionModel().getSelectedIndex();
        int t = timeComboBox.getSelectionModel().getSelectedIndex();
        if (!(d != -1 && d != 5 && t != -1)) {  // The 5 is there because of the error
            returnBool = false;
        }


        if (cardRadioButton.isSelected()) {
            String[] inputs = {cardOwnerArea.getText(), cardNumberArea1.getText(), cardNumberArea2.getText(), cardNumberArea3.getText(), cardNumberArea4.getText(), cardMonthArea.getText(), cardYearArea.getText(), cardCVCArea.getText()};

            if(!checkCardNumberArea()){
                returnBool = false;
            }

            for (int i = 0; i < inputs.length; i++) {
                if (inputs[i].equals("")) {
                    returnBool = false;
                    switch (i) {
                        case 0:
                            cardOwnerFeedbackImage.getStyleClass().removeAll("correct", "error");
                            break;

                        case 6:
                            if (!inputs[i - 1].equals("")) {
                                cardDateFeedbackImage.getStyleClass().removeAll("correct", "error");
                            }
                            break;
                        case 7:
                            break;
                    }
                } else {

                    switch (i) {
                        case 0:
                            if (saveCardCheckBox.isSelected()){
                                main.iMat.getCreditCard().setHoldersName(cardOwnerArea.getText());
                            }
                            cardOwnerFeedbackImage.getStyleClass().add("correct");
                            break;

                        case 6: // Expiration Year and Month
                            try {
                                int a = Integer.parseInt(inputs[i - 1]);
                                if (a > 12 || a < 1) {
                                    //return false;
                                    throw new NumberFormatException();
                                }
                                int b = Integer.parseInt(inputs[i]);
                                if (b > 99 || b < 0) {
                                    //return false;
                                    throw new NumberFormatException();
                                }
                                cardDateFeedbackImage.getStyleClass().removeAll("correct", "error");
                                cardDateFeedbackImage.getStyleClass().add("correct");
                                if (saveCardCheckBox.isSelected()){
                                    main.iMat.getCreditCard().setValidMonth(Integer.parseInt(cardMonthArea.getText()));
                                    main.iMat.getCreditCard().setValidYear(Integer.parseInt(cardYearArea.getText()));
                                }

                            } catch (NumberFormatException n) {
                                returnBool = false;
                                cardDateFeedbackImage.getStyleClass().removeAll("correct", "error");
                                cardDateFeedbackImage.getStyleClass().add("error");
                                //return false;
                            }
                            break;

                        case 7: // Card CVC
                            boolean isOk = true;
                            if (inputs[i].length() != 3) {
                                returnBool = false;
                                isOk = false;

                            } else {
                                for (char c : inputs[i].toCharArray()) {
                                    if (Character.getNumericValue(c) == -1) {
                                        //return false;
                                        returnBool = false;
                                        isOk = false;
                                    }
                                }
                            }
                            if (isOk){
                                cardCVCFeedbackImage.getStyleClass().removeAll("correct", "error");
                                cardCVCFeedbackImage.getStyleClass().add("correct");
                                if (saveCardCheckBox.isSelected()){
                                    main.iMat.getCreditCard().setVerificationCode(Integer.parseInt(cardCVCArea.getText()));
                                }
                            }
                            else {
                                cardCVCFeedbackImage.getStyleClass().removeAll("correct", "error");
                                cardCVCFeedbackImage.getStyleClass().add("error");
                            }
                            break;
                    }
                }
            }
        }

        if (!returnBool) {
            return false;
        }

        for (Toggle to : payMethodRadioButtons.getToggles()) {

            if (to.isSelected()) {
                return true;
            }
        }

        return false;
    }

    @FXML
    private void clearCardPressed(ActionEvent event) {
        TextField[] cardFields = {cardCVCArea, cardMonthArea, cardYearArea, cardNumberArea1, cardNumberArea2, cardNumberArea3, cardNumberArea4, cardOwnerArea};
        for (TextField t: cardFields) {
            t.setText("");
        }

        deliveryNextButton.setDisable(true);
    }

    @FXML
    private void saveCardCheckBoxPressed(){
        if (!saveCardCheckBox.isSelected()) {
            CreditCard card = main.iMat.getCreditCard();
            card.setHoldersName("");
            card.setValidMonth(-1);
            card.setVerificationCode(-1);
            card.setValidYear(-1);
            card.setCardNumber("");
        }
        else {
            isDeliveryPaymentFinished();
        }
    }


    //----------------Confirmation----------------\\

    @FXML
    private Button confirmationButton;

    @FXML
    private Label deliveryConfirmationLabel;

    @FXML
    private Label nameConfirmationLabel;

    @FXML
    private Label addressConfirmationLabel;

    @FXML
    private Label postalCodeConfirmationLabel;

    @FXML
    private Label postAdressConfirmationLabel;

    @FXML
    private Label paymentConfirmationLabel;

    @FXML
    private ListView<String> confirmationList;

    @FXML
    private Label confirmNumberOfItemsLabel;

    @FXML
    private Label confirmTotalPriceLabel;


    private void updateConfirmationLabels() {
        deliveryConfirmationLabel.setText(timeComboBox.getSelectionModel().getSelectedItem() + " " + dateComboBox.getSelectionModel().getSelectedItem());
        nameConfirmationLabel.setText(firstNameArea.getText() + " " + lastNameArea.getText());
        addressConfirmationLabel.setText(addressArea.getText());
        postalCodeConfirmationLabel.setText(postalCodeArea.getText());
        postAdressConfirmationLabel.setText(postAddressArea.getText());

        String paymentWords = "";
        for (int i = 0; i < payMethodRadioButtons.getToggles().size(); i++) {

            if (payMethodRadioButtons.getToggles().get(i).isSelected()) {
                switch (i) {
                    case 0:
                        paymentWords = "vid leverans";
                        break;
                    case 1:
                        paymentWords = "med kort";
                        break;
                    case 2:
                        paymentWords = "med faktura";
                        break;
                }
            }
        }

        paymentConfirmationLabel.setText(paymentWords);


    }

    private void updateConfirmationList(){

        List<String> items = new ArrayList<>();

        for (ShoppingItem s: main.iMat.getShoppingCart().getItems()) {
            items.add("  " + Math.round(s.getAmount()*100)/100 + " " + s.getProduct().getUnitSuffix() + "   " + s.getProduct().getName() + "  för  " + Math.round(s.getProduct().getPrice()*s.getAmount()*100)/100 + " kr" );
        }

        ObservableList<String> itemsObservable = FXCollections.observableArrayList(items);

        confirmationList.setItems(itemsObservable);

    }

    @FXML
    private void confirmPurchase(ActionEvent event){
        main.iMat.placeOrder(true);
        main.showConfirmationView();
        main.updateConfirmationViewText(timeComboBox.getSelectionModel().getSelectedItem(), dateComboBox.getSelectionModel().getSelectedItem());

        main.pageHistory().addLink(Link.CONFIRMEDVIEW);
    }

}
