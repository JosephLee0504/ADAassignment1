package utilise;

import java.net.*;

public class User {
	/* The User class, which defines the User object, 
	 * contains the User name and the received message
	 */
	private String userId=null; //UserId
    private DatagramPacket packet=null; //message
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public DatagramPacket getPacket() {
        return packet;
    }
    public void setPacket(DatagramPacket packet) {
        this.packet = packet;
    }   
}
