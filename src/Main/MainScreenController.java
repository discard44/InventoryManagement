package Main;

import Design.Inventory;
import Design.Part;
import Design.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class to manage actions of the buttons.
 *
 * @author Max Perrigo
 */
public class MainScreenController implements Initializable {
    
    public Part part;
    public Stage stage;

    @FXML
    private TextField searchForPart;

    @FXML
    private TextField prods;

    @FXML
    private TableView<Part> tablev;
    @FXML
    private TableColumn<Part, Integer> id;

    @FXML
    private TableColumn<Part, String> name;

    @FXML
    private TableColumn<Part, Integer> ilevel;

    @FXML
    private TableColumn<Part, Double> price;

    @FXML
    private TableView<Product> tablex;

    @FXML
    private TableColumn<Product, Integer> proId;

    @FXML
    private TableColumn<Product, String> proName;

    @FXML
    private TableColumn<Product, Integer> proILevel;

    @FXML
    private TableColumn<Product, Double> proPrice;

    @FXML
    private Button addBtn;

    @FXML
    private Button Mod;

    @FXML
    private Button repoPart;

    @FXML
    private Label assoc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generateParts();
        generateProds();

    }

    /**
     * Initializes Inventory
     *
     * @param stage State to close when ready.
     */
    public void initData(Stage stage) {
        this.stage = stage;

        //defaults when list is
        tablev.setPlaceholder(new Label("No parts have been added"));
        tablex.setPlaceholder(new Label("No products have been added"));
    }

    /**
     * Generates the parts table view.
     */
    public void generateParts() {
        id.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        ilevel.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        price.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        tablev.setItems(Inventory.getAllParts());
        tablev.refresh();
    }

    /**
     * Generates the products view.
     */
    private void generateProds() {
        proId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productID"));
        proName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        proILevel.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        proPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        tablex.setItems(Inventory.getAllProducts());
        tablex.refresh();
    }

    /**
     * LOGICAL ISSUE This is where I had my first logical error. The search
     * would find the ID but wouldn't take the name and return it. I fixed this
     * by adding the filter list and adding the listener to the text property of
     * textfield. Then I added
     * String.valueOf(inv.lookupPart(part.getId()).getId()).contains(newValue.trim())
     * Condition and the ID and the search by name worked perfectly. I had to do
     * the same for the Product search table.
     *
     * @param event keyboard event.
     */
    @FXML
    public void searchParts(KeyEvent event) {
        FilteredList<Part> filter = new FilteredList<>(Inventory.getAllParts(), e -> true);

        searchForPart.textProperty().addListener((observable, oldValue, newValue) -> {
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
                tablev.setPlaceholder(new Label("No parts found"));
                return false;
            });
        });

        tablev.setItems(filter);
        tablev.refresh();
    }

    /**
     * Just thought I would bring this one up. This search was harder and took
     * awhile to resolve. Originally I wasn't iterating through the list of
     * products and the result was that it would say no content to display in
     * the search when I was searching by name.
     *
     *
     * FEATURE TO ADD A Future enhancement I believe that would extend the
     * functionality of this application is barcoding. Add a feature that can
     * take in a bar code and add it to the inventory.
     *
     * @param event keyboard event.
     */
    @FXML
    public void searchProducts(KeyEvent event) {
        FilteredList<Product> filter = new FilteredList<>(Inventory.getAllProducts(), e -> true);

        prods.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(prod -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lower = newValue.toLowerCase();
                boolean firstletter = Character.isDigit(lower.charAt(0));

                if (String.valueOf(Inventory.lookupProduct(prod.getProductID()).getProductID()).contains(lower)) {
                    return true;
                } else if (!String.valueOf(Inventory.lookupProduct(prod.getProductID()).getProductID()).contains(lower) && !firstletter) {
                    if (prod.getName().toLowerCase().contains(lower)) {
                        return true;
                    }
                } else {
                    return false;
                }
                tablex.setPlaceholder(new Label("No Products Found"));
                return false;
            });

        });
        tablex.setItems(filter);
        tablex.refresh();

    }
    /**
     * Opens the Add window
     *
     * @param event Button event
     * @throws IOException Exception for when the loader doesn't load
     */
    @FXML
    public void openAddWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPart.fxml"));    
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        AddPartController control = loader.getController();
        control.initData(stage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    /**
     * Opens the Modify window
     *
     * @param event Button event
     * @throws IOException Loader exception
     */
    @FXML
    public void openModifyWindow(ActionEvent event) throws IOException {

        part = tablev.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("No Selected Item");
            alert.setTitle("Error");
            alert.setHeaderText("An Item Must Be Selected");
            alert.show();
        } else {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(loader1.load()));
            ModifyPartController control = loader1.getController();
            control.initData(stage, part);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        }
    }

    /**
     * Removes a part from the inventory.
     *
     * @param event Button event
     */
    @FXML
    public void removepart(ActionEvent event) {

        Part partToRemove = tablev.getSelectionModel().getSelectedItem();

        if (partToRemove == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText("No Selected Item");
            alert.setTitle("Error");
            alert.setHeaderText("An Item Must Be Selected");
            alert.show();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText("Items deleted are not recoverable.");
            alert.setTitle("Confirm");
            alert.setHeaderText("Are you sure you want to Delete?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deletePart(partToRemove);
            } else {

            }

        }

    }

    /**
     * Opens the add product window.
     *
     * @param event Button event.
     * @throws IOException exception
     */
    @FXML
    public void productAddBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));

        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        AddProductController control = loader.getController();
        control.initData(stage);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * Opens the Modify Product window.
     *
     * @param event Button event
     * @throws IOException exception
     */
    @FXML
    public void openModProdWindow(ActionEvent event) throws IOException {
        Product prod = tablex.getSelectionModel().getSelectedItem();
        if (prod == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("No Selected Item");
            alert.setTitle("Error");
            alert.setHeaderText("An Item Must Be Selected");
            alert.show();
        } else {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(loader1.load()));
            ModifyProductController control = loader1.getController();
            control.initData(stage, prod);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        }
    }

    /**
     * Deletes a product.
     * @param event Button event
     */
    @FXML
    public void deleteProdItem(ActionEvent event) {
        Product prod = tablex.getSelectionModel().getSelectedItem();

        if (prod == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText("No Selected Item");
            alert.setTitle("Error");
            alert.setHeaderText("An Item Must Be Selected");
            alert.show();
        } else if (prod.getAllAssociatedParts().size() > 0) {
            String error = "Error: Remove all Associated parts";
            assoc.setTextFill(Color.RED);
            assoc.setText(error);
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText("Items deleted are not recoverable.");
            alert.setTitle("Confirm");
            alert.setHeaderText("Are you sure you want to Delete?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deleteProduct(prod);
            } else {

            }

        }
    }

    /**
     * Generates a part id
     *
     * @return int Integer to the caller
     */
    public int generateId() {
        //int gen = inv.getAllParts().size() + 1;
        //return gen;
        return 1;
    }

    /**
     * Generates a productid
     *
     * @return int Integer to the caller
     */
    public int generateProdId() {
        int gen = Inventory.getAllProducts().size() + 1;
        return gen;
    }

    /**
     * Closes the application
     * @param event Button event
     */
    @FXML
    public void exitMain(ActionEvent event) {
        stage.close();
    }

}
