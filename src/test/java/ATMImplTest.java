
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ATMImplTest {

    @Test
    public void testSimpleOperation(){
        ATMImpl atm = new ATMImpl();
        List<Nominal> list = new ArrayList<>();
        list.add(Nominal.FIVE_HUNDRED);
        atm.putCash(list);
        list = atm.getCash(500);
        assertEquals(1, list.size());
        assertEquals(Nominal.FIVE_HUNDRED, list.get(0));
    }

    @Test
    public void testOperation(){
        ATMImpl atm = new ATMImpl();
        List<Nominal> list = new ArrayList<>();
        list.add(Nominal.FIVE_HUNDRED);
        list.add(Nominal.FIVE_HUNDRED);
        list.add(Nominal.TWO_HUNDRED);
        atm.putCash(list);
        list = atm.getCash(500);
        assertEquals(1, list.size());
        assertEquals(Nominal.FIVE_HUNDRED, list.get(0));
    }

    @Test
    public void testBadNominalsOperation(){
        ATMImpl atm = new ATMImpl();
        List<Nominal> list = new ArrayList<>();
        list.add(Nominal.FIVE_HUNDRED);
        atm.putCash(list);
        list = atm.getCash(222);
        assertNull(list);
    }

    @Test
    public void testALotOfMoneyOperation(){
        ATMImpl atm = new ATMImpl();
        List<Nominal> list = new ArrayList<>();
        list.add(Nominal.FIVE_HUNDRED);
        atm.putCash(list);
        list = atm.getCash(1000);
        assertNull(list);
    }

}
