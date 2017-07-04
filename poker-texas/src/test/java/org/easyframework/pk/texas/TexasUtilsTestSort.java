package org.easyframework.pk.texas;

import org.easyframework.pk.PokerCard;
import org.junit.Assert;
import org.junit.Test;

public class TexasUtilsTestSort {

	@Test
	public void testSort(){
		int[] cards={1,3,9,2,1,1,1,0,2,2,3,2};
		PokerCard[] pokers=PokerCard.createPokerCardArray(cards);
		int[] expPokers={2,2,3,2,9,2,1,0,1,1,1,3};
		String expString=PokerCard.getPokerCardsName(PokerCard.createPokerCardArray(expPokers));
		PokerCard[] sortPokers=TexasUtils.sortPokerCard(pokers);
		String sortPokersString=PokerCard.getPokerCardsName(sortPokers);
		Assert.assertTrue("排序不相等", expString.equals(sortPokersString));
	}
	@Test
	public void testSort2(){
		int[] cards={1,3,13,3,2,0,1,0,2,2,8,2};
		PokerCard[] pokers=PokerCard.createPokerCardArray(cards);
		int[] expPokers={2,0,2,2,8,2,13,3,1,0,1,3};
		String expString=PokerCard.getPokerCardsName(PokerCard.createPokerCardArray(expPokers));
		PokerCard[] sortPokers=TexasUtils.sortPokerCard(pokers);
		String sortPokersString=PokerCard.getPokerCardsName(sortPokers);
		Assert.assertTrue("排序不相等", expString.equals(sortPokersString));
	}
	@Test
	public void testSort3(){
		int[] cards={1,3,13,3};
		PokerCard[] pokers=PokerCard.createPokerCardArray(cards);
		int[] expPokers={13,3,1,3};
		String expString=PokerCard.getPokerCardsName(PokerCard.createPokerCardArray(expPokers));
		PokerCard[] sortPokers=TexasUtils.sortPokerCard(pokers);
		String sortPokersString=PokerCard.getPokerCardsName(sortPokers);
		Assert.assertTrue("排序不相等", expString.equals(sortPokersString));
	}
}
