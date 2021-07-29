/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.Data;

import Project.DbProject.DbProject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author icom
 */
public class Feedback {
    private ObjectProperty<Integer> FbID;
    private StringProperty Content;
    private StringProperty ProductID;

    public Feedback() {
        FbID = new SimpleObjectProperty<>(null);
        Content = new SimpleStringProperty();
        ProductID = new SimpleStringProperty();
    }

    public Integer getFbID() {
        return FbID.get();
    }

    public String getContent() {
        return Content.get();
    }

    public String getProductID() {
        return ProductID.get();
    }


    public void setFbID(int FbID) {
        this.FbID.set(FbID);
    }

    public void setContent(String Content) {
        this.Content.set(Content);
    }

    public void setProductID(String ProductID) {
        this.ProductID.set(ProductID);
    }

    public ObjectProperty<Integer> getFbIDProperty() {
        return this.FbID;
    }

    public StringProperty getContentProperty() {
        return this.Content;
    }

    public StringProperty getProductIDProperty() {
        return this.ProductID;
    }


    public static ObservableList<Feedback> selectAll() {

        ObservableList<Feedback> feedbacks = FXCollections.observableArrayList();

        try (
                Connection conn = DbProject.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT feedback.* , products.* FROM feedback  join products on feedback.ProductID = products.ProductID");) {
            while (rs.next()) {
                Feedback f = new Feedback();
                f.setFbID(rs.getInt("FbID")); //"id" is column name in table book
                f.setContent(rs.getString("Content")); //"title" is column name in table book
                f.setProductID(rs.getString("products.Name")); //"page" is column name in table book


                feedbacks.add(f);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }

        return feedbacks;
    }

    public static Feedback insert(Feedback newfeedback) throws SQLException {
        String pro = newfeedback.getProductID();
        String sql = "INSERT into feedback (Content, ProductID) "
                + "VALUES (?, ?)";
        ResultSet key = null;
        int productID =0;
        try (
                Connection conn = DbProject.getConnection();
                PreparedStatement stmt
                = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                Statement stsm = conn.createStatement();
                ResultSet result = stsm.executeQuery("SELECT ProductID FROM products WHERE Name = '" + pro + "'");
            
                
                ) {
                    
            if(result.next()){
                productID = result.getInt("ProductID");
            }
            stmt.setString(1, newfeedback.getContent());
            stmt.setInt(2, productID);

            int rowInserted = stmt.executeUpdate();

            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                newfeedback.setFbID(newKey);
                return newfeedback;
            } else {
                System.err.println("No feedback inserted");
                return null;
            }

        } catch (Exception e) {
            System.err.println(e);
            return null;
        } finally {
            if (key != null) {
                key.close();
            }
        }
    }

    public static boolean update(Feedback updatefeedback) {
        String pro = updatefeedback.getProductID();
        String sql = "UPDATE feedback SET "
                + "Content = ?, "
                + "ProductID = ? "
                + "WHERE FbID = ?";
        int productID =0;
        try (
                Connection conn = DbProject.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                 Statement stsm = conn.createStatement();
                ResultSet result = stsm.executeQuery("SELECT ProductID FROM products WHERE Name = '" + pro + "'");
            ) {
             if(result.next()){
                productID = result.getInt("ProductID");
            }
            stmt.setString(1, updatefeedback.getContent());
            stmt.setInt(2, productID);
            stmt.setInt(3, updatefeedback.getFbID());
            

            int rowUpdated = stmt.executeUpdate();

            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No Feedback updated");
                return false;
            }

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    public static boolean delete(Feedback deletefeedback) {
        String sql = "DELETE FROM feedback WHERE FbID = ?";
        try (
                Connection conn = DbProject.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, deletefeedback.getFbID());

            int rowDeleted = stmt.executeUpdate();

            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No FeedBack deleted");
                return false;
            }

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
}
