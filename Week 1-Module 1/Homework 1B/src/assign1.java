/*
Name: Tyler Meserve
Date: 8/31/2026
Homework #: Homework 1B
Source file: assign1.java
Class: C343
Action: 
External Assistance Provided By: 
*/


class Assign1
{
    private static int minRandomValue = 11;
    private static int maxRandomValue = 71;

    
    public static void main(String[] args)
    {
        System.out.println("---------------------------------------------");
        System.out.println("Creating an array and fill it with random numbers between 11 and 71:");
        System.out.println("Initializing with Random integers between 11 and 71");
        System.out.println("Returning the integer array");
        System.out.println("---------------------------------------------");
        System.out.println("Print Integer Array:");

        int[] random = createIntegers(10);
        for (int i: random)
            System.out.println(i);
        System.out.println("---------------------------------------------");

        String string1 = "Hello";
        String string2 = "Chicken wings";
        String string3 = "INFO-C 307";
        String string4 = "574-520-4517";
        String string5 = "574.520.4517";

        System.out.println("Spread Eagle Showcase:");
        System.out.println(new String(spreadEagle(string1)));
        System.out.println(new String(spreadEagle(string2)));
        System.out.println(new String(spreadEagle(string3)));
        System.out.println(new String(spreadEagle(string4)));
        System.out.println(new String(spreadEagle(string5)));

        String toCheckStr = "This is a test";
        char toCheckChar = 'e';
        int charCount = charFrequency(toCheckStr, toCheckChar);
        System.out.println("Original String: " + toCheckStr
            + "\nOriginal Size: " + toCheckStr.length());
        System.out.println("Frequency for character '" + toCheckChar + "' is: " + charCount);
        System.out.println("---------------------------------------------");

        System.out.println("Char Frequency Showcase:");
        toCheckChar = 't';
        charCount = charFrequency(toCheckStr, toCheckChar);
        System.out.println("Original String: " + toCheckStr 
            + "\nOriginal Size: " + toCheckStr.length());
        System.out.println("Frequency for character '" + toCheckChar + "' is: " + charCount);

        toCheckStr = "hhakimza@iusb.edu/syllabus.html";
        toCheckChar = '.';
        charCount = charFrequency(toCheckStr, toCheckChar);
        System.out.println("Original String: " + toCheckStr 
            + "\nOriginal Size: " + toCheckStr.length());
        System.out.println("Frequency for character '" 
            + toCheckChar + "' is: " + charCount);
    }


    /*
    Action: Generates a random array of integers
    Params: int arraySize is an integer representing how many random numbers to generate
    Returns: returns a randomly generated int array
    Precondition: arraysize must be a non negative number and max value must be greater than min value
    */
    public static int[] createIntegers(int arraySize)
    {
        int[] random = new int[arraySize];
        for (int i=0; i < arraySize; i++)
            //generates a number between 0 to max-min then offsets by adding the minimum.
            random[i] = (int) (Math.random() * (maxRandomValue - minRandomValue + 1))
                + minRandomValue;
        return random;
    }


    /*
    Action: Converts a string to "spread eagle" format by inserting a period after every alphabetic character, except the end of the string
    Params: theString is a string whose characters will be converted to spread eagle format 
    Returns: a char array of spread-out characters
    Precondition: theString must be non-null
    */
    public static char[] spreadEagle(String theString)
    {
        char[] estArray = new char[theString.length()*2]; // worst case every char get a trailing period
        char nullChar = '\0'; // sentintel marking unused slots in estArray, trimmed off below
        int nullCharCount = 0;
        int index = 0;
        int strIndex = 0;
        String specialChars = ".,/;'[]\\\\+_-=`~!@#$%^&*()<>?:\\\"{}|";

        for (char c: theString.toCharArray())
        {
            if (Character.isDigit(c) || Character.isSpaceChar(c) || (specialChars.indexOf(c) != -1))
                estArray[index] = c;
            else if (Character.isAlphabetic(c))
            {
                estArray[index] = c;
                if (!(strIndex == theString.length() - 1)) // dont add trailing period after final character
                    estArray[++index] = '.';
                else
                {
                    estArray[++index] = nullChar;
                    nullCharCount++;
                }
            }
            index++;
            strIndex++;
        }

        char[] completedArray = new char[estArray.length - nullCharCount];
        index = 0;
        // copy estArray into a right-sized array, stopping at the first unused (null) slot
        for (char c: estArray)
        {
            if (c == nullChar)
            {
                break;
            }

            completedArray[index++] = c;
        }

        return completedArray;
    }

    
    /*
    Action: Counts how many times a given character appears in a string, case-insensitive
    Params: theString - the string to search; theChar the char to count occurances of
    Returns: the number of times theChar appears in theString
    Precondition: theString is not null
    */
    public static int charFrequency(String theString, char theChar)
    {
        int count = 0;
        theChar = Character.toLowerCase(theChar);

        for (char c: theString.toLowerCase().toCharArray())
        {
            if (c == theChar)
                count++;
        }

        return count;
    }
}


/* ************************  Program Output  *******************************
---------------------------------------------
Creating an array and fill it with random numbers between 11 and 71:
Initializing with Random integers between 11 and 71
Returning the integer array
---------------------------------------------
Print Integer Array:
17
39
29
50
21
22
51
41
59
66
---------------------------------------------
Spread Eagle Showcase:
H.e.l.l.o
C.h.i.c.k.e.n. w.i.n.g.s
I.N.F.O.-C. 307
574-520-4517
574.520.4517
Original String: This is a test
Original Size: 14
Frequency for character 'e' is: 1
---------------------------------------------
Char Frequency Showcase:
Original String: This is a test
Original Size: 14
Frequency for character 't' is: 3
Original String: hhakimza@iusb.edu/syllabus.html
Original Size: 31
Frequency for character '.' is: 2
*/