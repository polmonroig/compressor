package domain.drivers;

import domain.Huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DriverHuffman {

    public static void main(String[] args) {
        Scanner Input = new Scanner(System.in);

        Huffman encoder = new Huffman();
        boolean input = false;
        System.out.println("Opciones:");
        String[] functions = {"comprimir", "descomprimir", "ponerFrequencias", "obtenerFrequencias", "salir"};
        for(int i = 0; i < functions.length; ++i){
            System.out.println((i + 1)  + "-" + functions[i]);
        }
        while(!input){
            System.out.println("Introduce una opcion");
            String userInput = Input.nextLine();

            switch (userInput) {
                case "1":
                    System.out.println("Introduce la sequencia de enteros a comprimir");
                    String file = Input.nextLine();
                    ArrayList<Integer> compression = new ArrayList<>();
                    for(int i = 0; i < file.length(); ++i) compression.add(Integer.parseInt(String.valueOf(i)));
                    String encode = encoder.compressHuffman(compression);
                    System.out.println(encode);
                    break;
                case "2":
                    System.out.println("Introduce la sequencia binaria a descomprimir");
                    file = Input.nextLine();
                    compression = encoder.decompressHuffman(file);
                    System.out.println(compression);
                    break;
                case "3":
                    System.out.println("Introduce la lista de frequencias (numero , espacio, frequencia)");
                    file = Input.nextLine();
                    Map<Integer, Integer> Freq = new HashMap<>();
                    for(int i = 0; i < file.length(); i+=3)Freq.put(Integer.parseInt(String.valueOf(i)), Integer.parseInt(String.valueOf(i+2)));
                    encoder.setFrequencies(Freq);
                    break;
                case "4":
                    Freq = encoder.getFrequencies();
                    System.out.println(Freq);
                    break;
                case "5":
                    input = true;
                    break;
                default:
                    System.out.println("La opcion introducida no es valida, por favor intentalo de nuevo");
                    for(int i = 0; i < functions.length; ++i){
                        System.out.println((i + 1)  + ".-" + functions[i]);
                    }
                    break;
            }
        }
    }
}
