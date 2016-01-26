package initMall;

public class TestTree {
	public static void main(String[] args){
		int total = 12;
		
		for(int i=1; i<=total; i++){
			if(i<=total/2)
				for(int count=1; count<=i; count++)
					if(count!=i)
						System.out.print("*");
					else
						System.out.println("*\n");
					
			else
				for(int count = i+1; (total-count)>=0; count++)
					if(total - count==0)
						System.out.println("*\n");
					else
						System.out.print("*");
				
			
		}
	}
}
