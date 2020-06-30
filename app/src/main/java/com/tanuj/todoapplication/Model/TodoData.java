package com.tanuj.todoapplication.Model;

import java.io.Serializable;

public class TodoData implements Serializable {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemdescription() {
        return itemdescription;
    }

    public void setItemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
    }

    private String itemName;
    private String itemdescription;
    private int isSelect;



    public int getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(int isSelect) {
        this.isSelect = isSelect;
    }



    public TodoData() {
    }

    public TodoData(int id, String itemName,String itemdescription,int isSelect) {
        this.id = id;
        this.itemName = itemName;
        this.itemdescription=itemdescription;
        this.isSelect=isSelect;

    }


}
