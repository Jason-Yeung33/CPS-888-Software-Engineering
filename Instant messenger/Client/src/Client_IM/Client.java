package Client_IM;


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
                while ((stream = BufferReader.readLine()) != null) 
                {
                    SplitData = stream.split(":");
                    switch(SplitData[2])
                    {
                        case "T":

                                Chat_Box.append(SplitData[0] + ": " + SplitData[1] + "\n");
                            Chat_Box.setCaretPosition(Chat_Box.getDocument().getLength());
                        break;
                        
                        
                        case "Done": 
                           
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
               Chat_Box.append("ERROR\n");
           }
        }
    }

    
    public void SendData(){
         String[] tempList = new String[(userNames.size())];
         userNames.toArray(tempList);
      
    }
    
    public void logOut(){
        
        if(ClientOnline == true){
            String bye = (ClientName + ":logged out. :D");
        
            try{
                PrintWriter.println(bye); 
                PrintWriter.flush();             
            }catch (Exception e){
                Chat_Box.append("ERROR\n");
            }
        }
    }
    

      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Chat_Box = new javax.swing.JTextArea();
        Chat_Field = new javax.swing.JTextField();
        Send_Button = new javax.swing.JButton();

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Chat_Field)
                        .addGap(7, 7, 7)
                        .addComponent(Send_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(203, 203, 203))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 201, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Chat_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Send_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
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

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        logOut();
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

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
    private javax.swing.JTextArea Chat_Box;
    private javax.swing.JTextField Chat_Field;
    private javax.swing.JButton Send_Button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
