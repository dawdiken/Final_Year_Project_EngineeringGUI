public class NewJobEntry {

    private Integer jobId;
    private String jobNumber = "";
    private String active = "";
    private String customerName = "";
    private String department = "";
    private String partName = "";
    private Integer batchNumber;
    private String batchQty = "";
    private Integer machineID;
    private Integer qtyMade;
    private Integer qtyScrap;
    private String dropPath = "";
    private String techniaclDrawing = "";
    private String dimension;
    private String partSop = "";
    private String userName = "";
    private String Password = "";
    private String Role = "";
    private String qtyOrdered = "";


    // empty constructor
    public NewJobEntry() {
    }

    public void setCustomerName(String first )
    {
        customerName = first;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setPartName(String last )
    {
        partName = last;
    }

    public String getPartName()
    {
        return partName;
    }

    public void setPartSop(String firstLine )
    {
        partSop = firstLine;
    }

    public String getPartSop()
    {
        return partSop;
    }

    public void setDepartment(String secondLine )
    {
        department = secondLine;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setBatchQty(String personCity )
    {
        batchQty = personCity;
    }

    public String getBatchQty()
    {
        return batchQty;
    }

    public String getDropPath() {
        return dropPath;
    }

    public void setDropPath(String dropPath) {
        this.dropPath = dropPath;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Integer getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Integer getMachineID() {
        return machineID;
    }

    public void setMachineID(Integer machineID) {
        this.machineID = machineID;
    }

    public Integer getQtyMade() {
        return qtyMade;
    }

    public void setQtyMade(Integer qtyMade) {
        this.qtyMade = qtyMade;
    }

    public Integer getQtyScrap() {
        return qtyScrap;
    }

    public void setQtyScrap(Integer qtyScrap) {
        this.qtyScrap = qtyScrap;
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

    public String getTechniaclDrawing() {
        return techniaclDrawing;
    }

    public void setTechniaclDrawing(String techniaclDrawing) {
        this.techniaclDrawing = techniaclDrawing;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getQtyOrdered() { return qtyOrdered; }

    public void setQtyOrdered(String qtyOrdered) { this.qtyOrdered = qtyOrdered; }


}  // end class NewJobEntry
