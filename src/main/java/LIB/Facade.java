/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LIB;

/**
 *
 * @author Amar Pajarito
 */
public class Facade implements IFacade {
    private Equipment equipment; 

    public Facade(Equipment equipment) {
        this.equipment = equipment;
    }

    @Override
    public void registerEquipment() {
        if (equipment != null) {
            equipment.register();
        } else {
            System.out.println("No equipment to register.");
        }
    }
}

