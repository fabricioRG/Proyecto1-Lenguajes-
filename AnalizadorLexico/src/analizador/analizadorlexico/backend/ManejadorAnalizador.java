package analizador.analizadorlexico.backend;

import analizador.archivos.frontend.GuardadorArchivo;
import analizador.analizadorlexico.frontend.*;
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
    }
    
    public void cerrarVentana(int ventana){
        AreaTexto at = (AreaTexto) analizador.jTabbedPane.getSelectedComponent();
        String texto = at.getjEditorPane1().getText();
        System.out.println(texto);
//        analizador.jTabbedPane.setTitleAt(ventana, "Ventana");
        analizador.jTabbedPane.remove(ventana);
    }
    
    public void guardarArchivo(){
        GuardadorArchivo ga = new GuardadorArchivo(analizador, true);
        ga.setVisible(true);
    }
    
}
