import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Starter {
    private ATMImpl atm;
    private String FILE_NAME="";
    public static void main(String args[]) throws IOException {
        Starter starter=new Starter();
        starter.FILE_NAME=args[0];
        if (starter.FILE_NAME.trim().length()>0)
            starter.startAtm(starter.FILE_NAME);
        else
            starter.startAtm();
        starter.startClientInteraction();
    }
    public void startAtm(){
        atm=new ATMImpl();
    }
    public void startAtm(String fileName) throws IOException {
        atm=new ATMImpl(fileName);
    }
    private  void serviceMenu()
    {
        System.out.println("Введите пароль для доступа в сервисное меню");
        Scanner scanner=new Scanner(System.in);
        String pass=scanner.nextLine();
        if (pass.equals("123456"))
        {
            String commandUser="";
            while (!commandUser.equalsIgnoreCase("exit")) {
                System.out.println("Введите команду (addCell, getCell  или exit):");
                commandUser = scanner.nextLine();
                switch (commandUser.toLowerCase()) {
                    case "addcell": {
                        System.out.println("Введите номинал:");
                        Integer nom=scanner.nextInt();
                        Nominal userNominal=Nominal.getNominalFromInt(nom);
                        if (userNominal!=null) {
                            atm.addCell(userNominal);
                            System.out.println("Успешно!");
                        }
                        else
                        {
                            System.out.println("В нашей стране такие купюры не существуют");
                        }
                        break;
                    }
                    case "getcell": {
                        System.out.println("Введите номинал:");
                        Integer nom = scanner.nextInt();
                        Nominal userNominal=Nominal.getNominalFromInt(nom);
                        if (userNominal!=null) {
                            atm.getCell(userNominal);
                        }
                        else
                        {
                            System.out.println("В нашей стране такие купюры не существуют");
                        }
                        break;
                    }
                }
                scanner.nextLine();
            }
        }
    }
    private  void startClientInteraction() throws IOException {
        System.out.println("Здравствуйте. Введите Ваше имя:");
        Scanner scanner=new Scanner(System.in);
        String nameClient=scanner.nextLine();
        String commandUser="";
        while (!commandUser.equalsIgnoreCase("exit")) {
            System.out.println("Привет "+nameClient+"! Введите команду (add, get или exit):");
            commandUser = scanner.nextLine();
            switch (commandUser.toLowerCase()) {
                case "add": {
                    System.out.println("Введите номинал:");
                    String inputSum=scanner.nextLine();
                    String[] arrSum=inputSum.split(" ");
                    Boolean isSuccess=true;
                    for (String str:arrSum) {
                        try {
                            Integer nom = Integer.parseInt(str);
                            Nominal nominal=Nominal.getNominalFromInt(nom);
                            if (nominal!=null) {
                                List<Nominal> listNominal = new ArrayList<>();
                                listNominal.add(nominal);
                                atm.putCash(listNominal);
                            }
                            else
                            {
                                System.out.println("Такой купюры ["+nom+"] не существует! Операция отменена");
                                isSuccess=false;
                                break;
                            }
                        }
                        catch (NumberFormatException ne){
                            System.out.println("Введен некорректный номинал - ["+str+"]! Операция отменена");
                            isSuccess=false;
                            break;
                        }
                    }
                    if (isSuccess)
                        System.out.println("Успешно!");
                    break;
                    /*Integer nom=scanner.nextInt();

                    Nominal userNominal=Nominal.getNominalFromInt(nom);
                    if (userNominal!=null) {
                        List<Nominal> listNominal=new ArrayList<>();
                        listNominal.add(userNominal);
                        atm.putCash(listNominal);
                        System.out.println("Успешно!");
                    }
                    else
                    {
                        System.out.println("Что происходит?");
                    }*/
                }
                case "get": {
                    System.out.println("Введите сумму:");
                    Integer sum = scanner.nextInt();
                    List<Nominal> clientCash = atm.getCash(sum);
                    if (clientCash==null)
                    {
                        System.out.println("Требуемой суммы нет в наличии");
                    }
                    else {
                        System.out.println(clientCash);
                    }
                    break;
                }
                case "service":
                {
                    serviceMenu();
                    break;
                }
            }
            scanner.nextLine();
        }
        ((ATMService)atm).saveToFile(FILE_NAME);
    }
}
