package forsale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Rory O'Connor, Joe Gasparich, Joseph O'Connor, Matthew Boyes
 */
public class BuyBot3002 implements Strategy {

    private int currentRound = 0;
    private Card bestPropertyInAuction = null;
    private Integer bestChequeInAuction = 0;
    private int maxBet = -1;

    //Modifier variables to weight decision
    //These were tested against one another and these are the most optimal
    //Initial decision
    private final float acMod = 0.6f; //Auction Value Calculation
    private final float caMod = 0.2f; //Cash Advantage
    private final float crMod = 0.2f; //Current Round
    //Final decision
    private final float avMod = 0.5f; //Auction Value
    private final float sdMod = 0.5f; //Standard Deviation

    //--BUYING--//
    /**
     * Calculates a bunch of variables relevant to the bidding decision, then
     * normalises them to be between 1 and 0 so that we can multiply them
     * together to calculate a final heuristic for how much to bid.
     */
    @Override
    public int bid(PlayerRecord p, AuctionState a) {

        float auctionValue = 0;
        float deckHeat = 0;
        float cashAdvantage = 0;
        float averagePlayerCash = 0;
        float averagePropertyValue = 0;
        int totalRounds = 11 - a.getPlayers().size();
        int roundsRemaining = -1;

        //Local copy of auction cards
        ArrayList<Card> auction = a.getCardsInAuction();

        //Sort the auction cards by quality
        Collections.sort(auction, new Comparator<Card>() {

            @Override
            public int compare(Card o1, Card o2) {
                return o2.getQuality() - o1.getQuality();
            }

        });

        //Get average property value in auction
        for (Card c : auction) {
            averagePropertyValue += c.getQuality();
        }
        averagePropertyValue /= auction.size();

        //Get property value standard deviation
        float stddev = 0;
        for (Card c : auction) {
            stddev += Math.abs(c.getQuality() - averagePropertyValue);
        }
        stddev /= auction.size();

        //Get average player cash
        for (PlayerRecord player : a.getPlayers()) {
            averagePlayerCash += player.getCash();

        }
        //Calculate cash advantage
        averagePlayerCash /= a.getPlayers().size();
        cashAdvantage = p.getCash() - averagePlayerCash;

        //Calculate deck heat
        for (Card c : a.getCardsInDeck()) {
            deckHeat += c.getQuality();
        }
        if (a.getCardsInDeck().size() > 0) {
            deckHeat /= a.getCardsInDeck().size();
            deckHeat -= 15;
        }

        //if the top card is not the same count a new round
        Card newTopCard = auction.get(0);
        if (!newTopCard.equals(bestPropertyInAuction)) {
            currentRound++;
            maxBet = -1;
        }
        bestPropertyInAuction = newTopCard;
        roundsRemaining = totalRounds - currentRound;

        //Calculate the value of the cards in the auction, with each consecutive
        //property having a smaller modifier
        //E.g. 1st property is *2.5, 2nd property is *2.0
        for (int i = 0; i < auction.size(); i++) {
            auctionValue += (((float) auction.get(i).getQuality()) - 15) 
                    * (float) (auction.size() - i) / 2;
        }
        auctionValue /= auction.size();

        //Normalise modifiers to be between 0 and 1
        float avNorm = (Math.max(Math.min
            ((auctionValue - deckHeat), 15), -5) + 5) / 20;
        float crNorm = (float) currentRound / (float) totalRounds;
        float caNorm = Math.max(Math.min((cashAdvantage + 5), 10), 0) / 10;

        float sdNorm = stddev / 15;
        float hvNorm = (float) auction.get(0).getQuality() / 30;

        //Calculate the auction value based on auction value, deck heat,
        //standard deviation and the highest value card
        float avCalc = 0;
        if (avNorm != 0) {
            avCalc = avMod * avNorm + sdMod * (sdNorm / hvNorm);
        }

        //Make bidding decision
        float decision = acMod * avCalc + caMod * crNorm + crMod * caNorm;

        //If the maximum bet hasn't already been set then calculate it based 
        //off of the decision and cash remaining
        if (maxBet == -1) {
            maxBet = Math.min((int) (decision * 4 * (p.getCash() / 
                    (roundsRemaining + 1))), p.getCash());
        }

        //If the current bid is below the maximum bet then bid 1 higher than 
        //the current bid
        if (a.getCurrentBid() < maxBet) {
            return a.getCurrentBid() + 1;
        }
        return -1;
    }

    //--SELLING--//
    /**
     * Calculates a bunch of variables relevant to selling and then does some
     * logic to decide which property to sell.
     */
    @Override
    public Card chooseCard(PlayerRecord p, SaleState s) {

        float saleValue = 0;
        float deckHeat = 0;

        //Calculate deck heat (mean difference from the average left in deck)
        for (Integer chequeValue : s.getChequesRemaining()) {
            deckHeat += chequeValue;
        }
        if (s.getChequesRemaining().size() > 0) {
            deckHeat /= s.getChequesRemaining().size();
            deckHeat -= 8;
        }

        //local copy of cheques in sale
        ArrayList<Integer> sale = s.getChequesAvailable();

        //sort the cheques by quality
        Collections.sort(sale);

        //If the top card is not the same then it is a new round so reset
        //the max bet
        Integer newTopCheque = sale.get(0);
        if (!newTopCheque.equals(bestChequeInAuction)) {
            maxBet = -1;
        }
        bestChequeInAuction = newTopCheque;

        //Calculate the value of the cheques in the sale
        //mean difference from the average cheque
        for (int i = sale.size() - 1; i > -1; i--) {
            saleValue += (((float) sale.get(i)) - 8f);
        }
        saleValue /= sale.size();

        //Highest cheque in sale
        int highestSaleCheque = sale.get(sale.size() - 1);

        //Sort other players cards and find the most valuable property owned by
        //another player
        int othersHighestProp = 0;
        ArrayList<Card> myHeldCards = new ArrayList<>();
        ArrayList<PlayerRecord> players = s.getPlayers();
        for (PlayerRecord player : players) {
            //Sort each players cards so we can find the highest card
            Collections.sort(player.getCards(), (Card o1, Card o2) -> 
                    o2.getQuality() - o1.getQuality());
            if (player.getName().equals(p.getName())) {
                myHeldCards = player.getCards();
            } else if (player.getCards().get(0).getQuality()
                    > othersHighestProp) {
                othersHighestProp = player.getCards().get(0).getQuality();
            }
        }
        
        //Calculate whether we have the most valuable property left in the game
        boolean hasHighestProperty = myHeldCards.get(0).getQuality()
                > othersHighestProp;

        //Calculate decision value
        float decision = saleValue - deckHeat;

        //If we have the most valuable property in the game, then wait for a 15
        //before spending it
        if (hasHighestProperty && highestSaleCheque == 15) {
            return myHeldCards.get(0);
        //Otherwise if there is more than 1 card in the hand
        } else if (myHeldCards.size() > 1) {
            //If the decision value is above 3 then override the strategy to
            //hold on to your most valuable property and just sell it
            if (decision > 3) {
                return myHeldCards.get(0);
            //If the decision value is above 0.5 then sell your second highest
            //property
            } else if (decision > 0.5) {
                return myHeldCards.get(1);
            //If the decision value is below 0.5 then sell your lowest property
            } else {
                return myHeldCards.get(myHeldCards.size() - 1);
            }
        }
        //If there is only one card in the hand then just return it
        return myHeldCards.get(0);
    }

}
