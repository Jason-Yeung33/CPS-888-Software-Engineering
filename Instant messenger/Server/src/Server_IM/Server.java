package Server_IM;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends javax.swing.JFrame 
{
   String UName;
   ArrayList<String> userNames;
   ArrayList ClientID;

   public Server() 
   {
        initComponents();
        Thread starter = new Thread(new ServerConnect());
        starter.start();    
        ServerField.append("Server has Started.\n");
   }
   
   public static void main(String args[]) 
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
                    ServerField.append(">>Received: " + Data + "\n");
                    String[] SplitData = Data.split(":");
                    switch(SplitData[2])
                    {
                        case "T":

                                sendAll(Data);
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


    
    public void sendAll(String Data) 
    {
	Iterator it = ClientID.iterator();
        while (it.hasNext()) 
        {
            try 
            {
                PrintWriter writer = (PrintWriter) it.next();
		writer.println(Data);
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
