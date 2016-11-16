package estacion_de_trabajo;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class InicioFrame extends JFrame {
    private JMenuBar menuBar = new JMenuBar();
    private JMenu Archivo = new JMenu();
    private JMenuItem Configuracion = new JMenuItem();
    private JMenuItem Estacion = new JMenuItem();
    private JMenuItem Salir = new JMenuItem();
    ActionListener actionListener = new MenuBarActionListener();

    public InicioFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setSize( new Dimension(400, 300) );
        this.setTitle("Estacion de Trabajo de Carga de Datos");
        this.setJMenuBar(menuBar);
        Archivo.setText("Archivo");
        Configuracion.setText("Configuracion");
        Configuracion.addActionListener(actionListener);
        Estacion.setText("Estacion de Trabajo");
        Estacion.addActionListener(actionListener);
        Salir.setText("Salir");
        Salir.addActionListener(actionListener);
        Archivo.add(Configuracion);
        Archivo.add(Estacion);
        Archivo.add(Salir);
        menuBar.add(Archivo);
    }
    
    class  MenuBarActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //label.setText("Seleccion: " + e.getActionCommand());
            if(e.getActionCommand()=="Configuracion"){
                JFrame frame = new ConfiguracionFrame();
                frame.setSize(new Dimension(412, 266));
                frame.setVisible(true);
            }
            if(e.getActionCommand()=="Estacion de Trabajo"){
                JFrame frame = new EstacionTrabajoFrame();
                frame.setVisible(true);
            }
            if(e.getActionCommand()=="Salir"){
                System.exit(0);
            }
        }
    }
}
