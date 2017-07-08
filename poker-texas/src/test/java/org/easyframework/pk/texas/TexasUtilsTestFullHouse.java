package org.easyframework.pk.texas;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月3日 下午10:44:39
 */
public class TexasUtilsTestFullHouse {
	@Test
	public void testThreeAndTwo(){
		int[] testPokers={13,3,13,1,1,2,11,2,5,0,11,3,5,1};
		int[] expectedPokers={11,2,11,3,13,3,13,1,1,2};
		Assert.assertTrue("四条ACE测试错误，高牌不一样", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}
	//@Test
	public void testThreeAndThree(){
		int[] testPokers={13,3,11,1,1,2,11,2,5,0,11,3,5,1};
		int[] expectedPokers={11,1,11,3,11,2,5,1,5,0};
		Assert.assertTrue("四条ACE测试错误，高牌不一样", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}
	//@Test
	public void testThreeAndThree2(){
		int[] testPokers={13,3,1,1,5,2,1,2,5,0,1,3,5,1};
		int[] expectedPokers={1,1,1,3,1,2,5,2,5,0};
		Assert.assertTrue("四条ACE测试错误，高牌不一样", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}
}
