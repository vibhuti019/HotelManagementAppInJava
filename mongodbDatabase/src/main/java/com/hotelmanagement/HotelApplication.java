package mongodbDatabase.src.main.java.com.hotelmanagement;
import com.mongodb.Block;
import com.mongodb.MongoClient;  
import com.mongodb.client.MongoCollection;  
import com.mongodb.client.MongoDatabase;
import com.oracle.truffle.js.builtins.helper.ReplaceStringParser.Consumer;

import org.bson.Document; 
import static com.mongodb.client.model.Filters.*;
import com.google.gson.Gson;
import mongodbDatabase.src.main.java.com.hotelmanagement.Login;
import mongodbDatabase.src.main.java.com.hotelmanagement.RoomDetails;
import mongodbDatabase.src.main.java.com.hotelmanagement.UserDetails;
import java.util.*;



public class HotelApplication {
    public static void main(String[] args){  

        HotelApplication functions = new HotelApplication();
        

        while(functions.printMenu()){

        }


    } 


    public boolean printMenu(){

        HotelApplication functions = new HotelApplication();
        Scanner sc= new Scanner(System.in);
        

        System.out.println("             Hotel Management App : By RAGHAV  \n\n");
        System.out.println("1) Login ");
        System.out.println("2) Register");
        System.out.print("Enter Choice: ");
    
        int choice= sc.nextInt();

        switch(choice){
            case 1: {
                System.out.print("Enter UserID: ");
                String UserID = sc.nextLine();
                System.out.print("Enter Password: ");
                String Password = sc.nextLine();
                switch(functions.loginUser(UserID, Password)){
                    case 1:{
                        functions.viewUsersByUserId(UserID);
                        System.out.print("Enter 1) Book Room");
                        System.out.print("Enter 2) View Booking");
                        System.out.print("Enter 3) Change A Booking");
                        System.out.print("Enter 4) Cancel A Booking");
                        int choice2 = sc.nextInt();
                        switch(choice2){
                            case 1: {
                                functions.viewUnbookedRooms();
                                System.out.print("Enter Room No: ");
                                String RoomNo = sc.nextLine();
                                functions.bookRoom(RoomNo, UserID);
                                break;
                            }
                            case 2:{
                                functions.viewUsersByUserId(UserID);
                                break;
                            }
                            case 3:{
                                functions.viewBookingByUserId(UserID);
                                System.out.print("Enter Room No To Change: ");
                                String OldRoom = sc.nextLine();
                                functions.viewUnbookedRooms();
                                System.out.print("Enter New Room No: ");
                                String NewRoom = sc.nextLine();
                                functions.changeBooking(OldRoom, NewRoom, UserID);
                                break;
                            }
                            case 4:{
                                functions.viewBookingByUserId(UserID);
                                System.out.print("Enter Room No To Cancel: ");
                                String RoomNo = sc.nextLine();
                                functions.cancelBooking(RoomNo);
                                break;
                            }
                        }
                        break;
                    }
                    case 2:{
                        System.out.print("Enter 1) View All Booked Rooms");
                        System.out.print("Enter 2) View Newly Booked Rooms");
                        System.out.print("Enter 3) Add Room");
                        int choice2 = sc.nextInt();
                        switch(choice2){
                            case 1: {
                                functions.viewAllBookedRooms();
                                break;
                            }
                            case 2:{
                                functions.viewBookingByVerifyStatus();
                                System.out.println("Confirm Any Room: ");
                                String RoomNo = sc.nextLine();
                                functions.verifyRoom(RoomNo, true);
                                break;
                            }
                            case 3:{
                                System.out.print("Enter Room No :");
                                String RoomNo = sc.nextLine();
                                System.out.print("Enter Floor :");
                                int Floor = sc.nextInt();
                                functions.addRoom(RoomNo, Floor);
                                break;
                            }
                            default:{
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 2: {
                System.out.print("Enter User ID : ");
                String UserID = sc.nextLine();
                System.out.print("Enter Password :");
                String Password = sc.nextLine();
                System.out.print("Enter First Name : ");
                String FirstName = sc.nextLine();
                System.out.print("Enter Last Name :");
                String LastName = sc.nextLine();
                System.out.print("Enter Phone :");
                String Phone = sc.nextLine();
                System.out.print("Enter Email :");
                String Email = sc.nextLine();
                System.out.println("Is Admin (Y/N):");
                char IsAdmin= sc.next().charAt(0);
                if(IsAdmin == 'Y'){
                    functions.registerUser(UserID, Password, true, FirstName, LastName, Phone, Email);
                }else if(IsAdmin == 'N' || true){
                    functions.registerUser(UserID, Password, false, FirstName, LastName, Phone, Email);
                }
                break;
            }
            default:{
                break;
            }
        }
        System.out.println("Do You Want To Repeat? (Y/N):");
        char choice3= sc.next().charAt(0);
                
        if(choice3 == 'Y'){
            return true;
        }else if(choice3 == 'N' || true){
            return false;
        }
        return false;
    }

    public boolean registerUser(String userId, String Password, boolean IsAdmin, String firstName,String lastName, String phone, String email){

        try{  
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );  

            MongoDatabase HotelMainDatabase = mongoClient.getDatabase("HotelManagement");

            MongoCollection<Document> LoginTable = HotelMainDatabase.getCollection("LoginData");
            MongoCollection<Document> UserDetailsTable = HotelMainDatabase.getCollection("UserDetails");

            try{
                Document UserVerify = LoginTable.find(eq("userID", 1)).first();
                String UserVerifyJson = UserVerify.toJson(); 
                Login login = new Gson().fromJson(UserVerifyJson, Login.class); 

                return false;
            }
            catch(Exception e){

                Document docLoginTable = new Document("userID",userId);
                docLoginTable.append("Password", Password); 
                docLoginTable.append("IsAdmin",IsAdmin);  

                Document docUserDetailsTable = new Document("userID", userId);
                docUserDetailsTable.append("FirstName", firstName);  
                docUserDetailsTable.append("LastName", lastName);
                docUserDetailsTable.append("Phone", phone);
                docUserDetailsTable.append("Email", email); 
                
                LoginTable.insertOne(docLoginTable);  
                UserDetailsTable.insertOne(docUserDetailsTable);

                return true;

            }
            
            
            
        }catch(Exception e){

            return false;

        }  
    }

    public int loginUser(String userId, String Password){

        try{  
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );  

            MongoDatabase HotelMainDatabase = mongoClient.getDatabase("HotelManagement");

            MongoCollection<Document> LoginTable = HotelMainDatabase.getCollection("LoginData");

            try{
                Document UserVerify = LoginTable.find(eq("userID", 1)).first();
                String UserVerifyJson = UserVerify.toJson(); 
                Login login = new Gson().fromJson(UserVerifyJson, Login.class); 

                if(login.getPassword().equals(Password)){
                    if(login.getIsAdmin()){
                        return 2;
                    }
                    return 1;
                }

                return 0;
            }
            catch(Exception e){
                return 0;
            }
            
            
        }catch(Exception e){

            return 0;

        }  
        
        
        
    }

    public boolean removeUser(String userId){

        try{  
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );  

            MongoDatabase HotelMainDatabase = mongoClient.getDatabase("HotelManagement");

            MongoCollection<Document> LoginTable = HotelMainDatabase.getCollection("LoginData");
            MongoCollection<Document> UserDetailsTable = HotelMainDatabase.getCollection("UserDetails");

            LoginTable.deleteOne(eq("userID",userId));
            UserDetailsTable.deleteOne(eq("userID",userId));

            return true;
        }catch(Exception e) {
            return false;
        }
    
    }

    public boolean addRoom(String RoomNo,int Floor){

        try{  
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );  

            MongoDatabase HotelMainDatabase = mongoClient.getDatabase("HotelManagement");

            MongoCollection<Document> RoomDetailsTable = HotelMainDatabase.getCollection("RoomDetails");
            
            try{
                Document Room = RoomDetailsTable.find(eq("userID", 1)).first();
                String RoomJson = Room.toJson(); 
                RoomDetails roomDetails = new Gson().fromJson(RoomJson, RoomDetails.class); 

                return false;
            }
            catch(Exception e){

                
                Document docRoomDetailsTable = new Document("Room No", RoomNo);
                docRoomDetailsTable.append("Floor", Floor);  
                docRoomDetailsTable.append("IsAllotedTo", "");  
                
                RoomDetailsTable.insertOne(docRoomDetailsTable);  

                return true;

            }
            
            
            
        }catch(Exception e){

            return false;

        }  
    }

    public boolean bookRoom(String RoomNo, String userId){

        try{  
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );  

            MongoDatabase HotelMainDatabase = mongoClient.getDatabase("HotelManagement");

            MongoCollection<Document> RoomDetailsTable = HotelMainDatabase.getCollection("RoomDetails");
            
            Document docRoomDetailsTable = new Document("Room No", RoomNo);
            docRoomDetailsTable.append("IsAllotedTo", userId);  
                
            RoomDetailsTable.updateOne(eq("RoomNo", RoomNo), docRoomDetailsTable);
            
            return true;
        }catch(Exception e){

        }

        return false;

    }

    public boolean verifyRoom(String RoomNo, boolean verify){

        try{  
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );  

            MongoDatabase HotelMainDatabase = mongoClient.getDatabase("HotelManagement");

            MongoCollection<Document> RoomDetailsTable = HotelMainDatabase.getCollection("RoomDetails");
            
            Document docRoomDetailsTable = new Document("Room No", RoomNo);
            docRoomDetailsTable.append("IsVerified", verify);  
                
            RoomDetailsTable.updateOne(eq("RoomNo", RoomNo), docRoomDetailsTable);

            return true;
        }catch(Exception e){

        }

        return false;

    }

    public boolean changeBooking(String OldRoom, String NewRoom, String Userid){
        
        try{  
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );  

            MongoDatabase HotelMainDatabase = mongoClient.getDatabase("HotelManagement");

            MongoCollection<Document> RoomDetailsTable = HotelMainDatabase.getCollection("RoomDetails");
            
            Document docRoomDetailsTable = new Document("Room No", OldRoom);
            docRoomDetailsTable.append("IsVerified", false);
            docRoomDetailsTable.append("allotedTo", "");  

            RoomDetailsTable.updateOne(eq("RoomNo", OldRoom), docRoomDetailsTable);
            docRoomDetailsTable = new Document("allotedTo", Userid);
            RoomDetailsTable.updateOne(eq("RoomNo", NewRoom), docRoomDetailsTable);

            return true;

        }catch(Exception e){

        }
        
        
        return false;
    }

    public boolean cancelBooking(String RoomNo){
        
        try{  
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );  

            MongoDatabase HotelMainDatabase = mongoClient.getDatabase("HotelManagement");

            MongoCollection<Document> RoomDetailsTable = HotelMainDatabase.getCollection("RoomDetails");
            
            Document docRoomDetailsTable = new Document("Room No", OldRoom);
            docRoomDetailsTable.append("IsVerified", false);
            docRoomDetailsTable.append("allotedTo", "");  

            RoomDetailsTable.updateOne(eq("RoomNo", RoomNo), docRoomDetailsTable);
            
            return true;

        }catch(Exception e){

        }
        
        
        return false;
    }

    public void viewBookingByUserId(String Userid){

        HotelApplication functions = new HotelApplication();
        

        try{  
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );  

            MongoDatabase HotelMainDatabase = mongoClient.getDatabase("HotelManagement");

            MongoCollection<Document> RoomDetailsTable = HotelMainDatabase.getCollection("RoomDetails");

            try{
                RoomDetailsTable.find(eq("IsAllotedTo", Userid)).forEach(printBlock);
            }
            catch(Exception e){
            }
            
            
        }catch(Exception e){

        }
        
    }

    Block<Document> printBlock = new Block<Document>() {
        @Override
        public void apply(final Document document) {
            String documentJSON = document.toJson();
            RoomDetails room = new Gson().fromJson(documentJSON, RoomDetails.class); 
            System.out.println("Room Floor: "+ room.getFloor()+ "Room No: "+room.getRoomNo());
        }
    };

    public void viewBookingByVerifyStatus(){


        try{  
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );  

            MongoDatabase HotelMainDatabase = mongoClient.getDatabase("HotelManagement");

            MongoCollection<Document> RoomDetailsTable = HotelMainDatabase.getCollection("RoomDetails");

            try{
                RoomDetailsTable.find(eq("IsVerified", false)).forEach(printBlockWithUser);
            }
            catch(Exception e){
            }
            
            
        }catch(Exception e){

        }
        
    }

    Block<Document> printBlockWithUser= new Block<Document>() {
        @Override
        public void apply(final Document document) {
            String documentJSON = document.toJson();
            RoomDetails room = new Gson().fromJson(documentJSON, RoomDetails.class); 
            System.out.println("Room Floor: "+ room.getFloor()+ "Room No: "+room.getRoomNo()+"Booked By: "+room.getAllotedTo());
        }
   };


   public void viewUsersByUserId(String UserId){


        try{  
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );  

            MongoDatabase HotelMainDatabase = mongoClient.getDatabase("HotelManagement");

            MongoCollection<Document> UserDetailsTable = HotelMainDatabase.getCollection("UserDetails");

            try{
                UserDetailsTable.find(eq("UserID", UserId)).forEach(printBlockUser);
            }
            catch(Exception e){
            }
            
            
        }catch(Exception e){

        }
        
    }

    Block<Document> printBlockUser= new Block<Document>() {
        @Override
        public void apply(final Document document) {
            String documentJSON = document.toJson();
            UserDetails User = new Gson().fromJson(documentJSON, UserDetails.class); 
            System.out.println("User FirstName: "+ User.getFirstName()+ "User LastName: "+User.getLastName()+"User Phone: "+User.getPhone()+"User Email: "+User.getEmail());
        }
    };

    
    public void viewUnbookedRooms(){


        try{  
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );  

            MongoDatabase HotelMainDatabase = mongoClient.getDatabase("HotelManagement");

            MongoCollection<Document> RoomDetailsTable = HotelMainDatabase.getCollection("RoomDetails");

            try{
                RoomDetailsTable.find().forEach(printBlockUnbooked);
            }
            catch(Exception e){
            }
            
            
        }catch(Exception e){

        }
        
    }
    Block<Document> printBlockUnbooked= new Block<Document>() {
        @Override
        public void apply(final Document document) {
            String documentJSON = document.toJson();
            RoomDetails room = new Gson().fromJson(documentJSON, RoomDetails.class); 
            if(room.getAllotedTo().length() == 0){
                System.out.println("Room Floor: "+ room.getFloor()+ "Room No: "+room.getRoomNo());
            }
        }
    };

    public void viewAllBookedRooms(){


        try{  
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );  

            MongoDatabase HotelMainDatabase = mongoClient.getDatabase("HotelManagement");

            MongoCollection<Document> RoomDetailsTable = HotelMainDatabase.getCollection("RoomDetails");

            try{
                RoomDetailsTable.find().forEach(printBlockAllBooked);
            }
            catch(Exception e){
            }
            
            
        }catch(Exception e){

        }
        
    }

    Block<Document> printBlockAllBooked= new Block<Document>() {
        @Override
        public void apply(final Document document) {
            String documentJSON = document.toJson();
            RoomDetails room = new Gson().fromJson(documentJSON, RoomDetails.class); 
            if(room.getAllotedTo().length() > 0){
                System.out.println("Room Floor: "+ room.getFloor()+ "Room No: "+room.getRoomNo());
            }
        }
    };



    
}
