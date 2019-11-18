package domain.drivers;

import domain.Stats;

import java.util.Scanner;

public class DriverStats {
    public static void main(String[] args) {
        Scanner Input = new Scanner(System.in);

        Stats est = new Stats();
        boolean input = false;
        System.out.println("Opciones:");
        String[] functions = {"Set tamaño original del archivo", "Set tamaño del archivo comprimido", "Set tiempo de compression",
                "Set factor de compression", "Set velocidad de compression", "Get tamaño original del archivo","Get tamaño del archivo comprimido",
                "Get tiempo de compression", "Get factor de compression", "Get velocidad de compression",  "salir"};
        for(int i = 0; i < functions.length; ++i){
            System.out.println((i + 1)  + "-" + functions[i]);
        }
        while(!input){
            System.out.println("Introduce una opcion");
            String userInput = Input.nextLine();

            switch (userInput) {
                case "1":
                    System.out.println("Introduce el tamaño del archivo");
                    String file = Input.nextLine();
                    float e = Float.parseFloat(file);
                    est.setOriginalFileSize(e);
                    break;
                case "2":
                    System.out.println("Introduce el tamaño del archivo comprimido");
                    file = Input.nextLine();
                    e = Float.parseFloat(file);
                    est.setCompressedFileSize(e);
                    break;
                case "3":
                    System.out.println("Introduce el tiempo de compression");
                    file = Input.nextLine();
                    e = Float.parseFloat(file);
                    est.setCompressionTime(e);
                case "4":
                    System.out.println("Introduce el factor de compression");
                    file = Input.nextLine();
                    e = Float.parseFloat(file);
                    est.setCompressionDegree(e);
                    break;
                case "5":
                    System.out.println("Introduce la velocidad de compression");
                    file = Input.nextLine();
                    e = Float.parseFloat(file);
                    est.setCompressionSpeed(e);
                    break;
                case "6":
                    e = est.getOriginalFileSize();
                    System.out.println(e);
                    break;
                case "7":
                    e = est.getCompressedFileSize();
                    System.out.println(e);
                    break;
                case "8":
                    e = est.getCompressionTime();
                    System.out.println(e);
                    break;
                case "9":
                    e = est.getCompressionDegree();
                    System.out.println(e);
                    break;
                case "10":
                    e = est.getCompressionSpeed();
                    System.out.println(e);
                    break;
                case "11":
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
