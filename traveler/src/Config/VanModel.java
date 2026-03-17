package config;

public class VanModel {
    private int vid;
    private String model;
    private String daily_rate;
    private String capacity;
    private String vimage;

    // 1. Default Constructor (Required for some tools)
    public VanModel() {} 

    // 2. THE FIX: The Overloaded Constructor
    // This MUST have 5 parameters to match your database query
    public VanModel(int vid, String model, String daily_rate, String capacity, String vimage) {
        this.vid = vid;
        this.model = model;
        this.daily_rate = daily_rate;
        this.capacity = capacity;
        this.vimage = vimage;
    }

    // Getters and Setters
    public int getVid() { return vid; }
    public void setVid(int vid) { this.vid = vid; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getVimage() { return vimage; }
    public void setVimage(String vimage) { this.vimage = vimage; }
    public String getDaily_rate() { return daily_rate; }
    public void setDaily_rate(String daily_rate) { this.daily_rate = daily_rate; }
    public String getCapacity() { return capacity; }
    public void setCapacity(String capacity) { this.capacity = capacity; }
    
    
    // Add this inside your VanModel class in the config package
public double getRate() {
    try {
        // Removes currency symbols and commas before converting to double
        return Double.parseDouble(daily_rate.replace("₱", "").replace(",", "").trim());
    } catch (Exception e) {
        return 0.0;
    }
}
}

