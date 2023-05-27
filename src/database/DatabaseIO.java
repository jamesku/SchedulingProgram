package database;

import model.Customer;
import model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.UtcConversion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
        try {
            query = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, User_ID, "+
                    "Customer_ID, Contact_ID) VALUES (" +
                    "'"+ newAppointment.getApTitle()+"', "+
                    "'"+ newAppointment.getApDesc()+"', "+
                    "'"+ newAppointment.getApLocation()+"', "+
                    "'"+ newAppointment.getApType()+"', "+
                    "'"+ UtcConversion.convertLocalToUTC((newAppointment.getLocalDateTimeStart()))+"', "+
                    "'"+ UtcConversion.convertLocalToUTC((newAppointment.getLocalDateTimeEnd()))+"', "+
//                    "'"+ newAppointment.getLocalDateTimeStart()+"', "+
//                    "'"+ newAppointment.getLocalDateTimeEnd()+"', "+
                    ""+ newAppointment.getApUID()+", "+
                    "'"+ newAppointment.getApCID()+"', "+
                    "(SELECT Contact_ID FROM contacts WHERE Contact_name = '"
                    + newAppointment.getApContactName()+"'));";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            int rowsaffected = ps.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
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
        try {
            query = "UPDATE customers SET "+
                    "Customer_Name = '" + selectedCustomer.getName()+"',"+
                    "Address = '" + selectedCustomer.getAddress()+"',"+
                    "Phone = '" + selectedCustomer.getPhoneNumber()+"',"+
                    "Postal_Code = " + selectedCustomer.getPostalCode()+","+
                    "Division_ID = " +
                    "(SELECT Division_ID FROM first_level_divisions WHERE Division = '"
                            + selectedCustomer.getDivision()+"') WHERE "+
                    "Customer_ID = "+selectedCustomer.getCustID()+";";
                PreparedStatement ps = JDBC.connection.prepareStatement(query);
            int rowsaffected = ps.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**This replaces a product in the Arraylist with another part.
     * @param newAppointment the new part to be inserted
     * */
    public static void updateAppointment(Appointment newAppointment){
        try {
            query = "UPDATE appointments SET " +
                    "Title = '"+ newAppointment.getApTitle()+"', "+
                    "Description= '" + newAppointment.getApDesc()+"', "+
                    "Location= '" + newAppointment.getApLocation()+"', "+
                    "Type= '" + newAppointment.getApType()+"', "+
                    "Start= '" + UtcConversion.convertLocalToUTC(newAppointment.getLocalDateTimeStart())+"', "+
//                    "Start= '" + Timestamp.valueOf(newAppointment.getLocalDateTimeStart().atZone(ZoneId.systemDefault()))+"', "+
//                    "End= '" + Timestamp.valueOf(newAppointment.getLocalDateTimeEnd())+"', "+
//                    "Start= '" + newAppointment.getLocalDateTimeStart()+"', "+
                    "End= '" + UtcConversion.convertLocalToUTC(newAppointment.getLocalDateTimeEnd())+"', "+
                    "User_ID= "+ newAppointment.getApUID()+", "+
                    "Customer_ID= " + newAppointment.getApCID()+", "+
                    "Contact_ID =(SELECT Contact_ID FROM contacts WHERE Contact_name = '" +
                    newAppointment.getApContactName()+"') WHERE Appointment_ID =" +
                    newAppointment.getApID()+";";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            int rowsaffected = ps.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }


        }
    /**This deletes a customer from the database.
     * @param custID The customer id associated with the customer to delete.
     **/
    public static boolean deleteCustomer(int custID){
        try {
            query = "DELETE FROM customers Where Customer_ID = '"
                    + custID+"';";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            int rowsaffected = ps.executeUpdate(query);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


//    /**This returns the select list of appointments.
//     * @return selectAppointments The list of select appointments.*/
//    public static ObservableList<Appointment> checkFifteenMinutes(int userID){
//        LocalDateTime utcNow = UtcConversion.convertLocalToUTC(LocalDateTime.now());
//        ObservableList<Appointment> selectAppointments = FXCollections.observableArrayList();
//        try {
//            query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, " +
//                    "Customer_ID, User_ID, Contact_Name "+
//                    "FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID "+
//                    "WHERE User_ID = "+userID+ " & WHERE Start;";
//            PreparedStatement ps = JDBC.connection.prepareStatement(query);
//            ResultSet rs = ps.executeQuery(query);
//
//            while(rs.next()) {
//                String start = rs.getTimestamp("Start").toString();
//                String end = rs.getObject("End").toString();
//                selectAppointments.add(new Appointment(rs.getInt("Appointment_ID"),
//                        rs.getString("Title"),
//                        rs.getString("Description"), rs.getString("Location"),
//                        rs.getString("Type"),
//                        UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("Start")),
//                        UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("End")),
//                        rs.getInt("Customer_ID"),
//                        rs.getInt("User_ID"),
//                        rs.getString("Contact_Name")));
//            }
//            return selectAppointments;
//        } catch (Exception e) {
//            System.out.println(e);
//            return null;
//        }
//    }



    /**This returns the select list of appointments.
     * @return selectAppointments The list of select appointments.*/
    public static ObservableList<Appointment> getSelectAppointments(int custID){
        ObservableList<Appointment> selectAppointments = FXCollections.observableArrayList();
        try {
            query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, " +
                    "Customer_ID, User_ID, Contact_Name "+
                    "FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID "+
                    "WHERE Customer_ID = "+custID+ ";";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);

//            LocalDateTime localDate = LocalDateTime.parse(strLocalDate, formatter);
            while(rs.next()) {
                String start = rs.getTimestamp("Start").toString();
                String end = rs.getObject("End").toString();
                selectAppointments.add(new Appointment(rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"), rs.getString("Location"),
                        rs.getString("Type"),
                        UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("Start")),
//                        rs.getTimestamp("Start").toLocalDateTime(),
//                        rs.getTimestamp("End").toLocalDateTime(),
//                        (LocalDateTime) rs.getObject("Start"),
                        UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("End")),
//                        (LocalDateTime) rs.getObject("End"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getString("Contact_Name")));
            }
            return selectAppointments;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**This returns the complete list of customers.
     * @return allCustomers The complete list of customers.*/
    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> selectAppointments = FXCollections.observableArrayList();
        try {
            query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, " +
                    "Customer_ID, User_ID, Contact_Name "+
                    "FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID;";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);

//            LocalDateTime localDate = LocalDateTime.parse(strLocalDate, formatter);
            while(rs.next()) {
                String start = rs.getTimestamp("Start").toString();
                String end = rs.getObject("End").toString();
                selectAppointments.add(new Appointment(rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"), rs.getString("Location"),
                        rs.getString("Type"),
                        UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("Start")),
//                        rs.getTimestamp("Start").toLocalDateTime(),
//                        rs.getTimestamp("End").toLocalDateTime(),
//                        (LocalDateTime) rs.getObject("Start"),
                        UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("End")),
//                        (LocalDateTime) rs.getObject("End"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getString("Contact_Name")));
            }
            return selectAppointments;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    /**This deletes a product from the ObservableList.
     * @param apptID The ID of the appointment to delete.
     * @return A boolean about if the product is deleted.*/
    public static boolean deleteAppointment(int apptID){
        try {
            query = "DELETE FROM appointments Where Appointment_ID = '"
                    + apptID+"';";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            int rowsaffected = ps.executeUpdate(query);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
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
            query = "DELETE FROM appointments Where Customer_ID = '"
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

    public static int checkLogin(String userName, String password) {
        try {
            query = "Select password from client_schedule.users where user_name = '" + userName + "'";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);
            rs.next();
            if ((rs.getString("Password")).equals(password)) {
                return Integer.parseInt(rs.getString("User_ID"));
            }
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
        return 0;
    }

    public static ObservableList getCustomerCombo() {
        ObservableList<String> allCustomers = FXCollections.observableArrayList();
        try {
            query = "SELECT Customer_ID " +
                    "FROM customers;";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);
            while(rs.next()) {
                allCustomers.add(rs.getInt("Customer_ID")+"");
            }
            return allCustomers;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static ObservableList getUserCombo() {
        ObservableList<String> allUsers = FXCollections.observableArrayList();
        try {
            query = "SELECT User_ID " +
                    "FROM users;";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);
            while(rs.next()) {
                allUsers.add(rs.getInt("User_ID")+"");
            }
            return allUsers;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static ObservableList getContactCombo() {
        ObservableList<String> allContacts = FXCollections.observableArrayList();
        try {
            query = "SELECT Contact_Name " +
                    "FROM contacts;";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);
            while(rs.next()) {
                allContacts.add(rs.getString("Contact_Name"));
            }
            return allContacts;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
