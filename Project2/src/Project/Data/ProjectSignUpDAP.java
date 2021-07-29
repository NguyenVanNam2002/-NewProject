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
public interface ProjectSignUpDAP {
    //admin
    public boolean LoginAdmin(ProjectSignUp account);
    // client
    public  ObservableList<ProjectSignUp> selectAll();
    public ProjectSignUp insert(ProjectSignUp insert);
    public boolean Login(ProjectSignUp account);
    public boolean delete(ProjectSignUp delete);
    public boolean update(ProjectSignUp update);
    public boolean information(ProjectSignUp update);
    public ProjectSignUp selectinformation(ProjectSignUp update);
}
