package domain.drivers;

import domain.JPEG;
import data.controllers.DataCtrl;

import java.util.Scanner;

public class DriverJPEG {
   /* public static void main(String[] args) {
        Scanner Input = new Scanner(System.in);
        JPEG encoder = new JPEG();
        boolean input = false;
        System.out.println("Opciones:");
        String[] functions = {"comprimir", "descomprimir", "salir"};
        for(int i = 0; i < functions.length; ++i){
            System.out.println((i + 1)  + "-" + functions[i]);
        }
        while(!input){
            System.out.println("Introduce una opcion");
            String userInput = Input.nextLine();

            switch (userInput) {
                case "1":
                    System.out.println("Introduce el archivo a comprimir");
                    String file = Input.nextLine();
                    System.out.println("Con que grado de calidad desea comprimir? (Siendo 0 el más bajo y 12 el más alto)");
                    String quality = Input.nextLine();
                    encoder.setQuality(Integer.parseInt(quality));
                    byte[] compression = encoder.compress(DataCtrl.ReadFile(file));
                    int lastPeriodPos = file.lastIndexOf('.');
                    file = file.substring(0,lastPeriodPos);
                    DataCtrl.WriteFile(file + ".jpeg", compression);
                    break;
                case "2":
                    System.out.println("Introduce el archivo a descomprimir");
                    file = Input.nextLine();
                    compression = encoder.decompress(DataCtrl.ReadFile(file));
                    lastPeriodPos = file.lastIndexOf('.');
                    file = file.substring(0,lastPeriodPos);
                    DataCtrl.WriteFile(file + ".ppm", compression);
                    break;
                case "3":
                    input = true;
                    break;
                default:
                    System.out.println("La opcion introducida no es valida, por favor intentalo de nuevo");
                    for(int i = 0; i < functions.length; ++i){
                        System.out.println((i + 1)  + "-" + functions[i]);
                    }
                    break;
            }
        }
    }*/
}
