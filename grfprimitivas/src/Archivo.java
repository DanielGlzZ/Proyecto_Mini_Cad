import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Archivo {

  public static ArrayList<String> leerArchivo(File archivo) {
    ArrayList<String> lineas = new ArrayList();

    try {
      FileReader flujo = new FileReader(archivo);
      BufferedReader buffer = new BufferedReader(flujo);
      String linea = buffer.readLine();
      while (linea != null) {
        lineas.add(linea);
        linea = buffer.readLine();
      }
      buffer.close();
      flujo.close();
    } catch (IOException ex) {
      System.out.println(ex);
      System.exit(-1);
    }

    return lineas;
  }

  public static ArrayList<String> leerArchivo(String archivo) {
    return leerArchivo(new File(archivo));
  }

  public static String leerArchivo1Linea(File archivo) {
    String linea = "";
    try {
      FileReader flujo = new FileReader(archivo);
      BufferedReader buffer = new BufferedReader(flujo);
      linea = buffer.readLine();
      if (linea == null) {
        System.out.println("El archivo se encuentra vació");
      }
      buffer.close();
      flujo.close();
    } catch (IOException ex) {
      System.out.println("Error de archivo" + ex);
      System.exit(-1);
    }
    return linea;
  }

  public static void grabarArchivo(File archivo, ArrayList<String> lineas) {
    try {
      FileWriter flujo = new FileWriter(archivo, false);
      BufferedWriter buffer = new BufferedWriter(flujo);
      for (String linea : lineas) {
        buffer.write(linea);
        buffer.newLine();
      }
      buffer.close();
      flujo.close();

    } catch (IOException ex) {
      System.out.println("Error de lectura!" + ex);
      System.exit(-1);
    }

  }
  
  public static void grabarArchivoSobres(File archivo, ArrayList<String> lineas){
    try {
      FileWriter flujo = new FileWriter(archivo);
      BufferedWriter buffer = new BufferedWriter(flujo);
      for (String linea : lineas) {
        buffer.write(linea);
        buffer.newLine();
      }
      buffer.close();
      flujo.close();

    } catch (IOException ex) {
      System.out.println("Error de lectura!" + ex);
      System.exit(-1);
    }
  }

  public static void grabarArchivo(String archivo, ArrayList<String> lineas) {
    grabarArchivo(new File(archivo), lineas);
  }
}
