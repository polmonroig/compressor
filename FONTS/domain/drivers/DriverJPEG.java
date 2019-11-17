package domain.drivers;

import domain.JPEG;
import data.controllers.DataCtrl;

import java.util.Scanner;

public class DriverJPEG {
    public static void main(String[] args) {
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
                    byte[] compression = encoder.compress(DataCtrl.ReadFileAsBytes(file));
                    int lastPeriodPos = file.lastIndexOf('.');
                    file = file.substring(0,lastPeriodPos);
                    DataCtrl.WriteBytesToFile(file + ".jpeg", compression);
                    break;
                case "2":
                    System.out.println("Lo siento esta funcion todavia no esta implementada");
                    /*file = Input.nextLine();
                    compression = encoder.descomprimir(CtrlPersistencia.ReadFileAsBytes(file));
                    lastPeriodPos = file.lastIndexOf('.');
                    file = file.substring(0,lastPeriodPos);
                    CtrlPersistencia.WriteBytesToFile(file + ".ppm", compression);*/
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
    }
}
