package qqServer;

import java.awt.*;
import java.awt.event.*;
import java.net.DatagramSocket;
import java.net.SocketException;
import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class ServerUI extends JFrame { /* ServerUI, Server interface class */
    private JButton btnStart;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JPanel midPanel;
    private JPanel topPanel;
    public JTextArea txtArea;
    private JTextField txtHostName;
    private JTextField txtHostPort;
    public ServerUI() {
        initComponents();
    }
    private void initComponents() {
        topPanel = new JPanel();
        jLabel1 = new JLabel();
        txtHostName = new JTextField();
        jLabel2 = new JLabel();
        txtHostPort = new JTextField();
        btnStart = new JButton();
        midPanel = new JPanel();
        jScrollPane1 = new JScrollPane();
        txtArea = new JTextArea();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");

        topPanel.setBorder(BorderFactory.createTitledBorder(null, "StartServer", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Consolas", 1, 18)));

        jLabel1.setFont(new Font("Consolas", 1, 18));
        jLabel1.setText("HostName:");

        txtHostName.setFont(new Font("Consolas", 1, 18));
        txtHostName.setText("localhost");

        jLabel2.setFont(new Font("Consolas", 1, 18));
        jLabel2.setText("Port:");

        txtHostPort.setFont(new Font("Consolas", 1, 18));
        txtHostPort.setText("50000");

        btnStart.setFont(new Font("Consolas", 1, 18));
        btnStart.setText("Start");
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        GroupLayout topPanelLayout = new GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
                topPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(topPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHostName, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHostPort, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                                .addGap(49, 49, 49)
                                .addComponent(btnStart, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        topPanelLayout.setVerticalGroup(
                topPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(topPanelLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(topPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtHostName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtHostPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnStart))
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        midPanel.setBorder(BorderFactory.createTitledBorder(null, "ChatroomHall", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Consolas", 1, 18))); // NOI18N

        txtArea.setColumns(30);
        txtArea.setFont(new Font("Consolas", 1, 18));
        txtArea.setLineWrap(true);
        txtArea.setRows(5);
        jScrollPane1.setViewportView(txtArea);

        GroupLayout midPanelLayout = new GroupLayout(midPanel);
        midPanel.setLayout(midPanelLayout);
        midPanelLayout.setHorizontalGroup(
                midPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING)
        );
        midPanelLayout.setVerticalGroup(
                midPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, midPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(topPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(midPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(topPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(midPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }

    //start server
    private void btnStartActionPerformed(ActionEvent evt) {
        try {
            //Gets the server work address port
            @SuppressWarnings("unused")
            String hostName=txtHostName.getText();
            int hostPort=Integer.parseInt(txtHostPort.getText());
            //Creates a UDP datagram socket that listens on the specified port
            DatagramSocket serverSocket=new DatagramSocket(hostPort);
            txtArea.append("The server starts listening...\n");
            //Create and start the UDP message receiving thread
            Thread recvThread=new ReceiveMessage(serverSocket,this);
            recvThread.start();
        } catch (NumberFormatException | SocketException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error message", JOptionPane.ERROR_MESSAGE);
        }
        btnStart.setEnabled(false);
    }
    public static void main(String args[]) {
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
        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerUI().setVisible(true);
            }
        });
    }
}