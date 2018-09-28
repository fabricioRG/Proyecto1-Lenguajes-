package analizador.archivos.backend;

import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author fabri
 */
public class ManejadorArchivos {

    public ManejadorArchivos() {
    }

    //Metodo encargado de obtener el texto contenido en un archivo de texto .txt y retornarlo como String
    public String obtenerTextoDeArchivo(File archivo) throws FileNotFoundException, Exception {
        FileReader fr = null;
        BufferedReader br = null;
        String linea = "";
        String texto = "";
        if (!archivo.exists()) {
            throw new FileNotFoundException("No se ha encontrado el archivo, intentalo de nuevo");
        }
        fr = new FileReader(archivo);
        br = new BufferedReader(fr);
        while ((linea = br.readLine()) != null) {
            texto += linea + "\n";
        }
        br.close();
        return texto;
    }

    //Metodo encargado de escribir en un archivo de texto el texto recibido como parametro
    public void escribirArchivoTexto(File archivo, String texto) throws Exception {
        FileWriter fichero = null;
        BufferedWriter bw = null;
        fichero = new FileWriter(archivo);
        bw = new BufferedWriter(fichero);
        bw.write(texto);
        bw.close();
    }

}
