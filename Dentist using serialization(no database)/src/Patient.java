import java.util.ArrayList;


public class Patient extends Person implements java.io.Serializable, Comparable {
	static int IDcount = 101;

	private int ID = IDcount;
	ArrayList<Invoice> p_invoiceList = new ArrayList<Invoice>();


	public Patient(){

	}

	public Patient(String name, String address, String phoneNo){
		super(name, address, phoneNo);
		IDcount ++;
	}

	@Override
	public int compareTo(Object o) {

		String name = super.getName();
		return name.compareTo((String) o);
	}


	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	public ArrayList<Invoice> getP_invoiceList() {
		return p_invoiceList;
	}

	public void setP_invoiceList(ArrayList<Invoice> p_invoiceList) {
		this.p_invoiceList = p_invoiceList;
	}

	public String toString() {
		return "Patient ID=" + ID;
	}

	public void print (){
		System.out.println("Name: " + super.getName() + "\nPatient number: " + ID + "\nAddress: "+super.getAddress()+"\nPhone Number: "+super.getPhoneNo());

	}



}
