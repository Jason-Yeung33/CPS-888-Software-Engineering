package Client_IM;

import javax.crypto.*;
import java.security.*;
import javax.crypto.spec.DESKeySpec;

import instant.messenger.My_Connection;
import java.awt.event.KeyEvent;
import java.net.*;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Client extends javax.swing.JFrame 
{
    Boolean ClientOnline = false;
    PrintWriter PrintWriter;
    String ClientName;
    Socket ClientSocket;
    BufferedReader BufferReader;
    ArrayList<String> userNames = new ArrayList();    
    String Stringkey= "com.sun.crypto.provider.DESKey@fffe7840";
    SecretKey key;
    Cipher encrypt;
    Cipher decrypt;
    String ciphertext;
    String cleartext;
    byte[] cleartext_Bytes;
    byte[] ciphertext_Bytes;
   
    public Client(){
        initComponents();
    }
    
    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() { @Override public void run() { new Client().setVisible(true); }});
    }
    
    public class ClientCommunication implements Runnable
    {
        @Override
        public void run() 
        {
            String[] SplitData;
            String stream, done = "Done";
            String[] SplitData2;
            
            try 
            {
                //key = KeyGenerator.getInstance("DES").generateKey();
                System.out.println(Stringkey);
                         
                DESKeySpec dks = new DESKeySpec(Stringkey.getBytes());
                SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
                key = skf.generateSecret(dks);
                System.out.println(key);
                 
                //Create Cipher objects using .getInstance Methods with DES
                encrypt = Cipher.getInstance("DES");
                decrypt = Cipher.getInstance("DES");

                //Initialize Cipher modes with generated key
                //  ENCRYPT_MODE = Encryption of Data
                //  DECRYPT_MODE = Descryption o Data
                encrypt.init(Cipher.ENCRYPT_MODE, key);
                decrypt.init(Cipher.DECRYPT_MODE, key);
            }
            catch (Exception ex)
            {
                Chat_Box.append("ERROR\n");
            }
            
            
            try 
            {
                while ((stream = BufferReader.readLine()) != null) 
                {
                    SplitData = stream.split(":");
                    switch(SplitData[2])
                    {
                        case "T":
                            if(SplitData[1].contains(">"))
                            {
                                SplitData2 = SplitData[1].split(">");
                                System.out.println("SplitData2[0]: "+SplitData2[0]);
                                System.out.println("SplitData2[1]: "+SplitData2[1]);
                                Chat_Box.append("[WHISPER] " + SplitData[0] + ": " + SplitData2[1] + "\n");
                            }
                         else
                            {
                                Chat_Box.append(SplitData[0] + ": " + SplitData[1] + "\n");
                            }
                            Chat_Box.setCaretPosition(Chat_Box.getDocument().getLength());
                        break;
                        
                        case "C":
                            Chat_Box.removeAll();
                            AddClient(SplitData[0]);
                        break;
                        
                        case"D":
                            RemoveClient(SplitData[0]);
                        break; 
                        
                        case "Done": 
                            Online_List.setText("");
                            SendData();
                            userNames.clear();
                        break;
                        
                        default: 
                            Chat_Box.append("Request not valid\n");
                        break;
                    }
                }
           }
            catch(Exception ex) 
           { 
               Chat_Box.append("Not connected to server.\n");
           }
        }
    }

    
    public void AddClient(String data){
         userNames.add(data);
    }
    
    public void RemoveClient(String data){
         Chat_Box.append(data + " is offline.\n");
    }
    
    public void SendData(){
         String[] tempList = new String[(userNames.size())];
         userNames.toArray(tempList);
         for (String x:tempList) 
         {
             Online_List.append(x + "\n");
         }
    }
    
    public void logOut(){
        
        if(ClientOnline == true){
            String bye = (ClientName + ":logged out. :D");
        
            try{
                cleartext_Bytes = bye.getBytes("UTF8");
                ciphertext_Bytes= encrypt.doFinal(cleartext_Bytes);
                ciphertext = new sun.misc.BASE64Encoder().encode(ciphertext_Bytes);
                PrintWriter.println(ciphertext); 
                PrintWriter.flush();             
            }catch (Exception e){
                Chat_Box.append("ERROR\n");
            }
        }
    }
    
    public void ClientDisconnect(){
        try 
        {
            Chat_Box.append("Client Disconnected.\n");
            ClientSocket.close();
        } catch(Exception ex) {
            Chat_Box.append("ERROR\n");
        }
        ClientOnline = false;
        
        User_Login.setEnabled(true);
        Username_Field.setEditable(true);
        Username_Field.setText(null);
        
        Password_Field.setEditable(true);
        Password_Field.setText(null);
        
        Online_List.setText(null);
        
        Guest_Field.setText(null);
    }
      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Username_Label = new javax.swing.JLabel();
        Username_Field = new javax.swing.JTextField();
        Password_Label = new javax.swing.JLabel();
        User_Login = new javax.swing.JButton();
        Logout_Button = new javax.swing.JButton();
        Guest_Login = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Chat_Box = new javax.swing.JTextArea();
        Chat_Field = new javax.swing.JTextField();
        Send_Button = new javax.swing.JButton();
        Password_Field = new javax.swing.JPasswordField();
        Guest_Field = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        Online_List = new javax.swing.JTextArea();
        Online_Label = new javax.swing.JLabel();
        ChatRoom = new javax.swing.JLabel();
        Guest_Label = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Client's frame");
        setName("client"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(236, 236, 236));

        Username_Label.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Username_Label.setText("Username:");

        Password_Label.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Password_Label.setText("Password: ");

        User_Login.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        User_Login.setText("Log in");
        User_Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                User_LoginActionPerformed(evt);
            }
        });

        Logout_Button.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Logout_Button.setText("Log out");
        Logout_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Logout_ButtonActionPerformed(evt);
            }
        });

        Guest_Login.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Guest_Login.setText("Log in as a guest");
        Guest_Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Guest_LoginActionPerformed(evt);
            }
        });

        Chat_Box.setEditable(false);
        Chat_Box.setColumns(20);
        Chat_Box.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        Chat_Box.setRows(5);
        jScrollPane1.setViewportView(Chat_Box);

        Chat_Field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Chat_FieldKeyPressed(evt);
            }
        });

        Send_Button.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Send_Button.setText("Send");
        Send_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Send_ButtonActionPerformed(evt);
            }
        });

        Password_Field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Password_FieldKeyPressed(evt);
            }
        });

        Guest_Field.setEditable(false);

        Online_List.setEditable(false);
        Online_List.setColumns(20);
        Online_List.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        Online_List.setRows(5);
        jScrollPane2.setViewportView(Online_List);

        Online_Label.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        Online_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Online_Label.setText("Users Online");

        ChatRoom.setFont(new java.awt.Font("DejaVu Sans", 0, 24)); // NOI18N
        ChatRoom.setText("LetsTalk Chatroom");

        Guest_Label.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Guest_Label.setText("Guest Username");

        jLabel3.setText("Send a private message by using \"username>your message\"");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Chat_Field)
                                .addGap(7, 7, 7)
                                .addComponent(Send_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(Username_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Username_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(7, 7, 7)
                                        .addComponent(Password_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Password_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(User_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel3)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(Guest_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Guest_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Guest_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 7, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(Online_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ChatRoom)
                        .addGap(146, 146, 146)
                        .addComponent(Logout_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ChatRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Logout_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Password_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Username_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Password_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(User_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(Username_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Online_Label))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Guest_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Guest_Login, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(Guest_Label))
                        .addGap(0, 17, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Chat_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Send_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void User_LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_User_LoginActionPerformed
        
        PreparedStatement pst;
        ResultSet rs; 
        
        //get username & password
        ClientName = Username_Field.getText();
        String password = String.valueOf(Password_Field.getPassword());
        
        //create select query to check if ClientName & password are in db
        String query = "SELECT * FROM `user_base` WHERE `username` = ? AND `password` = ?";
        
        //Check if ClientName or password fields are empty
        if(ClientName.trim().equals("")){
            JOptionPane.showMessageDialog(null, "Enter Your Username", "Username Field Empty", 2);
        }
        else if(password.trim().equals("")){
            JOptionPane.showMessageDialog(null, "Enter Your Password", "Password Field Empty", 2);
        }
        else{
            try{
                pst = My_Connection.getConnection().prepareStatement(query);
            
                pst.setString(1, ClientName);
                pst.setString(2, password);
                rs = pst.executeQuery();     
                
                if(rs.next()){ 
                    if(ClientOnline == false){
                        ClientName = Username_Field.getText();
                        Username_Field.setEditable(true);                   
                    
                        try{
                            ClientSocket = new Socket("localhost", 9999);
                             InputStreamReader streamreader = new InputStreamReader(ClientSocket.getInputStream());
                            BufferReader = new BufferedReader(streamreader);
                            PrintWriter = new PrintWriter(ClientSocket.getOutputStream());
                            cleartext_Bytes = (ClientName + ":logged in. :C").getBytes("UTF8");
                            ciphertext_Bytes= encrypt.doFinal(cleartext_Bytes);
                            ciphertext = new sun.misc.BASE64Encoder().encode(ciphertext_Bytes);
                            PrintWriter.println(ciphertext);
                            PrintWriter.flush();
                            ClientOnline = true;
                            Username_Field.setEditable(false);
                            Password_Field.setEditable(false);
                        }
                        catch (Exception ex){
                            Chat_Box.append("Cannot Connect! Try Again. \n");
                            Username_Field.setEditable(true);
                        }
                    }else if(ClientOnline == true){
                        Chat_Box.append("You are already connected. \n");
                    }            
                }else{
                    //error message
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Login Error", 2);
                }
            }catch (SQLException ex){
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }   
            
            Thread IncomingReader = new Thread(new ClientCommunication());
            IncomingReader.start();
        } 
    }//GEN-LAST:event_User_LoginActionPerformed

    private void Logout_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Logout_ButtonActionPerformed
        logOut();
        ClientDisconnect();
    }//GEN-LAST:event_Logout_ButtonActionPerformed

    private void Guest_LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Guest_LoginActionPerformed

        if(ClientOnline == false){
            Guest_Field.setText("");
            String LetsTalk="LetsTalk";
            Random generator = new Random(); 
            int i = generator.nextInt(999) + 1;
            String is=String.valueOf(i);
            LetsTalk=LetsTalk.concat(is);
            ClientName=LetsTalk;
            
            Guest_Field.setText(LetsTalk);
            Guest_Field.setEditable(false);

            try{
                ClientSocket = new Socket("localhost", 9999);
                InputStreamReader streamreader = new InputStreamReader(ClientSocket.getInputStream());
                BufferReader = new BufferedReader(streamreader);
                PrintWriter = new PrintWriter(ClientSocket.getOutputStream());
                PrintWriter.println(LetsTalk + ":logged in. :C");
                PrintWriter.flush(); 
                ClientOnline = true;
                User_Login.setEnabled(false);
            } 
            catch (Exception ex){
                Chat_Box.append("Cannot Connect! Try Again. \n");
                Guest_Field.setEditable(true);
            }
            Thread IncomingReader = new Thread(new ClientCommunication());
            IncomingReader.start();
            
        }else if(ClientOnline == true){
            Chat_Box.append("You are already connected. \n");
        }        
    }//GEN-LAST:event_Guest_LoginActionPerformed

    private void Send_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Send_ButtonActionPerformed
        String nothing = "";
        
        if((Chat_Field.getText()).equals(nothing)){
            Chat_Field.setText("");
            Chat_Field.requestFocus();
        }else{
            try{
               PrintWriter.println(ClientName + ":" + Chat_Field.getText() + ":" + "T");
               PrintWriter.flush(); // flushes the buffer
            }catch (Exception ex){
                Chat_Box.append("Message was not sent. \n");
            }
            Chat_Field.setText("");
            Chat_Field.requestFocus();
        }
        Chat_Field.setText("");
        Chat_Field.requestFocus();
    }//GEN-LAST:event_Send_ButtonActionPerformed

    private void Password_FieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Password_FieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            PreparedStatement pst;
            ResultSet rs; 
        
            //get ClientName & password
            ClientName = Username_Field.getText();
            String password = String.valueOf(Password_Field.getPassword());
        
            //create select query to check if ClientName & password are in db
            String query = "SELECT * FROM `user_base` WHERE `username` = ? AND `password` = ?";
        
            //Check if ClientName or password fields are empty
            if(ClientName.trim().equals("")){
                JOptionPane.showMessageDialog(null, "Enter Your Username", "Username Field Empty", 2);
            }
            else if(password.trim().equals("")){
                JOptionPane.showMessageDialog(null, "Enter Your Password", "Password Field Empty", 2);
            }
            else{
                try{
                    pst = My_Connection.getConnection().prepareStatement(query);
            
                    pst.setString(1, ClientName);
                    pst.setString(2, password);
                    rs = pst.executeQuery();     
                
                    if(rs.next()){ 
                        if (ClientOnline == false){
                            ClientName = Username_Field.getText();
                            Username_Field.setEditable(true);                   
                    
                            try{
                                ClientSocket = new Socket("localhost", 9999);
                                InputStreamReader streamreader = new InputStreamReader(ClientSocket.getInputStream());
                                BufferReader = new BufferedReader(streamreader);
                                PrintWriter = new PrintWriter(ClientSocket.getOutputStream());
                                PrintWriter.println(ClientName + ":logged in. :C");
                                PrintWriter.flush();
                                ClientOnline = true;
                            }
                            catch (Exception ex){
                                Chat_Box.append("Cannot Connect! Try Again. \n");
                                Guest_Field.setEditable(true);
                            }     
                        }else if(ClientOnline == true){
                            Chat_Box.append("You are already connected. \n");
                        }                             
                    }else{
                        //error message
                        JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Login Error", 2);
                    }
                }catch (SQLException ex){
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }       
                Thread IncomingReader = new Thread(new ClientCommunication());
                IncomingReader.start();
            }
        }   
    }//GEN-LAST:event_Password_FieldKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        logOut();
        ClientDisconnect(); 
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private void Chat_FieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Chat_FieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String empty = "";

            if((Chat_Field.getText()).equals(empty)){
                Chat_Field.setText("");
                Chat_Field.requestFocus();
            }else{
                try{
                    PrintWriter.println(ClientName + ":" + Chat_Field.getText() + ":" + "T");
                    PrintWriter.flush(); // flushes the buffer
                }catch (Exception ex){
                    Chat_Box.append("Message was not sent. \n");
                }
                Chat_Field.setText("");
                Chat_Field.requestFocus();
            }
            Chat_Field.setText("");
            Chat_Field.requestFocus();
        }
    }//GEN-LAST:event_Chat_FieldKeyPressed


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ChatRoom;
    private javax.swing.JTextArea Chat_Box;
    private javax.swing.JTextField Chat_Field;
    private javax.swing.JTextField Guest_Field;
    private javax.swing.JLabel Guest_Label;
    private javax.swing.JButton Guest_Login;
    private javax.swing.JButton Logout_Button;
    private javax.swing.JLabel Online_Label;
    private javax.swing.JTextArea Online_List;
    private javax.swing.JPasswordField Password_Field;
    private javax.swing.JLabel Password_Label;
    private javax.swing.JButton Send_Button;
    private javax.swing.JButton User_Login;
    private javax.swing.JTextField Username_Field;
    private javax.swing.JLabel Username_Label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
