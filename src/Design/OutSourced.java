package Design;

/**
 * Class For Outsourced parts.
 * @author Max perrigo
 */
public class OutSourced extends Part {

    private String companyName;
    /**
     * This method sets the information for a part.
     * @param partID Default Value Int
     * @param name Default Value String
     * @param price Default Value Double
     * @param numInStock Default Value Int
     * @param min Default Value Int
     * @param max Default Value INt
     * @param company Default Value
     * 
     */
    public OutSourced(int partID, String name, double price, int numInStock, int min, int max, String company) {
        super(partID, name, price, numInStock, min, max);
        setCompanyName(company);
    }
    /**
     * Returns Company Name
     * @return  companyName
     */
    public String getCompanyName() {
        return companyName;
    }
    /**
     * Sets the Company Name.
     * @param name Set Name
     */
    public void setCompanyName(String name) {
        this.companyName = name;
    }

}
