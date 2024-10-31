/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LIB;

/**
 *
 * @author Amar Pajarito
 */

public class Room {
    private String name;
    private String number; 
    private String floorId;
    private int floorNumber;

    public Room(String name, String number, String floorId, int floorNumber) {
        this.name = name;
        this.number = number;
        this.floorId = floorId;
        this.floorNumber = floorNumber;
    }

    public String getRoom() {
        return number; 
    }
}
