package qqServer;

import utilise.Message;
import utilise.Translate;
import utilise.User;
import java.io.*;
import java.net.*;
import java.util.*;

/* ReceiveMessage, the thread class that a server receives messages and processes messages */
public class ReceiveMessage extends Thread{
	private DatagramSocket serverSocket; //ServerSocket
    private DatagramPacket packet;  //message
    private List<User> userList=new ArrayList<User>(); //List of users
    private byte[] data=new byte[8096]; //An 8K byte array
    private ServerUI parentUI; //Message window
    public ReceiveMessage(DatagramSocket socket,ServerUI parentUI) {
        serverSocket=socket;
        this.parentUI=parentUI;
    }
    @Override
    public void run() {  
        while (true) { //The various messages received are processed in a loop
            try {
            packet=new DatagramPacket(data,data.length);//Construct receiving message
            serverSocket.receive(packet);//Receiving client data
            //The received data is turned into a message object
            Message msg=(Message)Translate.ByteToObject(packet.getData());
            String userId=msg.getUserId();//The current message is from the user's ID            
            if (msg.getType().equalsIgnoreCase("M_LOGIN")) { //Is M_LOGIN message 
                Message backMsg=new Message();
                //Assume that only 2000, 3000, 8000 accounts can be logged in
                if (!userId.equals("2000") && !userId.equals("3000") && !userId.equals("8000")) {//Logon unsuccessful
                    backMsg.setType("M_FAILURE");
                    byte[] buf=Translate.ObjectToByte(backMsg);
                    DatagramPacket backPacket=new DatagramPacket(buf,buf.length,packet.getAddress(),packet.getPort());//A message sent to a logged in user
                    serverSocket.send(backPacket); //send                  
                }else { //Login successful
                    backMsg.setType("M_SUCCESS");
                    byte[] buf=Translate.ObjectToByte(backMsg);
                    DatagramPacket backPacket=new DatagramPacket(buf,buf.length,packet.getAddress(),packet.getPort());//A message sent to a logged in user
                    serverSocket.send(backPacket); //send   
                    
                    User user=new User();
                    user.setUserId(userId); //UserName
                    user.setPacket(packet); //Save incoming messages
                    userList.add(user); //Add the new user to the user list
                    
                    //Update server chat room lobby
                    parentUI.txtArea.append(userId+" login!\n");
                    
                    //M_LOGIN messages are sent to all other online users and the entire user list is sent to the new logon
                    for (int i=0;i<userList.size();i++) { //Traverse the entire list of users                                      
                        //Send an M_LOGIN message to another online user
                        if (!userId.equalsIgnoreCase(userList.get(i).getUserId())){
                            DatagramPacket oldPacket=userList.get(i).getPacket(); 
                            DatagramPacket newPacket=new DatagramPacket(data,data.length,oldPacket.getAddress(),oldPacket.getPort());//Messages sent to other users
                            serverSocket.send(newPacket); //send
                        }
                        //M_ACK message is sent back to the current user to add the ith user to the current user's user list
                        Message other=new Message();
                        other.setUserId(userList.get(i).getUserId());
                        other.setType("M_ACK");
                        byte[] buffer=Translate.ObjectToByte(other);
                        DatagramPacket newPacket=new DatagramPacket(buffer,buffer.length,packet.getAddress(),packet.getPort());
                        serverSocket.send(newPacket);
                    }                  
                }                           
            }else if (msg.getType().equalsIgnoreCase("M_MSG")) { //Is M_MSG message
                //Update the display
                parentUI.txtArea.append(userId+" said: "+msg.getText()+"\n");
                //Forwarded message
                for (int i=0;i<userList.size();i++) { //Through the user
                    DatagramPacket oldPacket=userList.get(i).getPacket();
                    DatagramPacket newPacket=new DatagramPacket(data,data.length,oldPacket.getAddress(),oldPacket.getPort()); 
                    serverSocket.send(newPacket); //send
                }
            }else if (msg.getType().equalsIgnoreCase("M_QUIT")) { //Is M_QUIT message
                //Update the display
                parentUI.txtArea.append(userId+" offline!\n");
                //Delete user
                for(int i=0;i<userList.size();i++) {
                    if (userList.get(i).getUserId().equals(userId)) {
                        userList.remove(i);
                        break;
                    }
                }
                //Forward offline messages to other users
                for (int i=0;i<userList.size();i++) {
                    DatagramPacket oldPacket=userList.get(i).getPacket();
                    DatagramPacket newPacket=new DatagramPacket(data,data.length,oldPacket.getAddress(),oldPacket.getPort());
                    serverSocket.send(newPacket);
                }
            }
            } catch (IOException | NumberFormatException ex) {  }
        }
    }
}
