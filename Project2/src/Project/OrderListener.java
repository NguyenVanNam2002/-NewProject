/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import Project.Data.Order;
import javafx.scene.control.CheckBox;

/**
 *
 * @author Admin
 */
public interface OrderListener {
    public void onClickListener(CheckBox y ,Order order);
    public void OnDelete(Order order);
    public void Update();
}
