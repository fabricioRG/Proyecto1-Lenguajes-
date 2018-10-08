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
    private int columna = 0;
    private int columnaActual = 0;
    private int columnaError = 0;
    private int filaActual = 0;
    private int saltosLinea = 0;
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
        this.columna = 1;
        this.columnaError = 1;
        this.columnaActual = 0;
        this.filaActual = 1;
        this.saltosLinea = 0;
        this.listaLexema.clear();
        this.listaToken.clear();
        this.listaTokens.clear();
        this.listaErrores.clear();
        this.fuente = textoEntrada;
        if (textoEntrada.length() > 0) {
            iniciarProceso();
//            imprimirLista();
        }
    }

    /**
     * Metodo que contiene 137 "case" para su evalucacion, los cuales van
     * seleccionandose mediante se cumple con las condiciones de cada uno, se
     * lleva un contador de linea, asi como un contador de columna
     * respectivamente para conocer su posicion en cualquier momento en el
     * reporte de tokens
     */
    public void iniciarProceso() {
        caracter = fuente.charAt(posicion);
        switch (estado) {
            case 0: {
                if (caracter == SALTO_LINEA) {
                    lexema = "";
                    filaActual++;
                    columnaActual = -1;
                    columna = 1;
                    columnaError = 1;
                } else if (esEspacio(caracter)) {
                    lexema = "";
                    columna++;
                    columnaError++;
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
                    lexema += Character.toString(caracter);
                    estado = 11;
                } else if (esSignoPuntuacion(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 12;
                } else if (esSignoAgrupacion(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 13;
                } else if (caracter == DIAGONAL) {
                    lexema += Character.toString(caracter);
                    estado = 14;
                } else if (caracter == COMILLAS) {
                    lexema += Character.toString(caracter);
                    estado = 19;
                } else if (caracter == B_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 22;
                } else if (caracter == C_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 29;
                } else if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 37;
                } else if (caracter == F_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 47;
                } else if (caracter == I_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 62;
                } else if (caracter == P_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 84;
                } else if (caracter == R_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 104;
                } else if (caracter == S_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 110;
                } else if (caracter == T_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 120;
                } else if (caracter == V_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 127;
                } else if (caracter == W_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 131;
                } else if (esLetraMinusculaNoInicial(caracter) || esLetraMayuscula(caracter) || caracter == GUION_BAJO) {
                    lexema += Character.toString(caracter);
                    estado = 136;
                } else {
                    lexema += Character.toString(caracter);
                    estado = 138;
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
                    agregarOperador();
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
            case 11: {
                agregarOperador();
                nuevoLexema();
                break;
            }
            case 12: {
                agregarSignoPuntuacion();
                nuevoLexema();
                break;
            }
            case 13: {
                agregarSignoAgrupacion();
                nuevoLexema();
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
                    addToken(lexema, TOKEN_DIVISION);
                    nuevoLexema();
                }
                break;
            }
            case 15: {
                if (caracter == SALTO_LINEA) {
                    estado = 21;
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
                    if (caracter == SALTO_LINEA) {
                        filaActual++;
                        columnaActual = -1;
                        saltosLinea++;
                    }
                }
                break;
            }
            case 17: {
                if (caracter == ASTERISCO) {
                    lexema += Character.toString(caracter);
                } else if (caracter == DIAGONAL) {
                    lexema += Character.toString(caracter);
                    estado = 18;
                } else {
                    lexema += Character.toString(caracter);
                    estado = 16;
                    if (caracter == SALTO_LINEA) {
                        filaActual++;
                        columnaActual = -1;
                        saltosLinea++;
                    }
                }
                break;
            }
            case 18: {
                addToken(lexema, TOKEN_COMENTARIO_BLOQUE);
                nuevoLexema();
                break;
            }
            case 19: {
                if (caracter == COMILLAS) {
                    lexema += Character.toString(caracter);
                    estado = 20;
                } else {
                    lexema += Character.toString(caracter);
                }
                break;
            }
            case 20: {
                addToken(lexema, TOKEN_LITERAL);
                nuevoLexema();
                break;
            }
            case 21: {
                addToken(lexema, TOKEN_COMENTARIO_UNA_LINEA);
                nuevoLexema();
                nuevoLexema();
                break;
            }
            case 22: {
                if (caracter == O_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 23;
                } else if (esCaracterDeIdentificador(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 137;
                } else {
                    errorInCurrentCharacter();
                }
                break;
            }
            case 23: {
                if (caracter == O_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 24;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 24: {
                if (caracter == L_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 25;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 25: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 26;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 26: {
                if (caracter == A_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 27;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 27: {
                if (caracter == N_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 28;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 28: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 29: {
                if (caracter == A_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 30;
                } else if (caracter == L_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 33;
                } else if (esCaracterDeIdentificador(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 137;
                } else {
                    errorInCurrentCharacter();
                }
                break;
            }
            case 30: {
                if (caracter == S_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 31;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 31: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 32;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 32: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 33: {
                if (caracter == A_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 34;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 34: {
                if (caracter == S_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 35;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 35: {
                if (caracter == S_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 36;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 36: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 37: {
                if (caracter == L_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 38;
                } else if (caracter == X_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 41;
                } else if (esCaracterDeIdentificador(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 137;
                } else {
                    errorInCurrentCharacter();
                }
                break;
            }
            case 38: {
                if (caracter == S_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 39;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 39: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 40;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 40: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 41: {
                if (caracter == T_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 42;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 42: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 43;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 43: {
                if (caracter == N_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 44;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 44: {
                if (caracter == D_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 45;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 45: {
                if (caracter == S_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 46;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 46: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 47: {
                if (caracter == A_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 48;
                } else if (caracter == I_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 52;
                } else if (caracter == L_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 56;
                } else if (caracter == O_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 60;
                } else if (esCaracterDeIdentificador(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 137;
                } else {
                    errorInCurrentCharacter();
                }
                break;
            }
            case 48: {
                if (caracter == L_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 49;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 49: {
                if (caracter == S_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 50;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 50: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 51;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 51: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 52: {
                if (caracter == N_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 53;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 53: {
                if (caracter == A_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 54;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 54: {
                if (caracter == L_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 55;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 55: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 56: {
                if (caracter == O_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 57;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 57: {
                if (caracter == A_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 58;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 58: {
                if (caracter == T_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 59;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 59: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 60: {
                if (caracter == R_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 61;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 61: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 62: {
                if (caracter == M_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 63;
                } else if (caracter == F_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 68;
                } else if (caracter == N_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 76;
                } else if (esCaracterDeIdentificador(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 137;
                } else {
                    errorInCurrentCharacter();
                }
                break;
            }
            case 63: {
                if (caracter == P_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 64;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 64: {
                if (caracter == O_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 65;
                } else if (caracter == L_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 69;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 65: {
                if (caracter == R_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 66;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 66: {
                if (caracter == T_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 67;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 67: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 68: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 69: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 70;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 70: {
                if (caracter == M_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 71;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 71: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 72;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 72: {
                if (caracter == N_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 73;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 73: {
                if (caracter == T_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 74;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 74: {
                if (caracter == S_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 75;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 75: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 76: {
                if (caracter == T_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 77;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 77: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 78;
                } else if (esCaracterDeIdentificador(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 137;
                } else {
                    addToken(lexema, lexema);
                    nuevoLexema();
                }
                break;
            }
            case 78: {
                if (caracter == R_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 79;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 79: {
                if (caracter == F_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 80;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 80: {
                if (caracter == A_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 81;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 81: {
                if (caracter == C_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 82;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 82: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 83;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 83: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 84: {
                if (caracter == A_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 85;
                } else if (caracter == R_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 91;
                } else if (esCaracterDeIdentificador(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 137;
                } else {
                    errorInCurrentCharacter();
                }
                break;
            }
            case 85: {
                if (caracter == C_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 86;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 86: {
                if (caracter == K_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 87;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 87: {
                if (caracter == A_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 88;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 88: {
                if (caracter == G_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 89;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 89: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 90;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 90: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 91: {
                if (caracter == I_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 92;
                } else if (caracter == O_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 97;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 92: {
                if (caracter == V_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 93;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 93: {
                if (caracter == A_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 94;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 94: {
                if (caracter == T_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 95;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 95: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 96;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 96: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 97: {
                if (caracter == T_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 98;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 98: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 99;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 99: {
                if (caracter == C_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 100;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 100: {
                if (caracter == T_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 101;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 101: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 102;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 102: {
                if (caracter == D_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 103;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 103: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 104: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 105;
                } else if (esCaracterDeIdentificador(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 137;
                } else {
                    errorInCurrentCharacter();
                }
                break;
            }
            case 105: {
                if (caracter == T_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 106;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 106: {
                if (caracter == U_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 107;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 107: {
                if (caracter == R_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 108;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 108: {
                if (caracter == N_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 109;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 109: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 110: {
                if (caracter == H_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 111;
                } else if (caracter == T_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 115;
                } else if (esCaracterDeIdentificador(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 137;
                } else {
                    errorInCurrentCharacter();
                }
                break;
            }
            case 111: {
                if (caracter == O_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 112;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 112: {
                if (caracter == R_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 113;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 113: {
                if (caracter == T_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 114;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 114: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 115: {
                if (caracter == A_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 116;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 116: {
                if (caracter == T_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 117;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 117: {
                if (caracter == I_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 118;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 118: {
                if (caracter == C_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 119;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 119: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 120: {
                if (caracter == H_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 121;
                } else if (caracter == R_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 124;
                } else if (esCaracterDeIdentificador(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 137;
                } else {
                    errorInCurrentCharacter();
                }
                break;
            }
            case 121: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 122;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 122: {
                if (caracter == N_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 123;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 123: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 124: {
                if (caracter == U_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 125;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 125: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 126;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 126: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 127: {
                if (caracter == O_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 128;
                } else if (esCaracterDeIdentificador(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 137;
                } else {
                    errorInCurrentCharacter();
                }
                break;
            }
            case 128: {
                if (caracter == I_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 129;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 129: {
                if (caracter == D_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 130;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 130: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 131: {
                if (caracter == H_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 132;
                } else if (esCaracterDeIdentificador(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 137;
                } else {
                    errorInCurrentCharacter();
                }
                break;
            }
            case 132: {
                if (caracter == I_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 133;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 133: {
                if (caracter == L_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 134;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 134: {
                if (caracter == E_LOW) {
                    lexema += Character.toString(caracter);
                    estado = 135;
                } else {
                    verificarCaracter(caracter);
                }
                break;
            }
            case 135: {
                verificarCaracterFinal(caracter);
                break;
            }
            case 136: {
                if (esCaracterDeIdentificador(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 137;
                } else {
                    errorInCurrentCharacter();
                }
                break;
            }
            case 137: {
                if (esCaracterDeIdentificador(caracter)) {
                    lexema += Character.toString(caracter);
                    estado = 137;
                } else {
                    addToken(lexema, TOKEN_IDENTIFICADOR);
                    nuevoLexema();
                }
                break;
            }
            case 138: {
                if (esEspacio(caracter)) {
                    errorInCurrentCharacter();
                } else {
                    errorInCurrentCharacter();
                }
                break;
            }
            default:
                break;
        }

        posicion++; // despues de evaluar un caracter, la posicion de evaluacion aumenta
        columnaActual++; //Contador que tiene la posicion del caracter en cada linea
        if (posicion >= fuente.length()) { //Si la longitud es igual se evalua segun el estado en que haya quedado
            if (esEstadoFinal(estado)) { //si el lexema queda en un estado de aceptacion se agrega
                addToken(lexema, lexema);
            } else if (esEstadoNoFinal(estado)) { //si no es estado de aceptacion se reporta el error
                addError(lexema);
            } else if (esIdentificadorFinal(estado)) { //Si es un identificador se agrega
                addToken(lexema, TOKEN_IDENTIFICADOR);
            } else {
                //Funcion que agrega los lexemas en un estado de aceptacion segun su token
                switch (estado) {
                    case 1:
                        addToken(lexema, TOKEN_NUMERO_ENTERO);
                        break;
                    case 2:
                        addToken(lexema, TOKEN_NUMERO_ENTERO);
                        break;
                    case 3:
                        agregarOperador();
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
                    case 11:
                        agregarOperador();
                        break;
                    case 12:
                        agregarSignoPuntuacion();
                        break;
                    case 13:
                        agregarSignoAgrupacion();
                        break;
                    case 14:
                        addToken(lexema, TOKEN_DIVISION);
                        break;
                    case 15:
                        addToken(lexema, TOKEN_COMENTARIO_UNA_LINEA);
                        break;
                    case 18:
                        addToken(lexema, TOKEN_COMENTARIO_BLOQUE);
                        break;
                    case 20:
                        addToken(lexema, TOKEN_LITERAL);
                        break;
                    case 21:
                        addToken(lexema, TOKEN_COMENTARIO_UNA_LINEA);
                        break;
                    default:
                        break;
                }
            }
        } else {
            //Se vuelve a llamar a el mismo hasta que la longitud sea mayor o igual a la del texto
            iniciarProceso();
        }
    }

    //Metodo que varifica si contiene caracteres de un identificador, de lo contrario es una palabra reservada
    private void verificarCaracterFinal(char c) {
        if (esCaracterDeIdentificador(c)) {
            lexema += Character.toString(c);
            estado = 137;
        } else {
            addToken(lexema, lexema);
            nuevoLexema();
        }
    }

    //Metodo que verifica si el caracter actual no sigue con el camino de un automata pero pertenece
    //a un caracter de identificador, entonces se convierte a identificador, igual si no se completa
    private void verificarCaracter(char c) {
        if (esCaracterDeIdentificador(c)) {
            lexema += Character.toString(c);
            estado = 137;
        } else {
            addToken(lexema, TOKEN_IDENTIFICADOR);
            nuevoLexema();
        }
    }

    //Metodo que reporta errores que pueden ser seguidos de mas errores en conjunto
    private void errorInNextCharacter() {
        lexema += Character.toString(caracter);
        posicion++;
        columnaActual++;
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

    //Metodo que reporta errores encontrados en el caracter evaluado
    private void errorInCurrentCharacter() {
        addError(lexema);
        nuevoLexema();
    }

    private void nuevoLexema() {
        lexema = "";
        estado = 0;
        posicion--;
        columnaActual--;
    }

    private void imprimirLista() {
        String auxiliar = "Token    ------------   Lexema\n";
        for (int i = 0; i < listaLexema.size(); i++) {
            auxiliar += listaToken.get(i) + "  -------  " + listaLexema.get(i) + "\n";
        }
        System.out.println(auxiliar);
    }

    //Metodo encargado de agregar el error en la lista de objetos "listaErrores"
    private void addError(String lexema) {
        ErrorLexema el = new ErrorLexema(lexema, columnaError, filaActual);
        this.listaErrores.add(el);
        columnaError = columnaActual + 1;
    }

    //Metodo encargado de agregar los tokens en la lista de tokens "listaTokens"
    private void addToken(String lex, String tokn) {
        Token token = null;
        if (estado == 18) {
            int fila = filaActual - saltosLinea;
            token = new Token(tokn, lex, columna, fila);
            saltosLinea = 0;
        } else {
            token = new Token(tokn, lex, columna, filaActual);
        }
        this.listaTokens.add(token);
//        listaLexema.add(lex);
//        listaToken.add(tokn);
        columna = columnaActual + 1;
        columnaError = columna;
    }

    private void agregarOperador() {
        char c = fuente.charAt(posicion - 1);
        if (c == CRUZ) {
            addToken(lexema, TOKEN_SUMA);
        } else if(c == GUION){
            addToken(lexema, TOKEN_RESTA);
        } else if(c == ASTERISCO){
            addToken(lexema, TOKEN_MULTIPLICACION);
        } else if (c == DIAGONAL){
            addToken(lexema, TOKEN_DIVISION);
        } else {
            addToken(lexema, TOKEN_MODULO);
        }
    }

    private void agregarSignoPuntuacion(){
        char c = fuente.charAt(posicion - 1);
        if(c == PUNTO){
            addToken(lexema, TOKEN_PUNTO);
        } else if (c == COMA){
            addToken(lexema, TOKEN_COMA);
        } else if(c == PUNTO_Y_COMA){
            addToken(lexema, TOKEN_PUNTO_Y_COMA);
        } else {
            addToken(lexema, TOKEN_DOS_PUNTOS);
        }
    }
    
    private void agregarSignoAgrupacion(){
        char c = fuente.charAt(posicion - 1);
        if(c == PARENTESIS_IZQUIERDO){
            addToken(lexema, TOKEN_PARENTESIS_IZQUIERDO);
        } else if (c == PARENTESIS_DERECHO){
            addToken(lexema, TOKEN_PARENTESIS_DERECHO);
        } else if(c == CORCHETE_IZQUIERDO){
            addToken(lexema, TOKEN_CORCHETE_IZQUIERDO);
        } else if (c == CORCHETE_DERECHO){
            addToken(lexema, TOKEN_CORCHETE_DERECHO);
        } else if(c == LLAVE_IZQUIERDA){
            addToken(lexema, TOKEN_LLAVE_IZQUIERDA);
        } else {
            addToken(lexema, TOKEN_LLAVE_DERECHA);
        }
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
