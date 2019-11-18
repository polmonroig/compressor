package domain.drivers;


import domain.GlobalStats;

import java.util.Scanner;

public class DriverGlobalStats {
    public static void main(String[] args) {
        Scanner Input = new Scanner(System.in);


        GlobalStats est = new GlobalStats();
        boolean input = false;
        System.out.println("Opciones:");
        String[] functions = {"Set numero de fitxers", "Obtenir numero de fitxers", "Afegir mida del archiu original",
                "Afegir mida del archiu comprimit", "Afegir temps de compressió", "Afegir factor de compressió", "Afegir velocitat de compressió", "salir"};
        for(int i = 0; i < functions.length; ++i){
            System.out.println((i + 1)  + "-" + functions[i]);
        }
        while(!input){
            System.out.println("Introduce una opcion");
            String userInput = Input.nextLine();

            switch (userInput) {
                case "1":
                    System.out.println("Introduce el numero de fitxers");
                    String file = Input.nextLine();
                    float e = Float.parseFloat(file);
                    est.setNumberFiles(e);
                    break;
                case "2":
                    e = est.getNumberFiles();
                    System.out.println(e);
                    break;
                case "3":
                    System.out.println("Introduce la mida del archiu original");
                    file = Input.nextLine();
                    e = Float.parseFloat(file);
                    est.addOriginalSize(e);
                    break;
                case "4":
                    System.out.println("Introduce la mida del archiu comprimit");
                    file = Input.nextLine();
                    e = Float.parseFloat(file);
                    est.addCompressedSize(e);
                    break;
                case "5":
                    System.out.println("Introduce el temps de compresio");
                    file = Input.nextLine();
                    e = Float.parseFloat(file);
                    est.addCompressionTime(e);
                    break;
                case "6":
                    System.out.println("Introduce el factor de compressió");
                    file = Input.nextLine();
                    e = Float.parseFloat(file);
                    est.addCompressionDegree(e);
                    break;
                case "7":
                    System.out.println("Introduce la velocitat de compressio");
                    file = Input.nextLine();
                    e = Float.parseFloat(file);
                    est.addCompressionSpeed(e);
                    break;
                case "8":
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
