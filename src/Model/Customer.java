package model;

import javafx.collections.ObservableList;

/**Class to define a Customer.*/
public class Customer {

    private int custID;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private String country;
    private String division;
    private ObservableList<Appointment> associatedAppointments;

    /**An object constructor for customers.
     * @param custID the customer ID
     * @param name the customer name
     * @param address the customer address
     * @param postalCode the customer postal code
     * @param phoneNumber the customer phone Number
     * @param country the customer country
     * @param division the customer division
     * */
    public Customer(int custID, String name, String address,String postalCode, String phoneNumber,
                    String country, String division) {
        this.custID = custID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.division = division;
    }

    /**
     * @param custID the customer id to set
     */
    public void setId(int custID) {
        this.custID = custID;
    }
    /**
     * @return the customer ID
     */
    public int getCustID() {
        return custID;
    }
    /**
     * @param name the customer name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the customer name
     */
    public String getName() {
        return name;
    }
    /**
     * @param address the customer address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * @return the customer address
     */
    public String getAddress() {
        return address;
    }
    /**
     * @param postalCode the customer postal code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * @return the customer postal code
     */
    public String getPostalCode() {
        return postalCode;
    }
    /**
     * @param phoneNumber the customer phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**
     * @return the customer phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * @param country the customer country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * @return the customer country
     */
    public String getCountry() {
        return country;
    }
    /**
     * @param division the customer division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }
    /**
     * @return the customer division
     */
    public String getDivision() {
        return division;
    }
    /**
     * @param associatedAppointments the list of associated appointments to set
     */
    public void setAssociatedAppointments(ObservableList<Appointment> associatedAppointments) {
        this.associatedAppointments = associatedAppointments;
    }

    /**adds the associated appointment to the customer
    * @param app The appointment to associate
    * */
    public void addAssociatedAppointment(Appointment app){
        associatedAppointments.add(app);
    }

    /**removes the associated appointment from the customer
     * @param selectedAssociatedApp The appointment to disassociate
     * @return boolean if appointment is deleted.
     * */
    public boolean deleteAssociatedAppointment(Appointment selectedAssociatedApp){
        associatedAppointments.remove(selectedAssociatedApp);
        return true;
    }

    /**
     * @return all associated appointment
     */
    public ObservableList<Appointment> getAllAssociatedAppointments(){
        return associatedAppointments;
    }

}
