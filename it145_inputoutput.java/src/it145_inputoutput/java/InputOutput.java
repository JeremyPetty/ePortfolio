package it145_inputoutput.java;

import java.util.Scanner;
public class InputOutput {public static void main(String[] args){
/*Using print statements*/
System.out.println("Print first ");
System.out.println("Print second ");
/*Using println statements*/
System.out.println("Print third");
System.out.println("Print fourth");
System.out.println("Print fifth");
/*Create a scanner for inputs*/
Scanner input = new Scanner(System.in);
/*Get the color and display it*/
System.out.println("What is your favorite color?");
String color = input.nextLine();
System.out.println("You wrote: " + color);
/*Get the num of apples and display*/
System.out.println("How many apples do you have?");
int numApples = input.nextInt();
System.out.println("You wrote: " + numApples);
/*Close the scanner*/
input.close();}}