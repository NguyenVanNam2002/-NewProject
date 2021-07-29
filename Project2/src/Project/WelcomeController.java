package Project;

import Project.Data.ProjectSignUp;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author icom
 */
public class WelcomeController {

    @FXML
    private Text use;
    private ProjectSignUp psu;

    public void initialize(ProjectSignUp p) throws IOException, InterruptedException {
        this.psu = p;
        if(this.psu!=null){
            use.setText(p.getAccount());
            loading(p);
        }
        
    }

    public void initialize() {
        use.setVisible(false);
    }

    private void loading(ProjectSignUp u) throws IOException, InterruptedException {
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        Nagatice.getInstance().goToChoose(u);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage() + " WELCOME_FXML");
                    }
                });
            }
        }, 4000);
    }
}
