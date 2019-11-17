package domain;

import java.util.*;

public class Huffman {

    Map<Integer, Integer> frequencies = new HashMap<>();

    public Map<Integer, Integer> getFrequencies(){
        return this.frequencies;
    }

    public void setFrequencies(Map<Integer, Integer> Freq){
        this.frequencies = Freq;
    }

    public String compressHuffman(ArrayList<Integer> image){
        initializeFrequencies(image);
        TreeNode root = createTree();
        Map<Integer, String> codes = createCodes(root);
        StringBuilder compressedImage = new StringBuilder();
        for(int i = 0; i < image.size(); ++i){
            compressedImage.append(codes.get(image.get(i)));
        }
        return compressedImage.toString();
    }

    public ArrayList<Integer> decompressHuffman(String image){
        TreeNode compressedTree = createTree();
        ArrayList<Integer> decompressedImage = new ArrayList<Integer>();
        TreeNode actual = compressedTree;
        int i = 0;
        while(i < image.length()){
            char bit = '0';
            while(!actual.isLeaf()){
                System.out.println(decompressedImage.size());
                if( i < image.length()) bit = image.charAt(i);
                if(bit == '0') actual = actual.left;
                else actual = actual.right;
                ++i;
            }
            decompressedImage.add(actual.character);
            actual = compressedTree;
        }
        return decompressedImage;
    }

    private static Map<Integer, String> createCodes(TreeNode root){
        Map<Integer, String> codes = new HashMap<>();
        createCodesRecursively(root, "", codes);
        return codes;
    }

    private static void createCodesRecursively(TreeNode root, String code, Map<Integer, String> codes){
        if(root.isLeaf()) codes.put(root.character, code);
        else{
            createCodesRecursively(root.left, code + '0', codes);
            createCodesRecursively(root.right, code + '1', codes);
        }
    }

    private TreeNode createTree(){
        PriorityQueue<TreeNode> nodeQueue = new PriorityQueue<>();
        //Recorrer mapa y meter en la cola de nodos
        for(Map.Entry<Integer, Integer> entry: this.frequencies.entrySet()){
            nodeQueue.add(new TreeNode(entry.getKey(), entry.getValue(), null, null));
        }
        if(nodeQueue.size() == 1) nodeQueue.add(new TreeNode(256, 1, null, null));
        while(nodeQueue.size() > 1){
            TreeNode left = nodeQueue.poll();
            TreeNode right = nodeQueue.poll();
            TreeNode root = new TreeNode(256, left.frequency + right.frequency, left, right);
            nodeQueue.add(root);
        }

        return nodeQueue.poll();
    }

    private void initializeFrequencies(ArrayList<Integer> image){
        for (int i = 0; i < image.size(); ++i){
            if(!this.frequencies.isEmpty()){
                if(this.frequencies.containsKey(image.get(i))){
                    int aux = frequencies.get(image.get(i));
                    aux++;
                    frequencies.put(image.get(i), aux);
                }
                else this.frequencies.put(image.get(i), 1);
            }
            else this.frequencies.put(image.get(i), 1);
        }
    }

    static class TreeNode implements Comparable<TreeNode>{
        private final int character;
        private final int frequency;
        private final TreeNode left;
        private final TreeNode right;

        private TreeNode(final int character, final int frequency, final TreeNode left, final TreeNode right){
            this.character = character;
            this.right = right;
            this.frequency = frequency;
            this.left = left;
        }

        private boolean isLeaf(){
            return (this.left == null & this.right == null);
        }

        @Override
        public int compareTo(TreeNode node) {
            int comparison = Integer.compare(this.frequency, node.frequency);
            if(comparison == 0) return Integer.compare(this.character, node.character);
            else return comparison;
        }
    }

}
