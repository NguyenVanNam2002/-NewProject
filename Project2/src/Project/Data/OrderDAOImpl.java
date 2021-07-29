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
public class OrderDAOImpl implements OrderDAO{
    DbType data = DbType.SQL;
    
    // shopping Cart
    public  Order insert(Order order){
        String sql = "INSERT INTO ShoppingCart(productID, accounts , quantity , Total_price )"
                + " VALUES (? , ? , ? , ? )";
        ResultSet key = null;
        try (Connection con = DbProject.getConnection(data);
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ){
            stmt.setInt(1, Integer.parseInt(order.getProductID()));
            stmt.setString(2, order.getAccount());
            stmt.setInt(3, order.getQuantity());
            stmt.setString(4, order.getTotalPrice().toString());
            int row = stmt.executeUpdate();
            if(row ==1 ){
               
                return order;
            
            }else{
                return null;
            }
        } catch (Exception e) {
            return null;
        }finally {
            try {
                if (key != null) key.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    
    }
   
    public  boolean update(Order o){
        String sql = "UPDATE ShoppingCart SET "
                + " Total_price = ? ,"
                + " quantity = ? "
                + "WHERE productID = ? ";

        try (
                Connection conn = DbProject.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ) {

            stmt.setInt(1, o.getTotalPrice());
            stmt.setInt(2, o.getQuantity());
            stmt.setInt(3, Integer.parseInt(o.getProductID()));
            

            int rowUpdated = stmt.executeUpdate();

            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No order updated");
                return false;
            }

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    // Order
    public  Order insertOrder(Order order){
        String sql = "INSERT INTO Orders(Client_ID , total_price, dates) "
                + " VALUES ( ? , ? , ? )";
        ResultSet key = null;
        try (Connection con = DbProject.getConnection(data);
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ){
            
            stmt.setString(1, order.getAccount());
            stmt.setString(2, order.getTotalPrice().toString());
            stmt.setString(3, order.getDate());
            int row = stmt.executeUpdate();
            if(row ==1 ){
               key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                order.setID(newKey);
                return order;
            
            }else{
                return null;
            }
        } catch (Exception e) {
            return null;
        }finally {
            try {
                if (key != null) key.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    
    }
    
     public  ObservableList<Order> Sellect(String user){
        String sql = "SELECT * FROM Orders WHERE Client_ID = ? ";
        ObservableList<Order> o = FXCollections.observableArrayList();

        try (
                Connection con = DbProject.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql);
                ){
                stmt.setString(1, user);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    Order odr= new Order();   
                        odr.setID(rs.getInt("OrderID"));
                        odr.setTotalPrice(Integer.parseInt(rs.getString("total_price")));
                        odr.setDate(rs.getString("dates"));
                        o.add(odr);
                }
//                if(rs.next()){
//                    
//                }
        } catch (Exception e) {
        }
        return o;
    }
//  

}
