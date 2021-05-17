package org.example.Controller;

import java.io.IOException;
import javafx.fxml.FXML;
import org.example.App;

public class SecondStageController {

    @FXML
    private void nextStep() throws IOException {
        App.setRoot("FXML/thirdStage");
    }

    @FXML
    private void previousStep() throws IOException {
        App.setRoot("FXML/firstStage");
    }
}