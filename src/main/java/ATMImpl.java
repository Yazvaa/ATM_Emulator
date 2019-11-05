import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class ATMImpl implements ATM, ATMService {
    private Map<Nominal, Cell> cellMap = new HashMap<Nominal, Cell>();
    private Map<Nominal, List<Cell>> fullCellMap=new HashMap<>();

    public ATMImpl() {
        for (Nominal nominal: Nominal.values()) {
            Cell cell = new CellImpl(nominal, 0);
            cellMap.put(nominal, cell);
            /*List<Cell> listCell=new ArrayList<>();
            listCell.add(cell);
            fullCellMap.put(nominal,listCell);*/
        }
    }

    public ATMImpl(String fileName) throws IOException {
        List<String> listIni = readIniFile(fileName);
        if (listIni!=null)
        {
            for(String str:listIni)
            {
                String splitStr[]=str.split(":");
                Integer count=Integer.parseInt(splitStr[1]);
                Integer currNominal=Integer.parseInt(splitStr[0]);
                Cell cell=new CellImpl(Nominal.getNominalFromInt(currNominal), count);
                cellMap.put(cell.getNominal(), cell);
                /*List<Cell> listCell=new ArrayList<>();
                listCell.add(cell);
                fullCellMap.put(cell.getNominal(),listCell);*/
            }
        }
    }
    @Override
    public void putCash(List<Nominal> cashList) {
        for (Nominal nominal: cashList) {
            Cell cell = cellMap.get(nominal);
            cell.put(1);
        }
    }

    @Override
    public List<Nominal> getCash(Integer sum) {
        Set<Nominal> nominals = cellMap.keySet();
        List<Nominal> nominalsList = new ArrayList<>();
        nominalsList.addAll(nominals);
        Collections.sort(nominalsList, new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                Nominal n1 = (Nominal) o1;
                Nominal n2 = (Nominal) o2;
                return n2.getNominal().compareTo(n1.getNominal());
            }
        });

        Map<Nominal, Integer> requiredCashList = new HashMap<Nominal, Integer>();
        for (Nominal nominal: nominalsList) {
            requiredCashList.put(nominal, 0);
        }
        int rest = sum;
        for (Nominal nominal: nominalsList) {
            int currentNominalUsedCount = 0;
            while (rest >= nominal.getNominal()
                    && cellMap.get(nominal).getCount() > currentNominalUsedCount) {
                requiredCashList.put(
                        nominal,
                        requiredCashList.get(nominal) + 1
                );
                currentNominalUsedCount++;
                rest -= nominal.getNominal();
            }
        }
        if (rest != 0) {
            return null;
        }
        List<Nominal> cashList = new ArrayList<>();
        for (Map.Entry<Nominal, Integer> entry : requiredCashList.entrySet()) {
            for(int i = 0; i < entry.getValue(); i++) {
                cashList.add(entry.getKey());
            }
            Cell cell = cellMap.get(entry.getKey());
            cell.get(entry.getValue());
//            this.cellMap.put(
//                    entry.getKey(),
//                    requiredCashList.get(entry.getKey()) - entry.getValue()
//            );
        }
        return cashList;
    }
    @Override
    public void saveToFile(String fileName) throws IOException
    {
        File file=new File(fileName);
        if (file.exists())
            file.delete();
        FileWriter writer=new FileWriter(file);
        for(Cell item:cellMap.values()){
            String str=item.getNominal().getNominal()+":"+item.getCount()+"\n";
            writer.write(str);
        }
        writer.flush();
        writer.close();
    }
    public List<String> readIniFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            List<String> linesNominal = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                linesNominal.add(line);
            }
            return linesNominal;
        }
        else
            return null;
    }
    @Override
    public Integer getBalance(){
        Integer balance=0;
        for (Cell cell:this.cellMap.values()){
            balance+=cell.getCount()*cell.getNominal().getNominal();
        }
        return  balance;
    }
    public void addCell(Nominal nominal, Integer count)
    {
        List<Cell> cellList=fullCellMap.get(nominal);
        Cell newCell=new CellImpl(nominal,count);
        if (cellList!=null) {
            cellList.add(newCell);
        }
        else  {
            cellList=new ArrayList<>();
            cellList.add(newCell);
        }
        fullCellMap.put(nominal,cellList);
    }
    public void addCell(Nominal nominal)
    {
        List<Cell> cellList=fullCellMap.get(nominal);
        Cell newCell=new CellImpl(nominal,0);
        if (cellList!=null) {
            cellList.add(newCell);
        }
        else  {
            cellList=new ArrayList<>();
            cellList.add(newCell);
        }
        fullCellMap.put(nominal,cellList);
    }
    public void getCell(Nominal nominal)
    {
        List<Cell> cellList=fullCellMap.get(nominal);
        if (cellList!=null)
        {
            cellList.remove(cellList.size()-1);
            if (cellList.size()==0)
            {
                for (Iterator<Map.Entry<Nominal, List<Cell>>> it=fullCellMap.entrySet().iterator();it.hasNext();)
                {
                    Map.Entry<Nominal,List<Cell>> entry=it.next();
                    if (entry.getKey().equals(cellList))
                    {
                        it.remove();
                    }
                }
            }
            System.out.println("Успешно");
        }
        else
            System.out.println("Ячейки с таким номиналом нет!");
    }
}
