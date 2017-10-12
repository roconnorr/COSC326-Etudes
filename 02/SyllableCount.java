/*
 * Program to estimate syllables in a word (Java7+)
 *
 * Written by:
 * Rory O'Connor
 * Joe Gasparich
 * Rebecca Wilson
 * Jessy Ruiter
 */

import java.util.*;

public class SyllableCount { 

    public static ArrayList<Character> vowels = new ArrayList<Character>();
    public static ArrayList<String> words = new ArrayList<String>();
    public static ArrayList<Integer> results = new ArrayList<Integer>();

    public static void main(String[] args){
        //add vowels to arraylist
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');

        //parse words from system.in
        Scanner scan = new Scanner(System.in);
        while(scan.hasNextLine()){
            String input = scan.nextLine();
            words.add(input);
        }
        scan.close();

        for(String word : words){
            word = word.toLowerCase();
            int syllableCount = 0;
            for(int i=0; i<word.length(); i++){
                //if the current character is a vowel
                if(vowels.contains(word.charAt(i))){
                    //if this is the last character and it is equal to e
                    if(i == word.length()-1 && word.charAt(i) == 'e'){
                        if(i > 1 && (word.charAt(i-1) == 'r' || 
                            word.charAt(i-1) == 'l') && 
                            !vowels.contains(word.charAt(i-2))){
                            syllableCount++;
                        }
                    //If previous letter was also a vowel 
                    //(or at the start of the word)
                    }else if(i == 0 || i > 0 && 
                        !vowels.contains(word.charAt(i-1))){
                        syllableCount++;
                    }
                //If y is preceded by a consonant
                }else if(i > 0 && word.charAt(i) == 'y' && 
                    !vowels.contains(word.charAt(i-1))){
                    syllableCount++;
                }
            }
            //add the syllable estimate to results
            results.add(syllableCount);
        }

        //print the syllable estimate for each word
        for(Integer result : results){
            System.out.println(result);
        }
    }
}
