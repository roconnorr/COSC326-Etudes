/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forsale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author roconnor
 */
public class BuyBot3000 implements Strategy {

    private static int currentRound = 0;
    private static Card topCard = null;

    //modifier variables to weight decision
    private float avMod = 1;
    private float caMod = 1;
    private float crMod = 1;

    public BuyBot3000(float a, float b, float c) {
        this.avMod = a;
        this.caMod = b;
        this.crMod = c;
    }

    @Override
    public int bid(PlayerRecord p, AuctionState a) {

        //vars
        float auctionValue = 0;
        float deckHeat = 0;
        //b3k's cash advantage over other players
        float cashAdvantage = 0;
        float averagePlayerCash = 0;

        for (Card c : a.getCardsInDeck()) {
            deckHeat += c.getQuality();
        }

        for (PlayerRecord player : a.getPlayers()) {
            averagePlayerCash += player.getCash();

        }
        averagePlayerCash /= a.getPlayers().size();
        cashAdvantage = p.getCash() - averagePlayerCash;

        if (a.getCardsInDeck().size() > 0) {
            deckHeat /= a.getCardsInDeck().size();
            deckHeat -= 15;
        }

        //local copy of auction cards
        ArrayList<Card> auction = a.getCardsInAuction();

        //sort the auction cards by quality
        Collections.sort(auction, new Comparator<Card>() {

            @Override
            public int compare(Card o1, Card o2) {
                return o2.getQuality() - o1.getQuality();
            }

        });

        //if the top card is not the same count a new round
        Card newTopCard = auction.get(0);
        if (!newTopCard.equals(topCard)) {
            currentRound++;
        }
        topCard = newTopCard;
        int roundsRemaining = (11 - a.getPlayers().size()) - currentRound;

        //calculate the value of the cards in the auction
        for (int i = 0; i < auction.size(); i++) {
            auctionValue += (((float) auction.get(i).getQuality()) - 15) * (auction.size() - i) / 2;

        }
        auctionValue /= auction.size();

        //make bidding decision
        float decision = avMod * (auctionValue - deckHeat) + caMod * (cashAdvantage) + crMod * (currentRound);

        int maxBet = Math.min((int) ((((decision - 7) / 5) + 1) * (p.getCash() / (roundsRemaining + 1))), p.getCash());

        //print debug vars
        /*System.out.println("ROUND: ");
        for (Card c : auction) {
            System.out.print("Q: " + c.getQuality() + " ");
        }
        System.out.println(auction);
        System.out.println("auctionval: " + auctionValue);
        System.out.println("deck heat: " + deckHeat);
        System.out.println("average player cash " + averagePlayerCash);
        System.out.println("cash advantage " + cashAdvantage);
        System.out.println("rounds remaining: " + roundsRemaining);
        System.out.println("current round:  " + currentRound);
        System.out.println("Cash remaining: " + p.getCash());
        System.out.println("dec: " + decision);
        System.out.println("bet: " + maxBet);
        System.out.println();*/
        if (a.getCurrentBid() < maxBet) {
            return a.getCurrentBid() + 1;
        }
        return -1;
    }

    @Override
    public Card chooseCard(PlayerRecord p, SaleState s) {
        //default behavior - plays highest card
        return p.getCards().get(0);
    }

}
