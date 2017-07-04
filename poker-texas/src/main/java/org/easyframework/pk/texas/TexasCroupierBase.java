package org.easyframework.pk.texas;

import java.util.Random;

import org.easyframework.pk.PokerCard;

public class TexasCroupierBase {
	/**
	 * 洗牌
	 */
	public PokerCard[] shuffle(PokerCard[] cards){
		Random random=new Random();
		PokerCard[] newCards=new PokerCard[cards.length];
		for(int i=0;i<cards.length;i++){
			newCards[i]=cards[i];
		}
		PokerCard temp=null;
		for(int i=0;i<newCards.length;i++){
			int index=Math.abs(random.nextInt())%newCards.length;
			temp=newCards[i];
			newCards[i]=newCards[index];
			newCards[index]=temp;
		}
		
		return newCards;
		
	}
	
	/**
	 * 发牌
	 */
	public void dealing(){
		
	}
	/**
	 * 计算押注奖池情况
	 */
	public void countBet(){
		
	}
	/**
	 * 确定庄家及大小盲角色。
	 */
	public void countBlinds(){
		
	}

}
