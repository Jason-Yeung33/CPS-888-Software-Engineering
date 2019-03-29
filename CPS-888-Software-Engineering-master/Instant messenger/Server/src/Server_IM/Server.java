package Server_IM;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Scanner;
import javax.crypto.*;
import java.security.*;
import javax.crypto.spec.DESKeySpec;

public class Server extends javax.swing.JFrame 
{
   String UName;
   ArrayList<String> userNames;
   ArrayList ClientID;
   String Stringkey= "com.sun.crypto.provider.DESKey@fffe7840";
   SecretKey key;
   Cipher encrypt;
   Cipher decrypt;
   String ciphertext;
   String cleartext;
   byte[] cleartext_Bytes;
   byte[] ciphertext_Bytes;
   
   public Server() 
   {
        initComponents();
        Thread starter = new Thread(new ServerConnect());
        starter.start();    
        ServerField.append("Server has Started.\n");
   }
   
   
   public static void main(String args[]) throws Exception
   {

        java.awt.EventQueue.invokeLater(new Runnable(){@Override public void run() { new Server().setVisible(true);}});
   }
   
   public class ServerConnect implements Runnable 
    {
       
        @Override
        public void run() 
        {
            userNames = new ArrayList(); 
            ClientID = new ArrayList();
            
                    //Generate key
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
                ServerField.append("ERROR\n");
            }
            
            try 
            {
                ServerSocket ServerSocket = new ServerSocket(9999);
                while (true) 
                {
                    Socket ClientSocket = ServerSocket.accept();
                    ClientID.add(new PrintWriter(ClientSocket.getOutputStream()));
                    Thread listener = new Thread(new client(ClientSocket));
                    listener.start();
                }
            }
            catch (Exception ex)
            {
                ServerField.append("ERROR\n");
            }
        }
    }
   
   public class client implements Runnable	
   {
       BufferedReader BufferReader;
       
       public client(Socket Socket) 
       {
            try 
            {
                BufferReader = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
            }
            catch (Exception ex) 
            {
                ServerField.append("ERROR\n");
            }
            

       }

       @Override
       public void run() 
       {
            String Data;
            try 
            {
                while ((Data = BufferReader.readLine()) != null) 
                {
                   
                    ciphertext_Bytes = new sun.misc.BASE64Decoder().decodeBuffer(Data);
                    cleartext_Bytes = decrypt.doFinal(ciphertext_Bytes);
                    cleartext = new String(cleartext_Bytes, "UTF8");
                    Data = cleartext;
                    ServerField.append(">>Received: " + Data + "\n");
                    String[] SplitData = Data.split(":");
                    switch(SplitData[2])
                    {
                        case "T":
                            if(SplitData[1].contains(">"))
                            {
                                String[] SplitData2 = SplitData[1].split(">");
                                if(!SplitData2[0].equals("ALL"))
                                {
                                    privateMessage(Data, SplitData2[0]);
                                }
                                else
                                {
                                    sendAll(Data);
                                }
                            }
                            else
                            {
                                sendAll(Data);
                            }
                        break;

                        case "D":
                            sendAll((SplitData[0] + ":logged out." + ":" + "T"));
                            RemoveClient(SplitData[0]);
                        break;

                        case "C":
                            sendAll((SplitData[0] + ":" + SplitData[1] + ":" + "T"));
                            AddClient(SplitData[0]);
                        break;

                        default: 
                            ServerField.append("Request not valid\n");
                        break;
                    
                    }
                    for (String x:SplitData) 
                    {
                        ServerField.append(x + "\n");
                    }
                } 
             } 
             catch (Exception ex) 
             {
                ServerField.append("ERROR\n");
                ex.printStackTrace();
             } 
	} 
    }

    public void privateMessage(String Data, String username) 
    {
	Iterator it = ClientID.iterator();       
            try 
            {
               
                cleartext_Bytes = Data.getBytes("UTF8");
                ciphertext_Bytes= encrypt.doFinal(cleartext_Bytes);
                ciphertext = new sun.misc.BASE64Encoder().encode(ciphertext_Bytes);
                PrintWriter PrintWriter = (PrintWriter) ClientID.get(userNames.indexOf(username));
		PrintWriter.println(ciphertext);
		ServerField.append(">>Sent: " + Data + "\n\n");
                PrintWriter.flush();
                ServerField.setCaretPosition(ServerField.getDocument().getLength());
            } 
            catch (Exception ex) 
            {
		ServerField.append("ERROR\n");
            }
    }
    
    public void sendAll(String Data) 
    {
	Iterator it = ClientID.iterator();
        while (it.hasNext()) 
        {
            try 
            {
                cleartext_Bytes = Data.getBytes("UTF8");
                ciphertext_Bytes= encrypt.doFinal(cleartext_Bytes);
                ciphertext = new sun.misc.BASE64Encoder().encode(ciphertext_Bytes);
               
                PrintWriter writer = (PrintWriter) it.next();
		writer.println(ciphertext);
		ServerField.append(">>Sent: " + Data + "\n\n");
                writer.flush();
                ServerField.setCaretPosition(ServerField.getDocument().getLength());

            } 
            catch (Exception ex) 
            {
		ServerField.append("ERROR\n");
            }
        } 
    }
    
    public void AddClient (String username) 
    {
        userNames.add(username);
        ServerField.append(username + " online\n");
        String[] List = new String[(userNames.size())];
        userNames.toArray(List);

        for (String x:List) 
        {
            sendAll(x + ": :C");
        }
        sendAll("Server: :Done");
    }
    
    public void RemoveClient (String username) 
    {
        userNames.remove(username);
        ServerField.append(username + " offline\n");
        String[] List = new String[(userNames.size())];
        userNames.toArray(List);

        for (String x:List) 
        {
            sendAll(x + ": :C");
        }
        sendAll("Server: :Done");
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ServerField = new javax.swing.JTextArea();
        OnlineUsers_Button = new javax.swing.JButton();
        Clear_Button = new javax.swing.JButton();
        ChatServer = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chatroom");
        setName("Server"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        ServerField.setColumns(20);
        ServerField.setRows(5);
        jScrollPane1.setViewportView(ServerField);

        OnlineUsers_Button.setText("Online Users");
        OnlineUsers_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OnlineUsers_ButtonActionPerformed(evt);
            }
        });

        Clear_Button.setText("Clear");
        Clear_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Clear_ButtonActionPerformed(evt);
            }
        });

        ChatServer.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        ChatServer.setText("LetsTalk Server");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(OnlineUsers_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Clear_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ChatServer)
                .addGap(126, 126, 126))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ChatServer)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Clear_Button)
                    .addComponent(OnlineUsers_Button))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OnlineUsers_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OnlineUsers_ButtonActionPerformed
        ServerField.append("\nList of Users that are Online: \n");
        for (String x : userNames)
        {
            ServerField.append(x + "\n");
        }    
        
    }//GEN-LAST:event_OnlineUsers_ButtonActionPerformed

    private void Clear_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Clear_ButtonActionPerformed
        ServerField.setText("");
    }//GEN-LAST:event_Clear_ButtonActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ChatServer;
    private javax.swing.JButton Clear_Button;
    private javax.swing.JButton OnlineUsers_Button;
    private javax.swing.JTextArea ServerField;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
