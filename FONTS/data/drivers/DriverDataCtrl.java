package data.drivers;

import data.controllers.DataCtrl;

import java.util.Arrays;
import java.util.Scanner;

class DriverDataCtrl{
    public static void main(String[] args) {
        Scanner Input = new Scanner(System.in);

        boolean input = false;

        System.out.println("Opciones:");
        System.out.println("1.-Lleer archivo como bytes 2.- Escrivir archivo como bytes 3.-Salir");

        while(!input) {
            System.out.print("introducir opción: ");
            String userInput = Input.nextLine();
            switch (userInput) {
                case "1":
                    System.out.print("Dirección del archivo a leer: ");
                    userInput = Input.nextLine();
                    System.out.println(Arrays.toString(DataCtrl.ReadFileAsBytes(userInput)));
                    break;
                case "2":
                    System.out.print("Texto a guardar: ");
                    userInput = Input.nextLine();
                    byte[] texto = userInput.getBytes();
                    System.out.print("Dirección donde guardarlo: ");
                    userInput = Input.nextLine();
                    DataCtrl.WriteBytesToFile(userInput, texto );

                    break;
                case "3":
                    input = true;
                    break;
                default:
                    System.out.println("No ha selecionado ningua de las opciones validas. \nIntentelo de nuevo.");
                    System.out.println("Opciones:");
                    System.out.println("1.-Lleer archivo como bytes 2.- Escrivir archivo como bytes 3.-Salir");
                    break;
            }
        }

    }

}