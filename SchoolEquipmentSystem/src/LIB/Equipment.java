/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LIB;

/**
 *
 * @author Amar Pajarito
 */
public abstract class Equipment {
    protected String id;
    protected String name;
    protected String type;
    protected String condition;
    protected String location;
    protected String quantity;

    public Equipment(String id, String name, String type, String condition, String location, String quantity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.condition = condition;
        this.location = location;
        this.quantity = quantity;
    }

    public Equipment(String[] str) {
        if (str.length == 6) {
            this.id = str[0];
            this.name = str[1];
            this.type = str[2];
            this.condition = str[3];
            this.location = str[4];
            this.quantity = str[5];
        } else {
            throw new IllegalArgumentException("Array must have exactly 6 elements.");
        }
    }

    public void displayInfo() {
        System.out.println("ID: " + id + ", Name: " + name + ", Type: " + type + ", Condition: " + condition 
                           + ", Location: " + location + ", Quantity: " + quantity);
    }

    public String getId() {
        return id;
    }

    public String getCondition() {
        return condition;
    }
}

