/*
Name: Tyler Meserve
Date: 8/27/2026
Homework #: Lab 1
Source file: lab1.java
Class: C343
Action: Converts integer to binary
External Assistance Provided By: 
*/

package lab1;

import java.util.Scanner;

class Converter
{
    // Declare class variables
    private int decimal;
    private int binarySize;
    private int[] binary = new int[100];
    private boolean isNegative = false;

    public Converter() {}

    public static void main(String[] args)
    {
        // Declare Program Variables
        Scanner input = new Scanner(System.in);
        Converter c = new Converter();
        boolean runProgram = true;
        int menuOption;

        while (runProgram)
        {
            menuOption = 0;
            try
            {
                System.out.println("1. Convert Integer to Binary\n2. Quit");
                menuOption = input.nextInt();
            }
            catch (Exception e)
            {
                System.out.println("Please enter a valid number; 1 or 2.");
                input.nextLine();
                continue;
            }

            try
            {
                if (menuOption == 1)
                {
                    System.out.print("Please enter an integer to convert to binary: ");
                    c.convert(input.nextInt());
                    c.displayResults();
                }
                else if (menuOption == 2)
                    runProgram = false;
                else
                {
                    System.out.println("Please enter a valid number. 1 or 2.");
                    input.nextLine();
                }
                    
            }
            catch (Exception e)
            {
                System.out.println("Please enter a valid integer to convert to binary. ");
                input.nextLine();
            }
        }

        input.close();
    }

    /*
    Action: Converts integer to binary
    Params: N an integer value
    Returns: void
    Precondition: int n needs to be a valid integer
    */
    public void convert(int n)
    {
        this.decimal = n;
        int index = 0;

        if (n < 0)
        {
            this.isNegative = true;
            n = Math.abs(n);
        }
        else if (n == 0)
        {
            this.binarySize = 1;
            this.binary[0] = 0;
            return;
        }

        while (n > 0)
        {
            binary[index] = n % 2;
            n = n/2;
            index++;
        }

        this.binarySize = index;
    }

    /*
    Action: Displays results of the binary conversion
    Params: None
    Returns: void
    Precondition: Integer must've been given a valid number
    */
    public void displayResults()
    {
        System.out.println("Number converted to binary: " + this.decimal);
        System.out.println("Binary Size: " + this.binarySize);
        
        if (this.isNegative)
            System.out.print("Least Significant Bit Binary: -");
        else
            System.out.print("Least Significant Bit Binary: ");

        for (int i = 0; i < this.binarySize; i++)
            System.out.print(binary[i]);

        System.out.println();

        if (this.isNegative)
            System.out.print("Most Significant Bit: -");
        else
            System.out.print("Most Significant Bit: ");

        for (int i = this.binarySize-1; i >= 0; i--)
            System.out.print(binary[i]);
        System.out.println();
    }
}