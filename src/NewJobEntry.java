import com.google.api.client.json.Json;
import com.google.gson.JsonArray;
import netscape.javascript.JSObject;
import org.json.JSONArray;

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

    // empty constructor
    public NewJobEntry()
    {
    }


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

}  // end class NewJobEntry
