package analizador.archivos.frontend;

import java.io.*;

/**
 *
 * @author fabri
 */
public class ManejadorArchivos {

    
        FileReader fr = null;
        BufferedReader br = null;
    
    public ManejadorArchivos() {
    }

    public void obtenerTextoDeArchivo(String archivo) {
        String texto = null;
        try {        
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            texto = br.toString();
            if(texto.isEmpty()){
                System.out.println(texto);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }

    }
}
