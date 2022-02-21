package Design;

/**
 * Created In House Parts
 * @author Max Perrigo
 */
public class InHouse extends Part {

    private int machineId;
    
    /**
     * Constructor for In House items
     * @param id id
     * @param name name
     * @param price price
     * @param stock stock
     * @param min min
     * @param max max
     * @param machineId Machine Id
     */

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        setMachineID(machineId);
    }
    
    /**
     * Method to return the Machine ID
     * @return Machine ID
     */

    public int getMachineID() {
        return machineId;
    }
    /**
     * sets the Machine id
     * @param id id
     */

    public void setMachineID(int id) {
        this.machineId = id;
    }

}
