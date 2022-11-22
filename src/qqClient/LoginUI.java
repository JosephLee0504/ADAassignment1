package qqClient;

import utilise.Message;
import utilise.Translate;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

/* LoginUI class, client login interface */
@SuppressWarnings("serial")
public class LoginUI extends JDialog{
	private JButton btnGetPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JCheckBox chkAutoLogin;
    private JCheckBox chkRemember;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPasswordField txtPassword;
    private JTextField txtRemoteName;
    private JTextField txtRemotePort;
    private JTextField txtUserId;    
    /* create new form LoginUI */
    public LoginUI(Frame parent, boolean modal) {
    	super(parent, modal);
        initComponents();
        //Sets the form position to the center of the screen
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height)/2;
        this.setLocation(x, y);        
    }
    private void initComponents() {
        txtUserId = new JTextField();
        btnRegister = new JButton();
        btnGetPassword = new JButton();
        txtPassword = new JPasswordField();
        chkRemember = new JCheckBox();
        chkAutoLogin = new JCheckBox();
        btnLogin = new JButton();
        jLabel2 = new JLabel();
        txtRemoteName = new JTextField();
        jLabel3 = new JLabel();
        txtRemotePort = new JTextField();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("UserLogin");

        txtUserId.setFont(new Font("Consolas", 1, 18));

        btnRegister.setFont(new Font("Consolas", 0, 14));
        btnRegister.setText("RegisterAccount");
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnGetPassword.setFont(new Font("Consolas", 0, 14));
        btnGetPassword.setText("RetrievePassword");
        btnGetPassword.setBorderPainted(false);
        btnGetPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));

        txtPassword.setFont(new Font("Consolas", 1, 18));

        chkRemember.setText("RememberPassword");
        chkRemember.setCursor(new Cursor(Cursor.HAND_CURSOR));

        chkAutoLogin.setText("AutoLogin");
        chkAutoLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLogin.setBackground(new Color(153, 153, 255));
        btnLogin.setFont(new Font("Consolas", 1, 18));
        btnLogin.setText("Login");
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        jLabel2.setText("ServerHostName:");
        txtRemoteName.setText("localhost");
        jLabel3.setText("ServerPort:");
        txtRemotePort.setText("50000");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtUserId, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                    .addComponent(txtPassword, GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chkRemember, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                .addGap(13, 13, 13)
                                .addComponent(chkAutoLogin, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(btnRegister, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                            .addComponent(btnGetPassword, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRemoteName, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel3)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRemotePort)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(47, 47, 47)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtUserId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnRegister))
                            .addGap(27, 27, 27)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnGetPassword))
                            .addGap(14, 14, 14)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(chkRemember)
                                .addComponent(chkAutoLogin)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(26, 26, 26))
                        )
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(btnLogin)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtRemoteName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(txtRemotePort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
            );
        pack();
    }
    /* Process logics of login button click events and complete login steps */
    private void btnLoginActionPerformed(ActionEvent evt) {
    try {
        String id=txtUserId.getText();
        String password=String.valueOf(txtPassword.getPassword());       
        if (id.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(null, "The account or password cannot be empty!", "Error message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //Gets the server address and port
        String remoteName=txtRemoteName.getText();
        InetAddress remoteAddr=InetAddress.getByName(remoteName);
        int remotePort=Integer.parseInt(txtRemotePort.getText());   
        //Create the UDP socket
        DatagramSocket clientSocket=new DatagramSocket();
        clientSocket.setSoTimeout(3000);//Set the timeout
        //Build the user login message
        Message msg=new Message();
        msg.setUserId(id);//Login name       
        msg.setPassword(password); //password       
        msg.setType("M_LOGIN"); //Login message type
        msg.setToAddr(remoteAddr); //The target address
        msg.setToPort(remotePort); //The target port        
        byte[] data=Translate.ObjectToByte(msg); //Message object serialization
        //Define login message
        DatagramPacket packet=new DatagramPacket(data,data.length,remoteAddr,remotePort);
        //Send login message
        clientSocket.send(packet);
        //A message sent back by the receiving server
        DatagramPacket backPacket=new DatagramPacket(data,data.length);
        clientSocket.receive(backPacket); 
        clientSocket.setSoTimeout(0);//Untimeout period
        Message backMsg=(Message)Translate.ByteToObject(data);
        //Processing login results
        if (backMsg.getType().equalsIgnoreCase("M_SUCCESS")) { //Login successful
            this.dispose(); //Close the login dialog box
            ClientUI client=new ClientUI(clientSocket,msg); //Create client interface
            client.setTitle(msg.getUserId()); //Set the title
            client.setVisible(true); //Display session form           
        }
        else { //Login failed
            JOptionPane.showMessageDialog(null, "User ID or password error!\n\nLogin failed!\n", "Login failed",JOptionPane.ERROR_MESSAGE);           
        }
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Login error", JOptionPane.ERROR_MESSAGE);
      }
    } 
	public static void main(String[] args) {
		try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {} 
		  catch (InstantiationException ex) {} 
		  catch (IllegalAccessException ex) {} 
		  catch (UnsupportedLookAndFeelException ex) {}

        /* Create and display the dialog */
		EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginUI dialog = new LoginUI(new JFrame(), true);
                dialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
	}
}
