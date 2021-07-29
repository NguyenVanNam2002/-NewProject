/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.Data;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author icom
 */
public class Category {
    private ObjectProperty<Integer> CatID;
    private StringProperty Cat_name;

    public Category() {
        CatID = new SimpleObjectProperty<>(null);
        Cat_name = new SimpleStringProperty();
    }

    public Integer getCatID() {
        return CatID.get();
    }

    public String getCat_name() {
        return Cat_name.get();
    }

    public void setCatID(int CatID) {
        this.CatID.set(CatID);
    }

    public void setCat_name(String Cat_name) {
        this.Cat_name.set(Cat_name);
    }


    public ObjectProperty<Integer> getCatIDProperty() {
        return this.CatID;
    }

    public StringProperty getCat_nameProperty() {
        return this.Cat_name;
    }
   

   }
