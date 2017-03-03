// Java binary checker
// Created by Jacob Bryant

import java.util.*;
import java.util.regex.Matcher;	// Regular expression libraries
import java.util.regex.Pattern;

public class Project
{
	public static void main(String args[])
	{
		System.out.print("A valid binary number is a number that includes only the digits 0 and 1.\n");
		System.out.println("No letters, symbols, or numbers other than 0 and 1.\n");

		System.out.print("Please enter a valid binary number: ");

		boolean found=false;					// Boolean value for the bad character search
		int ones=0;								// A counter for the number ones
		Scanner scn = new Scanner(System.in);
		String bin = scn.nextLine();			// The binary number that the user enters
		String bad = "([2-9])|([^\\d])|(_)";	// This is a regular expression that includes all the bad characters,
												// This is broken down like
												// :::([2-9]), reg group for numbers 2-9
												// :::([^\\d]), checks for any characters that are not numbers (A-Z,a-z, etc.)
												// :::(_), checks for spaces
												// ::: Note that the | is the OR operator that will effectively include ALL bad characters

		Pattern bad_search = Pattern.compile(bad);		// Compiles the regular expression "bad"
		Matcher bad_match = bad_search.matcher(bin);	// Matches the newly compiled regular to the user input string

		int count = 0;									// Increment value for the text search

		while(bad_match.find())
		{
		 	if((bad_match.group(0)!=null)||(bad_match.group(1)!=null)||(bad_match.group(2)!=null))	// IF any of those three groups in the reg are not null (meaning that they were found)
				found=true;																			// note that .group(0)=([2-9]), .group(1)=([^\\d]), .group(2)=(_)
			count++;
		}
		if(found==true)
		{
			System.out.println("Invalid binary number.");
		}
		else
		{
			for(int i=0;i<bin.length();i++)	// Searches through the accepted string until the whole length have the string has been  searched
			{
				char c = bin.charAt(i);		// Fetches the character at the current position of index i in the input string
				int a=Integer.parseInt(bin.valueOf(c));	// Turns the fetched character c into an integer a
				if(a==1)
					ones+=1;				// If the converted integer is a 1, increment the ones count
			}
			if((ones>2)||(ones==0)||(ones<2))	// Checks to see if the amount of ones is not two
				System.out.println("Rejected.");
			else
				System.out.println("Accepted.");
		}
	}
}
