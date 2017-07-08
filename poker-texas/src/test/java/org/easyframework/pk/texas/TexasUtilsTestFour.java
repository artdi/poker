package org.easyframework.pk.texas;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月3日 下午10:42:43
 */
public class TexasUtilsTestFour {
	@Test
	public void testFourAndThree3(){
		int[] testPokers={5,3,13,1,5,2,13,2,5,0,13,3,5,1};
		int[] expectedPokers={13,1,5,1,5,2,5,3,5,0};
		Assert.assertTrue("四条ACE测试错误，高牌不一样", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}
	@Test
	public void testFourAndThree2(){
		int[] testPokers={5,3,1,1,5,2,1,2,5,0,1,3,5,1};
		int[] expectedPokers={1,1,5,1,5,2,5,3,5,0};
		Assert.assertTrue("四条ACE测试错误，高牌不一样", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}
	@Test
	public void testFourAndThree(){
		int[] testPokers={5,3,1,1,5,2,1,2,5,0,1,3,1,0};
		int[] expectedPokers={1,3,1,1,1,2,5,3,1,0};
		Assert.assertTrue("四条ACE测试错误，高牌不一样", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}
	@Test
	public void testFourAce2(){
		int[] testPokers={2,0,1,1,5,2,1,2,5,0,1,3,1,0};
		int[] expectedPokers={1,3,1,1,1,2,5,2,1,0};
		Assert.assertTrue("四条ACE测试错误，高牌不一样", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}
	@Test
	public void testFourAce(){
		int[] testPokers={2,0,1,1,5,0,1,2,2,2,1,3,1,0};
		int[] expectedPokers={1,3,1,1,1,2,5,0,1,0};
		Assert.assertTrue("四条ACE测试错误", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}
	@Test
	public void testFourHaveAce(){
		int[] testPokers={2,0,4,0,5,0,2,1,2,2,2,3,1,0};
		int[] expectedPokers={2,0,2,1,2,2,2,3,1,0};
		Assert.assertTrue("四条有ACE测试错误", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}
	@Test
	public void testFourSmall(){
		int[] testPokers={2,0,4,0,5,0,2,1,2,2,2,3};
		int[] expectedPokers={2,0,2,1,2,2,2,3,5,0};
		Assert.assertTrue("四条2，测试错误", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}
	

}
