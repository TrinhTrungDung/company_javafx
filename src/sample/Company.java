package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Company extends Application {
    private TextField firstNameTextField;
    private TextField lastNameTextField;
    private ComboBox<String> genderComboBox;
    private TextField emailTextField;
    private ComboBox<String> departmentIdComboBox;
    private ComboBox<String> positionIdComboBox;
    private TextField streetNumberTextField;
    private TextField streetNameTextField;
    private TextField stateTextField;
    private ComboBox<String> countryComboBox;
    private VBox infoEmployee;
    //private VBox infoDepartment;
    //private VBox departmentTable;
    private TableView<Employee> employeeTable;
    private PrintWriter employeeWriter;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // main layout for the app
        BorderPane mainLayout;

        // declarations for input
        firstNameTextField = new TextField();
        lastNameTextField = new TextField();
        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll(
                "Male",
                "Female"
        );
        emailTextField = new TextField();
        departmentIdComboBox = new ComboBox<>();
        departmentIdComboBox.getItems().addAll(
                "IT",
                "HR",
                "MK"
        );
        positionIdComboBox = new ComboBox<>();
        positionIdComboBox.getItems().addAll(
                "CO",
                "MGR",
                "EMP"
        );
        streetNumberTextField = new TextField();
        streetNameTextField = new TextField();
        stateTextField = new TextField();

        ObservableList<String> countries = Stream.of(Locale.getISOCountries())
                .map(locales -> new Locale("", locales))
                .map(Locale::getDisplayCountry)
                .collect(Collectors.toCollection(FXCollections::observableArrayList))
                .sorted();

        countryComboBox = new ComboBox<>(countries);

        employeeTable = new TableView<>();

        // open employees file
        try {
            BufferedReader employeeReader = new BufferedReader(new FileReader("Employees.txt"));
            String line;
            String[] parsedLine;

            while ((line = employeeReader.readLine()) != null) {
                parsedLine = line.split(", ", 0);
                String firstName = parsedLine[0];
                String lastName = parsedLine[1];
                String gender = parsedLine[2];
                String email = parsedLine[3];
                String departmentId = parsedLine[4];
                String positionId = parsedLine[5];
                String streetNumber = parsedLine[6];
                String streetName = parsedLine[7];
                String state = parsedLine[8];
                String country = parsedLine[9];
                Address address = new Address(streetNumber, streetName, state, country);
                Employee employee = new Employee(firstName, lastName, gender, email, departmentId, positionId, address);
                employeeTable.getItems().add(employee);
            }

            employeeReader.close();
        } catch (FileNotFoundException fne) {
            System.out.flush();
        }


        // mainLayout
        mainLayout = new BorderPane();

        /* Menu bar at the top of the app */
        MenuBar menuBar = new MenuBar();

        // File menu
        Text fileText = new Text("_File");
        Menu fileMenu = new Menu(fileText.getText());
        fileMenu.setAccelerator(KeyCodeCombination.keyCombination("ALT+F"));

        // New option
        MenuItem newMenuItem = new MenuItem("_New");
        newMenuItem.setAccelerator(KeyCombination.keyCombination("CTRL+N"));
        newMenuItem.setOnAction(event -> System.out.println("Create new file\n"));

        // Save option
        MenuItem saveMenuItem = new MenuItem("_Save");
        saveMenuItem.setAccelerator(KeyCombination.keyCombination("CTRL+S"));

        saveMenuItem.setOnAction(event -> {
            try {
                employeeWriter = new PrintWriter(new BufferedWriter(new FileWriter("Employees.txt")));

                for (Employee employee : employeeTable.getItems()) {
                    employeeWriter.println(employee);
                }

                employeeWriter.close();
            } catch (IOException e) {
                System.out.flush();
            }
        });

        // Exit option
        MenuItem exitMenuItem = new MenuItem("E_xit");
        exitMenuItem.setAccelerator(KeyCodeCombination.keyCombination("ALT+F4"));
        exitMenuItem.setOnAction(event -> primaryStage.close());
        fileMenu.getItems().addAll(newMenuItem, saveMenuItem, exitMenuItem);

        // Edit menu
        Menu editMenu = new Menu("_Edit");
        editMenu.setAccelerator(KeyCodeCombination.keyCombination("ALT+E"));

        // View menu
        Menu viewMenu = new Menu("_View");
        viewMenu.setAccelerator(KeyCodeCombination.keyCombination("ALT+V"));

        MenuItem employeeMenuItem = new MenuItem("Employee");
        employeeMenuItem.setOnAction(e -> {
            mainLayout.setLeft(infoEmployee);
            mainLayout.setCenter(employeeTable);
        });

        MenuItem departmentMenuItem = new MenuItem("Department");
        departmentMenuItem.setOnAction(e -> {
            //mainLayout.setLeft(infoDepartment);
            //mainLayout.setCenter(departmentTable);
        });
        viewMenu.getItems().addAll(employeeMenuItem, departmentMenuItem);

        // Help menu
        Menu helpMenu = new Menu("_Help");
        helpMenu.setAccelerator(KeyCodeCombination.keyCombination("ALT+H"));

        // About option
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setAccelerator(KeyCodeCombination.keyCombination("ALT+A"));
        aboutMenuItem.setOnAction(event -> {

        });
        helpMenu.getItems().add(aboutMenuItem);


        // Add all menu to menu bar
        menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu, helpMenu);
        mainLayout.setTop(menuBar);


        // Add employee table mouse clicked
        employeeTable.setOnMouseClicked(event ->  {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    Stage employeeInfoStage = new Stage();
                    employeeInfoStage.setTitle("Employee Information");
                    VBox empInfoBox = new VBox();
                    empInfoBox.setPadding(new Insets(0, 0, 0, 8));
                    Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

                    // First name label
                    HBox firstNameHBox = new HBox();

                    Label firstNameLabel = new Label(String.format("First Name:%14s%s", "",
                            selectedEmployee.getFirstName()));
                    firstNameLabel.setPadding(new Insets(5, 33, 10 ,10));
                    firstNameHBox.getChildren().addAll(firstNameLabel);

                    // First name label
                    HBox lastNameHBox = new HBox();
                    Label lastNameLabel = new Label(String.format("Last Name:%14s%s", "",
                            selectedEmployee.getLastName()));
                    lastNameLabel.setPadding(new Insets(5, 33, 10 ,10));
                    lastNameHBox.getChildren().addAll(lastNameLabel);

                    // Gender label
                    HBox genderHBox = new HBox();
                    Label genderLabel = new Label(String.format("Gender: %18s%s", "", selectedEmployee.getGender()));
                    genderLabel.setPadding(new Insets(5, 33, 10 ,10));
                    genderHBox.getChildren().addAll(genderLabel);

                    // Email label
                    HBox emailHBox = new HBox();
                    Label emailLabel = new Label(String.format("Email: %21s%s", "", selectedEmployee.getEmail()));
                    emailLabel.setPadding(new Insets(5, 33, 10 ,10));
                    emailHBox.getChildren().addAll(emailLabel);

                    // Department ID label
                    HBox departmentIdHBox = new HBox();
                    Label departmentLabel = new Label(String.format("Department ID: %6s%s", "",
                            selectedEmployee.getDepartmentId()));
                    departmentLabel.setPadding(new Insets(5, 33, 10 ,10));
                    departmentIdHBox.getChildren().addAll(departmentLabel);

                    // Position ID label
                    HBox positionIdHBox = new HBox();
                    Label positionIdLabel = new Label(String.format("Position ID: %12s%s", "",
                            selectedEmployee.getPositionId()));
                    positionIdLabel.setPadding(new Insets(5, 33, 10 ,10));
                    positionIdHBox.getChildren().addAll(positionIdLabel);

                    // Address label
                    HBox addressHBox = new HBox();
                    Label addressLabel = new Label(String.format("Address: %16s%s", "", selectedEmployee.getAddress()));
                    addressLabel.setPadding(new Insets(5, 33, 10 ,10));
                    addressHBox.getChildren().addAll(addressLabel);

                    empInfoBox.getChildren().addAll(firstNameHBox, lastNameHBox, genderHBox, emailHBox,
                            departmentIdHBox, positionIdHBox, addressHBox);
                    employeeInfoStage.setScene(new Scene(empInfoBox, 500, 500));

                    employeeInfoStage.show();
                } else if (event.getClickCount() == 1) {
                    try {
                        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
                        firstNameTextField.setText(selectedEmployee.getFirstName());
                        lastNameTextField.setText(selectedEmployee.getLastName());
                        genderComboBox.getSelectionModel().select(selectedEmployee.getGender());
                        emailTextField.setText(selectedEmployee.getEmail());
                        departmentIdComboBox.getSelectionModel().select(selectedEmployee.getDepartmentId());
                        positionIdComboBox.getSelectionModel().select(selectedEmployee.getPositionId());
                        Address address = selectedEmployee.getAddress();
                        streetNumberTextField.setText(address.getStreetNumber());
                        streetNameTextField.setText(address.getStreetName());
                        stateTextField.setText(address.getState());
                        countryComboBox.getSelectionModel().select(address.getCountry());
                    } catch (NullPointerException ne) {
                        System.out.flush();
                    }
                }
            }
        });

        // Registration form and edited option
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String gender = genderComboBox.getValue();
            String email = emailTextField.getText();
            String departmentId = departmentIdComboBox.getValue();
            String positionId = positionIdComboBox.getValue();
            String streetNumber = streetNumberTextField.getText();
            String streetName = streetNameTextField.getText();
            String state = stateTextField.getText();
            String country = countryComboBox.getValue();
            Address address = new Address(streetNumber, streetName, state, country);
            Employee employee = new Employee(firstName, lastName, gender, email, departmentId, positionId, address);

            employeeTable.getItems().add(employee);

            // Set to default
            firstNameTextField.clear();
            lastNameTextField.clear();
            genderComboBox.setValue("Male");
            emailTextField.clear();
            departmentIdComboBox.setValue("IT");
            positionIdComboBox.setValue("EMP");
            streetNumberTextField.clear();
            streetNameTextField.clear();
            stateTextField.clear();
            countryComboBox.setValue("Vietnam");
        });

        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> {
            int selectedIndex = employeeTable.getSelectionModel().getSelectedIndex();
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String gender = genderComboBox.getValue();
            String email = emailTextField.getText();
            String departmentId = departmentIdComboBox.getValue();
            String positionId = positionIdComboBox.getValue();
            String streetNumber = streetNumberTextField.getText();
            String streetName = streetNameTextField.getText();
            String state = stateTextField.getText();
            String country = countryComboBox.getValue();
            Address address = new Address(streetNumber, streetName, state, country);
            Employee newEmployee = new Employee(firstName, lastName, gender, email, departmentId, positionId, address);
            employeeTable.getItems().set(selectedIndex, newEmployee);

            // Set to default
            firstNameTextField.clear();
            lastNameTextField.clear();
            genderComboBox.setValue("Male");
            emailTextField.clear();
            departmentIdComboBox.setValue("IT");
            positionIdComboBox.setValue("EMP");
            streetNumberTextField.clear();
            streetNameTextField.clear();
            stateTextField.clear();
            countryComboBox.setValue("Vietnam");
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            ObservableList<Employee> employeeSelected, allEmployees;
            allEmployees = employeeTable.getItems();
            employeeSelected = employeeTable.getSelectionModel().getSelectedItems();

            employeeSelected.forEach(allEmployees::remove);
        });

        HBox horizontalButtons = new HBox();
        horizontalButtons.setPadding(new Insets(15, 12, 15, 12));
        horizontalButtons.setSpacing(20);
        horizontalButtons.getChildren().addAll(addButton, editButton, deleteButton);


        // Personal Information Label
        HBox personalInfoHBox = new HBox();
        personalInfoHBox.setAlignment(Pos.CENTER);
        Label personalInfoLabel = new Label("Personal Information");
        personalInfoLabel.setTextFill(Color.PURPLE);
        personalInfoLabel.setStyle("-fx-font: 24 arial");
        personalInfoLabel.setPadding(new Insets(10, 10, 10 ,10));
        personalInfoHBox.getChildren().addAll(personalInfoLabel);

        // First name label and textfield
        HBox firstNameHBox = new HBox();
        Label firstNameLabel = new Label("First Name");
        firstNameLabel.setPadding(new Insets(5, 33, 10 ,10));
        firstNameTextField.setPromptText("John");
        firstNameTextField.setPrefWidth(100);
        firstNameTextField.setMaxHeight(10);
        firstNameTextField.setAlignment(Pos.BOTTOM_LEFT);
        firstNameHBox.getChildren().addAll(firstNameLabel, firstNameTextField);


        // Last name label and textfield
        HBox lastNameHBox = new HBox();
        Label lastNameLabel = new Label("Last Name");
        lastNameLabel.setPadding(new Insets(5, 34, 10 ,10));
        lastNameTextField.setPromptText("Smith");
        lastNameTextField.setPrefWidth(100);
        lastNameTextField.setMaxHeight(10);
        lastNameTextField.setAlignment(Pos.BOTTOM_LEFT);
        lastNameHBox.getChildren().addAll(lastNameLabel, lastNameTextField);

        // departmentID label and textfield
        HBox genderHBox = new HBox();
        Label genderLabel = new Label("Gender");
        genderLabel.setPadding(new Insets(5, 50, 10 ,10));
        genderComboBox.setValue("Male");
        genderComboBox.setPrefWidth(100);
        genderComboBox.setMaxHeight(10);
        genderHBox.getChildren().addAll(genderLabel, genderComboBox);

        // Email label and textfield
        HBox emailHBox = new HBox();
        Label emailLabel = new Label("Email");
        emailLabel.setPadding(new Insets(5, 60, 10 ,10));
        emailTextField.setPromptText("john.smith@gmail.com");
        emailTextField.setPrefWidth(100);
        emailTextField.setMaxHeight(10);
        emailTextField.setAlignment(Pos.BOTTOM_LEFT);
        emailHBox.getChildren().addAll(emailLabel, emailTextField);


        // departmentID label and textfield
        HBox departmentIdHBox = new HBox();
        Label departmentIdLabel = new Label("Department ID");
        departmentIdLabel.setPadding(new Insets(5, 10, 10 ,10));
        departmentIdComboBox.setValue("IT");
        departmentIdComboBox.setPrefWidth(100);
        departmentIdComboBox.setMaxHeight(10);
        departmentIdHBox.getChildren().addAll(departmentIdLabel, departmentIdComboBox);


        // positionID label and textfield
        HBox positionIdHBox = new HBox();
        Label positionIdLabel = new Label("Position ID");
        positionIdLabel.setPadding(new Insets(5, 30, 10 ,10));
        positionIdComboBox.setValue("EMP");
        positionIdComboBox.setPrefWidth(100);
        positionIdComboBox.setMaxHeight(10);
        positionIdHBox.getChildren().addAll(positionIdLabel, positionIdComboBox);


        // Address Label
        HBox addressHBox = new HBox();
        addressHBox.setAlignment(Pos.CENTER);
        Label addressLabel = new Label("Address");
        addressLabel.setTextFill(Color.PURPLE);
        addressLabel.setStyle("-fx-font: 24 arial");
        addressLabel.setPadding(new Insets(10, 10, 10 ,10));
        addressHBox.getChildren().addAll(addressLabel);


        // Street number label and textfield
        HBox streetNumberHBox = new HBox();
        Label streetNumberLabel = new Label("Street Number");
        streetNumberLabel.setPadding(new Insets(5, 10, 10 ,10));
        streetNumberTextField.setPromptText("123A");
        streetNumberTextField.setPrefWidth(100);
        streetNumberTextField.setMaxHeight(10);
        streetNumberTextField.setAlignment(Pos.BOTTOM_LEFT);
        streetNumberHBox.getChildren().addAll(streetNumberLabel, streetNumberTextField);


        // Street name label and textfield
        HBox streetNameHBox = new HBox();
        Label streetNameLabel = new Label("Street Name");
        streetNameLabel.setPadding(new Insets(5, 20, 10 ,10));
        streetNameTextField.setPromptText("Wall Street");
        streetNameTextField.setPrefWidth(100);
        streetNameTextField.setMaxHeight(10);
        streetNameTextField.setAlignment(Pos.BOTTOM_LEFT);
        streetNameHBox.getChildren().addAll(streetNameLabel, streetNameTextField);


        // State label and textfield
        HBox stateHBox = new HBox();
        Label stateLabel = new Label("State");
        stateLabel.setPadding(new Insets(5, 60, 10 ,10));
        stateTextField.setPromptText("New York");
        stateTextField.setPrefWidth(100);
        stateTextField.setMaxHeight(10);
        stateTextField.setAlignment(Pos.BOTTOM_LEFT);
        stateHBox.getChildren().addAll(stateLabel, stateTextField);

        // Country label and textfield
        HBox countryHBox = new HBox();
        Label countryLabel = new Label("Country");
        countryLabel.setPadding(new Insets(5, 44, 10 ,10));
        countryComboBox.setValue("Vietnam");
        countryComboBox.setPrefWidth(100);
        countryComboBox.setMaxHeight(10);
        countryHBox.getChildren().addAll(countryLabel, countryComboBox);


        // information of employees
        infoEmployee = new VBox();
        infoEmployee.getChildren().addAll(horizontalButtons, personalInfoHBox, firstNameHBox, lastNameHBox, genderHBox,
                emailHBox, departmentIdHBox, positionIdHBox, addressHBox, streetNumberHBox, streetNameHBox, stateHBox,
                countryHBox);

        // Columns
        TableColumn<Employee, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setMinWidth(100);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Employee, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setMinWidth(100);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Employee, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setMinWidth(100);
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Employee, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setMinWidth(100);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Employee, String> departmentIdColumn = new TableColumn<>("Department ID");
        departmentIdColumn.setMinWidth(100);
        departmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("departmentId"));

        TableColumn<Employee, String> positionIdColumn = new TableColumn<>("Position ID");
        positionIdColumn.setMinWidth(100);
        positionIdColumn.setCellValueFactory(new PropertyValueFactory<>("positionId"));

        TableColumn<Employee, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setMinWidth(100);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));


        employeeTable.getColumns().add(firstNameColumn);
        employeeTable.getColumns().add(lastNameColumn);
        employeeTable.getColumns().add(genderColumn);
        employeeTable.getColumns().add(emailColumn);
        employeeTable.getColumns().add(departmentIdColumn);
        employeeTable.getColumns().add(positionIdColumn);
        employeeTable.getColumns().add(addressColumn);


        // This is the default scene
        mainLayout.setLeft(infoEmployee);
        mainLayout.setCenter(employeeTable);


        Scene scene = new Scene(mainLayout, 1200, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}