package analizador.automatas;

import analizador.analizadorlexico.backend.ManejadorAreaTexto;
import java.util.List;
import java.util.LinkedList;
import analizador.tokens.backend.*;

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
    private int posicionInicial = 0;
    private int filaActual = 0;
    private List<String> listaLexema;
    private List<String> listaToken;
    private List<Token> listaTokens;
    private List<ErrorLexema> listaErrores;
    private ManejadorAreaTexto mat = null;

    public Automata(ManejadorAreaTexto mat) {
        this.listaLexema = new LinkedList<>();
        this.listaToken = new LinkedList<>();
        this.listaTokens = new LinkedList<>();
        this.listaErrores = new LinkedList<>();
        this.mat = mat;
    }

    //Metodo que inica el AUTOMATA FINITO DETERMINISTA
    public void iniciarAFD(String textoEntrada) {
        this.estado = 0;
        this.posicion = 0;
        this.lexema = "";
        this.posicionInicial = 1;
        this.filaActual = 1;
        this.listaLexema.clear();
        this.listaToken.clear();
        this.listaTokens.clear();
        this.listaErrores.clear();
        this.fuente = textoEntrada;

        if (textoEntrada.length() > 0) {
            iniciarProceso();
            imprimirLista();
        }
    }

    public void iniciarProceso() {
        caracter = fuente.charAt(posicion);
        switch (estado) {
            case 0: {
                if(caracter == SALTO_LINEA){
                    lexema = "";
                    filaActual++;
                    posicionInicial = 1;
                } else if (esEspacio(caracter)) {
                    lexema = "";
                    posicionInicial++;
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
                    addToken(Character.toString(caracter), TOKEN_OPERADOR_ARITMETICO);
                } else if (esSignoPuntuacion(caracter)) {
                    addToken(Character.toString(caracter), TOKEN_SIGNO_PUNTUACION);
                } else if (esSignoAgrupacion(caracter)) {
                    addToken(Character.toString(caracter), TOKEN_SIGNO_AGRUPACION);
                } else if (caracter == DIAGONAL) {
                    lexema += Character.toString(caracter);
                    estado = 14;
                } else if (caracter == COMILLAS) {
                    lexema += Character.toString(caracter);
                    estado = 19;
                } else {
                    errorInNextCharacter();
                }
                break;
            }
            case 1: {
                if (caracter == PUNTO) {
                    lexema += Character.toString(caracter);
                    estado = 5;
                } else if (!esDigito(caracter)) {
                    addToken(lexema, TOKEN_NUMERO_ENTERO);
                    nuevoLexema();
                } else {
                    errorInNextCharacter();
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
                    addToken(lexema, TOKEN_NUMERO_ENTERO);
                    nuevoLexema();
                }
                break;
            }
            case 3: {
                if (esDigitoDiferenteCero(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 2;
                } else if (esCero(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 4;
                } else {
                    addToken(lexema, TOKEN_OPERADOR_ARITMETICO);
                    nuevoLexema();
                }
                break;
            }
            case 4: {
                if (caracter == PUNTO) {
                    lexema += Character.toString(caracter);
                    estado = 5;
                } else {
                    errorInNextCharacter();
                }
                break;
            }
            case 5: {
                if (esCero(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 7;
                } else if (esDigitoDiferenteCero(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 8;
                } else {
                    errorInCurrentCharacter();
                }
                break;
            }
            case 6: {
                if (esCero(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 9;
                } else if (esDigitoDiferenteCero(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 8;
                } else {
                    errorInCurrentCharacter();
                }
                break;
            }
            case 7: {
                if (esDigitoDiferenteCero(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 8;
                } else if (esCero(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 10;
                } else {
                    addToken(lexema, TOKEN_NUMERO_DECIMAL);
                    nuevoLexema();
                }
                break;
            }
            case 8: {
                if (esDigito(caracter)) {
                    lexema += Character.toString(caracter);
                } else {
                    addToken(lexema, TOKEN_NUMERO_DECIMAL);
                    nuevoLexema();
                }
                break;
            }
            case 9: {
                if (esDigitoDiferenteCero(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 8;
                } else if (esCero(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 7;
                } else {
                    addToken(lexema, TOKEN_NUMERO_DECIMAL);
                    nuevoLexema();
                }
                break;
            }
            case 10: {
                if (esCero(caracter)) {
                    lexema += Character.toString(caracter);
                } else if (esDigitoDiferenteCero(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 8;
                } else {
                    errorInCurrentCharacter();
                }
                break;
            }
            case 14: {
                if (caracter == DIAGONAL) {
                    lexema += Character.toString(caracter);
                    estado = 15;
                } else if (caracter == ASTERISCO) {
                    lexema += Character.toString(caracter);
                    estado = 16;
                } else {
                    addToken(lexema, TOKEN_OPERADOR_ARITMETICO);
                    nuevoLexema();
                }
                break;
            }
            case 15: {
                if (caracter == SALTO_LINEA) {
                    addToken(lexema, TOKEN_COMENTARIO_UNA_LINEA);
                    nuevoLexema();
                } else {
                    lexema += Character.toString(caracter);
                }
                break;
            }
            case 16: {
                if (caracter == ASTERISCO) {
                    lexema += Character.toString(caracter);
                    estado = 17;
                } else {
                    lexema += Character.toString(caracter);
                }
                break;
            }
            case 17: {
                if (caracter == ASTERISCO) {
                    lexema += Character.toString(caracter);
                } else if (caracter == DIAGONAL) {
                    lexema += Character.toString(caracter);
                    addToken(lexema, TOKEN_COMENTARIO_BLOQUE);
                    estado = 0;
                    lexema = "";
                } else {
                    lexema += Character.toString(caracter);
                    estado = 16;
                }
                break;
            }
            case 19: {
                if (caracter == COMILLAS) {
                    lexema += Character.toString(caracter);
                    addToken(lexema, TOKEN_LITERAL);
                    estado = 0;
                    lexema = "";
                } else {
                    lexema += Character.toString(caracter);
                }
                break;
            }
            default:
                break;
        }

        posicion++;
        if (posicion >= fuente.length()) {
            switch (estado) {
                case 1:
                    addToken(lexema, TOKEN_NUMERO_ENTERO);
                    break;
                case 2:
                    addToken(lexema, TOKEN_NUMERO_ENTERO);
                    break;
                case 3:
                    addToken(lexema, TOKEN_OPERADOR_ARITMETICO);
                    break;
                case 4:
                    addError(lexema);
                    break;
                case 5:
                    addError(lexema);
                    break;
                case 6:
                    addError(lexema);
                    break;
                case 7:
                    addToken(lexema, TOKEN_NUMERO_DECIMAL);
                    break;
                case 8:
                    addToken(lexema, TOKEN_NUMERO_DECIMAL);
                    break;
                case 9:
                    addToken(lexema, TOKEN_NUMERO_DECIMAL);
                    break;
                case 10:
                    addError(lexema);
                    break;
                case 14:
                    addToken(lexema, TOKEN_OPERADOR_ARITMETICO);
                    break;
                case 15:
                    addToken(lexema, TOKEN_COMENTARIO_UNA_LINEA);
                    break;
                case 16:
                    addError(lexema);
                    break;
                case 17:
                    addError(lexema);
                    break;
                case 19:
                    addError(lexema);
                    break;
            }
        } else {
            iniciarProceso();
        }
    }

    private void errorInNextCharacter() {
        lexema += Character.toString(caracter);
        posicion++;
        if (posicion >= fuente.length()) {
            estado = 0;
            addError(lexema);
        } else {
            caracter = fuente.charAt(posicion);
            if (estado == 1) {
                if (!esDigito(caracter)) {
                    addError(lexema);
                    nuevoLexema();
                } else {
                    errorInNextCharacter();
                }
            }
        }
    }

    private void errorInCurrentCharacter() {
        addError(lexema);
        nuevoLexema();
    }

    private void nuevoLexema() {
        lexema = "";
        estado = 0;
        posicion--;
        posicionInicial = posicion; 
    }

    private void imprimirLista() {
        String auxiliar = "Token    ------------   Lexema\n";
        for (int i = 0; i < listaLexema.size(); i++) {
            auxiliar += listaToken.get(i) + "  -------  " + listaLexema.get(i) + "\n";
        }
        System.out.println(auxiliar);
    }

    private void addError(String lexema) {
        ErrorLexema el = new ErrorLexema(lexema, 0, 0);
        this.listaErrores.add(el);
    }

    private void addToken(String lex, String tokn) {
        Token token = new Token(tokn, lex, posicionInicial, filaActual);
        this.listaTokens.add(token);
        listaLexema.add(lex);
        listaToken.add(tokn);
    }

    public List<ErrorLexema> getListaErrores() {
        return listaErrores;
    }

    public void setListaErrores(List<ErrorLexema> listaErrores) {
        this.listaErrores = listaErrores;
    }

    public List<Token> getListaTokens() {
        return listaTokens;
    }

    public void setListaTokens(List<Token> listaTokens) {
        this.listaTokens = listaTokens;
    }

}
