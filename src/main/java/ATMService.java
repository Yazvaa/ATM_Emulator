import java.io.IOException;

public interface ATMService {
    public Integer getBalance();
    void saveToFile(String fileName) throws IOException;
    void addCell(Nominal nominal);
    void getCell(Nominal nominal);
}
