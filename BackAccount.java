public class BankAccount implements Payment, Transfer{
    private int sotaikhoan;
    private double tilelaixuat;
    private double sodutaikhoan;
    public BankAccount(int sotaikhoan, double tilelaixuat)
    {
        this.sotaikhoan=sotaikhoan;
        this.tilelaixuat=tilelaixuat;
        sodutaikhoan=50;
    }
    public void topUp(double monney)
	{
		this.sodutaikhoan=monney;
	}
    public int getSTK()
    {
        return this.sotaikhoan;
    }
    @Override
    public double checkBalance()
    {
        return sodutaikhoan;
    }
    @Override
    public boolean pay(double amount)
    {
        double sotien=amount+50;
        if(sotien>sodutaikhoan)
        {
            return false;
        }
        sodutaikhoan-=amount;
        return true;
    }
    public void deposit(double amount)
    {
        this.sodutaikhoan+=amount;
    }
    @Override
    public boolean transfer (double amount, Transfer to)
    {
        double sotienchuyen=amount*(1+transferFee);
        double sotien=sotienchuyen+50;
        if(sotien>sodutaikhoan)
        {
            return false;
        }
        return true;
    }
    public boolean transfer(double amount, EWallet to)
	{
		double sotienchuyen;
		sotienchuyen=amount+amount*transferFee;
		if((sotienchuyen+50)>sodutaikhoan)
		{
			return false;
		}
		sodutaikhoan-=sotienchuyen;
		to.deposit(amount);
		return true;
	}
	public boolean transfer(double amount, BankAccount to)
	{
		double sotienchuyen;
		sotienchuyen=amount+amount*transferFee;
		if((sotienchuyen+50)>sodutaikhoan)
		{
			return false;
		}
		sodutaikhoan-=sotienchuyen;
		to.deposit(amount);
		return true;
	}
    @Override
    public String toString() {
        return sotaikhoan+","+tilelaixuat+","+sodutaikhoan;
    }
}
