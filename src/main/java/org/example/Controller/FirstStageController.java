package org.example.Controller;

import java.io.IOException;
import javafx.fxml.FXML;
import org.example.App;

public class FirstStageController {

    @FXML
    private void nextStep() throws IOException {
        App.setRoot("FXML/secondStage");
    }
}
