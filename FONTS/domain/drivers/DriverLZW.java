package domain.drivers;

import domain.LZW;
import data.controllers.DataCtrl;

import java.util.Scanner;

public class DriverLZW {

    public static void main(String[] args) {
        Scanner Input = new Scanner(System.in);

        LZW encoder = new LZW();

        boolean input = false;

        System.out.println("Opciones:");

        System.out.println("1.-Comprimir  2.-Descomprimir  3.-Salir");

        while(!input) {
            System.out.print("introducir opción: ");
            String userInput = Input.nextLine();
            switch (userInput) {
                case "1":
                    System.out.print("Introduce el archivo a comprimir: ");
                    String file = Input.nextLine();
                    byte[] compression = encoder.compress(DataCtrl.ReadFileAsBytes(file));
                    int lastPeriodPos = file.lastIndexOf('.');
                    file = file.substring(0,lastPeriodPos);
                    DataCtrl.WriteBytesToFile(file + ".lzw", compression);
                    break;
                case "2":
                    System.out.print("Introduce el archivo a descomprimir: ");
                    file = Input.nextLine();
                    compression = encoder.decompress(DataCtrl.ReadFileAsBytes(file));
                    lastPeriodPos = file.lastIndexOf('.');
                    file = file.substring(0,lastPeriodPos);
                    DataCtrl.WriteBytesToFile(file + "_new" + ".txt", compression);
                    break;
                case "3":
                    input = true;
                    break;
                default:
                    System.out.println("No ha selecionado ningua de las opciones validas. \n Intentelo de nuevo.");
                    System.out.println("Opciones:");
                    System.out.println("1.-Comprimir 2.- Descomprimir 3.-Salir");
                    break;
            }
        }
    }



}
