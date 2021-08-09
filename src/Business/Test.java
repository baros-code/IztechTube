package Business;


import DomainLayer.SocialUser;
import DomainLayer.User;

public class Test {
	public static void main(String [] args) {
		
		/**
		 * username: admin
		 * password: admin
				*/
		//ExampleCase.start();


		User user = new SocialUser("a","b");


		System.out.println(user.getClass().getSimpleName());




	}
}
