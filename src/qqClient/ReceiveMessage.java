package qqClient;

import utilise.Message;
import utilise.Translate;
import java.net.*;
import javax.swing.*;

/* ReceiveMessage Class, The thread class that the client receives and processes the message */
public class ReceiveMessage extends Thread{
	private DatagramSocket clientSocket; //Session socket
    private ClientUI parentUI; //The parent class
    private byte[] data=new byte[8096]; //An 8K byte array
	private DefaultListModel<String> listModel=new DefaultListModel<>(); //The list of the Model
    public ReceiveMessage(DatagramSocket socket,ClientUI parentUI) {
        clientSocket=socket;
        this.parentUI=parentUI;
    }   
    public void run() {
        while (true) { //Infinite loop, processing all kinds of messages received
        	try {
        		DatagramPacket packet=new DatagramPacket(data,data.length); //Construct receiving message
                clientSocket.receive(packet); //receive           
                Message msg=(Message)Translate.ByteToObject(data);//Restore the message object
                String userId=msg.getUserId(); //Current userId
                //Categorize processing by message type
                if (msg.getType().equalsIgnoreCase("M_LOGIN")) { //Is a login message for another user
                    //Update message window
                    parentUI.txtArea.append(userId+" enter in chatroom...\n");
                    //New users join the list
                    listModel.add(listModel.getSize(), userId);
                    parentUI.userList.setModel(listModel);
                }
                else if (msg.getType().equalsIgnoreCase("M_ACK")) { //It's a server confirmation message
                    //Login successful, add yourself to the list of users
                    listModel.add(listModel.getSize(), userId);
                    parentUI.userList.setModel(listModel);
                }
                else if (msg.getType().equalsIgnoreCase("M_MSG")) { //Is a normal session message 
                    //Update message window
                    parentUI.txtArea.append(userId+" said: "+msg.getText()+"\n");
                }
                else if (msg.getType().equalsIgnoreCase("M_QUIT")) { //Is another user's offline message  
                    //Update message window
                    parentUI.txtArea.append(userId+" Leave the chatRoom...\n");
                    //The offline user is removed from the list
                    listModel.remove(listModel.indexOf(userId));
                    parentUI.userList.setModel(listModel);
                }
             }catch (Exception ex) {
            	 JOptionPane.showMessageDialog(null, ex.getMessage(),"Error message",JOptionPane.ERROR_MESSAGE);
          }
      }
   }
}
