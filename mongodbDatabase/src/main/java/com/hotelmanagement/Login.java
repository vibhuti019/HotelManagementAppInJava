package mongodbDatabase.src.main.java.com.hotelmanagement;

public class Login {
    
    private String userID;
    private String Password;
    private boolean IsAdmin;

    public String getUserId(){
        return userID;
    }

    public void setUserId(String userId){
        this.userID = userId;
    }

    public String getPassword(){
        return Password;
    }

    public void setPassword(String password){
        this.Password = password;
    }

    public boolean getIsAdmin(){
        return IsAdmin;
    }

    public void setIsAdmin(boolean IsAdmin){
        this.IsAdmin = IsAdmin;
    }

    
    

    



}
