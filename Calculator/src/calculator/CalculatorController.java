/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Marcell
 */
public class CalculatorController implements Initializable {
    
    private BigDecimal left;
    private String selectedOperator;
    private boolean numberInputting;
    
    public CalculatorController(){
        this.left=BigDecimal.ZERO;
        this.numberInputting=false;
        this.selectedOperator="";
    }
    
    @FXML
    private TextField display;
    
    @FXML
    private void handleButtonAction (ActionEvent event) {
        Button btn = (Button)event.getSource();
        final String btnText = btn.getText();
        if(btnText.equals("CE") || btnText.equals("C")){
            if(btnText.equals("CE"))
                left = BigDecimal.ZERO;
            selectedOperator="";
            numberInputting=false;
            display.setText("0");
            return;
        }
        if(btnText.matches("[0-9]")){
            if(!numberInputting){
                numberInputting=true;
                display.clear();
            }
            display.appendText(btnText);
            return;
        }
        if(btnText.equals(",") && !display.getText().contains(",")){
            display.appendText(".");
            return;
        }
        if(btnText.equals("±") && !display.getText().equals("0")){
            left = new BigDecimal(display.getText()).negate();
            display.setText(left.toString());
            return;
        }
        if(btnText.matches("[+\\-÷×]")){
            left = new BigDecimal(display.getText());
            selectedOperator = btnText;
            numberInputting = false;
            return;
        }
        if((btnText.equals("1/x") || btnText.equals("x²")) && !display.getText().equals("0")){
            selectedOperator = btnText;
            left = calculate(selectedOperator, new BigDecimal(display.getText()));
            display.setText(left.toString());
            return;
        }
        if(btnText.equals("=")){
            final BigDecimal right = numberInputting ? new BigDecimal(display.getText()) : left;
            left = calculate(selectedOperator, left, right);
            display.setText(left.toString());
            numberInputting = false;
            return;
        }
        if(btnText.equals("⌫")){
            int length = display.getText().length();
            if(length==1)
                display.setText("0");
            else{
                display.setText(display.getText(0, length-1));
            }
            numberInputting=false;
        }
        
    }
    
    private BigDecimal calculate(String operator, BigDecimal left, BigDecimal right){
        switch(operator){
            case "+":
                return left.add(right);
            case "-":
                return left.subtract(right);
            case "÷":
                return left.divide(right);
            case "×":
                return left.multiply(right);
        }
        return right;
    }
    
    private BigDecimal calculate(String operator, BigDecimal left){
        switch(operator){
        case "√":
            return left;
        
        case "1/x":
            return new BigDecimal(1).divide(left);
        case "x²":
            return left.pow(2);
        }
        return left;
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    
}
