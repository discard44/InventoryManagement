package Main;

import Design.Inventory;
import Design.Part;
import Design.Product;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Max Perrigo
 */
public class AddProductController implements Initializable {

    private static int gen = 1;
    // private Inventory inv;
    private Stage stage;

    ObservableList<Part> resetparts = FXCollections.observableArrayList();
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML
    private TextField prodId;

    @FXML
    private TextField ProdName;

    @FXML
    private TextField stock;

    @FXML
    private TextField Price;

    @FXML
    private TextField mx;

    @FXML
    private TextField mn;

    @FXML
    private TableColumn<Part, Integer> pid;

    @FXML
    private TableColumn<Part, String> pnam;

    @FXML
    private TableColumn<Part, Integer> iLevel;

    @FXML
    private TableColumn<Part, Double> price;

    @FXML
    private TableColumn<Part, Integer> addedPId;

    @FXML
    private TableColumn<Part, String> addedName;

    @FXML
    private TableColumn<Part, Integer> addedILevel;

    @FXML
    private TableColumn<Part, Double> addedPrice;

    @FXML
    private TextField searchp;

    @FXML
    private Button removeAssoc;

    @FXML
    private Button addBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button canc;

    @FXML
    private TableView<Part> topTable;

    @FXML
    private TableView<Part> bottomTable;

    @FXML
    private Label errA;

    /**
     * Initializes the data
     *
     * @param stage Passed window for closing.
     */
    public void initData(Stage stage) {
        this.stage = stage;
        generatePartsTop();
        generateBottom();
        
        //Default message when table is empty.
        topTable.setPlaceholder(new Label("No parts have been added"));
        
    }

    /**
     * Initializes the controller class.
     *
     * @param url Defaults Generated with controller
     * @param rb Defaults Generated with controller
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO\

    }

    /**
     * Generates the top table for all parts
     */

    public void generatePartsTop() {
        pid.setCellValueFactory(new PropertyValueFactory<>("id"));
        pnam.setCellValueFactory(new PropertyValueFactory<>("name"));
        iLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        topTable.setItems(Inventory.getAllParts());
        topTable.refresh();
    }

    /**
     * Generates the bottom table for the associated parts.
     */
    public void generateBottom() {
        addedPId.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedILevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        bottomTable.setItems(associatedParts);
        bottomTable.refresh();

        //Custom message when a part has not been added yet.
        bottomTable.setPlaceholder(new Label("No Associated parts have been added."));
    }

    /**
     * Searches for a part.
     * 
     * @param event Button event
     */
    @FXML
    public void SearchForPart(KeyEvent event) {
        FilteredList<Part> filter = new FilteredList<>(Inventory.getAllParts(), e -> true);

        searchp.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(part -> {
                if (newValue.trim() == null || newValue.trim().isEmpty()) {
                    return true;
                }
                Validate val = new Validate();
                String lower = newValue.toLowerCase();
                boolean firstletter = Character.isDigit(lower.charAt(0));
                if (String.valueOf(Inventory.lookupPart(part.getId()).getId()).contains(lower)) {

                    return true;
                } else if (!String.valueOf(Inventory.lookupPart(part.getId()).getId()).contains(lower) && !firstletter) {
                    if (part.getName().toLowerCase().contains(lower)) {
                        return true;
                    }
                } else {
                    
                    return false;
                }
                topTable.setPlaceholder(new Label("No parts found"));
                return false;
            });
        });
        topTable.setItems(filter);
        topTable.refresh();
    }

    /**
     * Closes the window for Adding products.
     *
     * @param event Button event
     * @throws InterruptedException Exception for loader
     */
    @FXML
    public void cancAction(ActionEvent event) throws InterruptedException {
        stage.close();
    }

    /**
     * Deletes an association for the parts.
     *
     * @param event Button event
     */
    @FXML
    public void deleteAssoc(ActionEvent event) {
        Comparator<Part> c = Comparator.comparingInt((part) -> part.getId());
        Part part = bottomTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No Selected Item");
            alert.setTitle("Error");
            alert.setHeaderText("An Item Must Be Selected");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Items deleted are not recoverable.");
            alert.setTitle("Confirm");
            alert.setHeaderText("Are you sure you want to Delete?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.getAllParts().sort(c);

                associatedParts.remove(part);
                bottomTable.setItems(associatedParts);
                bottomTable.refresh();
            } else {

            }
        }
    }

    /**
     * Saves the product to add.
     * @param event Button event
     */
    @FXML
    public void saveBtnAction(ActionEvent event) {
        Comparator<Part> c = Comparator.comparingInt((part) -> part.getId());
        //validate data class
        Validate val = new Validate();
        // Error message intializes
        String error1 = "";
        if (val.textverify(ProdName)) {
            error1 += "Format: Name Is Not Valid \n";
        }
        if (!val.dub(Price)) {
            error1 += "Price isn't a valid number\n";
        }
        if (!val.number(stock)) {
            error1 += "Stock isn't a number\n";
        }
        if (!val.number(mn)) {
            error1 += "Min isn't a number\n";
        }
        if (!val.number(mx)) {
            error1 += "Max Isn't a number\n";
        }
        if (val.number(stock) && val.number(mn) && val.number(mx)) {
            if (!val.isBetween(stock, mn, mx)) {
                error1 += "Inventory must be in between Min and Max\n";
            }
        }
        if (val.number(mn) && val.number(mx)) {
            if (!val.isGreater(mn, mx)) {
                error1 += "Min must be less than Max\n";
            }
        }
        if (error1.length() > 0) {
            errA.setText(error1);
        } else {
            Product prodAddition = new Product(generateProdId(),
                    ProdName.getText().trim(),
                    parseDouble(Price.getText().trim()),
                    parseInt(stock.getText().trim()),
                    parseInt(mn.getText().trim()),
                    parseInt(mx.getText().trim()));
            if (associatedParts == null || associatedParts.isEmpty()) {
                Inventory.addProduct(prodAddition);
            } else {
                Inventory.addProduct(prodAddition);
                associatedParts.sort(c);
                for (Part p : associatedParts) {
                    prodAddition.addAssociatedPart(p);
                }

            }
            stage.close();
        }
    }

    /**
     * Added the association to the associated list.
     *
     * @param event Button event
     */
    @FXML
    public void addBtnAction(ActionEvent event) {
        Comparator<Part> c = Comparator.comparingInt((part) -> part.getId());
        Part part = topTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No Selected Item");
            alert.setTitle("Error");
            alert.setHeaderText("An Item Must Be Selected");
            alert.show();
        } else {
            associatedParts.add(part);
            associatedParts.sort(c);
            bottomTable.setItems(associatedParts);
            bottomTable.refresh();
        }
    }

    /**
     * Generates an id to the caller.
     *
     * @return integer
     */
    public static int generateProdId() {

        return gen++;
    }
}
