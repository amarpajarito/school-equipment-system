/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LIB;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
/**
 *
 * @author Amar Pajarito
 */
public class Room {
    private String name;
    private String number;
    private String floorId;
    private int floorNumber;
    private HashMap<String, Equipment> hashEq = new HashMap<>();
    
    public Room(String name, String number, String floorId, int floorNumber) {
        this.name = name;
        this.number = number;
        this.floorId = floorId;
        this.floorNumber = floorNumber;
    }

    public void addEquipment(Equipment equipment) {
        hashEq.put(equipment.getId(), equipment);
    }

    public void listAllEquipment() {
        System.out.println("Equipment in Room: " + name);
        for (Equipment equipment : hashEq.values()) {
            equipment.displayInfo();
        }
    }

    public void listEquipmentToReplace() {
        System.out.println("Equipment in Room: " + name + " Needing Replacement:");
        for (Equipment equipment : hashEq.values()) {
            if (equipment.getCondition().equals("for replacement") || equipment.getCondition().equals("lost")) {
                equipment.displayInfo();
            }
        }
    }

    public int getEquipmentCount() {
        return hashEq.size();
    }

    // Getters for room details
    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getFloorId() {
        return floorId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}