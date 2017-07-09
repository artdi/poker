package org.easyframework.pk.texas;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppTest {
	private static Logger log=LoggerFactory.getLogger(AppTest.class);
	@Test
	public void test(){
		int[] pairTwoPokers={2,1,  7,3,  6,3,  1,2,  12,0,  4,3,  12,1};// pair 12
		TexasPokerHand pokerHand15=TexasPokerHandFactory.createByNumArray(pairTwoPokers);
		TexasPokerHand pairTwoPokerHand = TexasUtils.countValue(pokerHand15);
		log.debug("pairTwoPokerHand:"+pairTwoPokerHand.getValue());
	}
}
