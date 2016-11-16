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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class GenerarReporteFrame extends JFrame {
    private JTextArea queryGenerarReporte = new JTextArea();
    private JLabel mensaje = new JLabel();
    private JButton generarReporte = new JButton();
    private String user;
    private String pass;
    
    public GenerarReporteFrame(String user, String pass) {
        try {
            this.user = user;
            this.pass = pass;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setSize( new Dimension(400, 300) );
        queryGenerarReporte.setBounds(new Rectangle(0, 60, 400, 175));
        mensaje.setText("Introducir el query para generar el reporte");
        mensaje.setBounds(new Rectangle(94, 25, 210, 20));
        generarReporte.setText("Generar Reporte");
        generarReporte.setBounds(new Rectangle(245, 245, 120, 20));
        generarReporte.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    generarReporte_actionPerformed(e);
                }
            });
        this.getContentPane().add(generarReporte, null);
        this.getContentPane().add(mensaje, null);
        this.getContentPane().add(queryGenerarReporte, null);
    }
    private void generarReporte_actionPerformed(ActionEvent e) {
        String urlhost = "jdbc:oracle:thin:@192.168.56.11:1521:orcl";
        Connection conn;
        try {
            Class.forName ("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException f) {
            System.out.println(f.getMessage());
        }
        try {
            conn = DriverManager.getConnection(urlhost, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(queryGenerarReporte.getText());
            stmt.close();
            conn.close();
            rset.close();
        } catch (SQLException f) {
            System.out.println(f.getMessage());
        }
    }
}
