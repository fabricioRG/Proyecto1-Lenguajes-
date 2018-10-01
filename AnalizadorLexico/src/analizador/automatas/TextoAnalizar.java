package analizador.automatas;

/**
 *
 * @author fabricio
 */
public class TextoAnalizar extends AsciiValues {

    private char[] cadenaChar = null;
    private int posicionActual = 0;

    public TextoAnalizar() {
    }

    public void obtenerTexto(String texto) {
        this.cadenaChar = texto.toCharArray();
        String textoLimpio = "";
        for (int i = 0; i < cadenaChar.length; i++) {
            estado1(i);
        }
    }

    private void estado1(int posicion) {
        switch (cadenaChar[posicion]) {
            case MENOR:
                posicion++;
                estado2(posicion);
                break;

        }
    }

    private void estado2(int posicion) {
        switch (cadenaChar[posicion]) {
            case D_LOW:
                posicion++;
                estado3(posicion);
                break;
        }
    }

    private void estado3(int posicion) {
        switch (cadenaChar[posicion]) {
            case I_LOW:
                posicion++;
                break;
        }
    }

    private void estado4() {

    }

}
