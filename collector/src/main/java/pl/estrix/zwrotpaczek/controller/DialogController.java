package pl.estrix.zwrotpaczek.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DialogController {

    private Stage dialogStage;
    private boolean okClicked = false;
    @FXML
    private Text header;
    @FXML
    private Text content;
    @FXML
    private AnchorPane headPane;

    @FXML
    private void initialize() {

    }

    public void setHeadPaneColor(String color) {
        this.headPane.setStyle(color);
    }

    public void setHeader(String header) {
        this.header.setText(header);
    }

    public void setContent(String content) {
        this.content.setText(content);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        okClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}
