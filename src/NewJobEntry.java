public class NewJobEntry {
    private String customerName = "";
    private String partName = "";
    private String partSop = "";
    private String department = "";
    private String batchQty = "";

    private String userName = "";
    private String Password = "";



    private String jobNumber = "";
//    private String state = "";
//    private String zipcode = "";
//    private String phoneNumber = "";
//    private String phoneNumber2 = "";
//    private String phoneNumber3 = "";
//    private String phoneNumber4= "";
//    private String phoneNumber5 = "";
//    private String emailAddress = "";
//    private int personID;
//    private int addressID;
//    private int phoneID;
//    private int emailID;

    // empty constructor
    public NewJobEntry()
    {
    }

    // set person's id
    public NewJobEntry( int id )
    {

    }

    // set customer name
    public void setCustomerName(String first )
    {
        System.out.println("setCustName first = "+ first);
        customerName = first;
    }

    // get customer name
    public String getCustomerName()
    {
        return customerName;
    }

    // set part name
    public void setPartName(String last )
    {
        partName = last;
    }

    // get part name
    public String getPartName()
    {
        return partName;
    }

    // set part SOP
    public void setPartSop(String firstLine )
    {
        partSop = firstLine;
    }

    // get part SOP
    public String getPartSop()
    {
        return partSop;
    }

    // set department
    public void setDepartment(String secondLine )
    {
        department = secondLine;
    }

    // get department
    public String getDepartment()
    {
        return department;
    }


    // set batchQty
    public void setBatchQty(String personCity )
    {
        batchQty = personCity;
    }

    // get batchQty
    public String getBatchQty()
    {
        return batchQty;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
//    // set state in which person lives
//    public void setState( String personState )
//    {
//        state = personState;
//    }
//
//    // get state in which person lives
//    public String getState()
//    {
//        return state;
//    }
//
//    // set person's zip code
//    public void setZipcode(String zip )
//    {
//        zipcode = zip;
//    }
//
//    // get person's zip code
//    public String getZipcode()
//    {
//        return zipcode;
//    }
//
//    // set person's phone number
//    public void setPhoneNumber( String number )
//    {
//        phoneNumber = number;
//    }
//
//    // get person's phone number
//    public String getPhoneNumber()
//    {
//        return phoneNumber;
//    }
//
//    // set person's email address
//    public void setEmailAddress( String email )
//    {
//        emailAddress = email;
//    }
//
//    // get person's email address
//    public String getEmailAddress()
//    {
//        return emailAddress;
//    }
//
//    // get person's ID
//    public int getPersonID()
//    {
//        return personID;
//    }
//
//    // set person's ID
//    public int setPersonID(int id)
//    {
//        return personID = id;
//    }
//
//    // set person's addressID
//    public void setAddressID( int id )
//    {
//        addressID = id;
//    }
//
//    // get person's addressID
//    public int getAddressID()
//    {
//        return addressID;
//    }
//
//    // set person's phoneID
//    public void setPhoneID( int id )
//    {
//        phoneID = id;
//    }
//
//    // get person's phoneID
//    public int getPhoneID()
//    {
//        return phoneID;
//    }
//
//    // set person's emailID
//    public void setEmailID( int id )
//    {
//        emailID = id;
//    }
//
//    // get person's emailID
//    public int getEmailID()
//    {
//        return emailID;
//    }
//
//    // set person's phone number
//    public void setPhoneNumber2( String number )
//    {
//        phoneNumber2 = number;
//    }
//
//    // get person's phone number
//    public String getPhoneNumber2()
//    {
//        return phoneNumber2;
//    }
//
//    // set person's phone number
//    public void setPhoneNumber3( String number )
//    {
//        phoneNumber3 = number;
//    }
//
//    // get person's phone number
//    public String getPhoneNumber3()
//    {
//        return phoneNumber3;
//    }
//
//    // set person's phone number
//    public void setPhoneNumber4( String number )
//    {
//        phoneNumber4 = number;
//    }
//
//    // get person's phone number
//    public String getPhoneNumber4()
//    {
//        return phoneNumber4;
//    }
//
//    // set person's phone number
//    public void setPhoneNumber5( String number )
//    {
//        phoneNumber5 = number;
//    }
//
//    // get person's phone number
//    public String getPhoneNumber5()
//    {
//        return phoneNumber5;
//    }
}  // end class NewJobEntry