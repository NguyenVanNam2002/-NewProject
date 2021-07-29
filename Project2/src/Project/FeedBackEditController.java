/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import Project.Data.Feedback;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 *
 * @author icom
 */
public class FeedBackEditController {
// đoạn nao >
    @FXML
    private JFXTextField txtProductID;

    @FXML
    private JFXTextArea txtContent;

    private Feedback editfeedback = null;

    @FXML
    private JFXButton btnIndex;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton btnReset;

    @FXML
    private Label lbMessage;

    @FXML
    void btnIndexClick(ActionEvent event) throws IOException {
        Nagatice.getInstance().goToFeedbackIndex();
    }

    private boolean validation() {
        String msg = "";
       

        if (txtContent.getText().isEmpty()) {
            msg += "Feedback content must be not null";
            lbMessage.setText(msg);
            return false;
        }
        if (txtProductID.getText().isEmpty()) {
            msg += "ProductID is not null";
            lbMessage.setText(msg);
            return false;
        }

        return true;
    }

    @FXML
    void btnSubmitClick(ActionEvent event) {
        String msg = "";

        if (validation() != false) {
            try {
                if (editfeedback == null) { //insert a new book
                    Feedback insertfeedback = extractFeedbackFromFields();
                    insertfeedback = Feedback.insert(insertfeedback);

                    msg = "a new feedback was insert with id:" + insertfeedback.getFbID();
                    lbMessage.setText(msg);
                } else { //update an existing book/
                    Feedback updatefeedback = extractFeedbackFromFields();
                    updatefeedback.setFbID(this.editfeedback.getFbID());

                    boolean result = Feedback.update(updatefeedback);
                    if (result) {
                        lbMessage.setText("Update feedback successfully");
                    } else {
                        lbMessage.setText("Update feedback unsuccessfully");
                    }
                }
            } catch (Exception e) {
                lbMessage.setText(e.getMessage());
            }
        }

    }

    public void initialize(Feedback editfeedback) {
        this.editfeedback = editfeedback;
        String msg = "";
        if (this.editfeedback == null) { //insert a new book
            msg = "Send a new feedback";
        } else { //update an existing book
            msg = "Update a feedback";
            txtContent.setText(editfeedback.getContent());
            txtProductID.setText(editfeedback.getProductID());
        }

        lbMessage.setText(msg);
    }

    private Feedback extractFeedbackFromFields() {
        Feedback feedback = new Feedback();
        feedback.setContent(txtContent.getText());
        feedback.setProductID(txtProductID.getText());
        return feedback;
    }
}
