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
 * @author DELL
 */
public class Product {
    
   private ObjectProperty<Integer> id;
    private StringProperty name;
    private ObjectProperty<Integer> price;
    private StringProperty properties;
    private StringProperty img;
    private StringProperty level;

    public Product() {
        id = new SimpleObjectProperty<>(null);
        name = new SimpleStringProperty();
        price = new SimpleObjectProperty<>();
        properties = new SimpleStringProperty();
        img = new SimpleStringProperty();
        level = new SimpleStringProperty();

    }

    public Integer getID() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public Integer getPrice() {
        return price.get();
    }

    public String getProperties() {
        return properties.get();
    }

    public String getImg() {
        return img.get();
    }
    
     public String getLevel() {
        return level.get();
    }
    

    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public void setProperties(String properties) {
        this.properties.set(properties);
    }

    public void setImg(String img) {
        this.img.set(img);
    }
    
    public void setLevel(String level) {
        this.level.set(level);
    }

    public ObjectProperty<Integer> getIDProperty() {
        return this.id;
    }

    public StringProperty getNameProperty() {
        return this.name;
    }

    public ObjectProperty<Integer> getPriceProperty() {
        return this.price;
    }

    public StringProperty getPropertiesProperty() {
        return this.properties;
    }

    public StringProperty getImgProperty() {
        return this.img;
    }
    
     public StringProperty getLevelProperty() {
        return this.level;
    }

    public static ObservableList<Product> selectAll() throws SQLException {
        ObservableList<Product> products = FXCollections.observableArrayList();

        try (
                Connection connect = DbProject.getConnection();
                Statement stsm = connect.createStatement();
                ResultSet result = stsm.executeQuery("SELECT p.*,c.* FROM products as p join category as c on p.CategoryID = c.CategoryID");) {

            while (result.next()) {
                Product a = new Product();
                a.setId(result.getInt("ProductID"));
                a.setName(result.getString("Name"));
                a.setPrice(result.getInt("Price"));
                a.setProperties(result.getString("Properties"));
                a.setImg(result.getString("ImgLink"));
                a.setLevel(result.getString("NameC"));
                products.add(a);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return products;

    }
    
    
    
    public static ObservableList selectImgByID(int id) throws SQLException {
        ObservableList products = FXCollections.observableArrayList();
        String sql = "SELECT ImgLink FROM products WHERE ProductID = ?";
        try (
                Connection connect = DbProject.getConnection();
                Statement stsm = connect.createStatement();
                ResultSet result = stsm.executeQuery("SELECT ImgLink FROM products WHERE ProductID = " + id);) {
            while (result.next()) {
                products.add(result.getString("ImgLink"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

   public static Product insert(Product newProduct) throws SQLException {
        String getLvl = newProduct.getLevel() ;
        int catID = 0;
        String sql = "INSERT INTO products (products.Name, products.Price, products.Properties, products.ImgLink, products.CategoryID) "
               + "VALUES (?, ?, ?, ?, ?)" 
                + "LIMIT 1";
        ResultSet key = null;
        try (
                Connection connect = DbProject.getConnection();
                PreparedStatement stmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                Statement stsm = connect.createStatement();
                ResultSet result = stsm.executeQuery("SELECT CategoryID FROM category WHERE NameC = '" + getLvl + "'");) {
            
            if(result.next()){
                catID = result.getInt("CategoryID");
            }
            
            stmt.setString(1, newProduct.getName());
            stmt.setInt(2, newProduct.getPrice());
            stmt.setString(3, newProduct.getProperties());
            stmt.setString(4, newProduct.getImg());
             stmt.setInt(5, catID);

            int rowInserted = stmt.executeUpdate();
            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                newProduct.setId(newKey);
                return newProduct;
            } else {
                System.out.println("No Product inserted");
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


    public static boolean delete(Product deleteProduct) {
        String sql = "DELETE  FROM products "
                + "WHERE ProductID = ? "
                + "LIMIT 1";
        try (
                Connection connect = DbProject.getConnection();
                PreparedStatement stmt = connect.prepareStatement(sql);) {

            stmt.setInt(1, deleteProduct.getID());

            int rowDeleted = stmt.executeUpdate();

            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No Product deleted");
                return false;
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

  public static boolean update(Product updateProduct) {
        String getLvl = updateProduct.getLevel() ;
        int catID = 0;
            String sql = "UPDATE products " 
                + "SET "
                + "CategoryID = ?, "
                + "`Name` = ?, "
                + "Price = ?, "
                + "Properties = ?, "
                + "ImgLink = ? "  
                + "WHERE ProductID = ? "
                + "LIMIT 1;";
        
        try (
                Connection connect = DbProject.getConnection();
                PreparedStatement stmt = connect.prepareStatement(sql);
                Statement stsm = connect.createStatement();
                ResultSet result = stsm.executeQuery("SELECT CategoryID FROM category WHERE NameC = '" + getLvl + "'");) {
            
            if(result.next()){
                catID = result.getInt("CategoryID");
            }
            
            stmt.setInt(1, catID);
            stmt.setString(2, updateProduct.getName());
            stmt.setInt(3, updateProduct.getPrice());
            stmt.setString(4, updateProduct.getProperties());
            stmt.setString(5, updateProduct.getImg());
            stmt.setInt(6, updateProduct.getID());
           

            int rowUpdated = stmt.executeUpdate();

            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No Product is updated");
                return false;
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
        
    }

    
}
