package analizador.archivos.frontend;

import java.io.*;

/**
 *
 * @author fabri
 */
public class ManejadorArchivos {

    File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;

    public ManejadorArchivos() {
    }

    
    
    public void obtenerTextoDeArchivo(String ruta) {
        String linea = "";
        String texto = "";
        try {
            archivo = new File(ruta);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            while ((linea = br.readLine()) != null) {
                texto += linea + "\n";
            }
            if (!texto.isEmpty()) {
                System.out.println(texto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }
}
