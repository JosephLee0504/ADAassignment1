package utilise;

import java.io.*;
import java.net.*;

/* Message class that defines the session message structure and specifies the session protocol */
@SuppressWarnings("serial")
public class Message implements Serializable {
	private String userId=null; //UserId
    private String password=null; //password
    /* Message type: M_LOGIN: User login message;
     * M_SUCCESS: Login succeeded;
     * M_FAULURE: Login failed;
     * M_ACK: the response message from the server to the logged in user;
     * M_MSG: Session message;
     * M_QUIT: User exit message
     */
    private String type=null; 
    
    private String text=null; //message body
    private InetAddress toAddr=null; //Target user address
    private int toPort; //Target user port
    private String targetId=null; //Target userId
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public InetAddress getToAddr() {
        return toAddr;
    }
    public void setToAddr(InetAddress toAddr) {
        this.toAddr = toAddr;
    }
    public int getToPort() {
        return toPort;
    }
    public void setToPort(int toPort) {
        this.toPort = toPort;
    }
    public String getTargetId() {
        return targetId;
    }
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }   
}
