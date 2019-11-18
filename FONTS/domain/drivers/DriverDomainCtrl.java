package domain.drivers;


import domain.controllers.DomainCtrl;

import java.util.Arrays;
import java.util.Scanner;

public class DriverDomainCtrl {
    public static void main(String args[]) {
        Scanner Input = new Scanner(System.in);
        DomainCtrl domainCtrl = new DomainCtrl();

        boolean input = false;
        String file = "";


        while(!input) {
            System.out.println("Opciones: ");
            System.out.println("1.- Imprimir tamaño del archivo original medio");
            System.out.println("2.- Imprimir tamaño de archivo comprimido medio");
            System.out.println("3.- Imprimir grado de compresión medio");
            System.out.println("4.- Imprimir velocidad de compresión medio");
            System.out.println("5.- Imprimir tiempo de compresión medio");
            System.out.println("6.- Imprimir numero de archivos comprimidos");
            System.out.println("7.- Comprimir");
            System.out.println("8.- Descomprimir");
            System.out.println("9.- Configurar calidad");
            System.out.println("10.- Imprimir tamaño original del archivo");
            System.out.println("11.- Imprimir tamaño archivo final");
            System.out.println("12.- Imprimir grado de compresión");
            System.out.println("13.- Imprimir tiempo de compresión");
            System.out.println("14.- Imprimir velocidad de compresión");
            System.out.println("15.- Introducir estadisticas de compresión");
            System.out.println("16.- Salir");
            System.out.print("Introducir opción: ");
            String userInput = Input.nextLine();
            switch (userInput) {
                case "1":
                    System.out.println(domainCtrl.getMeanOriginalFileSize());
                    break;
                case "2":
                    System.out.println(domainCtrl.getMeanCompressedFileSize());
                    break;
                case "3":
                    System.out.println(domainCtrl.getMeanCompressionDegree());
                    break;
                case "4":
                    System.out.println(domainCtrl.getMeanCompressionSpeed());
                    break;
                case "5":
                    System.out.println(domainCtrl.getMeanCompressionTime());
                    break;
                case "6":
                    System.out.println(domainCtrl.getMeanFiles());
                    break;
                case "7":
                    System.out.print("Introduce la dirección del archivo a comprimir: ");
                    file = Input.nextLine();
                    System.out.println("Escoge algoritmo (1.- LZ78 2.- LZW 3.- LZSS 4.- JPEG");
                    System.out.print("Que algoritmo: ");
                    userInput = Input.nextLine();
                    switch (userInput) {
                        case "1":
                            domainCtrl.compress("LZ78", file);
                            break;
                        case "2":
                            domainCtrl.compress("LZW", file);
                            break;
                        case "3":
                            domainCtrl.compress("LZSS", file);
                            break;
                        case "4":
                            domainCtrl.compress("JPEG", file);
                            break;
                        default:
                            System.out.println("Algoritmo no existente");
                            break;
                    }
                   break;
                case "8":
                    System.out.print("Introduce la dirección del archivo a descomprimir: ");
                    file = Input.nextLine();
                    System.out.println("Escoge algoritmo (1.- LZ78 2.- LZW 3.- LZSS 4.- JPEG");
                    System.out.print("Que algoritmo: ");
                    userInput = Input.nextLine();
                    switch (userInput) {
                        case "1":
                            domainCtrl.decompress("LZ78", file);
                            break;
                        case "2":
                            domainCtrl.decompress("LZW", file);
                            break;
                        case "3":
                            domainCtrl.decompress("LZSS", file);
                            break;
                        case "4":
                            domainCtrl.decompress("JPEG", file);
                            break;
                        default:
                            System.out.println("Algoritmo no existente");
                            break;
                    }
                    break;
                case "9":
                    System.out.print("Introduce qualidad: ");
                    userInput = Input.nextLine();
                    domainCtrl.setQuality(Integer.parseInt(userInput));
                    break;
                case "10":
                    System.out.println("Escoge algoritmo (1.- LZ78 2.- LZW 3.- LZSS 4.- JPEG");
                    System.out.print("Que algoritmo: ");
                    userInput = Input.nextLine();
                    switch (userInput) {
                        case "1":
                            System.out.println(domainCtrl.getOriginalFileSize("LZ78"));
                            break;
                        case "2":
                            System.out.println(domainCtrl.getOriginalFileSize("LZW"));
                            break;
                        case "3":
                            System.out.println(domainCtrl.getOriginalFileSize("LZSS"));
                            break;
                        case "4":
                            System.out.println(domainCtrl.getOriginalFileSize("JPEG"));
                            break;
                        default:
                            System.out.println("Algoritmo no existente");
                            break;
                    }
                    break;
                case "11":
                    System.out.println("Escoge algoritmo (1.- LZ78 2.- LZW 3.- LZSS 4.- JPEG");
                    System.out.print("Que algoritmo: ");
                    userInput = Input.nextLine();
                    switch (userInput) {
                        case "1":
                            System.out.println(domainCtrl.getMidaArxiuFinal("LZ78"));
                            break;
                        case "2":
                            System.out.println(domainCtrl.getMidaArxiuFinal("LZW"));
                            break;
                        case "3":
                            System.out.println(domainCtrl.getMidaArxiuFinal("LZSS"));
                            break;
                        case "4":
                            System.out.println(domainCtrl.getMidaArxiuFinal("JPEG"));
                            break;
                        default:
                            System.out.println("Algoritmo no existente");
                            break;
                    }
                    break;
                case "12":
                    System.out.println("Escoge algoritmo (1.- LZ78 2.- LZW 3.- LZSS 4.- JPEG");
                    System.out.print("Que algoritmo: ");
                    userInput = Input.nextLine();
                    switch (userInput) {
                        case "1":
                            System.out.println(domainCtrl.getCompressionDegree("LZ78"));
                            break;
                        case "2":
                            System.out.println(domainCtrl.getCompressionDegree("LZW"));
                            break;
                        case "3":
                            System.out.println(domainCtrl.getCompressionDegree("LZSS"));
                            break;
                        case "4":
                            System.out.println(domainCtrl.getCompressionDegree("JPEG"));
                            break;
                        default:
                            System.out.println("Algoritmo no existente");
                            break;
                    }
                    break;
                case "13":
                    System.out.println("Escoge algoritmo (1.- LZ78 2.- LZW 3.- LZSS 4.- JPEG");
                    System.out.print("Que algoritmo: ");
                    userInput = Input.nextLine();
                    switch (userInput) {
                        case "1":
                            System.out.println(domainCtrl.getCompressionTime("LZ78"));
                            break;
                        case "2":
                            System.out.println(domainCtrl.getCompressionTime("LZW"));
                            break;
                        case "3":
                            System.out.println(domainCtrl.getCompressionTime("LZSS"));
                            break;
                        case "4":
                            System.out.println(domainCtrl.getCompressionTime("JPEG"));
                            break;
                        default:
                            System.out.println("Algoritmo no existente");
                            break;
                    }
                    break;
                case "14":
                    System.out.println("Escoge algoritmo (1.- LZ78 2.- LZW 3.- LZSS 4.- JPEG");
                    System.out.print("Que algoritmo: ");
                    userInput = Input.nextLine();
                    switch (userInput) {
                        case "1":
                            System.out.println(domainCtrl.getCompressionSpeed("LZ78"));
                            break;
                        case "2":
                            System.out.println(domainCtrl.getCompressionSpeed("LZW"));
                            break;
                        case "3":
                            System.out.println(domainCtrl.getCompressionSpeed("LZSS"));
                            break;
                        case "4":
                            System.out.println(domainCtrl.getCompressionSpeed("JPEG"));
                            break;
                        default:
                            System.out.println("Algoritmo no existente");
                            break;
                    }
                    break;
                case "15":
                    System.out.print("Introduce tamaño original del archivo: ");
                    userInput = Input.nextLine();
                    float originalFileSize = Float.parseFloat(userInput.trim());

                    System.out.print("Introduce tamaño del archivo comprimido: ");
                    userInput = Input.nextLine();
                    float compressedFileSize = Float.parseFloat(userInput.trim());

                    System.out.print("Introduce tiempo de compresión (ms): ");
                    userInput = Input.nextLine();
                    float compressionTime = Float.parseFloat(userInput.trim());

                    System.out.print("Introduce grado de compresión: ");
                    userInput = Input.nextLine();
                    float compressionDegree = Float.parseFloat(userInput.trim());

                    System.out.print("Introduce velocidad de compresión: ");
                    userInput = Input.nextLine();
                    float compressionSpeed = Float.parseFloat(userInput.trim());

                    domainCtrl.setStats(originalFileSize, compressedFileSize, compressionTime, compressionDegree, compressionSpeed);
                    break;

                case "16":
                    input = true;
                    break;
                default:
                    System.out.println("Opciones:");
                    break;
            }
        }

    }
}
