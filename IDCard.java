import java.time.Year;
public class IDCard {
      private int Indent;
      private String name;
      private String sex;
      private String dayOfBirth;
      private String address;
      private int phoneNum;
      public IDCard(int IndentifierID, String name, String sex, String dayOfBirth, String address, int PhoneNum)
      {
            this.Indent=IndentifierID;
            this.name=name;
            this.sex=sex;
            this.dayOfBirth=dayOfBirth;
            this.address=address;
            this.phoneNum=PhoneNum;
      }
      public int getIndent()
      {
            return this.Indent;
      }
      public String getName() 
      {
          return name;
      }
      public String getSex() 
      {
          return sex;
      }
      public String getDayOfBirth() 
      {
          return dayOfBirth;
      }
      public String getAddress() 
      {
          return address;
      }
      public int getPhoneNum() 
      {
          return phoneNum;
      }
      public void setName(String name) 
      {
          this.name = name;
      }
      public void setIndent(int indent) 
      {
          Indent = indent;
      }
      public void setAddress(String address) 
      {
          this.address = address;
      }
      public void setDayOfBirth(String dayOfBirth) 
      {
          this.dayOfBirth = dayOfBirth;
      }
      public void setPhoneNum(int phoneNum) 
      {
          this.phoneNum = phoneNum;
      }
      public void setSex(String sex) 
      {
          this.sex = sex;
      }
        public int getYear()
	{
        int namhientai=Year.now().getValue();
		String[] getYear=dayOfBirth.split("/");
		int age=namhientai-Integer.valueOf(getYear[2]);
		return age;
	}
      @Override
      public String toString() {
          return this.Indent+","+this.name+","+this.sex+","+this.dayOfBirth+","+this.address+","+this.phoneNum;
      }

}
