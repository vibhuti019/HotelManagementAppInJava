package mongodbDatabase.src.main.java.com.hotelmanagement;

public class RoomDetails {
    
    private String roomNo;
    private int floor;
    private boolean isVerified;
    private String allotedTo;

    public String getRoomNo(){
        return roomNo;
    }

    public void setRoomNo(String RoomNo){
        this.roomNo = RoomNo;
    }

    public int getFloor(){
        return floor;
    }

    public void setFloor(int floor){
        this.floor = floor;
    }

    public boolean getIsVerified(){
        return isVerified;
    }

    public void setLastName(boolean isVerified){
        this.isVerified = isVerified;
    }


    public String getAllotedTo(){
        return allotedTo;
    }

    public void setAllotedTo(String AllotedTo){
        this.allotedTo = AllotedTo;
    }


}
