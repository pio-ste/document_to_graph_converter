package org.example.Controller;

import javafx.fxml.FXML;
import org.example.App;

import java.io.IOException;

public class ThirdStageController {

    @FXML
    private void previousStep() throws IOException {
        App.setRoot("FXML/secondStage");
    }
}
