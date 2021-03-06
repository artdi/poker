package org.easyframework.pk.texas;

import org.easyframework.pk.PokerCard;
import org.junit.Assert;
import org.junit.Test;

/**
 * 同花测试用例
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月3日 下午10:40:10
 */
public class TexasUtilsTestFlush {

	@Test
	public void test5(){
		int[] testPokers={11,2,9,3,12,3,11,3,11,0,10,3,13,3};
		int[] expectedPokers={13,3,12,3,11,3,9,3,10,3};
		Assert.assertTrue("同花中有三条", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}
	@Test
	public void test4(){
		int[] testPokers={1,2,9,3,12,3,11,3,10,0,10,3,13,3};
		int[] expectedPokers={13,3,12,3,11,3,9,3,10,3};
		Assert.assertTrue("同花中有顺子", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}
	@Test
	public void test3(){
		int[] testPokers={1,3,9,3,12,0,11,3,10,0,10,3,13,3};
		int[] expectedPokers={13,3,1,3,11,3,9,3,10,3};
		Assert.assertTrue("同花中无顺子", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}
	@Test
	public void test(){
		int[] testPokers={1,3,9,3,12,3,11,3,10,0,10,3,13,3};
		int[] expectedPokers={13,3,1,3,11,3,12,3,10,3};
		Assert.assertTrue("同花中有顺子", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}
	//@Test
	public void test2(){
		TexasPokerHand poker=new TexasPokerHand();
		PokerCard[] priPokers={new PokerCard(1,3),new PokerCard(2,3)};
		poker.addPriPokers(priPokers);
		PokerCard[] flopPokers={new PokerCard(3,3),new PokerCard(9,3),new PokerCard(5,3)};
		poker.addFlop(flopPokers);
		poker.addTurn(new PokerCard(6,3));
		poker.addRiver(new PokerCard(7,3));
		TexasPokerHand newPoker=TexasUtils.countValue(poker);
		String sortPokersString=PokerCard.getPokerCardsName(newPoker.getPokers());
		Assert.assertTrue("不相等", false);
	}
}
