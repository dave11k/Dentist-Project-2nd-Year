import java.util.*;



public class Payment implements java.io.Serializable{
		static int paymentNoCount = 1;

		private int paymentNo = paymentNoCount;
		private double paymentAmt;
		private Date paymentDate;


		public Payment(){

		}

		public Payment(double paymentAmt,Date paymentDate){
			this.paymentAmt=paymentAmt;
			this.paymentDate=paymentDate;
			paymentNoCount++;
		}

		public double getPaymentAmt() {
			return paymentAmt;
		}

		public void setPaymentAmt(double paymentAmt) {
			this.paymentAmt = paymentAmt;
		}

		public Date getPaymentDate() {
			return paymentDate;
		}

		public void setPaymentDate(Date paymentDate) {
			this.paymentDate = paymentDate;
		}

		public int getPaymentNo() {
			return paymentNo;
		}

		public String toString() {
			return "Payment Number: " + paymentNo + "\nPayment Amount: "+ paymentAmt + "\nPayment Date: " + paymentDate;
		}

		public void print(){
			System.out.println("Payment Number: " + paymentNo + "\nPayment Amount: "+ paymentAmt + "\nPayment Date: " + paymentDate);
		}


}
