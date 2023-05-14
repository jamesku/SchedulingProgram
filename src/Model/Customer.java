package Model;

import javafx.collections.ObservableList;

/**Class to define a Customer.*/
public class Customer {
    private ObservableList<Appointment> associatedAppointments;
   private int id;
   private String name;
   private double price;
   private int stock;
   private int min;
   private int max;

    /**An object part.
     * @param max max
     * @param min min
     * @param id part id
     * @param name part name
     * @param price part price
     * @param stock inventory
     * @param associatedParts list of parts*/
    public Customer(ObservableList<Appointment> associatedAppointments, int id, String name, double price, int stock, int min, int max) {
        this.associatedAppointments = associatedAppointments;
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }
    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }
    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }
    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

/**adds the associated appointment to the customer
 * @param app The appointment to associate
 * */
    public void addAssociatedPart(Appointment app){
        associatedAppointments.add(app);
    }

    /**removes the associated appointment from the customer
     * @param selectedAssociatedApp The appointment to disassociate
     * @return boolean if appointment is deleted.
     * */
    public boolean deleteAssociatedPart(Appointment selectedAssociatedApp){
        associatedAppointments.remove(selectedAssociatedApp);
        return true;

    }
    /**
     * @return all associated parts
     */
    public ObservableList<Appointment> getAllAssociatedParts(){
        return associatedAppointments;
    }

}
