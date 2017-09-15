import java.util.ArrayList;
import java.util.Date;

public class Invoice implements java.io.Serializable {
	static int invoiceNoCount = 1;

	private int invoiceNo=invoiceNoCount;
	private double invoiceAmt;
	private Date invoiceDate;
	private boolean isPaid;
	private ArrayList<Procedure> in_procList = new ArrayList<Procedure>();
	private ArrayList<Payment> in_paymentList = new ArrayList<Payment>();


	public Invoice(){

	}
	public Invoice(double invoiceAmt, Date invoiceDate, boolean isPaid){
		this.invoiceAmt=invoiceAmt;
		this.invoiceDate=invoiceDate;
		this.isPaid=isPaid;

		invoiceNoCount++;
	}

	public Invoice(Date date){
		this.invoiceDate = date;
	}


	public int getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public double getInvoiceAmt() {
		return invoiceAmt;
	}

	public void setInvoiceAmt(double invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public ArrayList<Procedure> getIn_procList() {
		return in_procList;
	}
	public void addToIn_procList(Procedure e) {
		in_procList.add(e);
	}
	public void addProcedure(Procedure p){
		in_procList.add(p);
	}
	public void removeProcedure(int index){
		in_procList.remove(index);
	}
	public ArrayList<Payment> getIn_paymentList() {
		return in_paymentList;
	}
	public void addToIn_paymentList(Payment p) {
		in_paymentList.add(p);
	}


	public String toString() {
		String b = isPaid?"yes":"no";
		String s ="Invoice No: " + invoiceNo+ "\nInvoice Amount: " + invoiceAmt+"\nInvoice Date: "+ invoiceDate + "\nPaid: " + b
				+ "";

		return s;
	}

	public void print(){
		String b = isPaid?"yes":"no";
		System.out.println("Invoice No: " + invoiceNo+ "\nInvoice Amount: " + invoiceAmt+"\nInvoice Date: "+ invoiceDate + "\nPaid: " + b);
	}




}
