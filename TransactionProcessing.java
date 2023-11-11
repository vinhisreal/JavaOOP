import java.util.*;
import java.io.*;

public class TransactionProcessing {
    private ArrayList<Payment> paymentObjects;
    private IDCardManagement idcm;
    
    public TransactionProcessing(String idCardPath, String paymentPath) {
        idcm = new IDCardManagement(idCardPath);
        readPaymentObject(paymentPath);
    
    }

    public ArrayList<Payment> getPaymentObject() {
        return this.paymentObjects;
    }
    // Requirement 3
    public boolean readPaymentObject(String path) {
        paymentObjects=new ArrayList<>();
        try
        {
            File myObj=new File(path);
            Scanner myReader=new Scanner(myObj);
            while(myReader.hasNextLine())
            {
                String data=myReader.nextLine();
                if(data.length()==6)
                {
                    for(IDCard x: idcm.getIDCards())
                    {
                        if(x.getIndent()==Integer.valueOf(data))
                        {
                            try 
                            {
                                ConvenientCard Card1=new ConvenientCard(x);
                                paymentObjects.add(Card1);
                            } catch (CannotCreateCard e) {
                                System.out.print(e);;

                            }
                                
                        }
                    }
                }
                else if(data.length()==7)
                {
                    EWallet Card2=new EWallet(Integer.valueOf(data));
                    paymentObjects.add(Card2);
                }
                else 
                {
                    String[] data1=data.split(",");
                    BankAccount Card3=new BankAccount(Integer.valueOf(data1[0]), Double.valueOf(data1[1]));
                    paymentObjects.add(Card3);
                } 
            }
            myReader.close();
        }
        catch (IOException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return true;
    }

    // Requirement 4
    public ArrayList<ConvenientCard> getAdultConvenientCards() {
        ArrayList<ConvenientCard> Req4=new ArrayList<>();
        for(Payment x: paymentObjects)
        {
            String[] temp1=x.toString().split(",");
            if(temp1.length==8)
            {
                for(IDCard y: idcm.getIDCards())
                {
                    if(temp1[0].equalsIgnoreCase(String.valueOf(y.getIndent())))
                    {
                        try
                        {
                            ConvenientCard card1=new ConvenientCard(y);
                            if(card1.getType().equalsIgnoreCase("Adult"))
                            {
                                Req4.add(card1);
                            }
                        }
                        catch (CannotCreateCard e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return Req4;
    }

    // Requirement 5
    public ArrayList<IDCard> getCustomersHaveBoth() {
        ArrayList<IDCard> newList=new ArrayList<>();
        for(IDCard x: idcm.getIDCards())
        {
            boolean flag1=false,flag2=false,flag3=false;
            for(Payment y: paymentObjects)
            {
                String[] temp1=y.toString().split(",");
                if(x.getIndent()==Integer.valueOf(temp1[0]))
                {
                    if(temp1.length==8)
                    {
                        flag1=true;
                    }
                    if(temp1.length==3)
                    {
                        flag2=true;
                    }
                }
                if(x.getPhoneNum()==Integer.valueOf(temp1[0]))
                {
                    flag3=true;
                }
            }
            if(flag1&&flag2&&flag3)
            {
                newList.add(x);
            }
        }
        return newList;
    }

    // Requirement 6
    public void processTopUp(String path) {
        //ArrayList CC//
        ArrayList<ConvenientCard> ListCC=new ArrayList<>();
        ArrayList<EWallet> ListEW=new ArrayList<>();
        ArrayList<BankAccount> ListBC=new ArrayList<>();

        for(Payment x: paymentObjects)
        {
            if(x instanceof ConvenientCard)
            {
                ListCC.add((ConvenientCard) x);
            }
            else if(x instanceof EWallet)
            {
                ListEW.add((EWallet) x);
            }
            else
            {
                ListBC.add((BankAccount) x);
            }
        }
        try {
            File myObj=new File(path);
            Scanner myScanner=new Scanner(myObj);
            while(myScanner.hasNextLine())
            {
                String data=myScanner.nextLine();
                String[] data1=data.split(",");
                if(data1[0].equalsIgnoreCase("CC"))
                {
                    for(ConvenientCard x: ListCC)
                    {
                        if(x.getID()==Integer.valueOf(data1[1]))
                        {
                            x.deposit(Double.valueOf(data1[2]));
                        }
                    }
                }
                if(data1[0].equalsIgnoreCase("EW"))
                {
                    for(EWallet x: ListEW)
                    {
                        if(x.getphoneNum()==Integer.valueOf(data1[1]))
                        {
                            x.deposit(Double.valueOf(data1[2]));
                        }
                    }
                }
                if(data1[0].equalsIgnoreCase("BA"))
                {
                    for(BankAccount x: ListBC)
                    {
                        if(x.getSTK()==Integer.valueOf(data1[1]))
                        {
                            x.deposit(Double.valueOf(data1[2]));
                        }
                    }
                }
            }
            myScanner.close();;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Requirement 7
    public ArrayList<Bill> getUnsuccessfulTransactions(String path) {
        ArrayList<Bill> newList=new ArrayList<>();
        ArrayList<ConvenientCard> ListCC=new ArrayList<>();
        ArrayList<EWallet> ListEW=new ArrayList<>();
        ArrayList<BankAccount> ListBA=new ArrayList<>();

        for(Payment x: paymentObjects)
        {
            if(x instanceof ConvenientCard)
            {
                ListCC.add((ConvenientCard) x);
            }
            else if(x instanceof EWallet)
            {
                ListEW.add((EWallet) x);
            }
            else
            {
                ListBA.add((BankAccount) x);
            }
        }
        try
        {
            File myObj=new File(path);
            Scanner myScanner=new Scanner(myObj);
            while(myScanner.hasNextLine())
            {
                String data=myScanner.nextLine();
                String[] data1=data.split(",");
                if(data1[3].equalsIgnoreCase("CC"))
                {
                    for(ConvenientCard x: ListCC)
                    {
                        if(x.getID()==Integer.valueOf(data1[4]))
                        {
                            if(!x.pay(Double.valueOf(data1[1])))
                            {
                                Bill newBill=new Bill(Integer.valueOf(data1[0]), Double.valueOf(data1[1]), data1[2]);
                                newList.add(newBill);
                            }
                        }
                    }
                }
                if(data1[3].equalsIgnoreCase("EW"))
                {
                    for(EWallet x: ListEW)
                    {
                        if(x.getphoneNum()==Integer.valueOf(data1[4]))
                        {
                            if(!x.pay(Double.valueOf(data1[1])))
                            {
                                Bill newBill=new Bill(Integer.valueOf(data1[0]), Double.valueOf(data1[1]), data1[2]);
                                newList.add(newBill);
                            }
                        }
                    }
                }
                if(data1[3].equalsIgnoreCase("BA"))
                {
                    for(BankAccount x: ListBA)
                    {
                        if(x.getSTK()==Integer.valueOf(data1[4]))
                        {
                            if(!x.pay(Double.valueOf(data1[1])))
                            {
                                Bill newBill=new Bill(Integer.valueOf(data1[0]), Double.valueOf(data1[1]), data1[2]);
                                newList.add(newBill);
                            }
                        }
                    }
                }
            }
            myScanner.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return newList;
    }

    // Requirement 8
    public ArrayList<BankAccount> getLargestPaymentByBA(String path) {
        ArrayList<BankAccount> newList=new ArrayList<>();
        ArrayList<BankAccount> ListBA=new ArrayList<>();
        HashMap<Integer, Double> Sotientieu=new HashMap<>();
        for(Payment x: paymentObjects)
        {
            if(x instanceof BankAccount)
            {
                ListBA.add((BankAccount) x);
            }
        }
        try
        {
            File myObj=new File(path);
            Scanner myScanner=new Scanner(myObj);
            while(myScanner.hasNextLine())
            {
                String data=myScanner.nextLine();
                String[] data1=data.split(",");
                if(data1[3].equalsIgnoreCase("BA"))
                {
                    for(BankAccount x: ListBA)
                    {
                        if(x.getSTK()==Integer.valueOf(data1[4]))
                        {
                            if(x.pay(Double.valueOf(data1[1])))
                            {     
                                double currentSum= Sotientieu.getOrDefault(x.getSTK(), 0.0);                           
                                Sotientieu.put(x.getSTK(), currentSum+Double.valueOf(data1[1]));
                            }
                        }
                    }
                }
            }
            myScanner.close();
            Set<Integer> keySet1=Sotientieu.keySet();
            double max=0;
            for(Integer x: keySet1)
            {
                if(max<=Sotientieu.get(x))
                {
                    max=Sotientieu.get(x);
                }
            }
            for(Integer x: keySet1)
            {
                if(max==Sotientieu.get(x))
                {
                    for(BankAccount y: ListBA)
                    {
                        if(x==y.getSTK())
                        {
                            newList.add(y);
                        }
                    }
                }
            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return newList;
    }

    //Requirement 9
    public void processTransactionWithDiscount(String path) {
        ArrayList<ConvenientCard> ListCC=new ArrayList<>();
        ArrayList<EWallet> ListEW=new ArrayList<>();
        ArrayList<BankAccount> ListBA=new ArrayList<>();

        for(Payment x: paymentObjects)
        {
            if(x instanceof ConvenientCard)
            {
                ListCC.add((ConvenientCard) x);
            }
            else if(x instanceof EWallet)
            {
                ListEW.add((EWallet) x);
            }
            else
            {
                ListBA.add((BankAccount) x);
            }
        }
        try
        {
            File myObj=new File(path);
            Scanner myScanner=new Scanner(myObj);
            while(myScanner.hasNextLine())
            {
                String data=myScanner.nextLine();
                String[] data1=data.split(",");
                if(data1[3].equalsIgnoreCase("CC"))
                {
                    for(ConvenientCard x: ListCC)
                    {
                        if(x.getID()==Integer.valueOf(data1[4]))
                        {
                            x.pay(Double.valueOf(data1[1]));
                        }
                    }
                }
                if(data1[3].equalsIgnoreCase("EW"))
                {
                    for(EWallet x: ListEW)
                    {
                        if(x.getphoneNum()==Integer.valueOf(data1[4]))
                        {
                            for(IDCard y: idcm.getIDCards())
                            {
                                if(x.getphoneNum()==y.getPhoneNum())
                                {
                                    if(y.getSex().equalsIgnoreCase("Male")&&y.getYear()<20&&Double.valueOf(data1[1])>500)
                                    {
                                        double i=Double.valueOf(data1[1]);
                                        i=i-i*0.15;
                                        x.pay(i);
                                    }
                                    else if(y.getSex().equalsIgnoreCase("Female")&&y.getYear()<18&&Double.valueOf(data1[1])>500)
                                    {
                                        double i=Double.valueOf(data1[1]);
                                        i=i-i*0.15;
                                        x.pay(i);
                                    }
                                    else
                                    {
                                        x.pay(Double.valueOf(data1[1]));
                                    }
                                }
                            }
                        }
                    }
                }
                if(data1[3].equalsIgnoreCase("BA"))
                {
                    for(BankAccount x: ListBA)
                    {
                        if(x.getSTK()==Integer.valueOf(data1[4]))
                        {
                            x.pay(Double.valueOf(data1[1]));
                        }
                    }
                }
            }
            myScanner.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
