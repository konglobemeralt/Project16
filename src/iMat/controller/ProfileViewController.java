package iMat.controller;

import iMat.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.Customer;

import java.io.IOException;

/**
 * Created by konglobemeralt on 2017-03-07.
 */
public class ProfileViewController {

    //Reference the main application
    private Main main;

    @FXML
    private TextField firstNameArea;

    @FXML
    private TextField lastNameArea;

    @FXML
    private TextField addressArea;

    @FXML
    private TextField postalCodeArea;

    @FXML
    private TextField postAddressArea;

    @FXML
    private TextField phoneArea;

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
    private void initialize(){

        TextField[] textFields = {firstNameArea, lastNameArea, addressArea, postAddressArea, postalCodeArea, phoneArea, cardNumberArea1, cardNumberArea2, cardNumberArea3, cardNumberArea4, cardOwnerArea, cardYearArea, cardMonthArea, cardCVCArea};

        for (TextField t: textFields) {
            t.textProperty().addListener(new ChangeListener<String>() {
                public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                    save();
                }
            });
        }

        postalCodeArea.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if(!newValue.matches("\\d{0,5}"))
                {
                    postalCodeArea.setText(oldValue);
                }
            }
        });

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

    }

    public void saveButtonPressed(){
        System.out.println("SaveButton pressed");
        save();
        main.pageHistory().goBack();
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void update(){
        Customer customer = main.iMat.getCustomer();

        firstNameArea.setText(customer.getFirstName());
        System.out.println(customer.getLastName()+"NU BORDE LASTNAME SKRIVITS UT!!!");
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
        cardMonthArea.setText(""+card.getValidMonth());
        cardYearArea.setText(""+card.getValidYear());
        cardCVCArea.setText(""+card.getVerificationCode());
    }

    private void save(){
        Customer customer = main.iMat.getCustomer();
        CreditCard card = main.iMat.getCreditCard();

        customer.setFirstName(firstNameArea.getText());
        customer.setLastName(lastNameArea.getText());
        customer.setAddress(addressArea.getText());
        customer.setPhoneNumber(phoneArea.getText());
        customer.setPostAddress(postAddressArea.getText());
        customer.setPostCode(postalCodeArea.getText());

        card.setHoldersName(cardOwnerArea.getText());
        card.setCardNumber(cardNumberArea1.getText()+cardNumberArea2.getText()+cardNumberArea3.getText()+cardNumberArea4.getText());
        try{
            card.setValidMonth(Integer.parseInt(cardMonthArea.getText()));
        } catch (NumberFormatException e){ }
        try{
            card.setValidYear(Integer.parseInt(cardYearArea.getText()));
        } catch (NumberFormatException e){ }
        try{
            card.setVerificationCode(Integer.parseInt(cardCVCArea.getText()));
        } catch (NumberFormatException e){ }

    }

}
