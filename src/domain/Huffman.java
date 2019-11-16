package domain;

import java.util.*;

public class Huffman {

    Map<Integer, Integer> frequencias = new HashMap<>();

    public Map<Integer, Integer> ObtenerFrequencias(){
        return this.frequencias;
    }

    public String ComprimirHuffman(ArrayList<Integer> imagen){
        InicializarFrequencias(imagen);
        NodoArbol raiz = CrearArbol();
        Map<Integer, String> Codigos = CrearCodigos(raiz);
        StringBuilder imagencomprimida = new StringBuilder();
        for(int i = 0; i < imagen.size(); ++i){
            imagencomprimida.append(Codigos.get(imagen.get(i)));
        }
        return imagencomprimida.toString();
    }

    public ArrayList<Integer> DescomprimirHuffman (String imagen, Map<Integer, Integer> freq){
        this.frequencias = freq; //Pongo las frequencias en la classe para poder crear el arbol
        NodoArbol ArbolComprimido = CrearArbol();
        ArrayList<Integer> imagendescomprimirda = new ArrayList<Integer>();
        NodoArbol actual = ArbolComprimido;
        int i = 0;
        while(i < imagen.length()){
            while(!actual.EsHoja()){
                char bit = imagen.charAt(i);
                if(bit == '0') actual = actual.izquierda;
                else actual = actual.derecha;
                ++i;
            }
            imagendescomprimirda.add(actual.caracter);
            actual = ArbolComprimido;
        }
        return imagendescomprimirda;
    }

    private static Map<Integer, String> CrearCodigos(NodoArbol raiz){
        Map<Integer, String> Codigos = new HashMap<>();
        CrearCodigosRecursivo(raiz, "", Codigos);
        return Codigos;
    }

    private static void CrearCodigosRecursivo(NodoArbol raiz, String codigo, Map<Integer, String> Codigos){
        if(raiz.EsHoja()) Codigos.put(raiz.caracter, codigo);
        else{
            CrearCodigosRecursivo(raiz.izquierda, codigo + '0', Codigos);
            CrearCodigosRecursivo(raiz.derecha, codigo + '1', Codigos);
        }
    }

    private NodoArbol CrearArbol(){
        PriorityQueue<NodoArbol> ColadeNodos = new PriorityQueue<>();
        //Recorrer mapa y meter en la cola de nodos
        for(Map.Entry<Integer, Integer> entry: this.frequencias.entrySet()){
            ColadeNodos.add(new NodoArbol(entry.getKey(), entry.getValue(), null, null));
        }
        if(ColadeNodos.size() == 1) ColadeNodos.add(new NodoArbol(256, 1, null, null));
        while(ColadeNodos.size() > 1){
            NodoArbol izquierdo = ColadeNodos.poll();
            NodoArbol derecho = ColadeNodos.poll();
            NodoArbol raiz = new NodoArbol(256, izquierdo.frequencia + derecho.frequencia, izquierdo, derecho);
            ColadeNodos.add(raiz);
        }

        return ColadeNodos.poll();
    }

    private void InicializarFrequencias(ArrayList<Integer> imagen){
        for (int i = 0; i < imagen.size(); ++i){
            if(!this.frequencias.isEmpty()){
               if(this.frequencias.containsKey(imagen.get(i))){
                    int aux = frequencias.get(imagen.get(i));
                    aux++;
                    frequencias.put(imagen.get(i), aux);
                }
               else this.frequencias.put(imagen.get(i), 1);
            }
            else this.frequencias.put(imagen.get(i), 1);
        }
    }

    static class NodoArbol implements Comparable<NodoArbol>{
        private final int caracter;
        private final int frequencia;
        private final NodoArbol izquierda;
        private final NodoArbol derecha;

        private NodoArbol(final int caracter, final int frequencia, final NodoArbol izquierda, final NodoArbol derecha){
            this.caracter = caracter;
            this.derecha = derecha;
            this.frequencia = frequencia;
            this.izquierda = izquierda;
        }

        private boolean EsHoja(){
            return (this.izquierda == null & this.derecha == null);
        }

        @Override
        public int compareTo(NodoArbol nodo) {
            int comparacion = Integer.compare(this.frequencia , nodo.frequencia);
            if(comparacion == 0) return Integer.compare(this.caracter , nodo.caracter);
            else return comparacion;
        }
    }

}