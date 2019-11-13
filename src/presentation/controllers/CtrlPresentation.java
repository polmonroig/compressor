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
            System.out.println("comprimir/descomprimir/nada");
            String userInput = Input.nextLine();
            String file;
            switch (userInput.toLowerCase().trim()) {
                case "comprimir":
                    file = getUserInput(Input, "Introduce el archivo que deseas comprimir");
                    userInput = getUserInput(Input, "Desea comprimir un texto o una imagen?");
                    switch (userInput.toLowerCase().trim()){
                        case "texto":
                            userInput = getUserInput(Input, "Que algoritmo desea utilizar?(LZ78, LZW, LSS, auto)");
                            switch (userInput.toLowerCase().trim()){
                                case "lz78":
                                    ctrlDomain.comprimir("lz78", file);
                                    break;
                                case "lzss":
                                    ctrlDomain.comprimir("lzss", file);;
                                    break;
                                case "lzw":
                                    ctrlDomain.comprimir("lzw", file);;
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
                    file = getUserInput(Input, "Introduce el archivo que deseas decomprimir");
                    userInput = getUserInput(Input, "Desea descomprimir un texto o una imagen?");
                    switch (userInput.toLowerCase().trim()){
                        case "texto":
                            userInput = getUserInput(Input, "Que algoritmo desea utilizar?(LZ78, LZW, LSS)");
                            switch (userInput.toLowerCase().trim()){
                                case "lz78":
                                    ctrlDomain.descomprimir("lz78", file);
                                    break;
                                case "lzss":
                                    ctrlDomain.descomprimir("lzss", file);;
                                    break;
                                case "lzw":
                                    ctrlDomain.descomprimir("lzw", file);;
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
