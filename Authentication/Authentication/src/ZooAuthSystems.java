import java.io.File;

import java.security.MessageDigest;

import java.util.Scanner;

public class ZooAuthSystems

{

     public static void main(String[] args) throws Exception

        {

            //read input from the console

             Scanner readInput=new Scanner(System.in);

             //keep track the number of attempts

             int attempts=0;

             /*Continue until successful attempt has been made or

             three unsuccessful attempts have been made or

             a user chooses to exit*/

             while(true)

             {

                 //username

                 System.out.print("Enter user name: ");

                 String uName=readInput.nextLine();

                 //password

                 System.out.print("Enter password: ");  

                 String original = readInput.nextLine();

                 //Convert the password using a message digest five (MD5) hash

                 MessageDigest md = MessageDigest.getInstance("MD5");

                 md.update(original.getBytes());

                 byte[] digest = md.digest();

                 StringBuffer sb = new StringBuffer();

                 for (byte b : digest)

                 {

                      sb.append(String.format("%02x", b & 0xff));

                 }

                 //track whether a login is successful or not

                

                 boolean authenticationSuccess=false;

                 //credentials file
                 Scanner readCred=new Scanner(new File("/Users/jeremypetty/NetBeansProjects/Authentication/credentials.txt"));

                 //user credentials in the credentials file

                 while(readCred.hasNextLine())

                 {

                      String record=readCred.nextLine();//Read a record

                      String columns[]=record.split("\t");//Split the record into individual fields

                      /*Check the credentials against the valid credentials

                      provided in the credentials file*/

                      //Check user name.

                      if(columns[0].trim().equals(uName))

                      {

                           /*digest five password with hashed password in the second column*/

                           if(columns[1].trim().equals(sb.toString()))

                           {

                               

                                authenticationSuccess=true;

                                //role file

                                Scanner readRole=new Scanner(new File(columns[3].trim()+".txt"));

                                //information stored in the role file

                                while(readRole.hasNextLine())

                                {

                                     System.out.println(readRole.nextLine());

                                }

                                break;

                           }

                      }

                 }

                 //ask if user wants to log out or not

                 if(authenticationSuccess)

                 {

                      System.out.println("Would you want to log out(y/n): ");

                      String choice=readInput.nextLine();

                      //exit the system.

                      if(choice.toLowerCase().charAt(0)=='y')

                      {

                           System.out.println("Successfully loged out.");

                           break;

                      }

                      
                      else

                      {

                           authenticationSuccess=false;

                      }

                 }

                 //update the number of attempts

                 else

                 {

                      attempts++;

                      //notify the user and exit the program

                      if(attempts==3)

                      {

                           System.out.println("User has been locked!\nExiting...");

                           break;

                      }

                      //prompt to enter credentials again

                      else

                      {

                           System.out.println("Please enter correct credentials!");     

                      }

                 }

             }

        }

}