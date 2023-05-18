package model;


import java.time.LocalDateTime;

/**
 *Class to define an Appointment
 * @author James Kuhr
 */
public class Appointment {

    private int apID;
    private String apTitle;
    private String apDesc;
    private String apLocation;
    private LocalDateTime localDateTimeStart;
    private LocalDateTime localDateTimeEnd;
    private int apUID;
    private int apCID;

    /**An object part.
     * @param apID appointment ID
     * @param apTitle appointment title
     * @param apDesc appointment description
     * @param apLocation appointment location
     * @param localDateTimeStart appointment start
     * @param localDateTimeEnd appointment end
     * @param apUID appointment user id
     * @param apCID appointment customer id*/
    public Appointment(int apID, String apTitle, String apDesc, String apLocation, LocalDateTime localDateTimeStart,
                       LocalDateTime localDateTimeEnd, int apUID, int apCID) {
        this.apID = apID;
        this.apTitle = apTitle;
        this.apDesc = apDesc;
        this.apLocation = apLocation;
        this.localDateTimeEnd = localDateTimeEnd;
        this.localDateTimeStart = localDateTimeStart;
        this.apUID = apUID;
        this.apCID = apCID;
    }

    /**
     * @return the appointment ID
     */
    public int getapID() {
        return apID;
    }

    /**
     * @param apID the appointment ID to set
     */
    public void setApID(int apID) {
        this.apID = apID;
    }

    /**
     * @return the appointment title
     */
    public String getApTitle() {
        return apTitle;
    }

    /**
     * @param apTitle the Appointment Title to set
     */
    public void setApTitle(String apTitle) {
        this.apTitle = apTitle;
    }

    /**
     * @return the appointment description
     */
    public String getApDesc() {
        return apDesc;
    }

    /**
     * @param apDesc the Appointment Description to set
     */
    public void setApDesc(String apDesc) {
        this.apDesc = apDesc;
    }

    /**
     * @return the appointment location
     */
    public String getApLocation() {
        return apLocation;
    }

    /**
     * @param apLocation the appointment location to set
     */
    public void setApLocation(String apLocation) {
        this.apLocation = apLocation;
    }

    /**
     * @return the start date and time of the appointment
     */
    public LocalDateTime getLocalDateTimeStart() {
        return localDateTimeStart;
    }

    /**
     * @param localDateTimeStart the start date and time to set
     */
    public void setLocalDateTimeStart(LocalDateTime localDateTimeStart) {
        this.localDateTimeStart = localDateTimeStart;
    }

    /**
     * @return the end date and time of the appointment
     */
    public LocalDateTime getLocalDateTimeEnd() {
        return localDateTimeEnd;
    }

    /**
     * @param localDateTimeEnd the end date and time to set
     */
    public void setLocalDateTimeEnd(LocalDateTime localDateTimeEnd) {
        this.localDateTimeEnd = localDateTimeEnd;
    }

    /**
     * @return the user ID associated with the appointment
     */
    public int getApUID() {
        return apUID;
    }

    /**
     * @param apUID the appointment user ID to set
     */
    public void setApUID(int apUID) {
        this.apUID = apUID;
    }

    /**
     * @return the customer ID associated with the appointment
     */
    public int getApCID() {
        return apCID;
    }

    /**
     * @param apCID the appointment customer ID to set
     */
    public void setApCID(int apCID) {
        this.apCID = apCID;
    }

}