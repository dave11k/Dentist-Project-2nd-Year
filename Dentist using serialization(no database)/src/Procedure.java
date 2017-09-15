
public class Procedure implements java.io.Serializable{
	static int procNoCount = 1;

	private int procNo = procNoCount;
	private String procName;
	private double procCost;

	public Procedure(){

	}

	public Procedure(String procName, double procCost){
		this.procName=procName;
		this.procCost=procCost;
		procNoCount++;
	}

	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public double getProcCost() {
		return procCost;
	}

	public void setProcCost(double procCost) {
		this.procCost = procCost;
	}

	public int getProcNo() {
		return procNo;
	}

	public String toString() {
		return "Procedure Number: "+ procNo + " \nProcedure Name: " +procName+"\nProcedure Cost: "+procCost;
	}

	public void print(){
		System.out.println("Procedure Number: "+ procNo + " \nProcedure Name: " +procName+"\nProcedure Cost: "+procCost);
	}




}
