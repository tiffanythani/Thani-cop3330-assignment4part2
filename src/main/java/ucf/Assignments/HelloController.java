/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 tiffany thani
 */


package ucf.Assignments;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void createThings() {

        welcomeText.setText("Welcome to JavaFX Application!");
    }
}