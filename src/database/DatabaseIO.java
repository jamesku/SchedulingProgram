package database;

import model.Customer;
import model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.UtcConversion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.Month;

/**This class manages the database transactions. It allows various combinations of customers and appointment
 * data to be retrieved.
 * */
public abstract class DatabaseIO {

    /**A String for the sql query*/
    private static String query;

    /** This adds a customer to the database. It gets passed a customer object and retrieves various
     * values to insert into the database.
     * @param newCustomer the customer object to add*/
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

    /** This adds an appointment to the database. It gets passed an appointment object and retrieves various
     * values to insert into the database. It converts time to UTC for storage in the database.
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

    /**This updates a customer in the database. It uses the provided customer ID to select from
     * a joined table of the customers and division_id.
     * @param selectedCustomer the customer to be updated
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

    /**This updates an appointment in the database.  It uses the provided appointment ID to select from
     * a joined table of the customers and contact_names and IDs. It converts times to UTC for storage
     * in the database.
     * @param newAppointment the new appointment to be updated
     * */
    public static void updateAppointment(Appointment newAppointment){
        try {
            query = "UPDATE appointments SET " +
                    "Title = '"+ newAppointment.getApTitle()+"', "+
                    "Description= '" + newAppointment.getApDesc()+"', "+
                    "Location= '" + newAppointment.getApLocation()+"', "+
                    "Type= '" + newAppointment.getApType()+"', "+
                    "Start= '" + UtcConversion.convertLocalToUTC(newAppointment.getLocalDateTimeStart())+"', "+
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

    /**This deletes a customer from the database based on customer_id. It returns true if successful.
     * @param custID The customer id associated with the customer to delete.
     * @return boolean if the delete is successful.
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

    /**This returns the select list of appointments based on customer ID. It uses functions to convert
     * times to UTC for storage in the database.  Additionally it populates fields in the appointment
     * object for formatted Strings of the time for viewer use.
     * @param custID the customer ID used to retrieve information.
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

            while(rs.next()) {
                String start = rs.getTimestamp("Start").toString();
                String end = rs.getObject("End").toString();
                selectAppointments.add(new Appointment(rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"), rs.getString("Location"),
                        rs.getString("Type"),
                        UtcConversion.dtFormat(UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("Start"))),
                        UtcConversion.dtFormat(UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("End"))),
                        UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("Start")),
                        UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("End")),
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

    /**This returns the select list of appointments based on the contact name. It uses functions to convert
     * times to UTC for storage in the database.  Additionally it populates fields in the appointment
     * object for formatted Strings of the time for viewer use.
     * @param contactName the contact name to index the search on.
     * @return selectAppointments The list of select appointments.*/
    public static ObservableList<Appointment> getSelectContactAppointments(String contactName){
        ObservableList<Appointment> selectAppointments = FXCollections.observableArrayList();
        try {
            query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, " +
                    "Customer_ID, User_ID, Contact_Name "+
                    "FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID "+
                    "WHERE Contact_Name = '"+contactName+ "';";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);

            while(rs.next()) {
                String start = rs.getTimestamp("Start").toString();
                String end = rs.getObject("End").toString();
                selectAppointments.add(new Appointment(rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"), rs.getString("Location"),
                        rs.getString("Type"),
                        UtcConversion.dtFormat(UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("Start"))),
                        UtcConversion.dtFormat(UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("End"))),
                        UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("Start")),
                        UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("End")),
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

    /**This returns the complete list of appointments. It uses functions to convert
     * times to UTC for storage in the database.  Additionally it populates fields in the appointment
     * object for formatted Strings of the time for viewer use.
     * @return allAppointments The complete list of appointments.*/
    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> selectAppointments = FXCollections.observableArrayList();
        try {
            query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, " +
                    "Customer_ID, User_ID, Contact_Name "+
                    "FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID;";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);

            while(rs.next()) {
                String start = rs.getTimestamp("Start").toString();
                String end = rs.getObject("End").toString();
                selectAppointments.add(new Appointment(rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"), rs.getString("Location"),
                        rs.getString("Type"),
                        UtcConversion.dtFormat(UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("Start"))),
                        UtcConversion.dtFormat(UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("End"))),
                        UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("Start")),
                        UtcConversion.convertUTCtoLocal((LocalDateTime) rs.getObject("End")),
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


    /**This deletes an appointment from the database based on appointment ID.
     * @param apptID The ID of the appointment to delete.
     * @return A boolean about if the appointment is deleted.*/
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
    /**This returns the complete list of customers from the database.
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
            }
            return allCustomers;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    /**This deletes the appointments indexed on customer id.
     * @param custID the customer ID to query on.*/
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

    /**This uses the given country value to retrieve first level division information from the database.
     * @param country the country to index to retrieve first level divisions.
     * @return allDivisions a list of all first level divisions in a given country.*/
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

    /**This retrieves countries to populate the country combobox.
     * @return allcountries the list of all countries to populate the combobox.*/
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

    /**This checks the database for a valid login/password combo.  It uses the user id to retrieve
     * the corresponding password and then checks that against the offered password value. If
     * successful it returns a user id greater than 0.  Otherwise it returns 0.
     * @param password the password
     * @param userName the username
     * @return an integer of the user ID
     * */
    public static int checkLogin(String userName, String password) {
        try {
            query = "Select password, User_ID from client_schedule.users where user_name = '" + userName + "'";
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

    /**This retrieves customer IDs to populate the combobox.
     * @return allcustomers the list of all customer IDs to populate the combobox.*/
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

    /**This retrieves user IDs to populate the combobox.
     * @return allUsers the list of all user IDs to populate the combobox.*/
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

    /**This retrieves a count based on month and type from the database.
     * @param month the month to index on
     * @param type the type to index on
     * @return the count of attributes that correspond to count and month*/
    public static int countTypeMonth(String month, String type){
        int monthNumber = Month.valueOf(month.toUpperCase()).getValue();
        try {
            query =
            "SELECT COUNT(*) AS appointment_count "+
            "FROM appointments "+
            "WHERE MONTH(Start) = '"+monthNumber+"'"+
                    "AND Type = '"+type+"';";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);
            while(rs.next()) {
                return(rs.getInt("appointment_count"));
            }
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    /**This retrieves a count based on how many customers live in a given first level division.
     * @param division the first level division to count instances of
     * @return the count of attributes that correspond to the first level division.*/
    public static int countCountryDivision(String division){
        try {
            query =
                    "SELECT COUNT(*) AS division_count "+
                            "FROM customers JOIN first_level_divisions ON "+
                            "first_level_divisions.Division_ID = customers.Division_ID "+
                            "WHERE Division = '"+division+"';";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);
            while(rs.next()) {
                return(rs.getInt("division_count"));
            }
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    /**This retrieves all contact names to populate the combobox.
     * @return allContacts the list of all contact names to populate the combobox.*/
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
