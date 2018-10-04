package analizador.automatas;

import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author fabricio
 */
public class Automatas extends AsciiValues {

    private int estado = 0;
    private int posicion = 0;
    private String fuente = "";
    private String lexema = "";
    private char caracter;
    private List<String> listaLexema = new LinkedList<>();
    private List<String> listaToken = new LinkedList<>();

    public Automatas() {
    }

    public void iniciarAFD() {
        estado = 0;
        posicion = 0;
        lexema = "";
        listaLexema.clear();
        listaToken.clear();
        fuente = "0 12 +123";

        iniciarProceso();
        imprimirLista();

    }

    public void iniciarProceso() {
        caracter = fuente.charAt(posicion);
        switch (estado) {
            case 0: {
                if (esEspacio(caracter)) {
                    lexema = "";
                } else if (caracter == CERO) {
                    lexema += Character.toString(caracter);
                    estado = 1;
                } else if (esDigitoDiferenteCero(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 2;
                } else if (caracter == CRUZ || caracter == GUION) {
                    lexema += Character.toString(caracter);
                    estado = 3;
                } else {

                }
                break;
            }
            case 1: {
                if (esEspacio(caracter)) {
                    addList(lexema, TOKEN_NUMERO_ENTERO);
                    lexema = "";
                    estado = 0;
                }
                break;
            }
            case 2: {
                if (esDigitoDiferenteCero(caracter) || caracter == CERO) {
                    lexema += Character.toString(caracter);
                } else if (esEspacio(caracter)) {
                    addList(lexema, TOKEN_NUMERO_ENTERO);
                    lexema = "";
                    estado = 0;
                }
                break;
            }
            case 3: {
                if (esDigitoDiferenteCero(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 2;
                } else {

                }
                break;
            }
            default:
                break;
        }

        posicion++;
//        imprimir();
        if (posicion >= fuente.length()) {
            if (estado == 1) {
                addList(lexema, TOKEN_NUMERO_ENTERO);
            } else if (estado == 2) {
                addList(lexema, TOKEN_NUMERO_ENTERO);
            }
        } else {
            iniciarProceso();
        }
    }

    private void imprimirLista() {
        String auxiliar = "Token    -------   Lexema\n";
        for (int i = 0; i < listaLexema.size(); i++) {
            auxiliar += listaToken.get(i) + "  -------  " + listaLexema.get(i) + "\n";
        }
        System.out.println(auxiliar);
    }

    private void imprimir() {
        System.out.println("estado:" + estado + " caracter:" + caracter + " lexema:"
                + lexema + " posicion:" + posicion);
    }

    private boolean esEspacio(char c) {
        return c == SALTO_LINEA || c == TAB || c == ESPACIO || c == SALTO_PAGINA;
    }

    private void addList(String lex, String token) {
        listaLexema.add(lex);
        listaToken.add(token);
    }

    private boolean esDigitoDiferenteCero(char caracter) {
        boolean estado = false;
        if (caracter == UNO) {
            estado = true;
        } else if (caracter == DOS) {
            estado = true;
        } else if (caracter == TRES) {
            estado = true;
        } else if (caracter == CUATRO) {
            estado = true;
        } else if (caracter == CINCO) {
            estado = true;
        } else if (caracter == SEIS) {
            estado = true;
        } else if (caracter == SIETE) {
            estado = true;
        } else if (caracter == OCHO) {
            estado = true;
        } else if (caracter == NUEVE) {
            estado = true;
        }
        return estado;
    }

}
