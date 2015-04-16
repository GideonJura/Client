/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PokerGame;

import PokerAI.BJAIMain;
import PokerDeck.Card;
import PokerDeck.CardDeck;
import PlayerInfo.AIPlayer;
import PlayerInfo.Player;
import PlayerInfo.GamePlayer;
import UI.BlackJackUINew;
import java.util.HashSet;

/**
 *
 * @author Administrator
 */
public class BlackJackPlayRound {

    private final GamePlayer pPlayer[];
    private final AIPlayer pAI;

    private int nIndex;
    private GamePlayer pCurrentPlayer;
    private final CardDeck cardDeck;
    private int nMoneyOfRound;
    private final int nNumberOfPlayer;

    BlackJackUINew UI;
    BlackJackPlay game;

    public BlackJackPlayRound(GamePlayer[] A, AIPlayer B, CardDeck d, BlackJackUINew ui, BlackJackPlay GAME) {
        pPlayer = A;
        pAI = B;
        cardDeck = d;
        nNumberOfPlayer = A.length;
        UI = ui;
        //pCurrentPlayer = pPlayer;
        game = GAME;
        nMoneyOfRound = 50;
        UI.setBet(nMoneyOfRound);

        for (int i = 0; i < nNumberOfPlayer; i++) {
            pPlayer[i].doDouble(false);
        }
        nIndex = 0;
        pCurrentPlayer = pPlayer[0];
    }

    public GamePlayer getCurrentPlayer() {
        return pCurrentPlayer;
    }

    public AIPlayer getAI() {
        return pAI;
    }

    public void setMoneyOfRound(int nMoney) {
        //Logic
        this.nMoneyOfRound = nMoney;
        //UI
        UI.setBet(nMoney);
    }

    public void PlayerXDouble() {
        pCurrentPlayer.doDouble(true);
    }

    public void PlayerXSurrender() throws InterruptedException {
        pCurrentPlayer.doSurrender(true);
        RoundEndByPlayerX();
    }

    public int getMoneyOfRoundth() {
        return this.nMoneyOfRound;
    }

    public void SendFirstTwoCardsToPllayerX() throws InterruptedException {

        //Logic
        if (nIndex == 0) {
            Card AICard1 = cardDeck.giveTopCardToPlayer(pAI);
            Card AICard2 = cardDeck.giveTopCardToPlayer(pAI);
            UI.SendCardToPosition(true, AICard1, 1, false);
            UI.SendCardToPosition(true, AICard2, 2, true);

        }
        Card playerCard1 = cardDeck.giveTopCardToPlayer(pCurrentPlayer);
        Card playerCard2 = cardDeck.giveTopCardToPlayer(pCurrentPlayer);
        UI.SendCardToPosition(false, playerCard1, 1, true);
        UI.SendCardToPosition(false, playerCard2, 2, true);

        UI.RefreshNumOfPlayerHand();
    }

    public void RoundEndByPlayerX() throws InterruptedException {
        if (nNumberOfPlayer - 1 == nIndex) {
            AIPhase();
        } else {
            nIndex++;
            pCurrentPlayer = pPlayer[nIndex];
            SendFirstTwoCardsToPllayerX();
            UI.RestoreControlOfPlayer();
        }
    }

    public void RoundEndByAI() {

        MoneyAffairs();
        
        UI.TerminateControlOfPlayer();
        UI.AskForNextRound();
        game.PrintLog();

    }

    public void MoneyAffairs() {
        for (GamePlayer player : pPlayer) {
            if (player.AmISurrender()) {
                RoundEndPlayerXLose(player, nMoneyOfRound / 2);
            } else if (BlackJackRule.GetBlackJackResult(player, pAI) > 0) {
                if (player.AmIDouble()) {
                    RoundEndPlayerXLose(player, nMoneyOfRound * 2);
                } else {
                    RoundEndPlayerXLose(player, nMoneyOfRound);
                }
            } else {
                if (player.AmIDouble()) {
                    RoundEndPlayerXWin(player, nMoneyOfRound * 2);
                } else {
                    RoundEndPlayerXWin(player, nMoneyOfRound);
                }
            }
        }
    }

    public void RoundEndPlayerXLose(GamePlayer pEndPlayer, int nMoney) {
        pEndPlayer.LoseMoney(nMoney);
        pAI.EarnMoney(nMoney);

    }

    public void RoundEndPlayerXWin(GamePlayer pEndPlayer, int nMoney) {
        pEndPlayer.EarnMoney(nMoney);
        pAI.LoseMoney(nMoney);
    }

    public void PlayerPhase() {
        PlayerDoublePhase();
        return;
    }

    public void PlayerDoublePhase() {
        if (DecidePlayerDoubleFromAI()) {
            this.nMoneyOfRound = nMoneyOfRound * 2;
        }
    }

    public boolean DecidePlayerDoubleFromAI() {
        return BlackJackRule.GetMaxValueOfHand(pCurrentPlayer.getPlayerCards()) > 14;
    }

    public boolean DecidePlayerDoubleFromUI() {
        return true;
    }

    public Card PlayerHit() {
        Card card = cardDeck.giveTopCardToPlayer(pCurrentPlayer);
        UI.SendCardToPosition(false, card, pCurrentPlayer.getPlayerCards().size(), true);
        UI.RefreshNumOfPlayerHand();

        //TODO player Bust
        if (BlackJackRule.AmIBust(pCurrentPlayer)) {
            ;
        }
        return card;
    }

    public void AIPhase() {

        UI.TerminateControlOfPlayer();
        BJAIMain aiMain = new BJAIMain();

        while (BlackJackRule.GetMaxValueOfHand(pAI.getPlayerCards()) < 17) {

            Card card = cardDeck.giveTopCardToPlayer(pAI);
            UI.SendCardToPosition(true, card, pAI.getPlayerCards().size(), true);

            if (BlackJackRule.AmIBust(pAI)) {
                RoundEndByAI();
                return;
            }
        }
        //A|B Test
        if (game.getNumOfRound() % 2 == 0) {
            while (aiMain.doMakeDecisionLevelSBMultiPlayer(cardDeck, pAI, pPlayer)) {
                cardDeck.giveTopCardToPlayer(pAI);

                if (BlackJackRule.AmIBust(pAI.getPlayerCards())) {
                    //AI Bust by BAD decison
                    RoundEndByAI();
                    return;
                }
            }
        } else {
            while (aiMain.doMakeDecisionLevel1MultiPlayer(cardDeck, pAI, pPlayer)) {
                cardDeck.giveTopCardToPlayer(pAI);

                if (BlackJackRule.AmIBust(pAI.getPlayerCards())) {
                    //AI Bust by BAD decison
                    RoundEndByAI();
                    return;
                }
            }
        }
        //AI survive without Bust
        RoundEndByAI();
    }
}
