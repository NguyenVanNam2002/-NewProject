/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import Project.Data.Category;
import Project.Data.CategoryDAO;
import Project.Data.CategoryDAOImpl;
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
 * @author icom
 */
public class CategoryUIController {
    private CategoryDAO ds = new CategoryDAOImpl();
    
    
    @FXML
    private TableView<Category> tvCategory;

    @FXML
    private JFXButton btnInsert;

    @FXML
    private TableColumn<Category, Integer> tcCatID;

    @FXML
    private TableColumn<Category, String> tcCat_name;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;
    
    
    
      @FXML
    private JFXButton btnproduct;

    @FXML
    private JFXButton btnfeedback;

    @FXML
    private JFXButton btncustomer;

    @FXML
    private JFXButton logout;
    @FXML
    void btncustomer(ActionEvent event) throws IOException {
         Nagatice.getInstance().goToCustomerIndex();
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
    void btnInsertClick(ActionEvent event) throws IOException {
        Nagatice.getInstance().goCategoryToEdit(null);
    }

 
    public void initialize() {

        System.out.println("#CategoryController initialized!");

        tvCategory.setItems(ds.selectAll());

        tcCatID.setCellValueFactory((category) -> {
            return category.getValue().getCatIDProperty();
        });

        tcCat_name.setCellValueFactory((category) -> {
            return category.getValue().getCat_nameProperty();
        });
    }

    @FXML
    void btnUpdateClick(ActionEvent event) throws IOException {
        Category updatecategory = tvCategory.getSelectionModel().getSelectedItem();

        if (updatecategory == null) { //no book is selected
            selectCategoryWarning();
        } else { //a book is selected
           Nagatice.getInstance().goCategoryToEdit(updatecategory);
        }
    }

    @FXML
    void btnDeleteClick(ActionEvent event) {
        Category selectedcategory= tvCategory.getSelectionModel().getSelectedItem();

        if (selectedcategory == null) { //no book is selected
            selectCategoryWarning();
        } else { //a book is selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure you want to delete the selected Category?");
            alert.setTitle("Deleting a Category");
            Optional<ButtonType> confirmationResponse
                    = alert.showAndWait();
            if (confirmationResponse.get() == ButtonType.OK) {
                Category deletecategory = tvCategory.getSelectionModel().getSelectedItem();
                boolean result = ds.delete(deletecategory);

                if (result) {
                    tvCategory.getItems().remove(deletecategory); //update TableView
                    System.out.println("Category is deleted");
                } else {
                    System.err.println("No Category is deleted");
                }
            }
        }

    }

    private void selectCategoryWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Please select a Category");
        alert.setHeaderText("A Category must be selected for the operation");
        alert.showAndWait();
    }
    
    
}
