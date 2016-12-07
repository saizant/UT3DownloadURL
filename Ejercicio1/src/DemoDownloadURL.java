import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DemoDownloadURL {
	public static void main(String[] args) {
		
		try{
			
			byte[] buffer = new byte[4096];
			int n = -1;
			String urlString;
			
			// 1. pedimos una URL por l铆nea de comandos
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Introduce una URL para descargar un archivo: ");
			urlString = stdin.readLine();
			
			//Obtengo el 鷏timo trozo de la URL para dar el nombre del recurso al archivo a exportar
			String[] trozos = urlString.split("/");
			String nombre = trozos[trozos.length-1];
			
			// 2. creamos el objeto URL
			URL url = new URL(urlString);
			
			// 3. Obtenemos un objeto HttpURLConnection. openConnection 
			HttpURLConnection conexion = (HttpURLConnection)url.openConnection();
			
			// 4. configuramos la conexi贸n al m茅todo GET. setRequestMethod
			conexion.setRequestMethod("GET");
			
			// 5. Nos conectamos. connect
			conexion.connect();
			
			// 6. Obtenemos y imprimimos el c贸digo de respuesta. getResponseCode
			int codigoRespuesta = conexion.getResponseCode();
			System.out.println("C骴igo de respuesta: " + codigoRespuesta);
			
			// 7. Obtenemos y imprimimos el tama帽o del recurso. ContentLength
			int tamanyoRecurso = conexion.getContentLength();
			System.out.println("Tama駉 del recurso: " + tamanyoRecurso);
			
			// 8. Guardamos el stream a un fichero con el nombre del recurso
			//    en caso de que el c贸digo sea correcto. (BufferedInputStream -> FileOutputStream)
			if (codigoRespuesta == 200) {
				//Streams y fichero a exportar
				InputStream input = conexion.getInputStream();					
				File fichero = new File(nombre + ".dat");
				OutputStream output = new FileOutputStream(fichero);
				
				//Mientras se lea se va escribiendo
				while ((n = input.read(buffer)) != -1) {
					output.write(buffer, 0, n);
				}
				
				//Se cierran los Streams y se avisa
				input.close();
				output.close();
				
				System.out.println("Se ha exportado el fichero correctamente. ");
				
			} else {
				// 9. Damos un error en caso de que el c贸digo sea incorrecto
				System.err.println("Error: C骴igo de respuesta de la URL incorrecto. ");
			}
			
			
		} catch (MalformedURLException e) {
			//Se comprueba si es una URL correcta
			System.err.println("Error de URL mal formada. ");
		} catch (IOException e) {
			System.err.println("Error de input/output. ");
		}
					

	}

}