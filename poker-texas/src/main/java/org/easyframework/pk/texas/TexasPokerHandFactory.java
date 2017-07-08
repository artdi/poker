package org.easyframework.pk.texas;

import org.easyframework.pk.PokerCard;
import org.easyframework.pk.texas.exception.TexasException;

public class TexasPokerHandFactory {
	
	public static TexasPokerHand createByNumArray(int[] pokersNumbers){
		
		TexasPokerHand pokerHand=new TexasPokerHand();
		PokerCard[] pokers=PokerCard.createPokerCardArray(pokersNumbers);
		
		if(pokers.length>7||pokers.length<2){
			throw new TexasException(101,"玩家牌数量不对",null);
		}
		if(pokers.length>1){
			PokerCard[] holdPokers={pokers[0],pokers[1]};
			pokerHand.addPriPokers(holdPokers);
		}
		if(pokers.length>4){
			PokerCard[] flop={pokers[2],pokers[3],pokers[4]};
			pokerHand.addFlop(flop);
		}
		if(pokers.length>5){
			pokerHand.addTurn(pokers[5]);
		}
		if(pokers.length>6){
			pokerHand.addRiver(pokers[6]);
		}
		
		return pokerHand;
	}

}
