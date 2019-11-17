package domain.drivers;

import domain.Utils;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriverUtils {
    private final Utils util = new Utils();

    @Test
    public void toBinaryStringTest(){//no tengo claro lo que hace
        assertEquals("01100001", util.toBinaryString("a"));
    }

    @Test
    public void getIntFromStringTest(){//ns que hace, puede que este bien
        assertEquals(10, util.getIntFromString("2", 0));
    }

    @Test
    public void getLetterTest(){//ns que hace, puede que este bien
        assertEquals("a", util.getLetter("97" ,0 ));
    }

    @Test
    public void toByteArrayTest(){//ns que hace
        assertEquals( "", util.toByteArray("01010101"));
    }

    @Test
    public void toByteArray2Test(){//ns que hace
        assertEquals( "", util.toByteArray2("01010101"));
    }

    @Test
    public void toStringTest(){//este debria estar bien
        byte[] prove = {97, 98};
        assertEquals( "ab", util.toString(prove));
    }

    @Test
    public void andOfStringTest(){
        //Si lo que hace es cambiar 1s por 0s llamar stringNegated mejor
        assertEquals("01", util.andOfString("10"));
    }

}
