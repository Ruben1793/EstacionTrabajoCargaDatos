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
import javax.swing.JOptionPane;

public class LimpiarInterfazFrame extends JFrame {
    private JCheckBox interfazTodas = new JCheckBox();
    private JCheckBox interfazIndividual = new JCheckBox();
    private JButton reiniciarInterfaz = new JButton();
    private JButton cancelarReinicio = new JButton();
    private JComboBox seleccionarInterfaz = new JComboBox();
    private String user;
    private String pass;
    private  String path;
    private String urlhost = "jdbc:oracle:thin:@192.168.56.11:1521:orcl";
    private static String interfaz = null;
    private String comando = "DECLARE\n" + 
    "  CURSOR limpia_interfaces IS\n" + 
    "    SELECT TABLE_NAME \n" + 
    "      FROM USER_TABLES \n" + 
    "     WHERE TABLE_NAME LIKE 'I_%'  \n" + 
    "       AND USER = 'GENERADOR' AND TABLE_NAME IN ( "+ interfaz +");\n" + 
    "  \n" + 
    "  v_sentence VARCHAR2(500);\n" + 
    "BEGIN\n" + 
    "  FOR i_clean IN limpia_interfaces\n" + 
    "  LOOP\n" + 
    "    v_sentence := 'DELETE FROM '||i_clean.table_name;\n" + 
    "    EXECUTE IMMEDIATE v_sentence;\n" + 
    "    DBMS_OUTPUT.PUT_LINE(v_sentence);\n" + 
    "    COMMIT;\n" + 
    "    COMMIT;\n" + 
    "  END LOOP;\n" + 
    "EXCEPTION\n" + 
    "  WHEN OTHERS\n" + 
    "  THEN\n" + 
    "    ROLLBACK;\n" + 
    "    DBMS_OUTPUT.PUT_LINE('ERROR: '||SQLERRM);\n" + 
    "END;";
    public LimpiarInterfazFrame(String user, String pass, String path) {
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
        reiniciarInterfaz.setText("Borrar");
        reiniciarInterfaz.setBounds(new Rectangle(105, 160, 80, 20));
        reiniciarInterfaz.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    borrarInterfaz_actionPerformed(e);
                }
            });
        cancelarReinicio.setText("Cancelar");
        cancelarReinicio.setBounds(new Rectangle(200, 160, 90, 20));
        cancelarReinicio.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cancelarBorrar_actionPerformed(e);
                }
            });
        seleccionarInterfaz.setBounds(120, 105, 135, 20);
        this.getContentPane().add(seleccionarInterfaz, null);
        this.getContentPane().add(cancelarReinicio, null);
        this.getContentPane().add(reiniciarInterfaz, null);
        this.getContentPane().add(interfazIndividual, null);
        this.getContentPane().add(interfazTodas, null);
        this.setTitle("Borrar Interfaz");
    }
    private void borrarInterfaz_actionPerformed(ActionEvent e) {
       
        Connection conn;
        try {
            Class.forName ("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException f) {
            System.out.println(f.getMessage());
        }
        try {
            conn = DriverManager.getConnection(urlhost, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet rset = null; 
            if(interfazIndividual.isSelected()){
                interfaz = "'"+seleccionarInterfaz.getSelectedItem().toString()+"'";
                String comando = "DECLARE\n" + 
                "  CURSOR limpia_interfaces IS\n" + 
                "    SELECT TABLE_NAME \n" + 
                "      FROM USER_TABLES \n" + 
                "     WHERE TABLE_NAME LIKE 'I_%'  \n" + 
                "       AND USER = 'GENERADOR' AND TABLE_NAME IN ( "+ interfaz +");\n" + 
                "  \n" + 
                "  v_sentence VARCHAR2(500);\n" + 
                "BEGIN\n" + 
                "  FOR i_clean IN limpia_interfaces\n" + 
                "  LOOP\n" + 
                "    v_sentence := 'DELETE FROM '||i_clean.table_name;\n" + 
                "    EXECUTE IMMEDIATE v_sentence;\n" + 
                "    DBMS_OUTPUT.PUT_LINE(v_sentence);\n" + 
                "    COMMIT;\n" + 
                "    COMMIT;\n" + 
                "  END LOOP;\n" + 
                "EXCEPTION\n" + 
                "  WHEN OTHERS\n" + 
                "  THEN\n" + 
                "    ROLLBACK;\n" + 
                "    DBMS_OUTPUT.PUT_LINE('ERROR: '||SQLERRM);\n" + 
                "END;";
                rset = stmt.executeQuery(comando);
            }
            else if(interfazTodas.isSelected()){
                for(int i =0; i<seleccionarInterfaz.getItemCount();i++){
                    interfaz = "'"+seleccionarInterfaz.getItemAt(i).toString()+"'";
                    String comando = "DECLARE\n" + 
                    "  CURSOR limpia_interfaces IS\n" + 
                    "    SELECT TABLE_NAME \n" + 
                    "      FROM USER_TABLES \n" + 
                    "     WHERE TABLE_NAME LIKE 'I_%'  \n" + 
                    "       AND USER = 'GENERADOR' AND TABLE_NAME IN ( "+ interfaz +");\n" + 
                    "  \n" + 
                    "  v_sentence VARCHAR2(500);\n" + 
                    "BEGIN\n" + 
                    "  FOR i_clean IN limpia_interfaces\n" + 
                    "  LOOP\n" + 
                    "    v_sentence := 'DELETE FROM '||i_clean.table_name;\n" + 
                    "    EXECUTE IMMEDIATE v_sentence;\n" + 
                    "    DBMS_OUTPUT.PUT_LINE(v_sentence);\n" + 
                    "    COMMIT;\n" + 
                    "    COMMIT;\n" + 
                    "  END LOOP;\n" + 
                    "EXCEPTION\n" + 
                    "  WHEN OTHERS\n" + 
                    "  THEN\n" + 
                    "    ROLLBACK;\n" + 
                    "    DBMS_OUTPUT.PUT_LINE('ERROR: '||SQLERRM);\n" + 
                    "END;";
                    rset = stmt.executeQuery(comando);
                }
            }
            else{
                JOptionPane.showMessageDialog(this,"Por favor selecionar individual o todas");
            }
            stmt.close();
            conn.close();
            rset.close();
        } catch (SQLException f) {
            System.out.println(f.getMessage());
        }
    }
    private void cancelarBorrar_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
