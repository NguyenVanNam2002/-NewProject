/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.Data;

import javafx.collections.ObservableList;

/**
 *
 * @author Admin
 */
public interface CategoryDAO {
    public  ObservableList<Category> selectAll();
    public  Category insert(Category newcategory);
    public  boolean update(Category updatecategory);
    public  boolean delete(Category deletecategory);
    public  ObservableList<String> selectName();
}
