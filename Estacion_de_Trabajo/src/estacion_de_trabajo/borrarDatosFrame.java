package estacion_de_trabajo;

import java.awt.Dimension;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class borrarDatosFrame extends JFrame {
    private JTextField queryText = new JTextField();
    private JButton botonBorrarDatos = new JButton();
    private JButton botonCancelar = new JButton();
    private String user;
    private String pass;
    private String url;

    public borrarDatosFrame(String user,String pass,String url) {
        this.user = user;
        this.pass = pass;
        this.url = url;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setSize( new Dimension(400, 300) );
        queryText.setBounds(new Rectangle(0, 115, 400, 40));
        botonBorrarDatos.setText("Borrar Datos");
        botonBorrarDatos.setBounds(new Rectangle(75, 190, 100, 20));
        botonBorrarDatos.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    botonBorrarDatos_actionPerformed(e);
                }
            });
        botonCancelar.setText("Cancelar");
        botonCancelar.setBounds(new Rectangle(225, 190, 100, 20));
        this.getContentPane().add(botonCancelar, null);
        this.getContentPane().add(botonBorrarDatos, null);
        this.getContentPane().add(queryText, null);
        this.setTitle("Borrar Datos");
    }

    private void botonBorrarDatos_actionPerformed(ActionEvent e) {
        CallableStatement callableStatement = null;
        try {
             Class.forName ("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
             ex.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("{ call "+ queryText.getText() + " }");
            String call = queryText.getText();
            callableStatement = conn.prepareCall("{ call "+ call + " }");
            callableStatement.execute();
            callableStatement.close();
            conn.close();
        } catch (SQLException f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        }
        
    }
}
