/**
 * Program for reading poker hands from stdin, validating them
 * and sorting them (Java7+)
 *
 * Written by Rory O'Connor
 **/
import java.util.*;

public class Poker {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        while(scan.hasNextLine()) {
            boolean isHandValid = true;

            String input = scan.nextLine();
            //holds split card strings
            ArrayList<String> cardStrings = new ArrayList<String>();
            
            //check which delimiter the hand uses
            char delimiter = 'z';
            if(input.indexOf('-') >= 0) {
                delimiter = '-';
            } else if(input.indexOf('/') >= 0) {
                delimiter = '/';
            } else if(input.indexOf(' ') >= 0) {
                delimiter = ' ';
            }

            //split the input by the delimiter
            int prevIndex = 0;
            for(int i=0; i<input.length(); i++) {
                if(input.charAt(i) == delimiter) {
                    cardStrings.add(input.substring(prevIndex, i));
                    prevIndex = i+1;
                }
            }
            //add final possible card
            cardStrings.add(input.substring(input.length()-2, input.length()));
            ArrayList<Card> hand = new ArrayList<Card>();

            //parse each line
            for(String cardString : cardStrings) {
                //System.out.println(cardString);
                cardString = cardString.toLowerCase();
                Card newCard = new Card(0, 'z');

                for(int i=0; i < cardString.length(); i++) {
                    char c = cardString.charAt(i);
                    if(Character.isDigit(c)) {
                        //if its a digit check for a double digit and parse, 
                        //don't check if its the last character
                        if(i < cardString.length()-1 && 
                            Character.isDigit(cardString.charAt(i+1))) {
                            //add both digits into a string for parsing
                            String s = Character.toString(c) 
                                     + Character.toString(
                                       cardString.charAt(i+1));
                            newCard.value = Integer.parseInt(s);
                            //increment i to skip the extra digit parsed
                            i++;
                        } else {
                            //If it is a single digit set it as the value
                            newCard.value = 
                            Integer.parseInt(Character.toString(c));
                            //add the card to the hand if its the last character
                            if(i == cardString.length()-1) {
                                    hand.add(newCard);
                            }
                        }
                    } else if(Character.isLetter(c)) {
                        switch(cardString.charAt(i)) {
                            //card values
                            //for each case set the value and check if last char
                            case 'a':
                                newCard.value = 1;
                                if(i == cardString.length()-1) {
                                    hand.add(newCard);
                                }
                                break;

                            case 't':
                                newCard.value = 10;
                                if(i == cardString.length()-1) {
                                    hand.add(newCard);
                                }
                                break;

                            case 'j':
                                newCard.value = 11;
                                if(i == cardString.length()-1) {
                                    hand.add(newCard);
                                }
                                break;

                            case 'q':
                                newCard.value = 12;
                                if(i == cardString.length()-1) {
                                    hand.add(newCard);
                                }
                                break;

                            case 'k':
                                newCard.value = 13;
                                if(i == cardString.length()-1){
                                    hand.add(newCard);
                                }
                                break;

                            //suits
                            case 'c':
                            case 'd':
                            case 'h':
                            case 's':
                                newCard.suit = c;
                                hand.add(newCard);
                                newCard = new Card(0, 'z');
                                break;

                            default:
                                hand.add(newCard);
                                newCard = new Card(0, 'z');
                                break;
                        }
                    } else {
                        //if the cardString has a char thats not letter or digit
                        //a delimiter got through and the hand format is invalid
                        isHandValid = false;
                    }
                }
            }

            //now that the whole hand has been extracted
            //perform validation logic
            //array for storing the count of each card
            int[] cardCounts = new int[14];

            //check if the hand is the correct size and has not been flagged invalid
            if(isHandValid == false || hand.size() > 5 || hand.size() < 5) {
                isHandValid = false;
            } else {
                //Check each card has a valid value and suit
                for(Card card : hand) {
                    //check card values are valid
                    if(card.value > 13 || card.value < 1){
                        isHandValid = false;
                        break;
                    } else {
                        //if the value is valid to the value index
                        cardCounts[card.value]++;
                    }

                    //check the suit matches a valid suit
                    switch(Character.toString(card.suit).charAt(0)){
                        case 'c':
                        case 'd':
                        case 'h':
                        case 's':
                            break;

                        default:
                            isHandValid = false;
                    }

                }

                //Check the count of each card value and compare their suits
                for(int i=0; i<cardCounts.length; i++){
                    ArrayList<Card> compareCards = new ArrayList<Card>();

                    //compare 2 cards of the same type
                    if(cardCounts[i] == 2){
                        //get each card of value i
                        for(int j=0; j<hand.size(); j++){
                            if(hand.get(j).value == i){
                                compareCards.add(hand.get(j));
                            }
                        }
                        //ensure each has a unique suit
                        if( compareCards.get(0).suit == 
                            compareCards.get(1).suit ){
                            isHandValid = false;
                            break;
                        }
                    }

                    //compare 3 cards of the same type
                    if(cardCounts[i] == 3){
                        //get each card of value i
                        for(int j=0; j<hand.size(); j++){
                            if(hand.get(j).value == i){
                                compareCards.add(hand.get(j));
                            }
                        }
                        //ensure each has a unique suit
                        if( compareCards.get(0).suit == 
                            compareCards.get(1).suit ||

                            compareCards.get(0).suit == 
                            compareCards.get(2).suit || 

                            compareCards.get(1).suit == 
                            compareCards.get(2).suit ){

                            isHandValid = false;
                            break;
                        }
                    }

                    //compare 4 cards of the same type
                    if(cardCounts[i] == 4){
                        //get each card of value i
                        for(int j=0; j<hand.size(); j++){
                            if(hand.get(j).value == i){
                                compareCards.add(hand.get(j));
                            }
                        }

                        //ensure each has a unique suit
                        if( compareCards.get(0).suit == 
                            compareCards.get(1).suit ||

                            compareCards.get(0).suit == 
                            compareCards.get(2).suit ||

                            compareCards.get(0).suit == 
                            compareCards.get(3).suit ||

                            compareCards.get(1).suit == 
                            compareCards.get(2).suit ||

                            compareCards.get(1).suit == 
                            compareCards.get(3).suit ||

                            compareCards.get(2).suit == 
                            compareCards.get(3).suit ){

                            isHandValid = false;
                            break;
                        }
                    }

                    //More than 4 of a kind is invalid
                    if(cardCounts[i] > 4){
                        isHandValid = false;
                        break;
                    }
                }
            }
            
            if(isHandValid == true){
                //sort poker hand
                Collections.sort(hand, new Comparator<Card>() {

                    public int compare(Card o1, Card o2) {
                        Integer val1 = o1.value;
                        Integer val2 = o2.value;

                        //Make aces the highest # to work with the comparator
                        if(val1.equals(1)){
                            val1 = 14;
                        }

                        if(val2.equals(1)){
                            val2 = 14;
                        }

                        Integer c = val1.compareTo(val2);

                        if(c != 0){
                            return c;
                        } else {
                            //for same cards, compare by suit
                            String s1 = Character.toString(o1.suit);
                            String s2 = Character.toString(o2.suit);

                            return s1.compareTo(s2);
                        }
                    }});

                //print poker hand
                for(Card card : hand){
                    switch(card.value){
                        case 1:
                            System.out.print("A");
                            break;
                        case 11:
                            System.out.print("J");
                            break;
                        case 12:
                            System.out.print("Q");
                            break;
                        case 13:
                            System.out.print("K");
                            break;
                        default:
                            System.out.print(card.value);
                            break;
                    }
                    System.out.print(Character.toString(card.suit)
                        .toUpperCase() + " ");
                }
                System.out.println();
            }else{
                System.out.println("Invalid: " + input);
            }
        }
        scan.close();
    }
}

class Card {
    public int value;
    public char suit;

    public Card(int value, char suit){
        this.value = value;
        this.suit = suit;
    }
}
