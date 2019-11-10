package presentation.controllers;


import domain.LZ78;
import domain.controllers.CtrlDomain;

import java.util.Scanner;

public class CtrlPresentation {

    public static void main(String[] args) {
        CtrlDomain ctrlDomain = new CtrlDomain();
        Scanner Input = new Scanner(System.in);


        boolean input = false;

        while(!input){
            System.out.println("Que desea hacer?");
            System.out.println("comprimir/descomprimir/nada");
            String userInput = Input.nextLine();
            switch (userInput.toLowerCase().trim()) {
                case "comprimir":
                    userInput = getUserInput(Input, "Desea comprimir un texto o una imagen?");
                    switch (userInput.toLowerCase().trim()){
                        case "texto":
                            userInput = getUserInput(Input, "Que algoritmo desea utilizar?(LZ78, LZW, LSS, auto)");
                            switch (userInput.toLowerCase().trim()){
                                case "lz78":
                                    userInput = getUserInput(Input, "Introduce el archivo que deseas comprimir");
                                    ctrlDomain.comprimir("lz78", userInput);
                                    break;
                                case "lzSS":
                                    System.out.println("Lo siento, esta funcion aun no esta implementada");
                                    break;
                                case "lzW":
                                    System.out.println("Lo siento, esta funcion aun no esta implementada");
                                    break;
                                case "auto":
                                    System.out.println("Lo siento, esta funcion aun no esta implementada");
                                    break;
                                default:
                                    System.out.println("La opcion introducida no es valida, por favor intentalo de nuevo :(");
                                    break;
                            }
                            break;
                        case "imagen":
                            System.out.println("Lo siento, esta funcion aun no esta implementada");
                            break;
                        default:
                            System.out.println("La opcion introducida no es valida, por favor intentalo de nuevo :(");
                            break;
                    }
                    break;
                case "descomprimir":
                    userInput = getUserInput(Input, "Desea descomprimir un texto o una imagen?");
                    switch (userInput.toLowerCase().trim()){
                        case "texto":
                            userInput = getUserInput(Input, "Que algoritmo desea utilizar?(LZ78, LZW, LSS)");
                            switch (userInput.toLowerCase().trim()){
                                case "lz78":
                                    userInput = getUserInput(Input, "Introduce el archivo que deseas decomprimir");
                                    ctrlDomain.descomprimir("lz78", userInput);
                                    break;
                                case "lzSS":
                                    System.out.println("Lo siento, esta funcion aun no esta implementada");
                                    break;
                                case "lzW":
                                    System.out.println("Lo siento, esta funcion aun no esta implementada");
                                    break;
                                default:
                                    System.out.println("La opcion introducida no es valida, por favor intentalo de nuevo :(");
                                    break;
                            }
                            break;
                        case "imagen":
                            System.out.println("Lo siento, esta funcion aun no esta implementada");
                            break;
                        default:
                            System.out.println("La opcion introducida no es valida, por favor intentalo de nuevo :(");
                            break;
                    }
                    break;
                case "nada":
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
