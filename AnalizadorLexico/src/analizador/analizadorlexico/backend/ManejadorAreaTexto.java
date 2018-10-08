package analizador.analizadorlexico.backend;

import analizador.analizadorlexico.frontend.AreaTexto;
import analizador.automatas.HerramientasAutomata;
import analizador.automatas.Automata;
import analizador.tokens.frontend.TablaTokens;
import javax.swing.JOptionPane;
import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

/**
 *
 * @author fabricio
 */
public class ManejadorAreaTexto extends HerramientasAutomata {

    private AreaTexto at = null;
    private Automata aut = null;

    public ManejadorAreaTexto(AreaTexto at) {
        this.at = at;
        this.aut = new Automata(this);
    }
    
    //Metodo que tiene como funcion principal iniciar el automata encargado de evaluar los caracteres
    //contenidos en el area de texto o "jEditorPane"
    public void iniciarAutomata(){
        aut.iniciarAFD(getPlainText());
        if(aut.getListaErrores().size() > 0){
            at.actualizarErrores(aut.getListaErrores());
            at.jPanelErrores.setVisible(true);
        } else {
            at.jPanelErrores.setVisible(false);
        }
    }
    
    //Metodo que devuelve un texto plano, tomando como texto un texto en html
    public String getPlainText() {
        Source htmlSource = new Source(at.getjEditorPane1().getText());
        Segment htmlSeg = new Segment(htmlSource, 0, htmlSource.length());
        Renderer htmlRend = new Renderer(htmlSeg);
        return htmlRend.toString();
    }

    //Metodo que devuelve la columna actual del cursor
    public int getColumn() {
        String texto = getPlainText();
        int posicion = at.getjEditorPane1().getCaretPosition();
        String textoLimpio = texto.replaceAll("\r\n", "\n");
        char[] cadenaChar = textoLimpio.toCharArray();
        int contador = 1;
        if (posicion > 1) {
            for (int i = 0; i < posicion - 1; i++) {
                contador++;
                if (cadenaChar[i] == SALTO_LINEA) {
                    contador = 1;
                }
            }
        } else {
            contador = posicion;
        }
        return contador;
    }

    //Metodo que devuelve la linea actual del cursor
    public int getLine() {
        String texto = getPlainText();
        int posicion = at.getjEditorPane1().getCaretPosition();
        String textoLimpio = texto.replaceAll("\r\n", "\n");
        char[] cadenaChar = textoLimpio.toCharArray();
        int contador = 1;
        for (int i = 0; i < posicion - 1; i++) {
            if (cadenaChar[i] == SALTO_LINEA) {
                contador++;
            }
        }
        return contador;
    }

    public void mostrarTokens(){
        TablaTokens tt = new TablaTokens(null, true);
        if (!aut.getListaTokens().isEmpty() && aut.getListaErrores().isEmpty()){
            tt.actualizarTokens(aut.getListaTokens());
            tt.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(at, "         No es posible mostrar los tokens disponibles.\nPues no hay tokens disponibles "
                    + "o hay lexemas no validos","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void subrayarTexto(String cadenaABuscar){
        Buscador buscador = new Buscador();
        String textoFinal = "<html>\n"
                + "<head>\n"
                + "</head>\n"
                + "<body style=\"color:white;font-family:Open Sans Light;padding:4px;\">\n"
                + "<pre>\n"
                + buscador.buscarCadenaEnTexto(cadenaABuscar, getPlainText())
                + "</pre>\n"
                + "</body>\n"
                + "</html>\n"
                + "";
        at.getjEditorPane1().setText(textoFinal);
        at.getjLabelCadena().setText(cadenaABuscar);
        at.getjLabelCoincidencias().setText(Integer.toString(buscador.getCoincidencias()));
    }
    
}
