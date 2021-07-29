/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import Project.Data.ProjectSignUp;
import Project.Data.ProjectSignUpDAOImpl;
import Project.Data.ProjectSignUpDAP;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Admin
 */
public class FunctionAminCustomer {
    private ProjectSignUpDAP pda = new ProjectSignUpDAOImpl();
    
    @FXML
    private JFXButton delete;

   

    @FXML
    private TableView<ProjectSignUp> Tableview;

    @FXML
    private TableColumn<ProjectSignUp, String> account;

    @FXML
    private TableColumn<ProjectSignUp, String> password;

    @FXML
    private TableColumn<ProjectSignUp, String> name;

    @FXML
    private TableColumn<ProjectSignUp, String> phone;

    @FXML
    private TableColumn<ProjectSignUp, String> address;
    @FXML
    private JFXButton btnproduct;

    @FXML
    private JFXButton btncategory;

    @FXML
    private JFXButton btnfeedback;

    @FXML
    private JFXButton logout;

     @FXML
    void btncategory(ActionEvent event) throws IOException {
        Nagatice.getInstance().goToCategoryIndex();
    }

    @FXML
    void btnfeedback(ActionEvent event) throws IOException {
        Nagatice.getInstance().goToFeedbackIndex();
    }

    @FXML
    void btnproduct(ActionEvent event) throws IOException {
        Nagatice.getInstance().goToIndexProduct();
    }

    @FXML
    void btnlogout(ActionEvent event) throws IOException {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Bạn chắc chắn muốn đăng xuất ?");
        alert.setTitle("Lưu ý ");
        Optional<ButtonType> confirmationResponse
                = alert.showAndWait();
        if (confirmationResponse.get() == ButtonType.OK) {
            Nagatice.getInstance().goToIndex();
        } 
    }

    @FXML
    void btndelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure you want to delete the selected Category?");
            alert.setTitle("Deleting a Customer");
            Optional<ButtonType> confirmationResponse
                    = alert.showAndWait();
            if (confirmationResponse.get() == ButtonType.OK) {
                ProjectSignUp delete = Tableview.getSelectionModel().getSelectedItem();
                boolean result = pda.delete(delete);

                if (result) {
                    Tableview.getItems().remove(delete); //update TableView
                } 
            }

    }
    
    public void initialize() {
        System.out.println("#Customer admin initialized!");
        Tableview.setItems(pda.selectAll());
        
        account.setCellValueFactory((account_client)->{
            return account_client.getValue().getAccountProperty();
        });
        
        password.setCellValueFactory((account_client)->{
            return account_client.getValue().getPasswordProperty();
        });
        
        name.setCellValueFactory((account_client)->{
            return account_client.getValue().getNameProperty();
        });
        
        phone.setCellValueFactory((account_client)->{
            return account_client.getValue().getPhoneProperty();
        });
        
        address.setCellValueFactory((account_client)->{
            return account_client.getValue().getAddressProperty();
        });
        
        
      
    }
}
