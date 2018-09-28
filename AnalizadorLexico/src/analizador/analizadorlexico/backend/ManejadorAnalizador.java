package analizador.analizadorlexico.backend;

import analizador.archivos.frontend.GuardadorArchivo;
import analizador.analizadorlexico.frontend.*;
import analizador.archivos.frontend.ManejadorArchivos;
import analizador.archivos.frontend.SeleccionadorArchivo;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;

/**
 *
 * @author fabricio
 */
public class ManejadorAnalizador {

    static final String NEW_TAB = "new tab";
    Analizador analizador = null;

    public ManejadorAnalizador(Analizador analizador) {
        this.analizador = analizador;
    }

    public void agregarVentana() {
        AreaTexto at = new AreaTexto();
        analizador.jTabbedPane.add(NEW_TAB, at);
        estadoGuardar();
    }

    public void agregarVentana(String path, String texto) {
        AreaTexto at = new AreaTexto();
        at.getjEditorPane1().setText(texto);
        analizador.jTabbedPane.add(path, at);
    }

    public void abrirDocumento(int i) {
        boolean error = false;
        SeleccionadorArchivo sa = new SeleccionadorArchivo(analizador, true);
        ManejadorArchivos ma = new ManejadorArchivos();
        File archivo = null;
        sa.setVisible(true);

        if (sa.getFile() != null) {
            archivo = sa.getFile();
            for (int j = 0; j < i; j++) {
                if (analizador.jTabbedPane.getTitleAt(j).equals(archivo.getAbsolutePath())) {
                    JOptionPane.showMessageDialog(analizador, "El archivo ya se encuentra abierto", "Informacion", JOptionPane.WARNING_MESSAGE);
                    error = true;
                    break;
                }
            }
            if (!error) {
                agregarVentana(archivo.getAbsolutePath(), ma.obtenerTextoDeArchivo(archivo));
            }
        }
    }

    public void cerrarVentana(int ventana) {
        AreaTexto at = (AreaTexto) analizador.jTabbedPane.getSelectedComponent();
        String texto = at.getjEditorPane1().getText();
        System.out.println(texto);
//        analizador.jTabbedPane.setTitleAt(ventana, "Ventana");
        analizador.jTabbedPane.remove(ventana);
        estadoGuardar();
    }

    public void guardarArchivo(int i) {
        if (analizador.jTabbedPane.getTitleAt(i).equals(NEW_TAB)) {
            GuardadorArchivo ga = new GuardadorArchivo(analizador, true);
            ga.setVisible(true);
        }
    }

    private void estadoGuardar() {
        boolean estado = true;
        if (analizador.jTabbedPane.getComponentCount() < 1) {
            estado = false;
        } else {
            estado = true;
        }
        analizador.jMenuItemSave.setEnabled(estado);
        analizador.jMenuItemSaveAs.setEnabled(estado);
    }

    public void showInformacionDesarrollador(){
        ImageIcon desarrollador = new ImageIcon("desarrollador.png");
        String informacion = "";
        informacion = "                     Analizador Lexico\n\n"
                + "                      Desarrollado por:\n"
                + "            Ivan Fabricio Racancoj García\n"
                + "                            201731115\n4to Semestre Ing. Sistemas CUNOC - USAC";
        JOptionPane.showMessageDialog(analizador, informacion, "About...", JOptionPane.INFORMATION_MESSAGE, desarrollador);
    }
    
}
