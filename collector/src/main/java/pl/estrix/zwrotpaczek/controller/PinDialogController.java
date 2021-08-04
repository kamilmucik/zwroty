package pl.estrix.zwrotpaczek.controller;

import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import pl.estrix.zwrotpaczek.dto.ReturnDialogDto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PinDialogController {

    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Button button0;
    @FXML
    private Button buttonOk;
    @FXML
    private Label resultLabel;
    private Stage dialogStage;
    private boolean okClicked = false;
    @FXML
    private Text header;
    @FXML
    private AnchorPane headPane;

    private Integer index;
    @FXML
    private Button buttonClean;

    StringBuilder concanetValue = new StringBuilder("");

    private StringProperty value = new SimpleStringProperty(concanetValue.toString());

    @FXML
    private void initialize() {
        index = 0;
        resultLabel.textProperty().bind(value);

        buttonClean.setOnMouseClicked(event -> {
            concanetValue.delete(0,concanetValue.length());
            value.setValue(concanetValue.toString());
            index = 0;
        });
        button1.setOnMouseClicked(event -> {
            addChar("1");
        });
        button2.setOnMouseClicked(event -> {
            addChar("2");
        });
        button3.setOnMouseClicked(event -> {
            addChar("3");
        });
        button4.setOnMouseClicked(event -> {
            addChar("4");
        });
        button5.setOnMouseClicked(event -> {
            addChar("5");
        });
        button6.setOnMouseClicked(event -> {
            addChar("6");
        });
        button7.setOnMouseClicked(event -> {
            addChar("7");
        });
        button8.setOnMouseClicked(event -> {
            addChar("8");
        });
        button9.setOnMouseClicked(event -> {
            addChar("9");
        });
        button0.setOnMouseClicked(event -> {
            addChar("0");
        });

        value.addListener((obs, oldState, newState) -> {
            if (newState.length() > 3) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd");
                String passwordField = simpleDateFormat.format(new Date());
                if (passwordField.equals(concanetValue.toString())){
                    okClicked = true;

                    PauseTransition delay = new PauseTransition(Duration.millis(250));
                    delay.setOnFinished( event -> dialogStage.close() );
                    delay.play();
                } else {
                    okClicked = false;
                    concanetValue.delete(0,concanetValue.length());
                    value.setValue(concanetValue.toString());
                    index = 0;
                }
            }
        });
    }

    private void addChar(String chr){
        concanetValue.append(chr);
        value.setValue(concanetValue.toString());
        index++;
    }

    public void setHeadPaneColor(String color) {
        this.headPane.setStyle(color);
    }

    public void setHeader(String header) {
        this.header.setText(header);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public ReturnDialogDto<Boolean> isOkClicked() {
        ReturnDialogDto<Boolean> result = new ReturnDialogDto<>();
        result.set(okClicked);
        return result;
    }

    @FXML
    private void handleOk() {
    }

    @FXML
    private void handleCancel() {
        okClicked = false;
        dialogStage.close();
    }
}
