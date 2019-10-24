import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTest {
    @Test
    public void testGetCashInc(){
        CellImpl currCell=new CellImpl(Nominal.FIFTY,100);
        currCell.get(40);
        assertEquals(currCell.getCount(),60);
    }
    @Test
    public void testGetCashDec(){
        CellImpl currCell=new CellImpl(Nominal.FIFTY,100);
        currCell.get(140);
        assertEquals(currCell.getCount(),0);
    }

}
