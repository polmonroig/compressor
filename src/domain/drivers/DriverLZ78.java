package domain.drivers;

import domain.LZ78;
import persistencia.controllers.DataCtrl;
import java.util.Scanner;

public class DriverLZ78 {


    public static void main(String[] args) {
        Scanner Input = new Scanner(System.in);


        LZ78 encoder = new LZ78();
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
                    DataCtrl.WriteBytesToFile(file + ".lz78", compression);
                    break;
                case "2":
                    System.out.println("Introduce el archivo a descomprimir");
                    file = Input.nextLine();
                    compression = encoder.decompress(DataCtrl.ReadFileAsBytes(file));
                    lastPeriodPos = file.lastIndexOf('.');
                    file = file.substring(0,lastPeriodPos);
                    DataCtrl.WriteBytesToFile(file + ".txt", compression);
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
