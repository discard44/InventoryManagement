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
 * FXML Controller class to Manage actions on the Modify Product window.
 *
 * @author Max Perrigo
 */
public class ModifyProductController implements Initializable {
 
    private Stage stage;
    private Product prod;
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML
    private TextField searchModParts;

    @FXML
    private TextField modPid;

    @FXML
    private TextField modName;

    @FXML
    private TextField monInv;

    @FXML
    private TextField modPrice;

    @FXML
    private TextField modMx;

    @FXML
    private TextField modMn;

    @FXML
    private TableView<Part> topModTable;

    @FXML
    private TableColumn<Part, Integer> modTopPid;

    @FXML
    private TableColumn<Part, String> modTopName;

    @FXML
    private TableColumn<Part, Integer> modTopIlevel;

    @FXML
    private TableColumn<Part, Double> modTopPrice;

    @FXML
    private TableView<Part> bottomModTable;

    @FXML
    private TableColumn<Part, Integer> modBottomPid;

    @FXML
    private TableColumn<Part, String> modBottomName;

    @FXML
    private TableColumn<Part, Integer> modBottomIlevel;

    @FXML
    private TableColumn<Part, Double> modBottomPrice;

    @FXML
    private Button addBtn;

    @FXML
    private Button removeModAssoc;

    @FXML
    private Button saveModBtn;

    @FXML
    private Button cancMod;
    
    @FXML
    private Label errA;

    /**
     * Initializes the controller class.
     *
     * @param url Default Generated
     * @param rb Default Generated
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    /**
     * Sets Defaults on window.
     * @param stage Window Stage
     * @param prod Product to Modify.
     */
    public void initData(Stage stage, Product prod) {

        this.stage = stage;
        this.prod = prod;
        generateTopTable();
        generateBottomTable();
        modPid.setText(String.valueOf(prod.getProductID()));
        modName.setText(prod.getName());
        monInv.setText(String.valueOf(prod.getStock()));
        modPrice.setText(String.valueOf(prod.getPrice()));
        modMx.setText(String.valueOf(prod.getMax()));
        modMn.setText(String.valueOf(prod.getMin()));
        
        // Customer message for parts search
        topModTable.setPlaceholder(new Label("Part Not Found in Table."));
        
        //Custom message when a part has not been added yet.
        bottomModTable.setPlaceholder(new Label("No Associated parts have been added."));      
    }
   /**
    * Sets the top table data.
    */
    public void generateTopTable() {
        modTopPid.setCellValueFactory(new PropertyValueFactory<>("id"));
        modTopName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modTopIlevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modTopPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        topModTable.setItems(Inventory.getAllParts());
        topModTable.refresh();
    }
    /**
     * Sets the bottom tables data.
     */
    public void generateBottomTable() {
        modBottomPid.setCellValueFactory(new PropertyValueFactory<>("id"));
        modBottomName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modBottomIlevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modBottomPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        bottomModTable.setItems(prod.getAllAssociatedParts());
        bottomModTable.refresh();
    }
    /**
     * Adds the modifications to the product if the data was changed Then it sorts the list.
     * @param event button event
    */
    @FXML
    public void addModAction(ActionEvent event) {
        Comparator<Part> c = Comparator.comparingInt((part) -> part.getId());
        Part part = topModTable.getSelectionModel().getSelectedItem();
        boolean partExist = false;
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No Selected Item");
            alert.setTitle("Error");
            alert.setHeaderText("An Item Must Be Selected");
            alert.show();
        } else {
            for(Part p: prod.getAllAssociatedParts()){
                if (p.getId() == part.getId()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("WARNING!!!");
                    alert.setTitle("Warning");
                    alert.setHeaderText("Product already contains part selected.");
                    alert.show();
                    partExist = true;
                }
            }
                prod.addAssociatedPart(part);
                prod.getAllAssociatedParts().sort(c);

                bottomModTable.setItems(prod.getAllAssociatedParts());
                bottomModTable.refresh();
            
        }
    }
    /**
     * Closes the window.
     * @param event Button event
     * @throws InterruptedException Exception for loader
     */
    @FXML
    public void cancModAction(ActionEvent event) throws InterruptedException {
        stage.close();
    }
    /**
     * deletes an associated part
     * @param event Button event
     */
    @FXML
    public void deleteModAssoc(ActionEvent event) {
        Comparator<Part> c = Comparator.comparingInt((part) -> part.getId());
        Part part = bottomModTable.getSelectionModel().getSelectedItem();
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
                prod.deleteAssociatedPart(part.getId());
                bottomModTable.refresh();
            } else {

            }

        }
    }
    /**
     * Saves modifications
     * @param event button event
     * @throws java.lang.InterruptedException Exception for loader
     */
    @FXML
    public void saveModAction(ActionEvent event) throws InterruptedException {
        Validate val = new Validate();
        // Error message intializes
        String error1 = "";
        if (val.textverify(modName)) {
            error1 += "Format: Name Is Not Valid \n";
        }
        if (!val.dub(modPrice)) {
            error1 += "Price isn't a valid Number\n";
        }
        if (!val.number(monInv)) {
            error1 += "Stock isn't a number\n";
        }
        if (!val.number(modMn)) {
            error1 += "Min isn't a number\n";
        }
        if (!val.number(modMx)) {
            error1 += "Max Isn't a number\n";
        }
        if (val.number(monInv) && val.number(modMn) && val.number(modMx)) {
            if (!val.isBetween(monInv, modMn, modMx)) {
                error1 += "Inventory must be in between Min and Max\n";
            }
        }
        if (val.number(modMn) && val.number(modMx)) {
            if (!val.isGreater(modMn, modMx)) {
                error1 += "Min must be less than Max\n";
            }
        }
        if (error1.length() > 0){
            errA.setText(error1);
        }else {
            Product added = new Product(parseInt(modPid.getText()),
                modName.getText(),
                parseDouble(modPrice.getText()),
                parseInt(monInv.getText()),
                parseInt(modMn.getText()),
                parseInt(modMx.getText())
            );
            // move parts to added product
            for (Part p : prod.getAllAssociatedParts()) {
                
                added.addAssociatedPart(p);
            }

            if (prod.equals(added)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("No Changes Made");
                alert.setTitle("Information");
                alert.setHeaderText("No data changed");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    stage.close();
                } else {
                    stage.wait();
                }
            } else if (!prod.equals(added)) {

                Inventory.updateProduct(parseInt(modPid.getText()), added);

                stage.close();
            }
        }
    }
    /**
     * Searches the parts list in the Modification window.
     * @param event Button event
     */
    @FXML
    public void searchAddPart(KeyEvent event) {
        FilteredList<Part> filter = new FilteredList<>(Inventory.getAllParts(), e -> true);

        searchModParts.textProperty().addListener((observable, oldValue, newValue) -> {
                filter.setPredicate(part -> {
                    if (newValue.trim() == null || newValue.trim().isEmpty()) {
                        return true;
                    }

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
                    return false;
                });
            });
      
        topModTable.setItems(filter);
        topModTable.refresh();
    }

}
