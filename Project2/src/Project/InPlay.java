/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class InPlay extends Application {
    public static boolean IS = false;
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.setTitle("Snacks Shop");
        Nagatice.getInstance().setStage(primaryStage);
        Nagatice.getInstance().goToIndex();
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         launch(args);
    }
    
}
