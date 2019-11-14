
package presentation.controllers;

import domain.controllers.CtrlDomain;

import java.util.Scanner;

public class CtrlPresentation {

    public static void main(String[] args) {
        CtrlDomain ctrlDomain = new CtrlDomain();
        Scanner Input = new Scanner(System.in);


        boolean input = false;

        while(!input){
            System.out.println("Que desea hacer?");
            System.out.println("1-comprimir/2-descomprimir/3-nada");
            String userInput = Input.nextLine();
            String file;
            switch (userInput.toLowerCase().trim()) {
                case "1":
                    file = getUserInput(Input, "Introduce el archivo que deseas comprimir");
                    if (file.endsWith(".txt")) {
                        userInput = getUserInput(Input, "Que algoritmo desea utilizar?(1-LZ78, 2-LZW, 3-LZSS, 4-auto)");
                        switch (userInput.toLowerCase().trim()) {
                            case "1":
                                ctrlDomain.comprimir("lz78", file);
                                break;
                            case "2":
                                ctrlDomain.comprimir("lzw", file);
                                break;
                            case "3":
                                ctrlDomain.comprimir("lzss", file);
                                break;
                            case "4":
                                System.out.println("Lo siento, esta funcion aun no esta implementada");
                                break;
                            default:
                                System.out.println("La opcion introducida no es valida, por favor intentalo de nuevo :(");
                                break;
                        }
                    } else if (file.endsWith(".ppm")) {
                        String calidad = getUserInput(Input, "Con que grado de calidad desea comprimir? (Siendo 0 el más bajo y 12 el más alto)");
                        ctrlDomain.setCalidad(Integer.parseInt(calidad));
                        ctrlDomain.comprimir("jpeg", file);
                    } else System.out.println("La opcion introducida no es valida, por favor intentalo de nuevo :(");
                    break;
                case "2":
                    file = getUserInput(Input, "Introduce el archivo que deseas descomprimir");
                    if (file.endsWith("lz78")) ctrlDomain.descomprimir("lz78", file);
                    else if (file.endsWith("lzss")) ctrlDomain.descomprimir("lzss", file);
                    else if (file.endsWith("lzw")) ctrlDomain.descomprimir("lzw", file);
                    else if (file.endsWith("jpeg")) ctrlDomain.descomprimir("jpeg", file);
                    else System.out.println("La opcion introducida no es valida, por favor intentalo de nuevo :(");
                case "3":
                    input = true;
                    break;
                default:
                    System.out.println("La opcion introducida no es valida, por favor intentalo de nuevo :(");
                    break;
            }
        }
    }

    private static String getUserInput(Scanner input, String s) {
        String userInput;
        System.out.println(s);
        userInput = input.nextLine();
        return userInput;
    }
}
