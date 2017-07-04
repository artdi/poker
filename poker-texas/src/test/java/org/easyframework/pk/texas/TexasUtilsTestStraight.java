package org.easyframework.pk.texas;

import org.easyframework.pk.PokerCard;
import org.junit.Assert;
import org.junit.Test;

/**
 * 顺子测试用例
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月3日 下午10:40:53
 */
public class TexasUtilsTestStraight {

	@Test
	public void testStraightNotAce(){
		
		TexasPokerHand poker=new TexasPokerHand();
		PokerCard[] priPokers={new PokerCard(9,3),new PokerCard(7,1)};
		poker.addPriPokers(priPokers);
		PokerCard[] flopPokers={new PokerCard(2,3),new PokerCard(8,0),new PokerCard(10,0)};
		poker.addFlop(flopPokers);
		poker.addTurn(new PokerCard(11,3));
		poker.addRiver(new PokerCard(5,3));
		
		TexasPokerHandPoint newPoker=TexasUtils.findStraight(poker.getPokers());
		Assert.assertNotNull(newPoker);
		//int[] exp={0,2,3,4,6};
		//Assert.assertArrayEquals(exp, newPoker.getMaxPoint());
		
		
	}
	@Test
	public void testStraightHaveAce(){
		
		TexasPokerHand poker=new TexasPokerHand();
		PokerCard[] priPokers={new PokerCard(1,3),new PokerCard(7,1)};
		poker.addPriPokers(priPokers);
		PokerCard[] flopPokers={new PokerCard(2,3),new PokerCard(3,0),new PokerCard(4,0)};
		poker.addFlop(flopPokers);
		poker.addTurn(new PokerCard(1,3));
		poker.addRiver(new PokerCard(5,3));
		
		TexasPokerHandPoint newPoker=TexasUtils.findStraight(poker.getPokers());
		Assert.assertNotNull(newPoker);
		//int[] exp={0,2,3,4,6};
		//Assert.assertArrayEquals(exp, newPoker.getMaxPoint());
		Assert.assertTrue(62==newPoker.getWeight());
		
		
	}
	@Test
	public void testStraightNull(){
		
		TexasPokerHand poker=new TexasPokerHand();
		PokerCard[] priPokers={new PokerCard(1,3),new PokerCard(2,1)};
		poker.addPriPokers(priPokers);
		PokerCard[] flopPokers={new PokerCard(1,3),new PokerCard(2,0),new PokerCard(2,0)};
		poker.addFlop(flopPokers);
		poker.addTurn(new PokerCard(1,3));
		poker.addRiver(new PokerCard(1,3));
		Assert.assertNull(TexasUtils.findStraight(poker.getPokers()));
		
	}
	
}
