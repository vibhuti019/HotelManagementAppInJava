package mongodbDatabase.src.main.java.com.hotelmanagement;

public class UserDetails {
    
    private String userID;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    public String getUserId(){
        return userID;
    }

    public void setUserId(String userId){
        this.userID = userId;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String Phone){
        this.phone = Phone;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }


}
