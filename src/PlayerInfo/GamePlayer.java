/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PlayerInfo;

import PokerDeck.Card;
import java.util.ArrayList;


/**
 *
 * @author Wei
 */
public class GamePlayer extends Player {

    private String NickName;
    private int Rank;
    private int Credit;
    private int Userid;
    private double Factor1;
    private double Factor2;
    private double Factor3;
    
    public void SetNickName(String nickname){
        this.NickName = nickname;
    }
    
    public String GetNickName(){
        return this.NickName;
    }
    
    public void SetRank(int rank){
        this.Rank = rank;
    }
    
    public int GetRank(){
        return this.Rank;
    }
    
    public void SetCredit(int credit){
        this.Credit = credit;
    }
    
    public int GetCredit(){
        return this.Credit;
    }
    
    public void SetUserId(int userid){
        this.Userid = userid;
    }
    
    public int GetUserId(){
        return this.Userid;
    }
    
    public void SetFactor1(double factor1){
        this.Factor1 = factor1;
    }
    
    public double GetFactor1(){
        return this.Factor1;
    }
     
    public void SetFactor2(double factor2){
        this.Factor1 = factor2;
    }
    
    public double GetFactor2(){
        return this.Factor2;
    }
    
    public void SetFactor3(double factor3){
        this.Factor1 = factor3;
    }
    
    public double GetFactor3(){
        return this.Factor3;
    }
            
    public GamePlayer(double balance, String nickname, int rank, int credit, int userid){
        this.dBalance = balance;
        this.NickName = nickname;
        this.Rank = rank;
        this.Credit = credit;
        this.Userid = userid;
        super.bDouble = false;
        super.bSurrender = false;
        super.nNumCards = 0;
        super.CardArray = new ArrayList<Card>();
    }
}
