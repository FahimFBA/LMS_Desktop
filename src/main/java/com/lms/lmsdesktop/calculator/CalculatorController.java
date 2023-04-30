package com.lms.lmsdesktop.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.w3c.dom.events.Event;

public class CalculatorController {


    @FXML
    private TextArea display;
    @FXML private Button one, two, three, four, five, six, seven, eight, nine, zero, times, divided, plus, minus, percent, change, buttonDelete;


    private Boolean[] operator = new Boolean[4];
    private int operatorCount = 0;

    double[] temporary = {0, 0};

    private String actualText;

    private Calculator main;

    public void setMain(Calculator main) {
        this.main = main;
        display.setEditable(false);

        display.setText("0");
        for(int i = 0; i<4; i++) {
            operator[i] = false;
        }
    }

    @FXML
    public void handleClick(ActionEvent event){
        actualText = display.getText();
        if (display.getText().equals("0") || display.getText() == "0"){
            buttonDelete.setText("C");
            display.setText("");
            System.out.println("actualText: "+actualText);
            actualText = "";
        }
        Button btn = (Button) event.getSource();
        switch(btn.getId()) {
            case "zero" :
                display.appendText("0");
                break;
            case "one" :
                display.appendText("1");
                break;
            case "two" :
                display.appendText("2");
                break;
            case "three" :
                display.appendText("3");
                break;
            case "four" :
                display.appendText("4");
                break;
            case "five" :
                display.appendText("5");
                break;
            case "six" :
                display.appendText("6");
                break;
            case "seven" :
                display.appendText("7");
                break;
            case "eight" :
                display.appendText("8");
                break;
            case "nine" :
                display.appendText("9");
                break;
            case "comma" :
                display.appendText(".");
                break;
        }
    }

    @FXML public void delete(ActionEvent event) {
        buttonDelete.setText("AC");
        display.setText("0");
        for(int i = 0; i < 2; i++) {
            temporary[i] = 0;
        }
        for(int i = 0; i<4; i++) {
            operator[i] = false;
        }
    }


    @FXML
    public void operation(ActionEvent event) {

        operatorCount ++;
        System.out.println("operatorCount: "+operatorCount);
        if (display.getText() != ""){
            actualText = display.getText();
        }
        Button btn = (Button) event.getSource();
        String operation = btn.getId();
        switch(operation) {
            case "plus":
                operator[1] = true;
                temporary[0] = Double.parseDouble(display.getText());
                break;
            case "minus":
                operator[2] = true;
                temporary[0] = Double.parseDouble(display.getText());
                break;
            case "times":
                operator[3] = true;
                temporary[0] = Double.parseDouble(display.getText());
                break;
            case "divided":
                operator[4] = true;
                temporary[0] = Double.parseDouble(display.getText());
                break;
        }
        display.setText("");
    }


    @FXML public void changeSign() {
        double number = Double.parseDouble(display.getText());
        try {
            if (number != 0){
                number = number * (-1);
                display.setText(Double.toString(number));
                System.out.println(number);
            } else {
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @FXML public void result(ActionEvent event) {
        double result = 0;
        temporary[1] = Double.parseDouble(display.getText());
        String temp0 = Double.toString(temporary[0]);
        String temp1 = Double.toString(temporary[1]);
        if (operator[1]){
            result = temporary[0] + temporary[1];
        } else if (operator[2]){
            result = temporary[0] - temporary[1];
        } else if (operator[3]){
            result = temporary[0] * temporary[1];
        } else if (operator[4]){
            result = temporary[0] / temporary[1];
        }
        display.setText(Double.toString(result));
    }
}
