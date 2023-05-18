package database;

import model.Customer;
import model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class manages the inventory of products and parts. It allows products and parts to be added
 * deleted, found, replaced, associated and disassociated and can return a full list of either set.*/
public abstract class DatabaseIO {

    /**A String for the sql query*/
    private static String query;

    /**Initialize lists of Parts*/
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    /**Initialize list of Products */
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    /** This adds a given part to the database.
     * @param newCustomer the part to add*/
    public static void addCustomer(Customer newCustomer){
//        allParts.add(newPart);
//        //trouble 1: filling test data
//        System.out.println(newPart.getName());
    }
    /** This adds an appointment to the list of appointments.
     * @param newAppointment the part to add*/
    public static void addAppointment(Appointment newAppointment){

//        allProducts.add(newProduct);
    }
    /** This finds a given part in the list of parts based on an id number.
     * @param customerId the part to add
     * @return p the found part*/
    public static Customer lookupCustomer(int customerId){
//        for(Part p : allParts) {
//            if (p.getId() == partId) {
//                return p;
//            }
//        }
        return null;
    }
    /** This finds a given product in the list of parts based on an id number.
     * @param appointmentId the appointment to add
     * @return p the found part*/
    public static Appointment lookupAppointment(int appointmentId){
//        for(Product p : allProducts) {
//            if (p.getId() == productId) {
//                return p;
//            }
//        }
        return null;
    }

    /** This finds one or more appointments in the list of appointments based on a String.
     * @param appointmentName the part to add
     * @return an ObservableList of namedParts of the found appointments*/
    public static ObservableList<Appointment> lookupAppointment(String appointmentName){
        ObservableList<Appointment> namedParts = FXCollections.observableArrayList();
//        for (Part p: allParts ){
//            if (p.getName().contains(partName)){
//                namedParts.add(p);
//            }
//        }
        return namedParts;
    }

    /** This finds one or more customers in the list of customers based on a String.
     * @param customerName the customer to add
     * @return an ObservableList of namedCustomers of the found customers*/
    public static ObservableList<Customer> lookupCustomer(String customerName){
        ObservableList<Customer> namedProducts = FXCollections.observableArrayList();
//        for (Product p: allProducts ){
//            if (p.getName().contains(productName)){
//                namedProducts.add(p);
//            }
//        }
        return namedProducts;
    }
/**This replaces a customer in the Arraylist with another customer.
 * @param selectedCustomer the new part to be inserted
 * */
    public static void updateCustomer(Customer selectedCustomer){

//        allCustomers.set(index, selectedPart);
    }
    /**This replaces a product in the Arraylist with another part.
     * @param newAppointment the new part to be inserted
     * */
    public static void updateAppointment(Appointment newAppointment){
//        allAppointments.set(index, newAppointment);
        }
    /**This deletes a part from the ObservableList.
     * @param selectedCustomer The part to delete.
     * @return A boolean about if the part is deleted.*/
    public static boolean deleteCustomer(Customer selectedCustomer){
//        boolean remove = allParts.remove(selectedPart);
//        return remove;
        return true;
    }
    /**This deletes a product from the ObservableList.
     * @param selectedAppointment The product to delete.
     * @return A boolean about if the product is deleted.*/
    public static boolean deleteAppointment(Appointment selectedAppointment){
//        boolean remove = allProducts.remove(selectedProduct);
//        return remove;
        return true;
    }
/**This returns the complete list of parts.
 * @return allParts The complete list of parts.*/
    public static ObservableList<Customer> getAllCustomers(){

        return allCustomers;
    }
    /**This returns the complete list of products.
     * @return allProducts The complete list of products.*/
    public static ObservableList<Appointment> getAllAppointments(){

        return allAppointments;
    }

    public static ObservableList<String> getDivisionCombo(String divison){
        ObservableList<String>division = null;
        return division;
    }

    public static ObservableList<String> getCountryCombo(){
        ObservableList<String>countryCombo = null;
        return countryCombo;
    }

    public static boolean checkLogin(String userName, String password) {
        try {
            query = "Select password from client_schedule.users where user_name = '" + userName + "'";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);
            rs.next();
            if ((rs.getString("Password")).equals(password)) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return false;
    }
}
