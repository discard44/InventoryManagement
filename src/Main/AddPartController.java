package Main;

import Design.InHouse;
import Design.Inventory;
import Design.OutSourced;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class to handle actions with the Add Part window is opened.
 *
 * @author Max Perrigo
 */
public class AddPartController implements Initializable {

    Stage stage;

    private static int genID = 1;

    public boolean changeType;

    @FXML
    private RadioButton iHouse;

    @FXML
    private ToggleGroup parttype;

    @FXML
    private RadioButton osourced;

    @FXML
    private TextField pid;

    @FXML
    private TextField fieldCompMach;

    @FXML
    private TextField mn;

    @FXML
    private TextField cost;

    @FXML
    private TextField inStock;

    @FXML
    private TextField pnam;

    @FXML
    private TextField mx;

    @FXML
    private Label errorArea;

    @FXML
    private Label lblMachComp;

    @FXML
    private Button exitcan;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Initializes default data.
     *
     * @param stage Default Stage for the Window.
     */
    public void initData(Stage stage) {

        this.stage = stage;
        this.changeType = false;

    }

    /**
     * Makes sure the code is ran when the Radio is Selected.
     *
     * @param event Mouse button event for the In House radio.
     */
    @FXML
    public void iisSelected(MouseEvent event) {
        changeType = true;
        lblMachComp.setText("Machine ID");
        fieldCompMach.setPromptText("Machine ID");
        fieldCompMach.setText(null);

    }

    /**
     * Makes sure the code is ran when the Radio is Selected.
     *
     * @param event Mouse button event for the OutSourced radio.
     */
    @FXML
    public void oisSelected(MouseEvent event) {
        changeType = false;
        lblMachComp.setText("Company Name");
        fieldCompMach.setPromptText("Company Name");
        fieldCompMach.setText(null);
    }

    /**
     * On button event to save the part to the Inventory.
     *
     * @param event ButtonClick event
     */
    @FXML
    public void btnSubmitAction(ActionEvent event) {
        //validate data class
        Validate val = new Validate();
        // Error message intializes
        String error = "";
        if (val.textverify(pnam)) {
            error += "Format: Name Is Not Valid \n";
        }
        if (!val.dub(cost)) {
            error += "Price isn't a valid number.\n";
        }
        if (!val.number(inStock)) {
            error += "Stock isn't a number\n";
        }
        if (!val.number(mn)) {
            error += "Min isn't a number\n";
        }
        if (!val.number(mx)) {
            error += "Max Isn't a number\n";
        }
        if (val.number(inStock) && val.number(mn) && val.number(mx)) {
            if (!val.isBetween(inStock, mn, mx)) {
                error += "Inventory must be in between Min and Max\n";
            }
        }
        if (val.number(mn) && val.number(mx)) {
            if (!val.isGreater(mn, mx)) {
                error += "Min must be less than Max\n";
            }
        }

        // Makes sure that In House is clicked
        if (iHouse.isSelected()) {
            if (!val.number(fieldCompMach)) {
                error += "Machine Id is not a number";
            }
            // Adds part if there is no error
            if (error.length() > 0) {
                errorArea.setText(error);
            } else {
                Inventory.addPart(new InHouse(generateId(),
                        pnam.getText().trim(),
                        Double.parseDouble(cost.getText().trim()),
                        parseInt(inStock.getText().trim()),
                        parseInt(mn.getText().trim()),
                        parseInt(mx.getText().trim()),
                        parseInt(fieldCompMach.getText().trim())));
                stage.close();
            }

        } else if (osourced.isSelected()) {
            if (!val.textverify(fieldCompMach.getText())) {

                error += "Company name is empty";
            }
            // Condition is greater than 0
            if (error.length() > 0) {
                errorArea.setText(error);
            } else {
                //Adds OutSourced part.
                Inventory.addPart(new OutSourced(generateId(),
                        pnam.getText().trim(),
                        Double.parseDouble(cost.getText().trim()),
                        parseInt(inStock.getText().trim()),
                        parseInt(mn.getText().trim()),
                        parseInt(mx.getText().trim()),
                        fieldCompMach.getText().trim()));
                stage.close();
            }
        }
    }

    /**
     * Close the window.
     *
     * @param event Button event
     */
    @FXML
    public void cancelbtnAction(ActionEvent event) {
        stage.close();
    }

    /**
     * Auto Generates the ID.
     *
     * @return Integer to the caller
     */
    public static int generateId() {
        return genID++;
    }

    /**
     * Resets the text in the fields to null or "";
     */
    public void resetf() {
        pnam.setText("");
        cost.setText("");
        inStock.setText("");
        mn.setText("");
        mx.setText("");
    }

}
