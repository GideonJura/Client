/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlayerInfo;


import PokerGame.BlackJackRule;
import PokerDeck.Card;
import java.util.ArrayList;
/**
 *
 * @author Boheng
 */
public class Player {
    
    protected double dBalance;
    int nNumCards;
    boolean bDouble;
    boolean bSurrender;
    ArrayList<Card> CardArray;
    
    public void doDouble(boolean b) {
        this.bDouble = b;
    }
    
    public void doSurrender(boolean b)
    {
        this.bSurrender = b;
    }
    
    public boolean AmIDouble() {
        return this.bDouble;
    }
    
    public boolean AmISurrender(){
        return this.bSurrender;
    }
    
    public void EarnMoney(int nInitMoney) {
        if (nInitMoney > 0) {
            this.dBalance += nInitMoney;
        }
    }
    
    public void LoseMoney(int nInitMoney) {
        if (nInitMoney > 0) {
            this.dBalance -= nInitMoney;
        }
        if (this.dBalance < 0) {
            this.dBalance = 0;
        }
    }
    
    public ArrayList<Card> getPlayerCards() {
        return this.CardArray;
    }   

    public void SendNewCardToPlayer(Card card) {
        CardArray.add(card);
    }
    
    public String printCardInHand() {
        StringBuffer sb = new StringBuffer();
        for (Card card : CardArray) {
            sb.append(card.printCard()).append(" ");
        }
        sb.append("Total Num: ");
        sb.append(BlackJackRule.GetMaxValueOfHand(CardArray));
        return sb.toString();
    }
    
    public void ResetHand() {
        CardArray.clear();
        nNumCards++;
    }
    
    public Card getHiddenCard() {
        if (CardArray.size() > 0) {
            return CardArray.get(0);
        } else {
            return null;
        }
    }
    
    public void getNewCard(Card cardNewCard) {
        CardArray.add(cardNewCard);
        nNumCards++;
    }    
    
    public double getBalance() {
        return this.dBalance;
    }

    
}
