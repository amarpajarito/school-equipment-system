/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LIB;

/**
 *
 * @author Amar Pajarito
 */
public class ElectricFan extends Equipment {

    public ElectricFan(String id, String name, String type, String condition, String location, String quantity) {
        super(id, name, type, condition, location, quantity);
    }

    public ElectricFan(String[] str) {
        super(str);
    }
}


