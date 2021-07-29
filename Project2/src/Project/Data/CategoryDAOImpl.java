/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.Data;

import Project.DbProject.DbProject;
import Project.DbProject.DbType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Admin
 */
public class CategoryDAOImpl implements CategoryDAO {
      DbType database = DbType.SQL;
    public  ObservableList<Category> selectAll() {

        ObservableList<Category> categories = FXCollections.observableArrayList();

        try (
                Connection conn = DbProject.getConnection(database);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM category");) {
            while (rs.next()) {
                Category c = new Category();
                c.setCatID(rs.getInt("CategoryID")); 
                c.setCat_name(rs.getString("NameC")); 
                categories.add(c);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }

        return categories;
    }
    
    public  ObservableList<String> selectName() {

        ObservableList<String> categories = FXCollections.observableArrayList();

        try (
                Connection conn = DbProject.getConnection(database);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT NameC FROM category");) {
            while (rs.next()) {
//                Category c = new Category();
//                c.setCatID(rs.getInt("CategoryID")); 
//                c.setCat_name(rs.getString("NameC")); 
                categories.add(rs.getString("NameC"));
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }

        return categories;
    }

    public  Category insert(Category newcategory){
        String sql = "INSERT into category (NameC) "
                + "VALUES (?)";
        ResultSet key = null;
        try (
                Connection conn = DbProject.getConnection(database);
                PreparedStatement stmt
                = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, newcategory.getCat_name());

            int rowInserted = stmt.executeUpdate();

            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                newcategory.setCatID(newKey);
                return newcategory;
            } else {
                System.err.println("No category inserted");
                return null;
            }

        } catch (Exception e) {
            System.err.println(e);
            return null;
        } finally {
            try {
                if (key != null) key.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public  boolean update(Category updatecategory) {
        String sql = "UPDATE category SET "
                + "NameC = ? "
                + "WHERE CategoryID = ?";

        try (
                Connection conn = DbProject.getConnection(database);
                PreparedStatement stmt = conn.prepareStatement(sql);
                ) {

            stmt.setString(1, updatecategory.getCat_name());
            stmt.setInt(2, updatecategory.getCatID());
            

            int rowUpdated = stmt.executeUpdate();

            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No category updated");
                return false;
            }

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    public  boolean delete(Category deletecategory) {
        String sql = "DELETE FROM category WHERE CategoryID = ?";
        try (
                Connection conn = DbProject.getConnection(database);
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, deletecategory.getCatID());

            int rowDeleted = stmt.executeUpdate();

            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No category deleted");
                return false;
            }

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

}
