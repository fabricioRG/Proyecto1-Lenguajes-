package analizador.automatas;

import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author fabricio
 */
public class Automata extends HerramientasAutomata {

    private int estado = 0;
    private int posicion = 0;
    private String fuente = "";
    private String lexema = "";
    private char caracter;
    private List<String> listaLexema;
    private List<String> listaToken;

    public Automata() {
        listaLexema = new LinkedList<>();
        listaToken = new LinkedList<>();
    }

    public void iniciarAFD(String textoEntrada) {
        estado = 0;
        posicion = 0;
        lexema = "";
        listaLexema.clear();
        listaToken.clear();
        fuente = textoEntrada;

        if (textoEntrada.length() > 0) {
            iniciarProceso();
            imprimirLista();
        }
    }

    public void iniciarProceso() {
        caracter = fuente.charAt(posicion);
        switch (estado) {
            case 0: {
                if (esEspacio(caracter)) {
                    lexema = "";
                } else if (esCero(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 1;
                } else if (esDigitoDiferenteCero(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 2;
                } else if (esSumaResta(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 3;
                } else if (caracter == ASTERISCO || caracter == MODULO) {
                    addList(lexema, TOKEN_OPERADOR_ARITMETICO);
                } else if (esSignoPuntuacion(caracter)) {
                    addList(lexema, TOKEN_SIGNO_PUNTUACION);
                } else if (esSignoAgrupacion(caracter)) {
                    addList(lexema, TOKEN_SIGNO_AGRUPACION);
                } else if (caracter == DIAGONAL) {
                    lexema += Character.toString(caracter);
                    estado = 14;
                } else if (caracter == COMILLAS) {
                    lexema += Character.toString(caracter);
                    estado = 19;
                } else {
                    error();
                }
                break;
            }
            case 1: {
                if (caracter == PUNTO) {
                    lexema += Character.toString(caracter);
                    estado = 5;
                } else if (!esDigito(caracter)) {
                    addList(lexema, TOKEN_NUMERO_ENTERO);
                    nuevoLexema();
                } else {
                    error();
                }
                break;
            }
            case 2: {
                if (esDigito(caracter)) {
                    lexema += Character.toString(caracter);
                } else if (caracter == PUNTO) {
                    lexema += Character.toString(caracter);
                    estado = 6;
                } else {
                    addList(lexema, TOKEN_NUMERO_ENTERO);
                    nuevoLexema();
                }
                break;
            }
            case 3: {
                if (esDigitoDiferenteCero(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 2;
                } else {
                    error();
                }
                break;
            }
            default:
                break;
        }

        posicion++;
        if (posicion >= fuente.length()) {
            if (estado == 1) {
                addList(lexema, TOKEN_NUMERO_ENTERO);
            } else if (estado == 2) {
                addList(lexema, TOKEN_NUMERO_ENTERO);
            } else if (estado == 3) {
                addList(lexema, TOKEN_OPERADOR_ARITMETICO);
            }
        } else {
            iniciarProceso();
        }
    }

    private void nuevoLexema() {
        lexema = "";
        estado = 0;
        posicion--;
    }

    private void error() {
        lexema += Character.toString(caracter);
        posicion++;
        if (posicion >= fuente.length()) {
            estado = 0;
            addList(lexema, TOKEN_ERROR);
        } else {
            caracter = fuente.charAt(posicion);
            if (estado == 1) {
                if (!esDigito(caracter)) {
                    addList(lexema, TOKEN_ERROR);
                    nuevoLexema();
                }
            } else if (esEspacio(caracter)) {
                addList(lexema, TOKEN_ERROR);
                estado = 0;
                lexema = "";
            } else {
                error();
            }
        }
    }

    private void imprimirLista() {
        String auxiliar = "Token    ------------   Lexema\n";
        for (int i = 0; i < listaLexema.size(); i++) {
            auxiliar += listaToken.get(i) + "  -------  " + listaLexema.get(i) + "\n";
        }
        System.out.println(auxiliar);
    }

    private void addList(String lex, String token) {
        listaLexema.add(lex);
        listaToken.add(token);
    }

}
