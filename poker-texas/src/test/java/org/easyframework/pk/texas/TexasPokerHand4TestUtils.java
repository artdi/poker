package org.easyframework.pk.texas;

import org.easyframework.pk.PokerCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TexasPokerHand4TestUtils {
	private static Logger log=LoggerFactory.getLogger(TexasPokerHand4TestUtils.class);
	
	public static boolean createPokerHandIsExpecteds(int[] testPokers,int[] expectedPokers){
		TexasPokerHand pokerHand=TexasPokerHandFactory.createByNumArray(testPokers);
		pokerHand=TexasUtils.countValue(pokerHand);
		int[] maxPoints=pokerHand.getMaxPoint();
		PokerCard[] maxCards=new PokerCard[pokerHand.getCardsNum()>5?5:pokerHand.getCardsNum()];
		for(int i=0;i<maxCards.length;i++){
			maxCards[i]=pokerHand.getPokers()[maxPoints[i]];
		}
		PokerCard[] expCards=PokerCard.createPokerCardArray(expectedPokers);
		String maxCardsName=PokerCard.getPokerCardsName(TexasUtils.sortPokerCard(maxCards));
		String expCardsName=PokerCard.getPokerCardsName(TexasUtils.sortPokerCard(expCards));
		log.debug("expCards:"+expCardsName);
		log.debug("maxCards:"+maxCardsName);
		return maxCardsName.equals(expCardsName);
	}

}
