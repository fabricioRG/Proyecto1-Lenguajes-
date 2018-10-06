package analizador.automatas;

/**
 *
 * @author fabricio
 */
public class HerramientasAutomata {

    public HerramientasAutomata() {
    }

    public boolean esDigitoDiferenteCero(char c) {
        return c == UNO || c == DOS || c == TRES || c == CUATRO || c == CINCO || c == SEIS || c == SIETE
                || c == OCHO || c == NUEVE;
    }

    public boolean esDigito(char c) {
        return esDigitoDiferenteCero(c) || esCero(c);
    }

    public boolean esCero(char c) {
        return c == CERO;
    }

    public boolean esEspacio(char c) {
        return c == SALTO_LINEA || c == TAB || c == ESPACIO || c == SALTO_PAGINA || c == RETORNO_CARRITO;
    }

    public boolean esSumaResta(char c) {
        return c == CRUZ || c == GUION;
    }

    public boolean esOperadorAritmetico(char c) {
        return esSumaResta(c) || c == DIAGONAL || c == ASTERISCO || c == MODULO;
    }

    public boolean esSignoPuntuacion(char c) {
        return c == PUNTO || c == COMA || c == DOS_PUNTOS || c == PUNTO_Y_COMA;
    }

    public boolean esSignoAgrupacion(char c) {
        return c == PARENTESIS_IZQUIERDO || c == PARENTESIS_DERECHO || c == CORCHETE_IZQUIERDO || c == CORCHETE_DERECHO
                || c == LLAVE_IZQUIERDA || c == LLAVE_DERECHA;
    }

    public boolean esLetraMayuscula(char c) {
        return c == A || c == B || c == C || c == D || c == E || c == F || c == G || c == H || c == I || c == J || c == K || c == L
                || c == M || c == N || c == O || c == P || c == Q || c == R || c == S || c == T || c == U || c == V || c == W
                || c == X || c == Y || c == Z;
    }

    public boolean esLetraMinuscula(char c) {
        return c == A_LOW || c == B_LOW || c == C_LOW || c == D_LOW || c == E_LOW || c == F_LOW || c == G_LOW 
                || c == H_LOW || c == I_LOW || c == J_LOW || c == K_LOW || c == L_LOW || c == M_LOW || c == N_LOW 
                || c == O_LOW || c == P_LOW || c == Q_LOW || c == R_LOW || c == S_LOW || c == T_LOW || c == U_LOW 
                || c == V_LOW || c == W_LOW || c == X_LOW || c == Y_LOW || c == Z_LOW;
    }
    
    public static final int TAB = 9;
    public static final int SALTO_LINEA = 10;
    public static final int SALTO_PAGINA = 12;
    public static final int RETORNO_CARRITO = 13;
    public static final int ESPACIO = 32;
    public static final int COMILLAS = 34;
    public static final int MODULO = 37;
    public static final int PARENTESIS_IZQUIERDO = 40;
    public static final int PARENTESIS_DERECHO = 41;
    public static final int ASTERISCO = 42;
    public static final int CRUZ = 43;
    public static final int COMA = 44;
    public static final int GUION = 45;
    public static final int PUNTO = 46;
    public static final int DIAGONAL = 47;
    public static final int CERO = 48;
    public static final int UNO = 49;
    public static final int DOS = 50;
    public static final int TRES = 51;
    public static final int CUATRO = 52;
    public static final int CINCO = 53;
    public static final int SEIS = 54;
    public static final int SIETE = 55;
    public static final int OCHO = 56;
    public static final int NUEVE = 57;
    public static final int DOS_PUNTOS = 58;
    public static final int PUNTO_Y_COMA = 59;
    public static final int MENOR = 60;
    public static final int IGUAL = 61;
    public static final int MAYOR = 62;
    public static final int A = 65;
    public static final int B = 66;
    public static final int C = 67;
    public static final int D = 68;
    public static final int E = 69;
    public static final int F = 70;
    public static final int G = 71;
    public static final int H = 72;
    public static final int I = 73;
    public static final int J = 74;
    public static final int K = 75;
    public static final int L = 76;
    public static final int M = 77;
    public static final int N = 78;
    public static final int O = 79;
    public static final int P = 80;
    public static final int Q = 81;
    public static final int R = 82;
    public static final int S = 83;
    public static final int T = 84;
    public static final int U = 85;
    public static final int V = 86;
    public static final int W = 87;
    public static final int X = 88;
    public static final int Y = 89;
    public static final int Z = 90;
    public static final int CORCHETE_IZQUIERDO = 91;
    public static final int CONTRA_DIAGONAL = 92;
    public static final int CORCHETE_DERECHO = 93;
    public static final int DIAGONAL_BAJO = 95;
    public static final int A_LOW = 97;
    public static final int B_LOW = 98;
    public static final int C_LOW = 99;
    public static final int D_LOW = 100;
    public static final int E_LOW = 101;
    public static final int F_LOW = 102;
    public static final int G_LOW = 103;
    public static final int H_LOW = 104;
    public static final int I_LOW = 105;
    public static final int J_LOW = 106;
    public static final int K_LOW = 107;
    public static final int L_LOW = 108;
    public static final int M_LOW = 109;
    public static final int N_LOW = 110;
    public static final int O_LOW = 111;
    public static final int P_LOW = 112;
    public static final int Q_LOW = 113;
    public static final int R_LOW = 114;
    public static final int S_LOW = 115;
    public static final int T_LOW = 116;
    public static final int U_LOW = 117;
    public static final int V_LOW = 118;
    public static final int W_LOW = 119;
    public static final int X_LOW = 120;
    public static final int Y_LOW = 121;
    public static final int Z_LOW = 122;
    public static final int LLAVE_IZQUIERDA = 123;
    public static final int LLAVE_DERECHA = 125;
    public static final String TOKEN_NUMERO_ENTERO = "Numero entero";
    public static final String TOKEN_NUMERO_DECIMAL = "Numero decimal";
    public static final String TOKEN_IDENTIFICADOR = "Identificador";
    public static final String TOKEN_PALABRA_RESERVADA = "Palabra reservada";
    public static final String TOKEN_LITERAL = "Literal";
    public static final String TOKEN_SIGNO_PUNTUACION = "Signo de puntuacion";
    public static final String TOKEN_OPERADOR_ARITMETICO = "Operador aritmetico";
    public static final String TOKEN_SIGNO_AGRUPACION = "Signo de agrupacion";
    public static final String TOKEN_COMENTARIO_UNA_LINEA = "Comentario de una linea";
    public static final String TOKEN_COMENTARIO_BLOQUE = "Comentario en bloque";
    public static final String TOKEN_CARACTER_ESPECIAL = "Caracter especial";
    public static final String TOKEN_ERROR = "Error";

}
