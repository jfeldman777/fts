/*
 * AppSchema.java
 *
 * Created on 11 ���� 2005 �., 9:29
 */

package j5feldman.schema;
import j5feldman.proc.basement.Sql;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import j5feldman.*;
import j5feldman.ex.*;
import j5feldman.proc.basement.*;
/**
 *
 * @author  Jacob Feldman
 */
public class AppSchema extends javax.swing.JFrame {
    
    /** Creates new form AppSchema */
    public AppSchema() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        fileMenu.setText("File");
        openMenuItem.setText("Go init!");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(openMenuItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        pack();
    }
    // </editor-fold>//GEN-END:initComponents
    String schemaName="";
    File confFile = null;
    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        schemaName = JOptionPane.showInputDialog(this,"schema name:");
        if(schemaName == null){
            JOptionPane.showConfirmDialog(this,"no schema name ... the End");
            return;
        }
        
        JFileChooser fc = new JFileChooser("c:/feldman");
        int returnVal = fc.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
         XPump pp = null;
         try{
            confFile = fc.getSelectedFile();
            if(confFile==null) {
                JOptionPane.showMessageDialog(this,"no db.conf-file ... the End");
                return;   
            }
            
            Schema sch = new Schema();
            pp = sch.go(schemaName,confFile);
            int res = JOptionPane.showConfirmDialog(this, "do you need script?");   
            if(res != JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(this," ... done - no script ...");
                return;
            };
            JFileChooser fc2 = new JFileChooser("c:/feldman");
            int returnVal2 = fc2.showOpenDialog(this);
            if(returnVal2 == JFileChooser.APPROVE_OPTION) {  
                File scriptFile = fc2.getSelectedFile();
                if(scriptFile!=null) {  
                    try{
                        RwFile rw = new RwFile(); 
                        String qq = rw.read(scriptFile,true); 
                        Sql sql = new Sql(pp,scriptFile);  
                        System.out.println("\n::: "+qq);
                        sql.area2db(qq,false); 
                    }catch//(Exception ee){}//
                    (ExScriptLine e2){
                        System.out.println("bad script line: "+e2.line);
                    }
                }  
            }       
            JOptionPane.showMessageDialog(this," ... done ...");
         }catch(Exception e){
            JOptionPane.showMessageDialog(this," problems ... read the black box +: "+e.getMessage());
         }finally{
             try{if(pp.con!=null)pp.con.close();}catch(Exception ee){}
         }
        }  
        System.exit(0);
    }//GEN-LAST:event_openMenuItemActionPerformed
        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws Exception{
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AppSchema().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    // End of variables declaration//GEN-END:variables
    
}