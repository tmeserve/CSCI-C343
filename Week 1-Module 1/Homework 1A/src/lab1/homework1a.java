/*
Name: Tyler Meserve
Date: 8/27/2026
Homework #: Homework 1A
Source file: homework1a.java
Class: C343
Action: Converts integer to binary
External Assistance Provided By: 
*/

package lab1;

import java.util.InputMismatchException;
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
                System.out.println("1. Convert Integer to Binary\n2. Quit\n3. Convert binary to decimal");
                menuOption = input.nextInt();
                input.nextLine();
            }
            catch (InputMismatchException e)
            {
                System.out.println("Please enter a valid number; 1, 2, or 3.");
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
                else if (menuOption == 3)
                {
                    System.out.print("Please provide a binary to convert to decimal: ");
                    String s = input.next();
                    c.setBinary(s);
                    c.binaryToDecimal();
                }
                else
                    System.out.println("Please enter a valid number. 1, 2, or 3.");

                if (runProgram)
                    input.nextLine();
            }
            catch (InputMismatchException e)
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

        System.out.print("Least Significant Bit Binary: ");
        for (int i = 0; i < this.binarySize; i++)
            System.out.print(binary[i]);

        if (isNegative)
            System.out.print("-");

        System.out.println();

        if (this.isNegative)
            System.out.print("Most Significant Bit: -");
        else
            System.out.print("Most Significant Bit: ");

        for (int i = this.binarySize-1; i >= 0; i--)
            System.out.print(binary[i]);
        System.out.println();
    }

    /*
    Action: Converts a binary string to a binary array of ints
    Params: String s is a string containing a number in binary
    Returns: None
    Precondition: String s is assumed to be a string of 1s and 0s in MSB
    */
    public void setBinary(String s)
    {
        this.isNegative = s.startsWith("-");
        this.binarySize = this.isNegative ? s.length() - 1 : s.length();
        int index = this.binarySize - 1;

        for (char c: s.toCharArray())
        {
            if (c == '0')
                this.binary[index--] = 0;
            else if (c == '1')
                this.binary[index--] = 1;
            else if (c == '-')
                this.isNegative = true;
            else
            {
                System.out.println("Invalid character recognized, please enter a positive or negative binary number only.");
                System.out.println("Valid characters: 0, 1, -");
                this.isNegative = false;
                this.binary = new int[100];
                this.binarySize = 0;
                return;
            }
        }
        if (this.isNegative)
            this.binarySize = s.length() - 1;
        else
            this.binarySize = s.length();
    }

    /*
    Action: Converts a binary array to an integer
    Params: None
    Returns: void
    Precondition: Binary array needs to be initialized with a binary representation
    */
    public void binaryToDecimal()
    {
        int result = 0;

        for (int i = this.binarySize - 1; i >= 0; i--)
            result = result * 2 + this.binary[i];

        this.decimal = result;

        if (this.isNegative)
            this.decimal = this.decimal * -1;
        
        this.displayResults();
    }
}