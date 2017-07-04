package org.easyframework.pk.texas;

import org.easyframework.pk.PokerCard;
import org.junit.Test;

public class TexasCroupierBaseTest {
	@Test
	public void testShuffle(){
		PokerCard[] cards=PokerCard.create52PokerCard();
		cards=new TexasCroupierBase().shuffle(cards);
		for(PokerCard card:cards){
			System.out.println(card.toString());
		}
		
	}
}
