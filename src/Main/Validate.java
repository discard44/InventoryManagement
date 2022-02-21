package Main;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import javafx.scene.control.TextField;

/**
 * Class to validate the data that is inputted into the text fields.
 *
 * @author Max Perrigo
 */
public class Validate {

    /**
     * Verifies that the text entered is all text and no numbers.
     *
     * @param input Takes a TextField to validate the string
     * @return Boolean Value
     */
    public boolean textverify(TextField input) {
        boolean isAlphabet = false;
        String valText = (String) input.getText();

        try {
            if (!valText.isEmpty()) {;
                System.out.print(parseInt(valText.substring(0, 1)));
            }

            isAlphabet = true;
        } catch (NumberFormatException e) {

        }

        return isAlphabet;
    }
    
    public boolean textverify(String input){
        boolean isAlphabet = false;
        
        try{
            String test = (String) input;
            isAlphabet = true;
        }catch (Exception e){
            
        }
        return isAlphabet;
    }

    /**
     * Verifies that a TextField data is a number.
     *
     * @param input TextField
     * @return Boolean value
     */
    public boolean number(TextField input) {
        boolean numorfloat = false;
        String validationString = input.getText();

        try {
            if(!validationString.isEmpty()){
                parseInt(validationString);
                numorfloat = true;
            }
                
        }catch(Exception e){
          
        }
        return numorfloat;
    }
    public boolean number(String input) {
        boolean numorfloat = false;
        

        try {
            if(!input.isEmpty()){
                parseInt(input);
                numorfloat = true;
            }
                
        }catch(Exception e){
          
        }
        return numorfloat;
    }

    /**
     * Verifies that a fields text is a Double.
     *
     * @param input TextField.
     * @return Boolean
     */
    public boolean dub(TextField input) {
        boolean numorfloat = false;
        String testDouble = input.getText();

        try {
            parseDouble(testDouble);
            numorfloat = true;
        } catch (NumberFormatException e) {
            
        }

        return numorfloat;
    }

    /**
     * Verifies that stock is between the Min and Max Values.
     *
     * @param inStock Number of Stock
     * @param mn Value of Min Field
     * @param mx Value of Max Field
     * @return Boolean
     */
    public boolean isBetween(TextField inStock, TextField mn, TextField mx) {
        boolean between = false;

        int stock = parseInt(inStock.getText().trim());
        int min = parseInt(mn.getText().trim());
        int max = parseInt(mx.getText().trim());

        if (stock >= min && stock <= max) {
            between = true;
        }
        return between;
    }

    /**
     * Verifies that the Min value is less then the Max number.
     *
     * @param mn Value for Minimuml.
     * @param mx Value for Maximum.
     * @return boolean value.
     */
    public boolean isGreater(TextField mn, TextField mx) {
        boolean great = false;

        int min = parseInt(mn.getText().trim());
        int max = parseInt(mx.getText().trim());

        if (min < max) {
            great = true;
        }
        return great;
    }

}
