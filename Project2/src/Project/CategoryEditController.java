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
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 *
 * @author icom
 */
public class CategoryEditController {
    
    private CategoryDAO ds = new CategoryDAOImpl();
    
    @FXML
    private JFXTextField txtCat_name;

    private Category editcategory = null;

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
        Nagatice.getInstance().goToCategoryIndex();
    }

    private boolean validation() {
        String msg = "";
        if (txtCat_name.getText().isEmpty()) {
            msg +="Category name must not be null" ;
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
                if (editcategory == null) { //insert a new book
                    Category insertcategory = extractCategoryFromFields();
                    insertcategory = ds.insert(insertcategory);
                    
                    msg = "a new category was insert with id:" +insertcategory.getCatID();
                    lbMessage.setText(msg);
                } else { //update an existing book/
                    Category updatecategory = extractCategoryFromFields();
                    updatecategory.setCatID(this.editcategory.getCatID());

                    boolean result = ds.update(updatecategory);
                    if (result) {
                        lbMessage.setText("Update category successfully");
                    } else {
                        lbMessage.setText("Update category unsuccessfully");
                    }
                }
            } catch (Exception e) {
                lbMessage.setText(e.getMessage());
            }
        }
        
    }

    public void initialize(Category editcategory) {
        this.editcategory = editcategory;
        String msg = "";
        if (this.editcategory == null) { //insert a new book
            msg = "Create a category ";
        } else { //update an existing book
            msg = "Update a category";
            txtCat_name.setText(editcategory.getCat_name());
        }

        lbMessage.setText(msg);
    }

    private Category extractCategoryFromFields() {
        Category category = new Category();
        category.setCat_name(txtCat_name.getText());
        return category;
    }
}
