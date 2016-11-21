 package estacion_de_trabajo;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.IllegalFormatConversionException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class EstacionTrabajoFrame extends JFrame {
    private JButton botonExaminar = new JButton();
    private JButton botonCargarInterfaz = new JButton();
    private JButton botonReiniciarInterfaz = new JButton();
    private JButton botonBorrarInterfaz = new JButton();
    private JButton botonCargarDatos = new JButton();
    private JButton botonBorrarDatos = new JButton();
    private JButton botonGenerarReporte = new JButton();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JTextField direccion = new JTextField();
    private JScrollPane scroll = new JScrollPane();
    private JTable tabla = new JTable();
    private static String pathCSV;
    private static String user;
    private static String pass;
    private String urlhost = "jdbc:oracle:thin:@192.168.56.11:1521:orcl";
    protected static int csvCount;
    static int interfaces;
    static int i_numero;
    public EstacionTrabajoFrame() {
        try {
            jbInit();
            
            File file = new File("C:\\configConexion\\config.txt");
            if(file.exists()){
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
                             user = text.substring(8);
                        }
                        if(text.contains("Password")){
                            System.out.println("password: " + text.substring(9));
                            //password.setText(text.substring(9));
                            pass = text.substring(9);
                        }
                    }
                    else {
                        isEOF = false;
                    }
                } while(isEOF);    
                br.close();
                reader.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
             Class.forName ("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
             ex.printStackTrace();
        }
        try {
            DefaultTableModel model = (DefaultTableModel) tabla.getModel();
            Connection conn = DriverManager.getConnection(urlhost, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("select INTERFAZ from AA_CARGAS_DE_DATOS");
            while (rset.next()){
                    System.out.println (rset.getString(1));   // Print col 1
                    model.addRow(new Object[]{rset.getString(1)});         
            }
            stmt.close();
            conn.close();
            rset.close();
        } catch (SQLException f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setSize(740, 550);
        this.setTitle("Estacion de Trabajo");
        this.setResizable(false);
        
        direccion.setBounds(70, 40, 540, 20);
        
        botonExaminar.setText("Examinar");
        botonExaminar.setBounds(new Rectangle(630, 40, 80, 25));

        botonExaminar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    botonExaminar_actionPerformed(e);
                }
            });


        botonCargarInterfaz.setText("Cargar Interfaz");
        botonCargarInterfaz.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                   botonCargarInterfaz_actionPerformed(e);
                }
            });
        botonCargarInterfaz.setBounds(new Rectangle(15, 135, 120, 23));
        
        botonReiniciarInterfaz.setText("Reinicar Interfaz");
        botonReiniciarInterfaz.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    botonReiniciarInterfaz_actionPerformed(e);
                }
            });
        botonReiniciarInterfaz.setBounds(new Rectangle(15, 170, 120, 23));
        
        botonBorrarInterfaz.setText("Borrar Interfaz");
        botonBorrarInterfaz.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    botonBorrarInterfaz_actionPerformed(e);
                }
            });
        botonBorrarInterfaz.setBounds(new Rectangle(155, 130, 120, 23));
        
        botonCargarDatos.setText("Cargar Datos");
        botonCargarDatos.setBounds(new Rectangle(390, 130, 110, 23));

        botonCargarDatos.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    botonCargarDatos_actionPerformed(e);
                }
            });
        botonBorrarDatos.setText("Borrar Datos");
        botonBorrarDatos.setBounds(new Rectangle(515, 130, 110, 23));

        botonBorrarDatos.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    botonBorrarDatos_actionPerformed(e);
                }
            });
        botonGenerarReporte.setText("Generar Reportes");
        botonGenerarReporte.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    botonGenerarReporte_actionPerformed(e);
                }
            });
        botonGenerarReporte.setBounds(new Rectangle(390, 165, 130, 23));
        
        jLabel1.setText("1. Extraccion ");
        jLabel1.setBounds(new Rectangle(10, 10, 70, 14));
        
        jLabel2.setText("Ruta");
        jLabel2.setBounds(new Rectangle(40, 40, 23, 14));
        
        jLabel3.setText("2. Transformacion");
        jLabel3.setBounds(new Rectangle(5, 105, 120, 14));
        
        jLabel4.setText("3. Carga ");
        jLabel4.setBounds(new Rectangle(390, 105, 60, 14));
        
        tabla.setModel(new DefaultTableModel(
                    new Object [][] {},
                    new String [] {
                        "Nombre Interfaz", "CSV", "Carga Interfaz", "Rechazados","Cargados", "%Cargados", "Estatus"
                    }
                ));
        
        scroll.setBounds(new Rectangle(0, 220, 740, 280));
        scroll.setPreferredSize(new Dimension(400,300));
        scroll.setViewportView(tabla);
        scroll.getViewport().add(tabla);

        this.getContentPane().add(scroll, null);
        this.getContentPane().add(direccion, null);
        this.getContentPane().add(jLabel4, null);
        this.getContentPane().add(jLabel3, null);
        this.getContentPane().add(jLabel2, null);
        this.getContentPane().add(jLabel1, null);
        this.getContentPane().add(botonGenerarReporte, null);
        this.getContentPane().add(botonBorrarDatos, null);
        this.getContentPane().add(botonCargarDatos, null);
        this.getContentPane().add(botonBorrarInterfaz, null);
        this.getContentPane().add(botonReiniciarInterfaz, null);
        this.getContentPane().add(botonCargarInterfaz, null);
        this.getContentPane().add(botonExaminar, null);
        this.setTitle("Estacion de Trabajo");
    }

    
    private void botonExaminar_actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("C:\\"));
        chooser.setDialogTitle("Selecciona el folder de archivos CSV");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            direccion.setText(chooser.getSelectedFile().getPath());
            System.out.println(chooser.getSelectedFile().getPath());
        }
        pathCSV = direccion.getText();
        DefaultTableModel model = (DefaultTableModel)tabla.getModel();
        File file = new File(pathCSV);
        File[] ficheros = file.listFiles();
        metodos m = new metodos();
        for (int x=0;x<ficheros.length;x++){
            try {
                if(ficheros[x].getName().endsWith(".csv")){
                    csvCount= m.getCSVCount(ficheros[x]);
                    System.out.println(csvCount);
                    model.setValueAt(csvCount, i_numero, 1);
                    model.setValueAt("Pendiente", i_numero, 6);
                    i_numero++;
                }
            } catch (IOException f) {
                f.printStackTrace();
            }
        }
        interfaces = i_numero;
        i_numero =0;
        csvCount =0;
    }
    private void botonBorrarInterfaz_actionPerformed(ActionEvent e) {
        if(direccion.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Por favor seleccionar directorio de los archivos CSV");
        }
        else{
            Frame frame = new LimpiarInterfazFrame(user,pass,pathCSV);
            frame.setVisible(true);
        }
    }
    private void botonReiniciarInterfaz_actionPerformed(ActionEvent e) {
        if(direccion.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Por favor seleccionar directorio de los archivos CSV");
        }
        else{
            Frame frame = new ReiniciarInterfazFrame(user,pass,pathCSV);
            frame.setVisible(true);
        }
    }
    private void botonCargarInterfaz_actionPerformed(ActionEvent e) {
        if(direccion.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Por favor seleccionar directorio de los archivos CSV");
        }
        else{
            Frame frame = new CargaInterfazFrame(user,pass,pathCSV,tabla);
            frame.setVisible(true);
        }
    }
    private void botonBorrarDatos_actionPerformed(ActionEvent e) {  
        borrarDatosFrame bdf = new borrarDatosFrame(user,pass,urlhost);
        bdf.setVisible(true);
    }
    private void botonGenerarReporte_actionPerformed(ActionEvent e) {
        ReporteInterfaz reporte = new ReporteInterfaz(user,pass,urlhost,direccion.getText());
        reporte.setVisible(true);
    }
    private void botonCargarDatos_actionPerformed(ActionEvent e) {
        CallableStatement callableStatement = null;
        try {
             Class.forName ("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
             ex.printStackTrace();
        }
        try {
            DefaultTableModel model = (DefaultTableModel) tabla.getModel();
            Connection conn = DriverManager.getConnection(urlhost, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("select PROCEDIMIENTO_CARGA from AA_CARGAS_DE_DATOS");
            String call;
            while (rset.next()){
                    System.out.println (rset.getString(1));   // Print col 1
                    call = rset.getString(1);
                    callableStatement = conn.prepareCall("{ call " + call + " }");
                    callableStatement.execute();
                }     
            for(int x =0; x<interfaces; x++){
                rset = stmt.executeQuery("Select Count(*) From " + model.getValueAt(x, 0));
                System.out.println("Select Count(*) From " + model.getValueAt(x, 0));
                int value = (Integer)model.getValueAt(x, 1);
                System.out.println("value " + value);
                //int tablevalue = Integer.parseInt(model.getValueAt(x, 2).toString());
                if(rset.next()){
                    model.setValueAt(rset.getInt(1),x ,2);
                    model.setValueAt("En proceso", x, 6);
                    model.setValueAt(value - rset.getInt(1),x, 3);
                }  
                //System.out.println("SELECT COUNT(*) FROM "+ model.getValueAt(x, 0) +" WHERE ESTATUS_CARGA = 'OK'");
                rset = stmt.executeQuery("SELECT COUNT(*) FROM "+ model.getValueAt(x, 0) +" WHERE ESTATUS_CARGA = 'OK'");
                int valor = (Integer)model.getValueAt(x, 2);
                String porcentaje;
                if(rset.next()){
                    model.setValueAt(rset.getInt(1), x, 4);
                    //porcentaje = Math.round(((rset.getInt(1)/valor)*100));
                    double cantidad = ((rset.getInt(1)*100)/valor);
                    porcentaje = String.format("%.02f",cantidad);
                    System.out.println("porcentaje: " + porcentaje);
                    model.setValueAt(porcentaje, x, 5);
                    model.setValueAt("Terminado", x, 6);
                }
            }
            stmt.close();
            conn.close();
            rset.close();
        } catch (SQLException f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        } 
    }
}
