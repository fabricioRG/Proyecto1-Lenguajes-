package analizador.analizadorlexico.backend;

import analizador.archivos.frontend.GuardadorArchivo;
import analizador.analizadorlexico.frontend.*;
import analizador.archivos.frontend.ManejadorArchivos;
import java.io.File;
import javax.swing.JEditorPane;

/**
 *
 * @author fabricio
 */
public class ManejadorAnalizador {

    static final String NEW_TAB =  "new tab";
    Analizador analizador = null;
    
    public ManejadorAnalizador(Analizador analizador) {
        this.analizador = analizador;
    }
    
    public void agregarVentana(){
        AreaTexto at = new AreaTexto();
        analizador.jTabbedPane.add(NEW_TAB, at);
        estadoGuardar();
    }
    
    public void abrirDocumento(){
        ManejadorArchivos ma = new ManejadorArchivos();
        ma.obtenerTextoDeArchivo("‪C:\\Users\\fabri\\Documents\\Algoritmos metodos ordenacion.txt");
    }
    
    public void cerrarVentana(int ventana){
        AreaTexto at = (AreaTexto) analizador.jTabbedPane.getSelectedComponent();
        String texto = at.getjEditorPane1().getText();
        System.out.println(texto);
//        analizador.jTabbedPane.setTitleAt(ventana, "Ventana");
        analizador.jTabbedPane.remove(ventana);
        estadoGuardar();
    }
    
    public void guardarArchivo(){
        GuardadorArchivo ga = new GuardadorArchivo(analizador, true);
        ga.setVisible(true);
    }
    
    private void estadoGuardar(){
        boolean estado = true;
        if(analizador.jTabbedPane.getComponentCount() < 1){
            estado = false;
        } else {
            estado = true;
        }
        analizador.jMenuItemSave.setEnabled(estado);
            analizador.jMenuItemSaveAs.setEnabled(estado);
    }
    
}
