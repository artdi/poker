package org.easyframework.pk.texas;

import org.junit.Assert;
import org.junit.Test;

public class TexasUtilsTestTwoPair {

	@Test
	public void testTwoAndTwo2(){
		int[] testPokers={1,3,13,1,1,2,11,2,5,0,11,3,5,1};
		int[] expectedPokers={11,2,11,3,1,3,13,1,1,2};
		Assert.assertTrue("两对测试错误，高牌不一样", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}
	@Test
	public void testTwoAndTwo(){
		int[] testPokers={13,3,13,1,1,2,11,2,5,0,11,3,5,1};
		int[] expectedPokers={11,2,11,3,13,3,13,1,1,2};
		Assert.assertTrue("两对测试错误，高牌不一样", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}
}
