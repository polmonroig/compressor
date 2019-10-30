package domain.drivers;

import domain.LZ78;

public class DriverLZ78 {

    public static void main(String[] args) {
        DriverLZ78 driver = new DriverLZ78();
        driver.testAddZeros();
        driver.testGetIntFromString();
    }

    public void testGetIntFromString(){
        System.out.println("Testing Int from string");

        System.out.println("Done");
    }

    public void testAddZeros(){
        System.out.println("Testing add Zeros");
        if(!LZ78.addZeros("1", 1).equals("1")){
            System.out.println("Error");
        }
        if(!LZ78.addZeros("1", 2).equals("01")){
            System.out.println("Error");
        }
        if(!LZ78.addZeros("1", 3).equals("001")){
            System.out.println("Error");
        }if(!LZ78.addZeros("1", 4).equals("0001")){
            System.out.println("Error");
        }if(!LZ78.addZeros("1", 5).equals("00001")){
            System.out.println("Error");
        }if(!LZ78.addZeros("1", 6).equals("000001")){
            System.out.println("Error");
        }if(!LZ78.addZeros("1", 7).equals("0000001")){
            System.out.println("Error");
        }if(!LZ78.addZeros("1", 8).equals("00000001")){
            System.out.println("Error");
        }

        System.out.println("Done");

    }

}
