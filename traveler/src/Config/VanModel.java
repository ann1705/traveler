package config;

public class VanModel {
    private int vid; // NEW: Added to match tbl_bookings.van_id
    private String model;
    private String daily_rate;
    private String capacity;
    private String vimage;

    public VanModel() {} 

    // NEW: Getter and Setter for ID
    public int getVid() { return vid; }
    public void setVid(int vid) { this.vid = vid; }

    // Existing Getters and Setters
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getVimage() { return vimage; }
    public void setVimage(String vimage) { this.vimage = vimage; }
    public String getDaily_rate() { return daily_rate; }
    public void setDaily_rate(String daily_rate) { this.daily_rate = daily_rate; }
    public String getCapacity() { return capacity; }
    public void setCapacity(String capacity) { this.capacity = capacity; }
}