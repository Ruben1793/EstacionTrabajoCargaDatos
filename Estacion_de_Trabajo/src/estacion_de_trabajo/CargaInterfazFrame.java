package estacion_de_trabajo;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;

import java.io.FileWriter;
import java.io.IOException;

import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CargaInterfazFrame extends JFrame {
    private JButton cargarInterfaz = new JButton();
    private JButton cancelarCargaInterfaz = new JButton();
    private JComboBox seleccionarInterfaz = new JComboBox();
    private JCheckBox interfazTodas = new JCheckBox();
    private JCheckBox interfazIndividual = new JCheckBox();
    private static String path;
    private static String comando = "sqlldr userid=";
    private static String user;
    private static String pass;
    private static JTable tabla;
    private String urlhost = "jdbc:oracle:thin:@192.168.56.11:1521:orcl";
    private JLabel proceso = new JLabel();
    public static int cargaexito;
    public CargaInterfazFrame(String user, String pass,String path, JTable tabla) {
        try {
            this.user = user;
            this.pass = pass;
            this.path = path;
            this.tabla = tabla;
            DefaultTableModel model = (DefaultTableModel) tabla.getModel();
                jbInit();
                    try {
                         Class.forName ("oracle.jdbc.driver.OracleDriver");
                    } catch (ClassNotFoundException ex) {
                        System.out.println(ex.getMessage());
                         ex.printStackTrace();
                    }
                    try {
                        Connection conn = DriverManager.getConnection(urlhost, user, pass);
                        Statement stmt = conn.createStatement();
                        ResultSet rset = stmt.executeQuery("select INTERFAZ from AA_CARGAS_DE_DATOS");
                        while (rset.next()){
                                System.out.println (rset.getString(1));   // Print col 1
                                seleccionarInterfaz.addItem(rset.getString(1));
                            }     
                        stmt.close();
                        conn.close();
                        rset.close();
                    } catch (SQLException f) {
                        System.out.println(f.getMessage());
                        f.printStackTrace();
                    }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setSize( new Dimension(400, 300) );
        cargarInterfaz.setText("Carga");
        cargarInterfaz.setBounds(new Rectangle(105, 160, 80, 20));
        cargarInterfaz.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cargarInterfaz_actionPerformed(e);
                }
            });
        cancelarCargaInterfaz.setText("Cancelar");
        cancelarCargaInterfaz.setBounds(new Rectangle(200, 160, 90, 20));
        cancelarCargaInterfaz.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cancelarCargaInterfaz_actionPerformed(e);
                }
            });
        seleccionarInterfaz.setBounds(new Rectangle(120, 105, 135, 20));
        interfazTodas.setText("Todas");
        interfazTodas.setBounds(new Rectangle(105, 60, 74, 18));
        interfazIndividual.setText("Individual");
        interfazIndividual.setBounds(new Rectangle(220, 60, 74, 18));
        proceso.setText("proceso");
        proceso.setBounds(new Rectangle(-55, -5, 130, 15));
        this.getContentPane().add(proceso, null);
        this.getContentPane().add(interfazIndividual, null);
        this.getContentPane().add(interfazTodas, null);
        this.getContentPane().add(seleccionarInterfaz, null);
        this.getContentPane().add(cancelarCargaInterfaz, null);
        this.getContentPane().add(cargarInterfaz, null);
        this.setTitle("Carga de Interfaz");
    }
    private void cargarInterfaz_actionPerformed(ActionEvent e) {
        if(interfazIndividual.isSelected()){
            seleccionarInterfaz.getSelectedItem();
            int confirmacion = JOptionPane.showConfirmDialog(this, seleccionarInterfaz.getSelectedItem());
            if (JOptionPane.OK_OPTION == confirmacion){
               JOptionPane.showMessageDialog(this, "Entendido");
               System.out.println(path);
               //primero es leer del archivo de configuracion 
                File file = new File("C:\\configConexion\\config.txt");
                if(file.exists()){
                    try {
                        FileReader reader = new FileReader(file);
                        BufferedReader br = new BufferedReader(reader);
                        boolean isEOF = false;
                        do {
                            String text = br.readLine(); //apartir de aqui se va armando el comando para subir interfaz
                            if (text != null) {
                                isEOF = true;
                                text = text.replaceAll("\\n|\\t|\\s|=", "");
                                if (text.contains("Usuario")){
                                    System.out.println("usuario: " + text.substring(8));
                                    //usuario.setText(text.substring(8));
                                     comando = comando + text.substring(8)+"/";
                                }
                                if(text.contains("Password")){
                                    System.out.println("password: " + text.substring(9));
                                    //password.setText(text.substring(9));
                                    comando = comando + text.substring(9)+"@";
                                }
                                if(text.contains("TNSNames")){
                                    System.out.println("tnsnames: " + text.substring(9));
                                    // TNSNames.addItem(text.substring(9));
                                    comando = comando + text.substring(9)+ " control="
                                              +seleccionarInterfaz.getSelectedItem().toString()+".ctl";
                                }
                                System.out.println(comando); // comando completo para cargar la interfaz
                            }
                            else {
                                isEOF = false;
                            }
                        } while(isEOF);    
                        br.close();
                        reader.close();
                        //ahora se ejecuta el archivo bat que se acaba de crear
                        Process process = Runtime.getRuntime().exec(comando,null,new File(path));
                       // process.waitFor();
                        comando="sqlldr userid="; 
                    } catch (IOException ex) {
                        Logger.getLogger(CargaInterfazFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
               
            }
        }
        else if (interfazTodas.isSelected()){
        }
        else{
            JOptionPane.showMessageDialog(this, "Por favor selecciona: Indivual o Todas?");
        }
    }

    private void cancelarCargaInterfaz_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
