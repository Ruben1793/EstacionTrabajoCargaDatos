package estacion_de_trabajo;

import java.awt.Component;
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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import sun.security.util.Password;

public class ConfiguracionFrame extends JFrame {
    static String oraLocation;
    static File file;
    static String configLocation;
    
    private JLabel lblUsuario = new JLabel();
    private JTextField usuario = new JTextField();
    private JPasswordField password = new JPasswordField();
    private JLabel lblPassword = new JLabel();
    private JComboBox TNSNames = new JComboBox();
    private JLabel lblTNSNAMES = new JLabel();
    private JButton botonGuardar = new JButton();
    private JButton selectORA = new JButton();
    private String path = "C:\\configConexion\\config.txt";

    public ConfiguracionFrame() {
        try {
            jbInit();
            File f = new File(path);
            if (f.exists()){
                JOptionPane.showMessageDialog(this,"Ora File Location:" + f.getAbsolutePath());
                getConfigInfo(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        //this.setSize(new Dimension(400,350));
        this.setPreferredSize(new Dimension(400,350));
        lblUsuario.setText("Usuario");
        lblUsuario.setBounds(new Rectangle(40, 25, 70, 15));
        usuario.setBounds(new Rectangle(170, 20, 150, 20));
        password.setBounds(new Rectangle(170, 60, 150, 20));
        lblPassword.setText("Password");
        lblPassword.setBounds(new Rectangle(40, 65, 65, 15));
        TNSNames.setBounds(new Rectangle(170, 100, 150, 20));
        lblTNSNAMES.setText("TNS Name");
        lblTNSNAMES.setBounds(new Rectangle(40, 105, 75, 15));
        botonGuardar.setText("Guardar");
        botonGuardar.setBounds(new Rectangle(245, 165, 75, 21));
        botonGuardar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    botonGuardar_actionPerformed(e);
                }
            });
        selectORA.setText("Archivo .ora");
        selectORA.setBounds(new Rectangle(50, 170, 100, 20));
        selectORA.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    selectORA_actionPerformed(e);
                }
            });
        this.getContentPane().add(selectORA, null);
        this.getContentPane().add(botonGuardar, null);
        this.getContentPane().add(lblTNSNAMES, null);
        this.getContentPane().add(TNSNames, null);
        this.getContentPane().add(lblPassword, null);
        this.getContentPane().add(password, null);
        this.getContentPane().add(usuario, null);
        this.getContentPane().add(lblUsuario, null);
        this.setTitle("Configuraciones");
        this.setSize(new Dimension(395, 311));
    }

    private void botonGuardar_actionPerformed(ActionEvent e) {
        File f = new File("C:\\configConexion\\config.txt");
        if(!f.exists()){
            JFileChooser chooser = new JFileChooser();
             chooser.setCurrentDirectory(new File("C:\\"));
             chooser.setDialogTitle("Directorio de Config File");
             chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
             if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                configLocation = chooser.getSelectedFile().getPath();
                System.out.println("PATH: " + configLocation);
                // Creating new directory in Java, if it doesn't exists 
             boolean success = false;
             String path = configLocation + "\\configConexion";
             File directory = new File("C:\\configConexion\\config.txt"); 
             if (directory.exists()) { 
                 System.out.println("Directory already exists ...");
             } 
             else { 
                 System.out.println("Directory not exists, creating now"); 
                 JOptionPane.showMessageDialog(this,"Directory not exists, creating now");
                 success = directory.mkdir();
                 if (success) { 
                     System.out.printf("Successfully created new directory : %s%n", directory); 
                     JOptionPane.showMessageDialog(this,"Successfully created new directory : "+ directory);
                 } 
                 else { 
                     System.out.printf("Failed to create new directory: %s%n", directory); 
                 } 
             } 
             //Creating new File
             String filePath = path +"\\config.txt";
             File file = new File(filePath);
             try{
                 if(!file.exists()){
                     file.createNewFile();
                     System.out.println("File created successfully");
                     FileWriter fw = new FileWriter(file.getAbsoluteFile());
                     BufferedWriter bw = new BufferedWriter(fw);
                     bw.write("Usuario: " + usuario.getText()+"\n");
                     bw.write("Password: " + password.getText()+"\n");
                     bw.write("TNSNames: " + TNSNames.getSelectedItem().toString());
                         bw.close();
                     }
                     else{
                         System.out.println("File already exists");
                     }
                 }catch(IOException ex){
                      Logger.getLogger(ConfiguracionFrame.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
        }else{
            f.delete();
            File file = new File("C:\\configConexion\\config.txt");
            FileWriter fw;
            try {
                fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("Usuario: " + usuario.getText()+"\n");
                bw.write("Password: " + password.getText()+"\n");
                bw.write("TNSNames: " + TNSNames.getSelectedItem().toString());
                bw.close();
                System.out.println("Se guardo la nueva configuracion de conexion");
                JOptionPane.showMessageDialog(this, "Se guardo la nueva configuracion de conexion");
            } catch (IOException g) {
                g.printStackTrace();
            }
        }

    }
    private void selectORA_actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("C:\\"));
        chooser.setDialogTitle("Directorio Archivo Ora");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
            oraLocation = chooser.getSelectedFile().getPath();
            System.out.println("oraDirectory: " + oraLocation);
            file = chooser.getSelectedFile();
            try {
                getTNSNamesORA(file);
            } catch (IOException ex) {
                Logger.getLogger(ConfiguracionFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{JOptionPane.showMessageDialog(this,"No se selecciono ningun archivo");}
    }
    public void getTNSNamesORA(File file) throws IOException{
       FileReader reader = new FileReader(file);
       BufferedReader bufferedReader = new BufferedReader(reader);
       boolean isEOF;
        do {
            String text = bufferedReader.readLine();
            if (text != null) {
                isEOF = true;
                text = text.replaceAll("\\n|\\t|\\s|=", "");
                if ((!text.equals("")) && (!text.startsWith("("))&&(!text.startsWith("#"))&& (!text.endsWith(")"))){
                    System.out.println("variable: " + text);
                    TNSNames.addItem(text);
                }
            }
            else {
                isEOF = false;
            }
        } while(isEOF);
        bufferedReader.close();
        reader.close();
    }
    
    public void getConfigInfo(File file) throws IOException{
       FileReader reader = new FileReader(file);
       BufferedReader bufferedReader = new BufferedReader(reader);
       boolean isEOF;
        do {
            String text = bufferedReader.readLine();
            if (text != null) {
                isEOF = true;
                text = text.replaceAll("\\n|\\t|\\s|=", "");
                if (text.contains("Usuario")){
                    System.out.println("usuario: " + text.substring(8));
                    usuario.setText(text.substring(8));
                    //TNSNames.addItem(text);
                }
                if(text.contains("Password")){
                    System.out.println("password: " + text.substring(9));
                    password.setText(text.substring(9));
                }
                if(text.contains("TNSNames")){
                    System.out.println("tnsnames: " + text.substring(9));
                    TNSNames.addItem(text.substring(9));
                }
            }
            else {
                isEOF = false;
            }
        } while(isEOF);
        bufferedReader.close();
        reader.close();
    }
    
      
}
