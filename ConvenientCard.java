import java.time.Year;
public class ConvenientCard implements Payment{
	// code here
	
    private String type;
	private IDCard TheDD;
	private double sodu;
	public String getType() {
		return this.type;
	}
	public ConvenientCard(IDCard TheDinhDanh) throws CannotCreateCard
	{
		this.TheDD=TheDinhDanh;
		this.sodu=100;
		if(getDayOfBirth()<12)
		{
			throw new CannotCreateCard("Not enough age");
		}
		else
		{
			setCardType();
		}
	}
	private int getDayOfBirth()
	{
		int namhientai=Year.now().getValue();
		String[] getYear=this.TheDD.getDayOfBirth().split("/");
		int age=namhientai-Integer.valueOf(getYear[2]);
		return age;
	}
	private void setCardType()
	{
		if(getDayOfBirth()<=18)
		{
			this.type="Student";
		}
		else
		{
			this.type="Adult";
		}
	}
	public int getID()
	{
		return TheDD.getIndent();
	}
	public boolean pay(double amount)
	{
		double TienCanThanhToan;
		if(type.equalsIgnoreCase("Student"))
		{
			TienCanThanhToan=amount;
		}
		else
		{
			TienCanThanhToan=amount+amount*0.01;
		}
		if(TienCanThanhToan<=sodu)
		{
			sodu-=TienCanThanhToan;
			return true;
		}
		else
		{
			return false;
		}
	}
	public double checkBalance()
	{
		return sodu;
	}
	public void deposit(double depositAmount)
	{
		this.sodu+=depositAmount;
	}

	@Override
	public String toString() {
		return TheDD+","+this.type+","+this.sodu;
	}
}

