package Main;

import Design.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Default Main class
 * @author Max Perrigo
 */
public class InvManagement extends Application {

    Scene window;
    public Stage mainParent;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        adddata();
        FXMLLoader root = new FXMLLoader(getClass().getResource("MainScreen.fxml"));

        stage.setScene(new Scene(root.load()));
        MainScreenController control = root.getController();

        control.initData(stage);

        stage.show();
    }
        /**
     * Not required from what the video stated. But Added for testing purposes.
     */
    public void adddata() {
        Part a1 = new InHouse(AddPartController.generateId(), "Brakes", 2.99, 10, 5, 100, 101);
        Inventory.addPart(a1);
        Part a2 = new InHouse(AddPartController.generateId(), "Tires", 4.99, 11, 5, 100, 103);
        Inventory.addPart(a2);
        Part b = new InHouse(AddPartController.generateId(), "Axles", 3.99, 9, 5, 100, 102);
        Inventory.addPart(b);

        Part o1 = new OutSourced(AddPartController.generateId(), "Body", 2.99, 10, 5, 100, "Acme");
        Inventory.addPart(o1);
        
        Part o2 = new OutSourced(AddPartController.generateId(), "Engine", 2.99, 10, 5, 100, "Acme");
        Inventory.addPart(o2);

        Product prod1 = new Product(AddProductController.generateProdId(), "Car", 9.99, 20, 5, 100);
        prod1.addAssociatedPart(a1);
        prod1.addAssociatedPart(b);
        Inventory.addProduct(prod1);

        Product prod2 = new Product(AddProductController.generateProdId(), "Bicycle", 10.99, 20, 5, 100);
        Inventory.addProduct(prod2);
    }


}
