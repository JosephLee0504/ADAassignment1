package qqClient;

import utilise.Message;
import utilise.Translate;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class ClientUI extends JFrame{
	private JButton btnSend;
    private JScrollPane leftScrollPane1;
    private JScrollPane leftScrollPane2;
    private JScrollPane rightScrollPane;
    public JTextArea txtArea;
    private JTextArea txtInput;
    public JList<String> userList;
	private DatagramSocket clientSocket; //Client socket
    private Message msg; //Message object
    private byte[] data=new byte[8096]; //An 8K byte array
    public ClientUI() {
        initComponents();
        //Sets the form position to the center of the screen
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height)/2;
        this.setLocation(x, y);
    }
    public ClientUI(DatagramSocket socket,Message msg) {
        this();
        clientSocket=socket;
        this.msg=msg; //Login message
        //Create client message receiving and processing threads
        Thread recvThread=new ReceiveMessage(clientSocket,this);
        recvThread.start();
    }
    private void initComponents() {
        leftScrollPane1 = new JScrollPane();
        txtArea = new JTextArea();
        leftScrollPane2 = new JScrollPane();
        txtInput = new JTextArea();
        btnSend = new JButton();
        rightScrollPane = new JScrollPane();
        userList = new JList<>();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        leftScrollPane1.setBorder(BorderFactory.createTitledBorder(null, "MessageWindow", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Consolas", 1, 14)));

        txtArea.setColumns(20);
        txtArea.setFont(new Font("Consolas", 1, 16));
        txtArea.setLineWrap(true);
        txtArea.setRows(5);
        leftScrollPane1.setViewportView(txtArea);

        leftScrollPane2.setBorder(BorderFactory.createTitledBorder(null, "SpeechWindow", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Consolas", 1, 14)));

        txtInput.setColumns(20);
        txtInput.setFont(new Font("Consolas", 1, 16));
        txtInput.setLineWrap(true);
        txtInput.setRows(5);
        leftScrollPane2.setViewportView(txtInput);

        btnSend.setBackground(new Color(153, 204, 255));
        btnSend.setText("Send");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        rightScrollPane.setBorder(BorderFactory.createTitledBorder(null, "Online users", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Consolas", 1, 14)));

        userList.setBackground(new Color(255, 204, 255));
        userList.setFont(new Font("Consolas", 1, 14));
        rightScrollPane.setViewportView(userList);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSend))
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(leftScrollPane1, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                            .addComponent(leftScrollPane2))))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rightScrollPane, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(leftScrollPane1, GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(leftScrollPane2, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSend)
                        .addGap(9, 9, 9))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(rightScrollPane)
                        .addContainerGap())))
        );
        pack();
    }
    /* Send message: "Send" button click the processing logic of the event */
    private void btnSendActionPerformed(ActionEvent evt) {
        try {
            msg.setText(txtInput.getText());//Gets the input text
            msg.setType("M_MSG"); //Normal session message
            data=Translate.ObjectToByte(msg);//Message object serialization
            //Build send message
            DatagramPacket packet=new DatagramPacket(data,data.length,msg.getToAddr(),msg.getToPort());
            clientSocket.send(packet); //send
            txtInput.setText(""); //Clear the input field
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error message", JOptionPane.ERROR_MESSAGE);
        }       
    }
    /* Click the Form close button to send the "M_QUIT" offline message before closing the form */
    private void formWindowClosing(WindowEvent evt) {
        try {
            msg.setType("M_QUIT"); //message typr
            msg.setText(null);
            data=Translate.ObjectToByte(msg); //Message object serialization
            //Build to send
            DatagramPacket packet=new DatagramPacket(data,data.length,msg.getToAddr(),msg.getToPort());       
            clientSocket.send(packet); //send
        } catch (IOException ex) { }
        clientSocket.close(); //Close the socket
    }
}
