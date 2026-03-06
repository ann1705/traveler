
package config;


public class Singleton {
    private static Singleton instance;
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String status;
    private String address;
    private String contact; // maps to phone_no in DB
    private String gender;
    private String type;
    private String image;
    private int vid;
    private String model;
    private String plate_no;
    private String capacity;
    private String daily_rate;
    private String category;
    private String v_status;
    private String v_image;
    private int bid;
    private String destination;
    private String s_date;
    private String e_date;
    private String t_price;
    private String b_status; 
    private int sid;
    private String s_loc;
    private String s_price;
    
    

    private Singleton() {
        // Private constructor to prevent instantiation
    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
    
    public static boolean isInstanceEmpty() {
        return instance == null;
    }

   public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
      public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
     public String getGender() {
        return gender;
     }
    public void setGender (String gender) {
        this.gender = gender;
    }
    
    public String getAddress() {
        return address;
     }
     
    public void setAddress(String address){
        this.address = address;
    }
    
    public String getImage() {
        return image;
     }
     
    public void setImage(String image){
        this.image = image;
    }
    
    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }
    
    public String getModel() {
        return model;
     }
     
    public void setModel(String model){
        this.model = model;
    }
    
    public String getPlate_no() {
        return plate_no;
     }
     
    public void setPlate_no(String plate_no){
        this.plate_no = plate_no;
    }
    
     public String getCapacity() {
        return capacity;
     }
     
    public void setCapacity (String capacity){
        this.capacity = capacity;
    }
    
      public String getDaily_rate() {
        return daily_rate;
     }
     
    public void setDaily_rate (String daily_rate){
        this.daily_rate = daily_rate;
    }
    
    public String setCategory() {
        return category;
     }
     
    public void setCategory (String category){
        this.category = category;
    }

    public String getVstatus() {
        return v_status;
     }
     
    public void setVstatus (String v_status){
        this.v_status = v_status;
    }
    
    public String getVimage() {
        return v_image;
     }
     
    public void setVimage (String v_image){
        this.v_image = v_image;
    }    
            
         
    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }
    
    public String getDestination() {
        return destination;
     }
     
    public void setDestination (String destination){
        this.destination = destination;
    }
    
    public String getSdate() {
        return s_date;
     }
     
    public void setSdate (String s_date){
        this.s_date = s_date;
    }
    
    public String getEdate() {
        return e_date;
     }
     
    public void setEdate (String e_date){
        this.e_date = e_date;
    }

    public String getTprice() {
        return t_price;
     }
     
    public void setTprice (String t_price){
        this.t_price = t_price;
        
    }
    
    public String getBstatus() {
        return b_status;
     }
     
    public void setBstatus (String b_status){
        this.b_status = b_status;
        
    }
    
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }
    
    public String getS_loc() {
        return s_loc;
     }
     
    public void setS_loc (String s_loc){
        this.s_loc = s_loc;
        
    }
    
    public String getS_price() {
        return s_price;
     }
     
    public void setS_price (String s_price){
        this.s_price = s_price;
        
    }
    
}



