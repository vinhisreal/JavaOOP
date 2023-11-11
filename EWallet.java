public class EWallet implements Payment, Transfer {
	private int phoneNum;
	private double sodutaikhoan;
	public EWallet(int phoneNum)
	{
		this.phoneNum=phoneNum;
		sodutaikhoan=0;
	}
	public void topUp(double monney)
	{
		this.sodutaikhoan=monney;
	}
	public boolean pay(double amount)
	{
		if(amount<=sodutaikhoan)
		{
			this.sodutaikhoan-=amount;
			return true;
		}
		else
		{
			return false;
		}
	}
	public double checkBalance()
	{
		return sodutaikhoan;
	}
	@Override
	public boolean transfer(double amount, Transfer to)
	{
		double sotienchuyen;
		sotienchuyen=amount+amount*transferFee;
		if(sotienchuyen>sodutaikhoan)
		{
			return false;
		}
		return true;
	}
	public boolean transfer(double amount, EWallet to)
	{
		double sotienchuyen;
		sotienchuyen=amount+amount*transferFee;
		if(sotienchuyen>sodutaikhoan)
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
		if(sotienchuyen>sodutaikhoan)
		{
			return false;
		}
		sodutaikhoan-=sotienchuyen;
		to.deposit(amount);
		return true;
	}
	public void deposit(double Amount)
	{
		sodutaikhoan+=Amount;
	}
	public int getphoneNum()
	{
		return this.phoneNum;
	}
	@Override
    public String toString() 
	{
        return phoneNum + "," + sodutaikhoan;
    }
}
