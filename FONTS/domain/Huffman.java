package domain;

import java.util.*;

/**
 * This is the essential part of the encoding of the JPEG. These Huffman implementation
 * uses a HuffmanTree with their frequencies in order to achieve a compress binary string
 * of the image.
 **/

public class Huffman {
    /**
     * This variable store the frequencies of an image
     **/
    private Map<Integer, Integer> frequencies = new HashMap<>();

    /**
     * <p>getFrequencies is the form to achieve the frequencies of an image</p>
     *
     * @return Map with all the frequencies of the image
     **/
    public Map<Integer, Integer> getFrequencies() {
        return this.frequencies;
    }

    /**
     * <p>setFrequencies is the form to set a value to the variable frequencies</p>
     *
     * @param Freq Map with the frequencies to set
     **/
    public void setFrequencies(Map<Integer, Integer> Freq) {
        this.frequencies = Freq;
    }

    /**
     * <p>compressHuffman as his name says is the core for the compression of Huffman</p>
     *
     * @param image Array of integer with all the image
     * @return A binary string with the compression of the image
     **/
    public String compressHuffman(ArrayList<Integer> image) {
        initializeFrequencies(image);
        TreeNode root = createTree();
        Map<Integer, String> codes = createCodes(root);
        StringBuilder compressedImage = new StringBuilder();
        for (int i = 0; i < image.size(); ++i) {
            compressedImage.append(codes.get(image.get(i)));
        }
        return compressedImage.toString();
    }

    /**
     * <p>decompressHuffman as his name says is the core for the decompression of Huffman</p>
     *
     * @param image A binary string with the compression of the image
     * @return Array of integer with all the image
     **/
    public ArrayList<Integer> decompressHuffman(String image) {
        TreeNode compressedTree = createTree();
        ArrayList<Integer> decompressedImage = new ArrayList<Integer>();
        TreeNode actual = compressedTree;
        int i = 0;
        while (i < image.length()) {
            char bit = '0';
            while (!actual.isLeaf()) {
                if (i < image.length()) bit = image.charAt(i);
                if (bit == '0') actual = actual.left;
                else actual = actual.right;
                ++i;
            }
            decompressedImage.add(actual.character);
            actual = compressedTree;
        }
        return decompressedImage;
    }

    /**
     * <p>createCodes, creates the codes to make after the binary string of the compression</p>
     *
     * @param root TreeNode with all the frequencies update in the Tree
     * @return Map with the frequencies and the codes for each one
     **/
    private static Map<Integer, String> createCodes(TreeNode root) {
        Map<Integer, String> codes = new HashMap<>();
        createCodesRecursively(root, "", codes);
        return codes;
    }

    /**
     * <p>createCodesRecursively is the core function for the createCodes</p>
     *
     * @param root  TreeNode with all the frequencies update in the Tree
     * @param code  String with the code for the node using
     * @param codes Map with all the codes and frequencies
     **/
    private static void createCodesRecursively(TreeNode root, String code, Map<Integer, String> codes) {
        if (root.isLeaf()) codes.put(root.character, code);
        else {
            createCodesRecursively(root.left, code + '0', codes);
            createCodesRecursively(root.right, code + '1', codes);
        }
    }

    /**
     * <p>createTree creates the main Tree of Huffman</p>
     *
     * @return TreeNode with the HuffmanTree created
     **/
    private TreeNode createTree() {
        PriorityQueue<TreeNode> nodeQueue = new PriorityQueue<>();
        //Recorrer mapa y meter en la cola de nodos
        for (Map.Entry<Integer, Integer> entry : this.frequencies.entrySet()) {
            nodeQueue.add(new TreeNode(entry.getKey(), entry.getValue(), null, null));
        }
        if (nodeQueue.size() == 1) nodeQueue.add(new TreeNode(256, 1, null, null));
        while (nodeQueue.size() > 1) {
            TreeNode left = nodeQueue.poll();
            TreeNode right = nodeQueue.poll();
            TreeNode root = new TreeNode(256, left.frequency + right.frequency, left, right);
            nodeQueue.add(root);
        }

        return nodeQueue.poll();
    }

    /**
     * <p>initializeFrequencies, creates the frequencies of the image</p>
     *
     * @param image ArrayList of integers with the image
     **/
    private void initializeFrequencies(ArrayList<Integer> image) {
        for (int i = 0; i < image.size(); ++i) {
            if (!this.frequencies.isEmpty()) {
                if (this.frequencies.containsKey(image.get(i))) {
                    int aux = frequencies.get(image.get(i));
                    aux++;
                    frequencies.put(image.get(i), aux);
                } else this.frequencies.put(image.get(i), 1);
            } else this.frequencies.put(image.get(i), 1);
        }
    }

    /**
     * Class that implements a node of the HuffmanTree
     **/
    static class TreeNode implements Comparable<TreeNode> {
        private final int character;
        private final int frequency;
        private final TreeNode left;
        private final TreeNode right;

        private TreeNode(final int character, final int frequency, final TreeNode left, final TreeNode right) {
            this.character = character;
            this.right = right;
            this.frequency = frequency;
            this.left = left;
        }

        private boolean isLeaf() {
            return (this.left == null & this.right == null);
        }

        @Override
        public int compareTo(TreeNode node) {
            int comparison = Integer.compare(this.frequency, node.frequency);
            if (comparison == 0) return Integer.compare(this.character, node.character);
            else return comparison;
        }
    }

}
