package Voting_sys;
import java.sql.*;
import java.util.Scanner;
public class practice_VS {
	
	String VoterName,VoterPassword;
	int voterId;
	
	Scanner sc=new Scanner(System.in);
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		practice_VS p= new practice_VS();
		
		System.out.println("************* VOTER'S DETAILS**************");
		 
		p.voters();
		
		
	}
	
	
	void voters() throws SQLException, ClassNotFoundException {
		
		do {
			
		
		
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/VotingApp","root","9857"); 
		Statement stmt=con.createStatement();
		System.out.println("enter u voter name");
		VoterName=sc.next();
		
		System.out.println("enter u r voterId ");
		voterId=sc.nextInt();
		
		System.out.println("enter u r password");
		VoterPassword=sc.next();
  
		
		 try
	     {
				
	         stmt.executeUpdate("INSERT INTO voters(name,voterId,password,VotingStatus)values('"+VoterName+"',"+voterId+",'"+VoterPassword+"',"+ "'0'"+");");
	        
	         System.out.println("Successfully inserted record!!!!");
	         con.close();
	         
	         boolean addingVoters = true;
	         while (addingVoters) {
	             System.out.println("Do you want to add a voter? (yes/no)");
	             String response = sc.nextLine().toLowerCase();
	             if (response.equals("yes")) {
	             	voters();
	             	continue;
	             	
	             }
	             else if (response.equals("no")) {
	                     addingVoters = false;
	                     
	                     boolean addingCandidate=true;
	                     
	        	         while (addingCandidate) {
	                         System.out.println("Do you want to add a candidates? (yes/no)");
	                          String response1 = sc.nextLine().toLowerCase();
	                         if (response1.equals("yes")) {
	                         	candidates();
	                         	continue;
	                         }
	                         	else if (response1.equals("no")) {
	                                 addingCandidate= false;
	                                 System.out.println("************START ELECTION**************");
	                                 start_Election();
	                                 break;
	                            }
				               else {
				                     	System.out.println("Invalid response. Please enter 'yes' or 'no'.");
				                         	}
				        	     }
	        	     
	                     
	     }
             
	           
	         }
	     }
	   catch(Exception e)
	     {
	         System.out.println(e);
	     }
	}
	while(true);
	}
	
	
	void candidates() throws SQLException, ClassNotFoundException {
		
		String candiname,candipass ;
		int candiID ;
		do {
		
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/VotingApp","root","9857"); 
		Statement stmt=con.createStatement();
		System.out.println("enter candidatename");
		candiname=sc.next();
		
		System.out.println("enter u r candidateId ");
		candiID=sc.nextInt();
		
		System.out.println("enter u r candidatepassward");
		candipass=sc.next();
		
		 try
	     {
			    
	            stmt.executeUpdate("INSERT INTO candidate(name,candidateId,password,votes)values('"+candiname+"',"+candiID+",'"+candipass+"',"+ 0+");");
	        
	         System.out.println("Successfully inserted record!!!!");
	         con.close();
	         boolean addingCandidate=true;
	         while (addingCandidate) {
                 System.out.println("Do you want to add a candidates? (yes/no)");
                  String response = sc.nextLine().toLowerCase();
                 if (response.equals("yes")) {
                 	candidates();
                 }
                 	else if (response.equals("no")) {
                         addingCandidate= false;
                         System.out.println("************START ELECTION**************");
                         start_Election();
                    }
                 	else {
                 		System.out.println("Invalid response. Please enter 'yes' or 'no'.");
                 	}
	     }
	     }
	   catch(Exception e)
	     {
	         System.out.println(e);
	     }
		}
		while(true);
		
		 
	}
	

	
	void start_Election() throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/VotingApp","root","9857"); 
		Statement stmt=con.createStatement();
		 try {
	            
	           

	            Scanner sc = new Scanner(System.in);

	            System.out.println("Enter your VoterId:");
	            int vid = sc.nextInt();
	            System.out.println("Enter your Votername:");
	            String votername = sc.next();

	            ResultSet rs = stmt.executeQuery("SELECT * FROM voters");

	            boolean validVoter = false;

	            while (rs.next()) {
	                String username = rs.getString("name");
	                int id = rs.getInt("voterId");
	                int votingStatus = rs.getInt("VotingStatus");

	                if (username.equals(votername) && id == vid && votingStatus == 0) {
	                    validVoter = true;
	                    break;
	                }
	            }

	            if (validVoter) {
	                System.out.println("Authentication successful. You are a valid voter.");
	                
	                ResultSet candidate = stmt.executeQuery("SELECT candidateId, name FROM candidate");
					System.out.println("***Available Candidate***");
						
					while(candidate.next())
					{
						
							System.out.println("Candidate ID: "+candidate.getInt("candidateId") +"   " + "Candidate Name: "+candidate.getString("name") );	
						
					}
					System.out.println("*************Vote*************");
					
					System.out.println("Enter Candidate ID:");
					int cid = sc.nextInt();
//					System.out.println("Enter Candidate Name");
//					String name = sc.next();
					PreparedStatement ps,vs = null;
					try 
					{
						ps = con.prepareStatement("Update candidate SET Votes=Votes+1 WHERE candidateId=?");
				         ps.setInt(1, cid);
				         ps.executeUpdate();
				         System.out.println("your Vote is updated successfully ");
				         vs = con.prepareStatement("Update voters SET Votingstatus=1 WHERE voterId=?");
				         vs.setInt(1, vid);
				         vs.executeUpdate();
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
					
	                
	            } else {
	                System.out.println("Authentication failed. You are not a valid voter.");
	            }

	            // Close resources
	            rs.close();
	            stmt.close();
	            con.close();

	        } catch (Exception e) {
	        	System.out.println(e);
	        }

	}
}

		