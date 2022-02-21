package Main;

import Design.InHouse;
import Design.Inventory;
import Design.OutSourced;
import Design.Part;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class to manage the Actions for the Modify Part window.
 *
 * @author Max Perrigo
 */
public class ModifyPartController implements Initializable {

    private Part part;
    private Stage stage;

    @FXML
    private RadioButton iHouse;

    @FXML
    private ToggleGroup parttype1;

    @FXML
    private RadioButton osourced;

    @FXML
    private TextField pid;

    @FXML
    private TextField name;

    @FXML
    private TextField stock;

    @FXML
    private TextField price;

    @FXML
    private TextField mx;

    @FXML
    private TextField mn;

    @FXML
    private Label cm;

    @FXML
    private TextField compMachField;

    @FXML
    private Button saveMod;

    @FXML
    private Button modCan;

    @FXML
    private Label errorArea;

    /**
     * Initializes part stage and inventory.
     *
     * @param stage Passed Stage: Stage
     * @param part Passed Part to Modify: Part Class
     */
    public void initData(Stage stage, Part part) {
        this.part = part;
        this.stage = stage;

        // Set data in the fields based on the part received.
        if (this.part instanceof InHouse) {

            InHouse part1 = (InHouse) this.part;
            iHouse.setSelected(true);
            cm.setText("Machine ID");
            this.name.setText(part1.getName());
            this.pid.setText((Integer.toString(part1.getId())));           
            this.stock.setText((Integer.toString(part1.getStock())));
            this.price.setText((Double.toString(part1.getPrice())));
            this.mn.setText((Integer.toString(part1.getMin())));
            this.mx.setText((Integer.toString(part1.getMax())));
            this.compMachField.setText((Integer.toString(part1.getMachineID())));
            
            //Custom message initialize
            

        }

        if (this.part instanceof OutSourced) {

            OutSourced part2 = (OutSourced) this.part;
            osourced.setSelected(true);
            cm.setText("Company Name");
            this.name.setText(part2.getName());
            this.pid.setText((Integer.toString(part2.getId())));
            this.stock.setText((Integer.toString(part2.getStock())));
            this.price.setText((Double.toString(part2.getPrice())));
            this.mn.setText((Integer.toString(part2.getMin())));
            this.mx.setText((Integer.toString(part2.getMax())));
            this.compMachField.setText(part2.getCompanyName());
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }
    /**
     * Changes the value of the text field for the Company name or Machine ID
     * @param event Button click event; Action Event
     */
    @FXML
    public void modifyRadio(ActionEvent event) {
        if (part instanceof OutSourced) {
            if (osourced.isSelected()) {
                cm.setText("Company Name");
                OutSourced part2 = (OutSourced) this.part;
                this.compMachField.setText(part2.getCompanyName());
            }
            if (iHouse.isSelected()) {
                cm.setText("Machine ID");
                compMachField.setPromptText("Machine ID");
                this.compMachField.setText("");
            }
        }
        if (part instanceof InHouse) {
            if (iHouse.isSelected()) {
                cm.setText("Machine ID");
                InHouse part2 = (InHouse) this.part;
                this.compMachField.setText(part2.getMachineID() + "");
            }
            if (osourced.isSelected()) {
                cm.setText("Company Name");
                compMachField.setPromptText("Company Name");
                this.compMachField.setText("");
            }
        }
    }
    /**
     * Saves the Modifications in the Inventory.
     * @param event Handles on ActionEvent
     */
    @FXML
    public void saveMods(ActionEvent event) {
        Validate val = new Validate();
        //Init's the error message.
        String error = "";
        //Validation of data
        if (val.textverify(name)) {
            error += "Format: Name Is Not Valid \n";
        }
        if (!val.dub(price)) {
            error += "Price isn't a valid\n";
        }
        if (!val.number(stock)) {
            error += "Stock isn't a number\n";
        }
        if (!val.number(mn)) {
            error += "Min isn't a number\n";
        }
        if (!val.number(mx)) {
            error += "Max Isn't a number\n";
        }
        if (val.number(stock) && val.number(mn) && val.number(mx)) {
            if (!val.isBetween(stock, mn, mx)) {
                error += "Inventory must be in between Min and Max\n";
            }
        }
        if (val.number(mn) && val.number(mx)) {
            if (!val.isGreater(mn, mx)) {
                error += "Min must be less than Max\n";
            }
        }
        //Changes In House to Out Sourced
        if (part instanceof OutSourced && iHouse.isSelected()) {
            if(!val.number(compMachField)){
                error += "Machine ID is not a number";
            }
            if (error.length() > 0) {
                errorArea.setText(error);
            } else {
                Inventory.updatePart(parseInt(pid.getText().trim()), new InHouse(parseInt(pid.getText().trim()),
                        name.getText().trim(),
                        Double.parseDouble(price.getText().trim()),
                        parseInt(stock.getText().trim()),
                        parseInt(mn.getText().trim()),
                        parseInt(mx.getText().trim()),
                        parseInt(compMachField.getText().trim())));
                
                stage.close();

            }
        //updates outsourced product
        } else if (part instanceof OutSourced && osourced.isSelected()) {
            /**
             * Fixed Logical error here.
             */
            if(!val.textverify(compMachField.getText())){
                error += "Company Name is empty is empty";
            }
            if (error.length() > 0) {
                errorArea.setText(error);
            } else {
                Inventory.updatePart(parseInt(pid.getText()), new OutSourced(parseInt(pid.getText().trim()),
                        name.getText().trim(),
                        Double.parseDouble(price.getText().trim()),
                        parseInt(stock.getText().trim()),
                        parseInt(mn.getText().trim()),
                        parseInt(mx.getText().trim()),
                        compMachField.getText().trim()));
                
                stage.close();
            }
        //Changes in house to Outsourced
        } else if (part instanceof InHouse && osourced.isSelected()) {
            if(!val.textverify(compMachField.getText())){
                error += "Company Name is empty is empty";
            }
            if (error.length() > 0) {
                errorArea.setText(error);
            } else {
                Inventory.updatePart(parseInt(pid.getText()), new OutSourced(parseInt(pid.getText().trim()),
                        name.getText().trim(),
                        Double.parseDouble(price.getText().trim()),
                        parseInt(stock.getText().trim()),
                        parseInt(mn.getText().trim()),
                        parseInt(mx.getText().trim()),
                        compMachField.getText().trim()));
                stage.close();
            }

        } else {
            if(!val.number(compMachField)){
                error += "Machine ID is not a number";
            }
            if (error.length() > 0) {
                errorArea.setText(error);
            } else {
                Part added =  new InHouse(parseInt(pid.getText().trim()),
                        name.getText().trim(),
                        Double.parseDouble(price.getText().trim()),
                        parseInt(stock.getText().trim()),
                        parseInt(mn.getText().trim()),
                        parseInt(mx.getText().trim()),
                        parseInt(compMachField.getText().trim()));
                Inventory.updatePart(parseInt(pid.getText()), added);
                stage.close();
            }

        }
    }
    /**
     * Closes the Modify window.
     * @param event Button ActionEvent.
     */
    @FXML
    public void modCancAct(ActionEvent event) {
        stage.close();
    }
}
