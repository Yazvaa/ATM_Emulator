import java.util.List;

public interface ATM {
    public void putCash(List<Nominal> ListIn);
    public List<Nominal> getCash(Integer sum);
}
