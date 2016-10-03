
package enterprisehw2;

import java.util.concurrent.ThreadLocalRandom;

/* Name: Victor Bassey
 Course: CNT 4714 – Fall 2016
 Assignment title: Program 2 – Synchronized, Cooperating Threads Under Locking
 Due Date: September 25, 2016
*/

public class EnterpriseHW2 {

	  // Create threads
	  Thread Deposit1 = new Thread(new Deposit(1));
	  Thread Deposit2 = new Thread(new Deposit(2));
	  Thread Deposit3 = new Thread(new Deposit(3));
	  Thread Withdrawal1 = new Thread(new Withdraw(1));
	  Thread Withdrawal2 = new Thread(new Withdraw(2));
	  Thread Withdrawal3 = new Thread(new Withdraw(3));
	  Thread Withdrawal4 = new Thread(new Withdraw(4));
	  Thread Withdrawal5 = new Thread(new Withdraw(5));
	  Thread Withdrawal6 = new Thread(new Withdraw(6));
          int accountFunds = 0;
	  
	public static void main(String[] args) 
	{
            System.out.println("Deposit Threads      Withdrawal Threads      Balance");
            System.out.println("---------------      ------------------      ---------------");
            
            new EnterpriseHW2();
	}
	
	 public EnterpriseHW2() 
	  {
	    // Start threads
	    Deposit1.start();
	    Deposit2.start();
	    Deposit3.start();
	    Withdrawal1.start();
	    Withdrawal2.start();
	    Withdrawal3.start();
	    Withdrawal4.start();
	    Withdrawal5.start();
	    Withdrawal6.start();
	  }


    public static int randInt(int min, int max) 
    {
    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

         
	  // The thread class for depositing money into the account
	  class Deposit implements Runnable 
	  {
            private int threadNum;

	    // Construct the deposit thread with a thread identifier
	    public Deposit(int num) 
	    {
               setNum(num);
	    }
            
            private void setNum(int num)
            {
                threadNum = num;
            }

	    // Override the run() method to tell the system what the thread will do
	    public void run() 
	    {
	      for (;;)
              {
                
                int randomInt = randInt(1,200);
                accountFunds += randomInt;
                int accountSnapshot = accountFunds;
                
                System.out.printf("Thread %d deposits $%d                        Balance is $%d \n", threadNum, randomInt, accountSnapshot);
                
                try
                {
                    
                    Thread.sleep(randInt(1000,2000));   
                }
                catch (InterruptedException ex){}
              }

	    }
	  }

	  // The thread class for withdrawing funds
	  class Withdraw implements Runnable 
	  {
	    private int threadNum;

	    // Construct a thread with thread identifier
	    public Withdraw(int num) 
	    {
	      setNum(num);
	    }
            
            private void setNum(int num)
            {
                threadNum = num;
            }

	    // Tell the thread how to run 
	    public void run() 
	    {
	      for (;;)
                {
                    int randomInt = randInt(1,50);
                    
                    if(accountFunds < randomInt)
                    {
                        System.out.printf("                        Thread %d withdraws $%d  Withdrawal - Blocked - Insufficient Funds\n",
                                threadNum, randomInt);
                        try 
                        {
                            Thread.sleep(1000); 
                        }
                        catch (InterruptedException ex) { } 
                    }
                    
                    else
                    {
                        accountFunds -= randomInt;
                        int accountSnapshot = accountFunds;
                        System.out.printf("                        Thread %d withdraws $%d  Balance is $%d\n", threadNum, randomInt, accountSnapshot);
                        
                        try 
                        {
                            Thread.sleep(randInt(500,750)); 
                        }
                        catch (InterruptedException ex) { } 
                    }
                }
	    }
	  }
	
}
