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
//    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    /**Initialize list of Products */
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    /** This adds a given part to the database.
     * @param newCustomer the part to add*/

    public static void addCustomer(Customer newCustomer){
        try {
            query = "INSERT INTO customers(Customer_Name, Address, Postal_Code, "+
                    "Phone, Division_ID) VALUES (" +
                    "'"+ newCustomer.getName()+"',"+
                    "'"+ newCustomer.getAddress()+"',"+
                    "'"+ newCustomer.getPostalCode()+"',"+
                    "'"+ newCustomer.getPhoneNumber()+"',"+
                    "(SELECT Division_ID FROM first_level_divisions WHERE Division = '"
                    + newCustomer.getDivision()+"'));";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            int rowsaffected = ps.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
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
    /**This deletes a customer from the database.
     * @param custID The customer id associated with the customer to delete.
     **/
    public static void deleteCustomer(int custID){
        try {
            query = "DELETE * FROM customers Where Customer_ID = '"
                    + custID+"';";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            int rowsaffected = ps.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**This deletes a product from the ObservableList.
     * @param selectedAppointment The product to delete.
     * @return A boolean about if the product is deleted.*/
    public static boolean deleteAppointment(Appointment selectedAppointment){
//        boolean remove = allProducts.remove(selectedProduct);
//        return remove;
        return true;
    }
/**This returns the complete list of customers.
 * @return allCustomers The complete list of customers.*/
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            query = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Country, Division " +
                    "FROM customers " +
                    "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                    "JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID;";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);
            while(rs.next()) {
                allCustomers.add(new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"),
                        rs.getString("Address"), rs.getString("Postal_Code"),
                        rs.getString("Phone"),rs.getString("Country"),
                        rs.getString("Division")));
                System.out.println(allCustomers);
            }
            return allCustomers;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    /**This returns the complete list of products.
     * @return allProducts The complete list of products.*/
    public static void deleteAssociatedAppointments(int custID){
        try {
            query = "DELETE * FROM appointments Where Customer_ID = '"
                    + custID+"';";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            int rowsaffected = ps.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ObservableList<String> getDivisionCombo(String country){
        ObservableList<String> allDivisions = FXCollections.observableArrayList();
        try {
            query = "SELECT Division " +
                    "FROM first_level_divisions WHERE Country_ID = "+
                    "(SELECT Country_ID FROM countries WHERE Country = '"+country+"');";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);
            while(rs.next()) {
                allDivisions.add(rs.getString("Division"));
            }
            return allDivisions;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static ObservableList<String> getCountryCombo(){
        ObservableList<String> allCountries = FXCollections.observableArrayList();
        try {
            query = "SELECT Country " +
                    "FROM countries;";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);
            while(rs.next()) {
                allCountries.add(rs.getString("Country"));
            }
            return allCountries;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
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
