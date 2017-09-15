 import java.util.ArrayList;
import java.util.Date;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TabPane.TabClosingPolicy;


public class gui extends Application {

	Stage window;

	public static void main(String[] args) {

	launch(args);
	}


	Controller controller = new Controller();


	public void start(Stage primaryStage){
		LogInWindow.display();

		window = primaryStage;
		window.setTitle("Dentist");

		//Tab Pane
		TabPane tabPane = new TabPane();
		Tab patient = new Tab("Patient Management");
		Tab invoiceProcedure = new Tab("Invoice and Procedure Management");
		Tab payment = new Tab("Payment Management");
		Tab reports = new Tab("Reports");
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tabPane.getTabs().addAll(patient, invoiceProcedure, payment, reports);
		patient.setContent(patientTab());
		invoiceProcedure.setContent(invoiceProcedureTab());
		payment.setContent(paymentManagementTab());
		reports.setContent(reportTab());

		//Bottom buttons
		Button exitSave = new Button("Exit and Save");
		Button exit = new Button("Exit");
		HBox exitButtons = new HBox(20);
		exitButtons.getChildren().addAll(exit, exitSave);
		exitButtons.setPadding(new Insets(20,20,20,20));
		exitButtons.setAlignment(Pos.BOTTOM_RIGHT);
		exit.setOnAction(e-> window.close());
		exitSave.setOnAction(e-> {
				 controller.writeToFile();
				 window.close();
			 });

		BorderPane borderPane = new BorderPane();
		borderPane.setBottom(exitButtons);
		borderPane.setCenter(tabPane);

		Scene scene = new Scene(borderPane,650, 450);
		window.setScene(scene);
		window.show();


	}


	private VBox reportTab() {
		//labels and textfields
		Button genPatient = new Button("Generate Patient Report");
		Button genPatientsOweMoney = new Button("Generate Owed Money Report");
		TextArea textArea = new TextArea();


		HBox buttons = new HBox(20);
		buttons.getChildren().addAll(genPatient, genPatientsOweMoney);

		genPatient.setOnAction(e->{
			textArea.setText(controller.generatePatientReport());
		});

		genPatientsOweMoney.setOnAction(e->{
			controller.generatePatientOweReport();
		});

		VBox layout = new VBox(20);
		layout.getChildren().addAll(textArea, buttons);
		layout.setPadding(new Insets(15,15,15,15));

		return layout;
	}


	public VBox invoiceProcedureTab(){
		//labels and text fields
		Label patientNameLabel = new Label("Patient Name: ");
		TextField patientNameField = new TextField();
		patientNameField.setPrefWidth(300);


		//Name and display invoice button HBox's
		HBox name = new HBox(36);
		name.getChildren().addAll(patientNameLabel, patientNameField);
		Button viewInvoices = new Button("View Patient Invoices");
		viewInvoices.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				 String patientName = patientNameField.getText();
				 controller.selectPatientInvoices(patientName);
				 invoiceListWindow(patientName);
			 }
			 });
		HBox invoiceButton = new HBox(10);
		invoiceButton.getChildren().addAll(viewInvoices);


		//Procedure List View
		ListView<String> listView = new ListView<String>();



		//Procedure Buttons HBox
		HBox procedureButtons = new HBox(20);
		Button display = new Button("Display");
		display.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				 listView.getItems().clear();
				 listView.getItems().addAll(controller.getProcedures());
			 }
			 });


		Button addProcedure = new Button("Add");
		addProcedure.setOnAction(e-> procedureUpdaterWindow("Add", 0));

		Button updateProcedure = new Button("Update");
	    updateProcedure.setOnAction(e-> procedureUpdaterWindow("Update", listView.getSelectionModel().getSelectedIndex()));

		Button deleteProcedure = new Button("Delete");
		deleteProcedure.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				 controller.deleteProcedure(listView.getSelectionModel().getSelectedIndex());
				 listView.getItems().clear();
				 listView.getItems().addAll(controller.getProcedures());
			 }
			 });

		procedureButtons.getChildren().addAll(display, addProcedure, updateProcedure, deleteProcedure);


		//main Layout
		VBox VBox = new VBox(10);
		VBox.getChildren().addAll(name, invoiceButton, listView, procedureButtons);
		VBox.setPadding(new Insets(15,15,15,15));

		return VBox;
	}

	public VBox patientTab(){
		//Labels and text fields
		Label label1 = new Label("Patient Name:");
		TextField patientName = new TextField ();
		patientName.setPrefWidth(300);
		Label label2 = new Label("Address: ");
		TextField address = new TextField();
		address.setPrefWidth(300);
		Label label3 = new Label("Contact Number: ");
		TextField contactNumber = new TextField();
		contactNumber.setPrefWidth(300);
		TextArea textArea = new TextArea();
		textArea.setText(controller.getPatientList());


		//Buttons
		Button addPatient = new Button("Add Patient");
		Button removePatient = new Button("Remove Patient");
		addPatient.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				 controller.addPatient(patientName.getText(), address.getText(), contactNumber.getText());
				 textArea.setText(controller.getPatientList());
			 }
			 });

		removePatient.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				 controller.removePatient(patientName.getText());
				 String patients= controller.getPatientList();
			        textArea.setText(patients);
			 }
			 });


		//HBox's
		HBox name = new HBox(39);
		name.getChildren().addAll(label1,patientName);
		HBox patientAddress = new HBox(65);
		patientAddress.getChildren().addAll(label2, address);
		HBox contact = new HBox(20);
		contact.getChildren().addAll(label3,contactNumber);
		HBox buttons = new HBox(30);
		buttons.getChildren().addAll(addPatient, removePatient);
		buttons.setPadding(new Insets(5,5,5,5));


		//Main LAyout
		VBox layout = new VBox(10);
	    layout.getChildren().addAll(name, patientAddress, contact, buttons, textArea);
	    layout.setPadding(new Insets(15,15,15,15));

	    return layout;
	}

	public VBox paymentManagementTab(){
		//labels and textfields
		Label patientNameLabel = new Label("Patient Name: ");
		TextField patientNameField = new TextField();
		patientNameField.setPrefWidth(300);
		Label paymentAmountLabel = new Label("Payment Amount: ");
		TextField paymentAmountField = new TextField();
		paymentAmountField.setPrefWidth(300);
		TextArea textArea = new TextArea();

		//Buttons
		Button addPayment = new Button("Add Payment");
		addPayment.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				 double amount = Double.parseDouble(paymentAmountField.getText());
				 Date date = new Date();

				 controller.addPayment(amount, date);
				 textArea.setText(controller.getPaymentListString());
			 }
			 });


		//HBox's
		HBox name = new HBox(36);
		name.getChildren().addAll(patientNameLabel, patientNameField);
		HBox paymentAmount = new HBox(15);
		paymentAmount.getChildren().addAll(paymentAmountLabel, paymentAmountField);
		HBox buttons = new HBox(30);
		buttons.getChildren().addAll(addPayment);
		buttons.setPadding(new Insets(5,5,5,5));


		//Main Layout
		VBox layout = new VBox(20);
		layout.getChildren().addAll(name, paymentAmount,buttons, textArea);
		layout.setPadding(new Insets(15,15,15,15));

		return layout;
	}

	public void invoiceListWindow(String patientName){
		Stage window = new Stage();
		window.setTitle(patientName + " Invoices");
		window.initModality(Modality.APPLICATION_MODAL);

		//List View
		ListView<String> listView = new ListView<String>();
		listView.getItems().addAll(controller.getInvoices(patientName));

		//Labels and textfields HBox
		Label invoiceAmountLabel = new Label("Invoice Amount (€): ");
		TextField invoiceAmountTextfield = new TextField();
		invoiceAmountTextfield.setPrefWidth(200);
		HBox invoiceAmount = new HBox(10);
		invoiceAmount.getChildren().addAll(invoiceAmountLabel, invoiceAmountTextfield);
		invoiceAmount.setPadding(new Insets(5,5,5,5));

		//Buttons HBox
		HBox buttons = new HBox(20);
		Button addInvoice = new Button("Add Invoice");
		addInvoice.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				 controller.addInvoice(patientName, invoiceAmountTextfield.getText());
				 listView.getItems().clear();
				 listView.getItems().addAll(controller.getInvoices(patientName));
			 }
			 });
		Button selectInvoice = new Button("Select Invoice");
		selectInvoice.setOnAction(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent event){
					controller.setCurrentInvoiceNo(listView.getSelectionModel().getSelectedIndex());
					window.close();
				}
		});
		Button exit = new Button("Exit");
		exit.setOnAction(e-> window.close());
		buttons.getChildren().addAll(addInvoice, selectInvoice, exit);
		buttons.setPadding(new Insets(15,15,15,15));


		//Main Layout
		VBox layout = new VBox(10);
		layout.getChildren().addAll(listView,invoiceAmount, buttons);

		Scene scene = new Scene(layout,430, 450);
		window.setScene(scene);
		window.showAndWait();
	}

	public void procedureUpdaterWindow(String addOrUpdate, int updateIndex){

		Stage window = new Stage();
		window.setTitle("Procedure");
		window.initModality(Modality.APPLICATION_MODAL);


		Label nameLabel = new Label("Name: ");
		TextField nameField = new TextField ();
		nameField.setPrefWidth(120);
		HBox name = new HBox(20);
		name.getChildren().addAll(nameLabel, nameField );
		name.setPadding(new Insets(5,5,5,12));

		Label costLabel = new Label("Cost: ");
		TextField costField = new TextField();
		costField.setPrefWidth(120);
		HBox cost = new HBox(28);
		cost.getChildren().addAll(costLabel, costField );
		cost.setPadding(new Insets(5,5,5,12));

		HBox buttons = new HBox(20);
		Button button = new Button(addOrUpdate + " Procedure");
		button.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				 String procName = nameField.getText();
				 double procCostToDouble = Double.parseDouble(costField.getText());

				 if(addOrUpdate.equals("Add")){
					 controller.addProcedure(procName, procCostToDouble);
					 window.close();
				 }
				 else
				 {
					 controller.updateProcedure(updateIndex, procName, procCostToDouble);
					 window.close();
				 }

			 }
			 });

		Button cancel = new Button("Cancel");
		buttons.getChildren().addAll(button, cancel);
		buttons.setPadding(new Insets(10,10,10,10));
		cancel.setOnAction(e-> window.close());


		//VBox Layout
		VBox layout = new VBox(10);
		layout.getChildren().addAll(name, cost, buttons);

		Scene scene = new Scene(layout,230, 170);
		window.setScene(scene);
		window.showAndWait();
	}


}






