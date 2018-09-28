package analizador.archivos.frontend;

import java.io.*;

/**
 *
 * @author fabri
 */
public class ManejadorArchivos {

    public ManejadorArchivos() {
    }

    public String obtenerTextoDeArchivo(File archivo) {
        FileReader fr = null;
        BufferedReader br = null;
        String linea = "";
        String texto = "";
        try {
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            while ((linea = br.readLine()) != null) {
                texto += linea + "\n";
            }
            return texto;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (fr != null ) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return texto;
    }
}
