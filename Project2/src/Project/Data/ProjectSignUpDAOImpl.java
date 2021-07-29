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
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Admin
 */
public class ProjectSignUpDAOImpl implements ProjectSignUpDAP{
    DbType database = DbType.SQL;
    //Admin
    public boolean LoginAdmin(ProjectSignUp account){
        String sql = "SELECT * FROM account_admin WHERE accountadmin = ? and passwordadmin =?";
        try (
            Connection conn = DbProject.getConnection(database);
            PreparedStatement stmt = 
                    conn.prepareStatement(sql);
                ) {
            
            stmt.setString(1, account.getAccount());
            stmt.setString(2, account.getPassword());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
               
                return true;
            } else {
                 return false;
            }  
        } catch (Exception e) {
            return false;
        }
    
    
    
    }
   //Client
    public  ProjectSignUp insert(ProjectSignUp inserts){
        
        String sql = "INSERT into account_client (accounts,passwords,nick_name, phone , address)"
                + "VALUES (?, ?, ?, ?, ?)";
        ResultSet key = null;
        try (
            Connection conn = DbProject.getConnection(database);
            PreparedStatement stmt = 
                    conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ) {
            
            stmt.setString(1, inserts.getAccount());
            stmt.setString(2, inserts.getPassword());
            stmt.setString(3, inserts.getName());
            stmt.setString(4, inserts.getPhone());
            stmt.setString(5, inserts.getAddress());
          
            
           
            int rowInserted = stmt.executeUpdate();
            if (rowInserted == 1) {
               
                return inserts;
            } else {
                System.err.println("No Manager inserted");
                
                return null;
            }  
        } catch (Exception e) {
            return null;
        }
     }
    public boolean Login(ProjectSignUp account){
        String sql = "SELECT * FROM account_client WHERE accounts = ? and passwords =?";
        try (
            Connection conn = DbProject.getConnection(database);
            PreparedStatement stmt = 
                    conn.prepareStatement(sql);
                ) {
            
            stmt.setString(1, account.getAccount());
            stmt.setString(2, account.getPassword());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
               
                return true;
            } else {
                 return false;
            }  
        } catch (Exception e) {
            return false;
        }
    
    }
    public  ObservableList<ProjectSignUp> selectAll() {

        ObservableList<ProjectSignUp> select = FXCollections.observableArrayList();

        try (
                Connection conn = DbProject.getConnection(database);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM account_client");) {
            while (rs.next()) {
                ProjectSignUp p = new ProjectSignUp();
                p.setAccount(rs.getString("accounts"));
                p.setPassword(rs.getString("passwords"));
                p.setName(rs.getString("nick_name"));
                p.setPhone(rs.getString("phone"));
                p.setAddress(rs.getString("address"));
                
                select.add(p);
            }         
        } catch (Exception e) {
            System.err.println(e.getMessage());

        }

        return select;
    }
    public  boolean delete(ProjectSignUp delete) {
        String sql = "DELETE FROM account_client WHERE accounts = ?";
        try (
                Connection conn = DbProject.getConnection(database);
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, delete.getAccount());

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
    public  boolean update(ProjectSignUp update){
        String sql = "UPDATE account_client SET "
                + " passwords = ?"
                +"WHERE accounts = ? ";
        try(Connection conn = DbProject.getConnection(database);
                PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setString(1, update.getPassword());
            stmt.setString(2, update.getAccount());
            
            int row = stmt.executeUpdate();
            if(row == 1 ){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    public boolean information(ProjectSignUp update){
        String sql = "UPDATE account_client SET "
                + " nick_name = ? ,"
                + " phone = ? ,"
                + " address = ? "
                +"WHERE accounts = ? ";
        try(Connection conn = DbProject.getConnection(database);
                PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
            stmt.setString(1, update.getName());
            stmt.setString(2, update.getPhone());
            stmt.setString(3, update.getAddress());
            stmt.setString(4, update.getAccount());
            
            int row = stmt.executeUpdate();
            if(row == 1 ){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    public ProjectSignUp selectinformation(ProjectSignUp update){
        String sql = "SELECT * FROM account_client WHERE accounts = ? ";
        ProjectSignUp n = new ProjectSignUp();
        try (
            Connection conn = DbProject.getConnection(database);
            PreparedStatement stmt = 
                    conn.prepareStatement(sql);
                ) {
            
            stmt.setString(1, update.getAccount());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
              
                
            } else {
                 
            }  
        } catch (Exception e) {
            
        }
        
        return n;
    }
}
