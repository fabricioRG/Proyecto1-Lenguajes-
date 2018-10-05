package analizador.tokens.backend;

/**
 *
 * @author fabricio
 */
public class Token {

private String nombreToken;
private String lexema;
private int fila;
private int colummna;

    public Token(String nombreToken, String lexema, int fila, int colummna) {
        this.nombreToken = nombreToken;
        this.lexema = lexema;
        this.fila = fila;
        this.colummna = colummna;
    }

    public String getNombreToken() {
        return nombreToken;
    }

    public void setNombreToken(String nombreToken) {
        this.nombreToken = nombreToken;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColummna() {
        return colummna;
    }

    public void setColummna(int colummna) {
        this.colummna = colummna;
    }
    
}
