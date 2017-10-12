/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forsale;

import java.util.ArrayList;

/**
 *
 * @author MichaelAlbert
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //our player
        BuyBot3000 a = new BuyBot3000(1, 1, 1);
        BuyBot3000 b = new BuyBot3000(1.2f, 0.8f, 1);
        BuyBot3000 c = new BuyBot3000(0.8f, 1, 1.2f);
        BuyBot3000 d = new BuyBot3000(0.9f, 1.1f, 1);
        BuyBot3000 e = new BuyBot3000(1.3f, 0.7f, 1);
        BuyBot3000 f = new BuyBot3000(1, 1.2f, 0.8f);
        
        /*// A null strategy - never bid, always play your top card.
        Strategy s = new Strategy() {

            @Override
            public int bid(PlayerRecord p, AuctionState a) {
                return -1;
                
            }

            @Override
            public Card chooseCard(PlayerRecord p, SaleState s) {
                return p.getCards().get(0);
            }
            
        };
        
        // A random strategy - make a random bid up to your amount remaining,
        // choose a rand card to sell.
        Strategy r = new Strategy() {

            @Override
            public int bid(PlayerRecord p, AuctionState a) {
                return (int) (1 + (Math.random()*p.getCash()));
            }

            @Override
            public Card chooseCard(PlayerRecord p, SaleState s) {
                return p.getCards().get((int) (Math.random()*p.getCards().size()));
            }
            
        };*/
        
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(new Player("bota", a));
        players.add(new Player("botb", b));
        players.add(new Player("botc", c));
        players.add(new Player("botd", d));
        players.add(new Player("bote", e));
        players.add(new Player("botf", f));
        
        //for(int i = 0; i < 3; i++) {
            
        //    players.add(new Player("R"+ ((char) ('A' + i)), r));
        //}
        GameManager g = new GameManager(players);
        g.run();
        System.out.println(g.getLog());
    }

}
