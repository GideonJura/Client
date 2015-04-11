/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PokerGame;

import PlayerInfo.AIPlayer;
import PlayerInfo.Player;
import PlayerInfo.GamePlayer;
import PokerDeck.Card;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Administrator
 */
public class BlackJackRule {

    static public boolean AmIBlackJack(Player player) {
        return AmIBlackJack(player.getPlayerCards());
    }

    static public boolean AmIBlackJack(ArrayList<Card> cardArray) {
        if (cardArray.size() != 2) {
            //You certainly not, Man!
            return false;
        }
        if (cardArray.get(0).getNum() == 1 && cardArray.get(1).getNum() >= 10 && cardArray.get(1).getNum() < 12) {
            return true;
        }
        if (cardArray.get(1).getNum() == 1 && cardArray.get(0).getNum() >= 10 && cardArray.get(0).getNum() < 12) {
            return true;
        }
        return false;
    }

    static public boolean AmIFiveDragon(Player player) {
        return AmIFiveDragon(player.getPlayerCards());
    }

    static public boolean AmIFiveDragon(ArrayList<Card> cardArray) {
        if (cardArray.size() >= 5 && GetMaxValueOfHand(cardArray) != -1) {
            return true;
        } else {
            return false;
        }
    }

    static public boolean AmIBust(ArrayList<Card> cardArray) {
        return GetMaxValueOfHand(cardArray) == -1;
    }

    static public boolean AmIBust(Player player) {
        return AmIBust(player.getPlayerCards());
    }

    static public int GetMaxValueOfHand(ArrayList<Card> cardArray) {
        int nNumOfAce = 0;
        int nTotal = 0;

        for (Card card : cardArray) {
            nTotal += card.getBlackJackNumberOfCard();
            if (card.getBlackJackNumberOfCard() == 11) {
                nNumOfAce++;
            }
        }
        if (nTotal <= 21) {
            return nTotal;
        } else if (nTotal > 21 && nNumOfAce == 0) {
            return -1;
        } else if (nNumOfAce == 0) {
            return nTotal;
        } else {
            //Total > 21 and there is on more A
            for (int i = 0; i < nNumOfAce; i++) {
                nTotal -= 10;
                if (nTotal <= 21) {
                    return nTotal;
                }
            }
            // Could not happen but need some log
            return -1;
        }
    }

    static public int GetMaxValueOfHand(Player player) {
        return GetMaxValueOfHand(player.getPlayerCards());
    }

    static public int GetMaxValueOfHand(Card[] cardArray) {
        int nNumOfAce = 0;
        int nTotal = 0;

        for (Card card : cardArray) {
            nTotal += card.getBlackJackNumberOfCard();
            if (card.getBlackJackNumberOfCard() == 11) {
                nNumOfAce++;
            }
        }
        if (nTotal <= 21) {
            return nTotal;
        } else if (nTotal > 21 && nNumOfAce > 0) {
            return -1;
        } else if (nNumOfAce == 0) {
            return nTotal;
        } else {
            //Total > 21 and there is on more A
            for (int i = 0; i < nNumOfAce; i++) {
                nTotal -= 10;
                if (nTotal <= 21) {
                    return nTotal;
                }
            }
            // Could not happen but need some log
            return -1;
        }
    }

    static public int GetMaxValueOfHand(HashSet<Card> cardArray) {
        int nNumOfAce = 0;
        int nTotal = 0;

        for (Card card : cardArray) {
            nTotal += card.getBlackJackNumberOfCard();
            if (card.getBlackJackNumberOfCard() == 11) {
                nNumOfAce++;
            }
        }
        if (nTotal <= 21) {
            return nTotal;
        } else if (nTotal > 21 && nNumOfAce > 0) {
            return -1;
        } else if (nNumOfAce == 0) {
            return nTotal;
        } else {
            //Total > 21 and there is on more A
            for (int i = 0; i < nNumOfAce; i++) {
                nTotal -= 10;
                if (nTotal <= 21) {
                    return nTotal;
                }
            }
            // Could not happen but need some log
            return -1;
        }
    }

    static public int GetBlackJackResult(Player pPlayer, AIPlayer pAI) {
        int nRet = 0;
        boolean bAIWin = false;
        if (BlackJackRule.AmIBlackJack(pAI)) {
            nRet = 10;
            bAIWin = true;
        } else if (BlackJackRule.AmIBlackJack(pPlayer)) {
            nRet = -10;
            bAIWin = false;
        } else if (BlackJackRule.AmIFiveDragon(pAI)) {
            nRet = 20;
            bAIWin = true;
        } else if (BlackJackRule.AmIFiveDragon(pPlayer)) {
            nRet = -20;
            bAIWin = false;
        } else if (BlackJackRule.GetMaxValueOfHand(pPlayer) <= BlackJackRule.GetMaxValueOfHand(pAI)) {
            nRet = 30;
            bAIWin = true;
        } else {
            nRet = -30;
            bAIWin = false;
        }
        return nRet;
    }
}
