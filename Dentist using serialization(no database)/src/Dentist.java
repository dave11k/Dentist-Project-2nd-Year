import java.util.ArrayList;

public class Dentist {

		private String password;
		
		
		public Dentist(){
			
		}
		
		public Dentist(String password){
			this.password = password;
		}
	 
		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public boolean login(String password){
			boolean login = false;
			
			if(this.password.equals(password)){
				login = true;
			}
			
			return login;
			
		}
}
