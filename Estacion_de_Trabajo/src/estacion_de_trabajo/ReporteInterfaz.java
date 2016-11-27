package estacion_de_trabajo;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.*;
import java.sql.*;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFCell;


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
    private String direccion;
    public ReporteInterfaz(String user, String pass, String url, String direccion) {
        try {
            this.user = user;
            this.pass = pass;
            this.url = url;
            this.direccion = direccion;
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
       // ExcelDocument xls = new ExcelDocument();
      // HSSFWorkbook wb = new HSSFWorkbook();
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
                
                String path = direccion + "\\reporte " + interfaz + ".txt";
                
                System.out.println("SELECT '"+ interfaz +"' INTERFASE,  REPLACE(estatus_carga, 'OK', 'REGISTROS CORRECTOS') ESTATUS, TOTAL\n" + 
                                    "  FROM (SELECT estatus_carga, COUNT(estatus_carga) TOTAL\n" + 
                                    "  FROM " + interfaz +"\n" + 
                                    " GROUP BY estatus_carga)");
                
                rset = stmt.executeQuery("SELECT '"+ interfaz +"' INTERFASE,  REPLACE(estatus_carga, 'OK', 'REGISTROS CORRECTOS') ESTATUS, TOTAL\n" + 
                                            "  FROM (SELECT estatus_carga, COUNT(estatus_carga) TOTAL\n" + 
                                            "  FROM " + interfaz +"\n" + 
                                            " GROUP BY estatus_carga)");
                File file = new File(path);
                file.createNewFile();
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                while(rset.next()){
                    bw.write("Interface: " + rset.getString(1) + ", ");
                    bw.write("Estatus: " + rset.getString(2) + ", ");
                    bw.write("Total: " + rset.getString(3));
                    bw.newLine();
                    
                    System.out.print(rset.getString(1)+" ");
                    System.out.print(rset.getString(2)+" ");
                    System.out.println(rset.getString(3));
                }
                bw.close();
                stmt.close();
                conn.close();
                rset.close();
                interfaz = null;
            }catch(SQLException ex){
                ex.printStackTrace();
            }catch (IOException f) {
                f.printStackTrace();
            }
        }
        else if(reporteGlobal.isSelected()){
            GenerarReporteFrame gr = new GenerarReporteFrame(user, pass,direccion);
            gr.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(this, "Por favor selecciona: Indivual o Todas?");
        }
    }
    private void cancelarReporte_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
