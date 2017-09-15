import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



public class Controller {

	private ArrayList<Patient> patientList = new ArrayList<Patient>();

	private int currentInvoiceNo = 0;
	private int currentPatientIndex = 0;
	private boolean showProcedures = false;

	public Controller(){
		readFile();
		//patientList = new ArrayList<Patient>();
	}

	public void addPatient(String name, String address, String contactNo){

		Patient e = new Patient(name, address, contactNo);
		this.patientList.add(e);
	}

	public void removePatient(String name){
		int size = patientList.size();
		int i =0;
		boolean found = false;

		while(i<size && found == false){
			String compareName = patientList.get(i).getName();
			if(compareName.equals(name)){
				found = true;
				patientList.remove(i);
			}
			else{
				i++;
			}

		}
	}


	public String getPatientList(){
		String s ="";

		for(int i =0; i<patientList.size(); i++){
			String name = patientList.get(i).getName();
			int ID = patientList.get(i).getID();
			String address = patientList.get(i).getAddress();
			String contactNo = patientList.get(i).getPhoneNo();

			String newLine = ID + "			" + name + "		" +  address + "			" + contactNo + "\n";

			s = s + newLine;
		}

		return s;
	}

	public void setCurrentInvoiceNo(int newNo){
		this.currentInvoiceNo = newNo;
	}


	public int getCurrentPatientIndex() {
		return currentPatientIndex;
	}

	public void setCurrentPatientIndex(int currentPatientIndex) {
		this.currentPatientIndex = currentPatientIndex;
	}


	public int getCurrentInvoiceNo() {
		return currentInvoiceNo;
	}


	public ArrayList<String> getInvoices(String patientName){
		//finding the right patient in the list
		int indexOfPatient = 0;
		for(int i = 0; i<patientList.size(); i++){
			String compareName = patientList.get(i).getName();
			if(compareName.equals(patientName)){
				indexOfPatient = i;
			}
		}


		ArrayList<Invoice> invoiceList = patientList.get(currentPatientIndex).getP_invoiceList();
		ArrayList<String> invoiceStringsList = new ArrayList<String>();

		for(int i = 0; i < invoiceList.size(); i++){
			int invoiceNo = invoiceList.get(i).getInvoiceNo();
			String number = Integer.toString(invoiceNo);

			double invoiceAmount = invoiceList.get(i).getInvoiceAmt();
			String amount = Double.toString(invoiceAmount);

			Date date = invoiceList.get(i).getInvoiceDate();


			String isPaidString = "Not Paid";
			Boolean isPaid = invoiceList.get(i).isPaid();
				if(isPaid){
					isPaidString= "Paid";
				}

				String s = "Invoice: " + number + "		" + "€" + amount + "	" + date + "		" + isPaidString;

				invoiceStringsList.add(s);
		}

		return invoiceStringsList;

	}

	public void addInvoice(String patientName, String invoiceAmount ){
		//finding the right patient in the list


		Date date = new Date();
		Double invoiceAmountDouble = Double.parseDouble(invoiceAmount);
		Invoice e = new Invoice(invoiceAmountDouble, date, false);
		patientList.get(currentPatientIndex).getP_invoiceList().add(e);

	}

	public void selectPatientInvoices(String patientName){

		//changing the current patient selected
				for(int i = 0; i<patientList.size(); i++){
					String compareName = patientList.get(i).getName();
					if(compareName.equals(patientName)){
						currentPatientIndex = i;
					}
				}

		//Boolean to display the procedure list for that invoice
		showProcedures = true;

	}


	public ArrayList<String> getProcedures(){

		ArrayList<String> returnList = new ArrayList<String>();

		if(showProcedures){
		ArrayList<Invoice> invoiceList = patientList.get(currentPatientIndex).getP_invoiceList();
		ArrayList<Procedure> procedureList = invoiceList.get(currentInvoiceNo).getIn_procList();

		for(int i = 0; i < procedureList.size(); i++){
			int procNo = procedureList.get(i).getProcNo();
			String procNoString = Integer.toString(procNo);

			String procName = procedureList.get(i).getProcName();

			double procCost = procedureList.get(i).getProcCost();
			String procCostString = Double.toString(procCost);

				String s = "Procedure: " + procNoString + "		" + procName + "	 " + "€" + procCostString;

				returnList.add(s);
		}
		}


		return returnList;

	}

	public void addProcedure(String procName, double procCost){

		Procedure p = new Procedure(procName, procCost);

		if(p!= null){
			patientList.get(currentPatientIndex).getP_invoiceList().get(currentInvoiceNo).addToIn_procList(p);
		}

	}

	public void updateProcedure(int updateIndex, String procName, double procCost){

		ArrayList<Procedure> procList = patientList.get(currentPatientIndex).getP_invoiceList().get(currentInvoiceNo).getIn_procList();

		procList.get(updateIndex).setProcName(procName);
		procList.get(updateIndex).setProcCost(procCost);



	}

	public void deleteProcedure(int index){

		patientList.get(currentPatientIndex).getP_invoiceList().get(currentInvoiceNo).getIn_procList().remove(index);

	}



	public void addPayment(double amount, Date date){
		Payment p = new Payment(amount, date);
		patientList.get(currentPatientIndex).getP_invoiceList().get(currentInvoiceNo).addToIn_paymentList(p);

	}

	public String getPaymentListString() {
		String s = " ";

		ArrayList<Payment> list = patientList.get(currentPatientIndex).getP_invoiceList().get(currentInvoiceNo).getIn_paymentList();

		for(int i =0; i<list.size(); i++){
			int number = list.get(i).getPaymentNo();
			double amount = list.get(i).getPaymentAmt();
			Date date = list.get(i).getPaymentDate();

			String newLine = "Payment:	" + number + "		" + amount + "		" +  date + "\n";

			s = s + newLine;
		}

		return s;
	}


	public void writeToFile(){
		try {
	         FileOutputStream fileOut = new FileOutputStream("patientArray.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(patientList);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in patientArray.ser");
	      }catch(IOException i) {
	         i.printStackTrace();
			}
	}

	public void readFile(){

		boolean fileNotNull = false;
		try {
			FileReader fileReader= new FileReader("patientArray.ser");
			BufferedReader textReader = new BufferedReader(fileReader);

			String s;

			s = textReader.readLine();

			if(s != null){
				fileNotNull = true;
			}

		} catch (FileNotFoundException e) {
			System.out.println("Patient Array file not found");
			e.printStackTrace();
			return;
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}


		if(fileNotNull){

	      try {
	         FileInputStream fileIn = new FileInputStream("patientArray.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         patientList = (ArrayList<Patient>)in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i) {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c) {
	         System.out.println("Patient class not found");
	         c.printStackTrace();
	         return;
	      }
		}

	}

	public String generatePatientReport() {
		String s = "";

		int amtSorted = 0;
		Patient temp;
		boolean swap = true;

		while(swap || (patientList.size()>1)){
			swap = false;

			for(int count = 0; count< patientList.size() - amtSorted ;count++){
				if(patientList.get(count).compareTo(patientList.get(count+1)) > 0 ){
					temp = patientList.get(count);
					patientList.set(count, patientList.get(count+1));
					patientList.set(count+1, temp);
					swap =true;

				}
			}
			amtSorted++;
		}

		for(int i =0; i <patientList.size(); i++){
			s = s + patientList.get(i).getID() + "	" + patientList.get(i).getName() + "	"+ patientList.get(i).getAddress() + "	"+ patientList.get(i).getPhoneNo() + "\n";
		}

		return s;

	}

	public void generatePatientOweReport() {
		// TODO Auto-generated method stub

	}


}















