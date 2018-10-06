package analizador.tokens.backend;

/**
 *
 * @author fabricio
 */
public class Token {

private String nombreToken;
private String lexema;
private int colummna;
private int fila;

    public Token(String nombreToken, String lexema, int colummna, int fila) {
        this.nombreToken = nombreToken;
        this.lexema = lexema;
        this.colummna = colummna;
        this.fila = fila;
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

    public int getColummna() {
        return colummna;
    }

    public void setColummna(int colummna) {
        this.colummna = colummna;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }



}
