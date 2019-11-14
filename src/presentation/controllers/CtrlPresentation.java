
package presentation.controllers;

import domain.controllers.CtrlDomain;

import java.util.Scanner;

public class CtrlPresentation {

    public static void main(String[] args) {
        CtrlDomain ctrlDomain = new CtrlDomain();
        Scanner Input = new Scanner(System.in);

        boolean input = false;

        while(!input){

            String userInput;
            String[] functions = {"comprimir", "descomprimir", "salir"};
            writeFunctions(functions);
            userInput = getUserInput(Input, "Introduce una opcion");
            String file;
            int midaArxiuInicial;
            int midaArxiuFinal ;
            float tiempoCompresio;
            float grauCompresio;
            float velocitatCompresio;
            switch (userInput) {
                case "1":
                    file = getUserInput(Input, "Introduce el archivo que deseas comprimir");
                    if (file.endsWith(".txt")) {
                        String[] algoritmos = {"LZ78", "LZW", "LZSS", "auto"};
                        writeFunctions(algoritmos);
                        userInput = getUserInput(Input, "Introduce una opcion");
                        switch (userInput) {
                            case "1":
                                ctrlDomain.comprimir("lz78", file);
                                midaArxiuInicial = ctrlDomain.getMidaArxiuInicial("lz78");
                                midaArxiuFinal = ctrlDomain.getMidaArxiuFinal("lz78");
                                tiempoCompresio = ctrlDomain.getTempsCompressio("lz78");
                                grauCompresio = ctrlDomain.getGrauCompressio("lz78");
                                velocitatCompresio = ctrlDomain.getVelocitatCompressio("lz78");
                                showStats(midaArxiuInicial, midaArxiuFinal, tiempoCompresio, grauCompresio, velocitatCompresio);
                                break;
                            case "2":
                                ctrlDomain.comprimir("lzw", file);
                                midaArxiuInicial = ctrlDomain.getMidaArxiuInicial("lzw");
                                midaArxiuFinal = ctrlDomain.getMidaArxiuFinal("lzw");
                                tiempoCompresio = ctrlDomain.getTempsCompressio("lzw");
                                grauCompresio = ctrlDomain.getGrauCompressio("lzw");
                                velocitatCompresio = ctrlDomain.getVelocitatCompressio("lzw");
                                showStats(midaArxiuInicial, midaArxiuFinal, tiempoCompresio, grauCompresio, velocitatCompresio);
                                break;
                            case "3":
                                ctrlDomain.comprimir("lzss", file);
                                midaArxiuInicial = ctrlDomain.getMidaArxiuInicial("lzss");
                                midaArxiuFinal = ctrlDomain.getMidaArxiuFinal("lzss");
                                tiempoCompresio = ctrlDomain.getTempsCompressio("lzss");
                                grauCompresio = ctrlDomain.getGrauCompressio("lzss");
                                velocitatCompresio = ctrlDomain.getVelocitatCompressio("lzss");
                                showStats(midaArxiuInicial, midaArxiuFinal, tiempoCompresio, grauCompresio, velocitatCompresio);
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
                        midaArxiuInicial = ctrlDomain.getMidaArxiuInicial("jpeg");
                        midaArxiuFinal = ctrlDomain.getMidaArxiuFinal("jpeg");
                        tiempoCompresio = ctrlDomain.getTempsCompressio("jpeg");
                        grauCompresio = ctrlDomain.getGrauCompressio("jpeg");
                        velocitatCompresio = ctrlDomain.getVelocitatCompressio("jpeg");
                        showStats(midaArxiuInicial, midaArxiuFinal, tiempoCompresio, grauCompresio, velocitatCompresio);
                    } else {
                        System.out.println("El archivo introducido no es valido, porfavor introduce un archivo .txt o .ppm");
                    }
                    break;
                case "2":
                    file = getUserInput(Input, "Introduce el archivo que deseas descomprimir");
                    if (file.endsWith("lz78")) ctrlDomain.descomprimir("lz78", file);
                    else if (file.endsWith("lzss")) ctrlDomain.descomprimir("lzss", file);
                    else if (file.endsWith("lzw")) ctrlDomain.descomprimir("lzw", file);
                    else if (file.endsWith("jpeg")) ctrlDomain.descomprimir("jpeg", file);
                    else System.out.println("El archivo introducido no es valido, porfavor introduce un archivo .txt o .ppm");
                    break;
                case "3":
                input = true;
                break;
                default:
                    System.out.println("La opcion introducida no es valida, por favor intentalo de nuevo");
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

    private static void showStats(int midaArxiuInicial, int midaArxiuFinal, float tiempoCompresio, float grauCompresio, float velocitatCompresio){
        System.out.println("Estadisticas de la compression actual:");
        System.out.println("Tamaño archivo inicial: " + midaArxiuInicial);
        System.out.println("Tamaño archivo final: " + midaArxiuFinal);
        System.out.println("Tiempo de compresion: " + tiempoCompresio + " milisegundos");
        System.out.println("Grado de compresion: " + grauCompresio);
        System.out.println("Velocidad de compresion: " + velocitatCompresio + " bytes/milisegundos");
    }

    private static void writeFunctions(String[] functions){
        System.out.println("Opciones:");
        for(int i = 0; i < functions.length; ++i){
            System.out.println((i + 1)  + "-" + functions[i]);
        }
    }
}
