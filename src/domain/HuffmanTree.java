package domain;

import java.awt.*;
import java.util.SortedMap;
import java.util.TreeMap;

public class HuffmanTree implements Comparable<HuffmanTree>{
    private int colorValue;
    private HuffmanTree left;
    private HuffmanTree right;
    private int weight;
    private int size;
    private boolean isLeaf;

    HuffmanTree(int value, int weight){
        this.colorValue = value;
        this.left = null;
        this.right = null;
        this.weight = weight;
        this.size = 1;
        this.isLeaf = true;
    }

    HuffmanTree(HuffmanTree h1, HuffmanTree h2){
        this.left = h1;
        this.right = h2;
        this.size = h1.size + h2.size;
        this.weight = h1.weight + h2.weight;
        this.isLeaf = false;
        this.colorValue = -1;
    }

    public SortedMap<Integer, String> getTable(){
        SortedMap<Integer, String> table = new TreeMap<>();

        return table;
    }

    @Override
    public int compareTo(HuffmanTree h2){
        // this.weight must be less than h2.weight
        // this.size must be less than h2.size
        return (this.weight - h2.weight) + (this.size - h2.size);
    }

}


/*
===========================================================
                HUFFMAN ENCODING FOR IMAGES
===========================================================
Steps:
    0. Include libraries

        import java.util.Comparator;
        import java.util.PriorityQueue;
        import java.util.SortedMap;
        import java.util.TreeMap;

    1. Get histogram of colors of the image
    2. Create an empty HuffmanTree priority queue:

            PriorityQueue<HuffmanTree> minHeap = new PriorityQueue<>();

    3. Create a HuffmanTree for each non-zero histogram color,
       each tree should be initialized this way:

            for(int i = 0; i < 256; ++i){
                if(hist[i] > 0){
                    minHeap.add(new HuffmanTree(i, hist[i]));
                }
            }

    4. For each pair of huffman trees that correspond to the lowest ones
       we join them and create another one, do this until minHeap.size() == 1

            while(minHeap.size() > 1){
                HuffmanTree min1 = minHeap.remove();
                HuffmanTree min2 = minHeap.remove();
                minHeap.add(HuffmanTree(min1, min2));
            }
            HuffmanTree tree = minHeap.remove();


    5. Create encoding table, by traversing the tree and getting the binary
       encoding for each color, if we traverse left we add a '0', otherwise
       we add a '1'. This way for each leaf Tree we have an encoding

            SortedMap<Integer, String> table = new TreeMap<>();
            table = tree.getTable();

    6. Now that we have the table for each number in the histogram,
       we must encode the image

            String binaryImage = "";
            for(int i = 0; i < row; ++i){
                for(int j = 0; j < col; ++j){
                    binary += table.get(image[i][j]);
                }
            }

    7. We must encode the tree into a binary

        binaryImage = tree.encode() + binaryImage;

    8. Finally, for each byte in binaryString we must encode it into b
       a byte array

    9.  NOTE: "tree.encode()" is not implemented
        NOTE: "tree.getTable()" is not implemented
              for getTable see 7:25 -> https://www.youtube.com/watch?v=IuFKDrdmgIQ















*/