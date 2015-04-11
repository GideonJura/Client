/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PokerGame;

import Log.Log;
import PokerDeck.CardDeck;
import PlayerInfo.AIPlayer;
import PlayerInfo.GamePlayer;
import UI.BlackJackUINew;
import javax.mail.MessagingException;

/**
 *
 * @author Administrator
 */
public class BlackJackPlay {

    private int nRound;
    private BlackJackPlayRound blackJackPlayRound;
    static private CardDeck deck;
    private final int nNumberOfPlayer;
    private GamePlayer pCurrentPlayer;
    private int nMoney;

    GamePlayer[] pPlayerArray;
    AIPlayer pAI;

    final BlackJackUINew UI;

    public BlackJackPlay(BlackJackUINew ui, int nPlayer) {
        if (deck == null) {
            deck = new CardDeck();
        }
        nRound = 0;
        int nStartMoney = 1000;
        nNumberOfPlayer = nPlayer;
        pPlayerArray = new GamePlayer[nNumberOfPlayer];
        if (nPlayer > 0) {
            //should get info from server
            for (int i = 0; i < nNumberOfPlayer; i++) {
                pPlayerArray[i] = new GamePlayer(nStartMoney, "Player" + String.valueOf(i), 1, 1000, i);
                pCurrentPlayer = pPlayerArray[0];
            }
        } else {
            Log.getInstance().Log(2, "PlayerSetToZero");
        }

        pAI = new AIPlayer(nStartMoney, true);
        UI = ui;
        UI.RefreshMoneyOfBothPlayer();
    }

    public void ResetHand() {
        for (int i = 0; i < nNumberOfPlayer; i++) {
            pPlayerArray[i].ResetHand();
        }
        pAI.ResetHand();
    }

    public void GameBegin() throws InterruptedException, MessagingException {
        PlayNewRound();
    }

    public boolean CheckPlayerLose(GamePlayer pPlayer) {

        if (pPlayer.getBalance() < 50) {
            Log.getInstance().Log(1, "A.I Wins in " + nRound + "Round!");
            return true;
        }
        return false;
    }

    public boolean GameEnd() {
        for (int i = 0; i < nNumberOfPlayer; i++) {
            if (!CheckPlayerLose(pPlayerArray[i])) {
                return false;
            }
        }
        return true;
    }

    public BlackJackPlayRound getCurrentPlayRound() {
        return this.blackJackPlayRound;
    }

    public int getNumOfRound() {
        return this.nRound;
    }

    public void RestorePlayerStatus() {
        for (GamePlayer player : pPlayerArray) {
            player.doDouble(false);
            player.doSurrender(false);
        }
    }

    public void PlayNewRound() throws InterruptedException, MessagingException {

        if (GameEnd()) {
            UI.GameEndProcedure();
        } else {
            blackJackPlayRound = new BlackJackPlayRound(pPlayerArray, pAI, deck, UI, this);
            RestorePlayerStatus();
            if (nRound != 0) {
                ResetHand();
                UI.RestoreControlOfPlayer();
                UI.InitialBoardsBetweenPlayers();
                PrintLog();
            }
            nRound++;

            //Shuffle When Number Reduce Slow
            if (deck.getNumber() < 30) {
                deck.RebuildDeck();
            }

            blackJackPlayRound.SendFirstTwoCardsToPllayerX();
            UI.setRoundInfo(blackJackPlayRound);

        }
    }

    public void PrintLog() {
        for (int i = 0; i < nNumberOfPlayer; i++) {
            Log.getInstance().Log(1, "AI Hand:      " + pAI.printCardInHand());
            Log.getInstance().Log(1, "Player[" + i + "]Hand : " + pPlayerArray[i].printCardInHand());
            Log.getInstance().Log(1, "Player[" + i + "]Money : " + pPlayerArray[i].getBalance());
            Log.getInstance().Log(1, "-----------------------------------------------");
        }
        System.out.println(Log.getInstance().getLog());
    }

    public AIPlayer getAI() {
        return blackJackPlayRound.getAI();
    }

    public GamePlayer getCurrentPlayer() {
        return blackJackPlayRound.getCurrentPlayer();
    }
}
