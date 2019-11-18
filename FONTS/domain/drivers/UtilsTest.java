package domain.drivers;

import domain.Utils;
import org.junit.Assert;
import org.junit.Test;


import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void singleIndexShouldEncodeIndexPlusLetter(){
        assertEquals("Result must encode be 0 + letter in UTF-8", "001100001", Utils.toBinaryString("0,a"));
        assertEquals("Result must encode be 1 + letter in UTF-8", "101100001", Utils.toBinaryString("1,a"));
    }

    @Test
    public void emptyIndexShouldReturnEmptyTest(){
        assertEquals("Result must be empty","", Utils.toBinaryString(""));
    }

    @Test
    public void binaryStringShouldReturnInt(){
        assertEquals("Result must be 0", 0, Utils.getIntFromString("0", 0));
        assertEquals("Result must be 1", 1, Utils.getIntFromString("1", 0));
        assertEquals("Result must be 2", 2, Utils.getIntFromString("10", 0));
        assertEquals("Result must be 3", 3, Utils.getIntFromString("11", 0));
        assertEquals("Result must be 4", 4, Utils.getIntFromString("100", 0));
        assertEquals("Result must be 5", 5, Utils.getIntFromString("101", 0));
    }

    @Test
    public void binaryStringShouldReturnIntWithoutHighBit(){
        assertEquals("Result must be 10 => 0 => 0", 0, Utils.getIntFromString("10", 1));
        assertEquals("Result must be 10100101 => 0100101 => 37", 37, Utils.getIntFromString("10100101", 1));
        assertEquals("Result must be 10100101 => 00101 => 5", 5, Utils.getIntFromString("10100101", 3));
        assertEquals("Result must be 10100101 => 1 => 1", 1, Utils.getIntFromString("10100101", 7));
    }

    @Test
    public void numberShouldEqualCharacterRepresentation(){
        assertEquals("Result must be 0", "a", Utils.getLetter("01100001", 0));
        assertEquals("Result must be 1", "c", Utils.getLetter("01100011", 0));
        assertEquals("Result must be 2", "W", Utils.getLetter("01010111", 0));
        assertEquals("Result must be 0", "a", Utils.getLetter("01100001", 1));
        assertEquals("Result must be 1", "c", Utils.getLetter("01100011", 1));
        assertEquals("Result must be 2", "W", Utils.getLetter("01010111", 1));
    }


    @Test
    public void andOfStringShouldReturnInverse(){
        assertEquals("Result must change 0 to 1 and 1 to 0","01", Utils.andOfString("10"));
        assertEquals("Result must change 0 to 1 and 1 to 0","01101011011011", Utils.andOfString("10010100100100"));
        assertEquals("Result must change 0 to 1 and 1 to 0","000000000000000000000000000", Utils.andOfString("111111111111111111111111111"));
        assertEquals("Result must change 0 to 1 and 1 to 0","01111000", Utils.andOfString("10000111"));
    }

    @Test
    public void byteArrayShouldReturnStringWithOffset(){

        assertEquals( "Should return binary 00100000 + 00000001","0010000000000001", Utils.toString(new byte[]{0, 32, 1}));
        assertEquals( "Should return binary 00100000 + 1","001000001", Utils.toString(new byte[]{1, 32, 1}));
        assertEquals( "Should return binary 00100000 + 01","0010000001", Utils.toString(new byte[]{2, 32, 1}));
        assertEquals( "Should return binary 00100000 + 001","00100000001", Utils.toString(new byte[]{3, 32, 1}));
        assertEquals( "Should return binary 00100000 + 0001","001000000001", Utils.toString(new byte[]{4, 32, 1}));
    }

    @Test
    public void stringWithOffsetShouldReturnByteArray(){
        Assert.assertArrayEquals("Should return {0, 32, 1}", new byte[]{0, 32, 1}, Utils.toByteArray("0010000000000001"));
        Assert.assertArrayEquals("Should return {1, 32, 1}", new byte[]{1, 32, 1}, Utils.toByteArray("001000001"));
        Assert.assertArrayEquals("Should return {2, 32, 1}", new byte[]{2, 32, 1}, Utils.toByteArray("0010000001"));
        Assert.assertArrayEquals("Should return {3, 32, 1}", new byte[]{3, 32, 1}, Utils.toByteArray("00100000001"));
    }

    @Test
    public void toByteArray2Test(){//ns que hace
        Assert.assertArrayEquals("Should return {64, 1}", new byte[]{ 64, 1}, Utils.toByteArray2("010000000000001"));
        Assert.assertArrayEquals("Should return {65}", new byte[]{ 65}, Utils.toByteArray2("01000001"));
    }


}