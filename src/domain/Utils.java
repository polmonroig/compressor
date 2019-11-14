package domain;

public class Utils {

    static public String intToString(int b, int binary_size){
        String s = Integer.toBinaryString(b);
        StringBuilder result = new StringBuilder();
        for(int i = s.length() - 1; i >= s.length() - binary_size && i >= 0; --i){
            result.insert(0, s.charAt(i));
        }
        return result.toString();
    }

    /**
     * <p>Transforms a given LZ78 compression into a binary string</>
     * @param coding text to transform into binary
     * @return the encoding as a binary text
     */
    static public String toBinaryString(String coding){
        StringBuilder binary_string = new StringBuilder();
        int current_index_size = 1;
        double log_2 = Math.log(2);
        int current_index = 0;
        for(int i = 0; i < coding.length(); i++){
            StringBuilder index = new StringBuilder();
            while(i < coding.length() && coding.charAt(i) != ','){
                index.append(coding.charAt(i));
                ++i;
            }
            ++i;
            if(current_index >= 2){
                double aux = (Math.log(current_index) / log_2);
                if(aux == Math.round(aux)){
                    current_index_size += 1;
                }
            }
            int binary_size = 8;
            if(current_index_size > 8){
                binary_size = current_index_size;
            }
            binary_string.append(addZeros(intToString(Integer.parseInt(index.toString()), binary_size), current_index_size));
            if(i  < coding.length()){
                char value = coding.charAt(i);
                binary_string.append(addZeros(intToString(value, 8), 8));
            }
            current_index++;
        }
        return binary_string.toString();
    }

    /**
     * <p>Adds a specified number of 0s at the left side of a string</>
     * @param binary binary string
     * @param n_zeros size of the final string
     * @return returns a binary string where its size >= n_zeros
     */
    static private String addZeros(String binary, int n_zeros){ // CORRECTA
        StringBuilder binaryBuilder = new StringBuilder(binary);
        while(binaryBuilder.length() < n_zeros){
            binaryBuilder.insert(0, "0");
        }
        binary = binaryBuilder.toString();
        return binary;
    }

    static public int getIntFromString(String text, int i){
        StringBuilder letter = new StringBuilder();
        for(int j = i; j < i + 8 && j < text.length(); ++j){
            letter.append(text.charAt(j));
        }
        return Integer.parseInt(letter.toString(), 2);
    }


    static public String getLetter(String text,  int i){
        return Character.toString((char) getIntFromString(text, i));
    }

    static public byte[] toByteArray(String binary_string){
        int size = (int)Math.ceil(binary_string.length() / 8.0) + 1; // number of bytes + offset byte
        byte [] byte_coding = new byte[size];
        int it = 0;
        byte_coding[0] = (byte)(binary_string.length() % 8); // offset of 0
        for(int i = 0; i < size; ++i){
            byte_coding[i] = (byte)getIntFromString(binary_string, it);
            it += 8;
        }

        return  byte_coding;
    }

    static public byte[] toByteArray2(String binary_string){
        int size = (int)Math.ceil(binary_string.length() / 8.0);
        byte [] byte_coding = new byte[size];
        int it = 0;
        for(int i = 0; i < size; ++i){
            byte_coding[i] = (byte)getIntFromString(binary_string, it);
            it += 8;
        }

        return  byte_coding;
    }

    static  public String toString(byte[] byte_coding){ // CORRECTA
        StringBuilder binary_string = new StringBuilder();
        int zeros_offset = byte_coding[0];
        for(int i = 0; i < byte_coding.length - 1; ++i){
            binary_string.append(addZeros(intToString(byte_coding[i], 8), 8));
        }
        if(zeros_offset == 0){
            zeros_offset = 8;
        }
        binary_string.append(addZeros(intToString(byte_coding[byte_coding.length - 1], 8), zeros_offset));
        return binary_string.toString();
    }

    static public String AndofString(String code){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < code.length(); ++i){
            if(code.charAt(i) == '0') result.append("1");
            else result.append("0");
        }
        return result.toString();
    }
}
