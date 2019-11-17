
package presentation.controllers;

import domain.controllers.DomainCtrl;

import java.util.Scanner;

public class PresentationCtrl {

    public static void main(String[] args) {
        DomainCtrl domainCtrl = new DomainCtrl();
        Scanner Input = new Scanner(System.in);

        boolean input = false;

        while(!input){

            String userInput;
            String[] functions = {"comprimir", "descomprimir", "estadisticas globales", "salir"};
            writeFunctions(functions);
            userInput = getUserInput(Input, "Introduce una opcion");
            String file;
            float originalFileSize;
            float compressedFileSize ;
            float compressionTime;
            float compressionDegree;
            float compressionSpeed;
            switch (userInput) {
                case "1":
                    file = getUserInput(Input, "Introduce el archivo que deseas comprimir");
                    if (file.endsWith(".txt")) {
                        String[] algoritmos = {"LZ78", "LZW", "LZSS", "auto"};
                        writeFunctions(algoritmos);
                        userInput = getUserInput(Input, "Introduce una opcion");
                        switch (userInput) {
                            case "1":
                                domainCtrl.compress("lz78", file);
                                originalFileSize = domainCtrl.getOriginalFileSize("lz78");
                                compressedFileSize = domainCtrl.getMidaArxiuFinal("lz78");
                                compressionTime = domainCtrl.getCompressionTime("lz78");
                                compressionDegree = domainCtrl.getCompressionDegree("lz78");
                                compressionSpeed = domainCtrl.getCompressionSpeed("lz78");
                                showStats(originalFileSize, compressedFileSize, compressionTime, compressionDegree, compressionSpeed);
                                domainCtrl.setStats(originalFileSize, compressedFileSize, compressionTime, compressionDegree, compressionSpeed);
                                break;
                            case "2":
                                domainCtrl.compress("lzw", file);
                                originalFileSize = domainCtrl.getOriginalFileSize("lzw");
                                compressedFileSize = domainCtrl.getMidaArxiuFinal("lzw");
                                compressionTime = domainCtrl.getCompressionTime("lzw");
                                compressionDegree = domainCtrl.getCompressionDegree("lzw");
                                compressionSpeed = domainCtrl.getCompressionSpeed("lzw");
                                showStats(originalFileSize, compressedFileSize, compressionTime, compressionDegree, compressionSpeed);
                                domainCtrl.setStats(originalFileSize, compressedFileSize, compressionTime, compressionDegree, compressionSpeed);
                                break;
                            case "3":
                                domainCtrl.compress("lzss", file);
                                originalFileSize = domainCtrl.getOriginalFileSize("lzss");
                                compressedFileSize = domainCtrl.getMidaArxiuFinal("lzss");
                                compressionTime = domainCtrl.getCompressionTime("lzss");
                                compressionDegree = domainCtrl.getCompressionDegree("lzss");
                                compressionSpeed = domainCtrl.getCompressionSpeed("lzss");
                                showStats(originalFileSize, compressedFileSize, compressionTime, compressionDegree, compressionSpeed);
                                domainCtrl.setStats(originalFileSize, compressedFileSize, compressionTime, compressionDegree, compressionSpeed);
                                break;
                            case "4":
                                System.out.println("Lo siento, esta funcion aun no esta implementada");
                                break;
                            default:
                                System.out.println("La opcion introducida no es valida, por favor intentalo de nuevo :(");
                                break;
                        }
                    } else if (file.endsWith(".ppm")) {
                        String quality = getUserInput(Input, "Con que grado de calidad desea comprimir? (Siendo 0 el más bajo y 12 el más alto)");
                        domainCtrl.setQuality(Integer.parseInt(quality));
                        domainCtrl.compress("jpeg", file);
                        originalFileSize = domainCtrl.getOriginalFileSize("jpeg");
                        compressedFileSize = domainCtrl.getMidaArxiuFinal("jpeg");
                        compressionTime = domainCtrl.getCompressionTime("jpeg");
                        compressionDegree = domainCtrl.getCompressionDegree("jpeg");
                        compressionSpeed = domainCtrl.getCompressionSpeed("jpeg");
                        showStats(originalFileSize, compressedFileSize, compressionTime, compressionDegree, compressionSpeed);
                        domainCtrl.setStats(originalFileSize, compressedFileSize, compressionTime, compressionDegree, compressionSpeed);
                    } else {
                        System.out.println("El archivo introducido no es valido, porfavor introduce un archivo .txt o .ppm");
                    }
                    break;
                case "2":
                    file = getUserInput(Input, "Introduce el archivo que deseas descomprimir");
                    if (file.endsWith("lz78")) domainCtrl.decompress("lz78", file);
                    else if (file.endsWith("lzss")) domainCtrl.decompress("lzss", file);
                    else if (file.endsWith("lzw")) domainCtrl.decompress("lzw", file);
                    else if (file.endsWith("jpeg")) domainCtrl.decompress("jpeg", file);
                    else System.out.println("El archivo introducido no es valido, porfavor introduce un archivo .lz78, .lzss, .lzw o .jpeg");
                    break;
                case "3":
                    float meanOriginalFileSize = domainCtrl.getMeanOriginalFileSize();
                    float meanCompressedFileSize = domainCtrl.getMeanCompressedFileSize();
                    float meanCompressionTime = domainCtrl.getMeanCompressionTime();
                    float meanCompressionDegree = domainCtrl.getMeanCompressionDegree();
                    float meanCompressionSpeed = domainCtrl.getMeanCompressionSpeed();
                    float nFiles = domainCtrl.getMeanFiles();
                    showGlobalStats(meanOriginalFileSize, meanCompressedFileSize,
                                    meanCompressionTime, meanCompressionDegree,
                                    meanCompressionSpeed, nFiles);
                    break;
                case "4":
                input = true;
                break;
                default:
                    System.out.println("La opcion introducida no es valida, por favor intentalo de nuevo");
                    break;
            }
        }
    }

    private static void showGlobalStats(float meanOriginalFileSize, float meanCompressedFileSize,
                                        float meanCompressionTime, float meanCompressionDegree,
                                        float meanCompressionSpeed, float nFiles) {
        System.out.println("Estadisticas en mediana de todas las compresiones:");
        System.out.println("Tamaño archivo inicial: " + meanOriginalFileSize +  " bytes");
        System.out.println("Tamaño archivo final: " + meanCompressedFileSize + " bytes");
        System.out.println("Tiempo de compresion: " + meanCompressionTime + " milisegundos");
        System.out.println("Grado de compresion: " + meanCompressionDegree + "%");
        System.out.println("Velocidad de compresion: " + meanCompressionSpeed + " bytes/milisegundos");
        System.out.println("Numero de archivos: " + nFiles);
    }

    private static String getUserInput(Scanner input, String s) {
        String userInput;
        System.out.println(s);
        userInput = input.nextLine();
        return userInput;
    }


    private static void showStats(float originalFileSize, float compressedFileSize, float compressionTime, float compressionDegree, float compressionSpeed){
        System.out.println("Estadisticas de la compression actual:");
        System.out.println("Tamaño archivo inicial: " + originalFileSize + " bytes");
        System.out.println("Tamaño archivo final: " + compressedFileSize + " bytes");
        System.out.println("Tiempo de compresion: " + compressionTime + " milisegundos");
        System.out.println("Grado de compresion: " + compressionDegree + "%");
        System.out.println("Velocidad de compresion: " + compressionSpeed + " bytes/milisegundos");
    }

    private static void writeFunctions(String[] functions){
        System.out.println("Opciones:");
        for(int i = 0; i < functions.length; ++i){
            System.out.println((i + 1)  + "-" + functions[i]);
        }
    }
}