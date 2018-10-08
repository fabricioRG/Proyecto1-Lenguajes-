package analizador.analizadorlexico.backend;

/**
 *
 * @author fabri
 */
public class Buscador {

    public final static String ABRIR_SPAN = "<span style=\"background-color:#009933;color:white;\">";
    public final static String CERRAR_SPAN = "</span>";
    private int coincidencias = 0;
    
    public Buscador() {
        coincidencias = 0;
    }

    public String buscarCadenaEnTexto(String cadena, String texto) {
        String textoFinal = "";
        
        boolean estado = false;
        if (texto.length() >= cadena.length()) {
            for (int i = 0; i <= texto.length() - cadena.length(); i++) {
                estado = false;
                for (int j = 0; j < cadena.length(); j++) {
                    if (texto.charAt(i + j) != cadena.charAt(j)) {
                        break;
                    }
                    if (j == cadena.length() - 1) {
                        i += j;
                        estado = true;
                        coincidencias++;
                    }
                }
                if(estado){
                    textoFinal += ABRIR_SPAN + cadena + CERRAR_SPAN;
                } else {
                    textoFinal += texto.charAt(i);
                }
            }
        }
        return textoFinal;
    }

    public int getCoincidencias() {
        return coincidencias;
    }

    public void setCoincidencias(int coincidencias) {
        this.coincidencias = coincidencias;
    }
}
