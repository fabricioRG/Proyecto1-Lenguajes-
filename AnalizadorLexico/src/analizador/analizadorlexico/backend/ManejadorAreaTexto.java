package analizador.analizadorlexico.backend;

import analizador.analizadorlexico.frontend.AreaTexto;
import analizador.automatas.HerramientasAutomata;
import analizador.automatas.Automata;
import analizador.automatas.TextoAnalizar;
import javax.swing.JEditorPane;
import javax.swing.text.Document;
import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import org.jsoup.Jsoup;

/**
 *
 * @author fabricio
 */
public class ManejadorAreaTexto extends HerramientasAutomata {

    private AreaTexto at = null;

    public ManejadorAreaTexto(AreaTexto at) {
        this.at = at;
    }

    public void iniciarAutomata(){
        Automata aut = new Automata();
        aut.iniciarAFD(getPlainText());
    }
    
    public String getPlainText() {
        Source htmlSource = new Source(at.getjEditorPane1().getText());
        Segment htmlSeg = new Segment(htmlSource, 0, htmlSource.length());
        Renderer htmlRend = new Renderer(htmlSeg);
        return htmlRend.toString();
    }

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

}
