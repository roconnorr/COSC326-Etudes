/* Finds connections between words using their suffixes and prefixes
 * Authors 	Joe Gasparich	2373980
 * 			Rory O'Connor	9825774
*/

import java.util.*;

public class Writing {

	public static ArrayList<String> dictionary;
	public static ArrayList<String> expandedWords;

	//--Main method--//
	public static void main(String[] args) {
		String startWord = args[0];
		String endWord = args[1];
		
		//Parse dictionary into array list
		dictionary = new ArrayList<String>();
		
		Scanner scan = new Scanner(System.in);
		while(scan.hasNextLine()) {
			String input = scan.nextLine();
			if(input.charAt(0) != '#'){
				dictionary.add(input.toLowerCase());
			}
		}
		Collections.sort(dictionary);

		// Find connections and count the words
		String singly = findConnection(startWord, endWord, true);
		int singlyCount = 0;
		for(char letter : singly.toCharArray()) {
			if(letter == ' ') singlyCount++;
		}
		String doubly = findConnection(startWord, endWord, false);
		int doublyCount = 0;
		for(char letter : doubly.toCharArray()) {
			if(letter == ' ') doublyCount++;
		}
		if(startWord.equals(endWord)) {
			singlyCount = 1;
			doublyCount = 1;
			singly = " " + endWord;
			doubly = " " + endWord;
		}
		System.out.println(singlyCount + singly);
		System.out.println(doublyCount + doubly);
	}
	
	//--Find Connection--//
	//Finds a connection between startWord and endWord
	//Connection type either singlylinked or doublylinked
	private static String findConnection(
		String startWord, String endWord, Boolean singly) {

		//Use queue data structure so that the search is breadth first
		LinkedList<String> queue = new LinkedList<String>();
		queue.add(" " + startWord + " ");
		ArrayList<String> prefixWords = new ArrayList<String>();

		expandedWords = new ArrayList<String>();

		String result = "";

		//Loop until connection is found or no words remain
		while(!prefixWords.contains(endWord) && !queue.isEmpty()) {
			prefixWords = new ArrayList<String>();
			String word = queue.poll();
				

			//Find word at end of string's length
			int wordLength = 1;
			while(wordLength < word.length() - 1
				&& word.charAt(word.length() - wordLength - 2) != ' ') {
				wordLength++;
			}
			//Determine word length requirements
			int minLength = 1;
			int dubMinLength = wordLength / 2; //Minimum length of a suffix
			if(wordLength % 2 == 1) dubMinLength++;
			if(!singly) minLength = dubMinLength;

			String suffix = "";
			int count = 0;
			//Find all words that have a prefix identical to this one's suffix
			while(suffix.length() < wordLength
			 && word.charAt(word.length() - 1 - (minLength + count)) != ' ') {
				
				suffix = word.substring(word.length() - 1 - (minLength + count), word.length() - 1);
				//Search for a singly linked prefix only if previous word
				//was double the suffix length
				if(suffix.length() >= dubMinLength && singly) {
					prefixWords.addAll(searchPrefix(suffix, true));
				} else {
					prefixWords.addAll(searchPrefix(suffix, false));
				}
				count++;
			}
			//Add new prefixes to the end of the queue
			for(String prefixWord : prefixWords) {
				//If end word found then store result
				//System.out.println(word + prefixWord);
				if(prefixWord.equals(endWord)) {
					result = word + prefixWord;
				}
				if(!word.contains(" " + prefixWord + " ") && !expandedWords.contains(prefixWord)) {
					queue.add(word + prefixWord + " ");
					expandedWords.add(prefixWord);
				}
			}
		}
		return result;
	}
	
	//--Search Prefix--//
	//Searches the dictionary for words with the specified prefix
	//using a binary search
	private static List<String> searchPrefix(
		String prefix, Boolean singly) {
		int start = Collections.binarySearch(dictionary, prefix);
		if(start < 0) {
			start = -start - 1;
		}
		int end = start;
		while(end < dictionary.size() && dictionary.get(end).startsWith(prefix))
			end++;
		List<String> prefixWords = dictionary.subList(start, end);
		if(!singly) {
			List<String> longPrefixWords = new ArrayList<String>();
			int maxLength = prefix.length() * 2;
			for(String word : prefixWords) {
				if(word.length() <= maxLength) {
					longPrefixWords.add(word);
				}
			}
			return longPrefixWords;
		}
		return prefixWords;
	}

}
