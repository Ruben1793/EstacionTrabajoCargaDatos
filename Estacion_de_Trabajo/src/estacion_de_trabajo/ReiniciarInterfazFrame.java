 package estacion_de_trabajo;

import java.awt.Dimension;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ReiniciarInterfazFrame extends JFrame {
    private JCheckBox interfazTodas = new JCheckBox();
    private JCheckBox interfazIndividual = new JCheckBox();
    private JButton reiniciarInterfaz = new JButton();
    private JButton cancelarReinicioIntefaz = new JButton();
    private JComboBox seleccionarInterfaz = new JComboBox();
    private String user;
    private String pass;
    private  String path;
    private static String interfaz;
    private String urlhost = "jdbc:oracle:thin:@192.168.56.11:1521:orcl";
    private String comando = "UPDATE "+ interfaz +" SET estatus_carga = 'CARGAR' WHERE estatus_carga != 'CARGAR'";
    public ReiniciarInterfazFrame(String user, String pass, String path) {
        try {
            this.user = user;
            this.pass = pass;
            this.path = path;
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
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setSize( new Dimension(400, 300) );
        
        interfazTodas.setText("Todas");
        interfazTodas.setBounds(new Rectangle(105, 60, 74, 18));
        
        interfazIndividual.setText("Individual");
        interfazIndividual.setBounds(new Rectangle(220, 60, 74, 18));
        
        reiniciarInterfaz.setText("Reiniciar");
        reiniciarInterfaz.setBounds(new Rectangle(105, 160, 80, 20));

        reiniciarInterfaz.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try{ 
                        reiniciarInterfaz_actionPerformed(e);
                    }catch(SQLException sq){
                        sq.printStackTrace();
                    }
                   
                }
            });
        cancelarReinicioIntefaz.setText("Cancelar");
        cancelarReinicioIntefaz.setBounds(new Rectangle(200, 160, 90, 20));

        cancelarReinicioIntefaz.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cancelarReinicioIntefaz_actionPerformed(e);
                }
            });
        seleccionarInterfaz.setBounds(new Rectangle(120, 105, 135, 20));
        
        this.getContentPane().add(seleccionarInterfaz, null);
        this.getContentPane().add(cancelarReinicioIntefaz, null);
        this.getContentPane().add(reiniciarInterfaz, null);
        this.getContentPane().add(interfazIndividual, null);
        this.getContentPane().add(interfazTodas, null);
        this.setTitle("Reiniciar Interfaz");
    }

    private void reiniciarInterfaz_actionPerformed(ActionEvent e)throws SQLException {
        Connection conn;
        try {
            Class.forName ("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException f) {
            System.out.println(f.getMessage());
        }
        if(interfazIndividual.isSelected()){
                conn = DriverManager.getConnection(urlhost, user, pass);
                Statement stmt = conn.createStatement();
                ResultSet rset = null;
                interfaz = seleccionarInterfaz.getSelectedItem().toString();
                System.out.println("UPDATE "+ interfaz +" SET estatus_carga = 'CARGAR' WHERE estatus_carga != 'CARGAR'");
                rset = stmt.executeQuery("UPDATE "+ interfaz +" SET estatus_carga = 'CARGAR' WHERE estatus_carga != 'CARGAR'");
                stmt.close();
                conn.close();
                rset.close();
                interfaz = null;
        }else if(interfazTodas.isSelected()){
        }else{
            JOptionPane.showMessageDialog(this, "Por favor selecciona: Indivual o Todas?");
        }
            
    }

    private void cancelarReinicioIntefaz_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
