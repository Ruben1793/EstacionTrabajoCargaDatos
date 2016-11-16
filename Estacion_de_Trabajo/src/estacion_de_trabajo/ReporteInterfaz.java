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
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class ReporteInterfaz extends JFrame {
    private JCheckBox reporteGlobal = new JCheckBox();
    private JComboBox seleccionarInterfaz = new JComboBox();
    private JCheckBox reporteIndividual = new JCheckBox();
    private JButton generarReporte = new JButton();
    private JButton cancelarReporte = new JButton();
    private String user;
    private String pass;
    private String url;
    private static String interfaz;
    private String comando = "SELECT '"+ interfaz +"' INTERFASE,  REPLACE(estatus_carga, 'OK', 'REGISTROS CORRECTOS') ESTATUS, TOTAL\n" + 
    "  FROM (SELECT estatus_carga, COUNT(estatus_carga) TOTAL\n" + 
    "  FROM " + interfaz +"\n" + 
    " GROUP BY estatus_carga);";
    public ReporteInterfaz(String user, String pass, String url) {
        try {
            this.user = user;
            this.pass = pass;
            this.url = url;
            try {
                 Class.forName ("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
                 ex.printStackTrace();
            }
            try {
                Connection conn = DriverManager.getConnection(url, user, pass);
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
        reporteGlobal.setText("Reporte Global");
        reporteGlobal.setBounds(new Rectangle(65, 55, 120, 20));
        seleccionarInterfaz.setBounds(new Rectangle(165, 120, 155, 20));
        reporteIndividual.setText("Reporte Individual");
        reporteIndividual.setBounds(new Rectangle(205, 55, 130, 20));
        generarReporte.setText("Generar Reporte");
        generarReporte.setBounds(new Rectangle(70, 205, 125, 20));
        generarReporte.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    generarReporte_actionPerformed(e);
                }
            });
        cancelarReporte.setText("Cancelar");
        cancelarReporte.setBounds(new Rectangle(210, 205, 90, 20));
        cancelarReporte.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cancelarReporte_actionPerformed(e);
                }
            });
        this.getContentPane().add(cancelarReporte, null);
        this.getContentPane().add(generarReporte, null);
        this.getContentPane().add(reporteIndividual, null);
        this.getContentPane().add(seleccionarInterfaz, null);
        this.getContentPane().add(reporteGlobal, null);
        this.setTitle("Reporte de Interfaz");
    }

    private void generarReporte_actionPerformed(ActionEvent e) {
        Connection conn;
        try {
            Class.forName ("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException f) {
            System.out.println(f.getMessage());
        }
        if(reporteIndividual.isSelected()){
            try{
                conn = DriverManager.getConnection(url, user, pass);
                Statement stmt = conn.createStatement();
                ResultSet rset = null;
                interfaz = seleccionarInterfaz.getSelectedItem().toString();
                System.out.println("SELECT '"+ interfaz +"' INTERFASE,  REPLACE(estatus_carga, 'OK', 'REGISTROS CORRECTOS') ESTATUS, TOTAL\n" + 
                                    "  FROM (SELECT estatus_carga, COUNT(estatus_carga) TOTAL\n" + 
                                    "  FROM " + interfaz +"\n" + 
                                    " GROUP BY estatus_carga);");
                rset = stmt.executeQuery("SELECT '"+ interfaz +"' INTERFASE,  REPLACE(estatus_carga, 'OK', 'REGISTROS CORRECTOS') ESTATUS, TOTAL\n" + 
                                            "  FROM (SELECT estatus_carga, COUNT(estatus_carga) TOTAL\n" + 
                                            "  FROM " + interfaz +"\n" + 
                                            " GROUP BY estatus_carga)");
                while(rset.next()){
                    //System.out.println(rset.getString(0));
                    System.out.print(rset.getString(1)+" ");
                    System.out.print(rset.getString(2)+" ");
                    System.out.println(rset.getString(3));
                }
                    
                stmt.close();
                conn.close();
                rset.close();
                interfaz = null;
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        else if(reporteGlobal.isSelected()){
            
        }
        
       // GenerarReporteFrame gr = new GenerarReporteFrame(user, pass);
        //gr.setVisible(true);
    }

    private void cancelarReporte_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
