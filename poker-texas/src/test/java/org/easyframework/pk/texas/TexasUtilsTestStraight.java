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
	
	public void testStraight1(){
		int[] testPokers={8,3,7,1,2,3,3,0,4,0,1,3,5,3};
		int[] expectedPokers={8,3,2,3,3,0,4,0,5,3};
		Assert.assertTrue("带ACE顺子测试错误", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	}

	//@Test
	public void testStraightNotAce(){
		int[] testPokers={9,3,7,1,2,3,8,0,10,0,11,3,5,3};
		int[] expectedPokers={8,0,9,3,7,1,10,0,11,3};
		Assert.assertTrue("不带ACE顺子测试错误", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	
	}
	@Test
	public void testStraightHaveAce(){
		int[] testPokers={1,3,7,1,2,3,3,0,4,0,1,3,5,3};
		int[] expectedPokers={1,3,2,3,3,0,4,0,5,3};
		Assert.assertTrue("带ACE顺子测试错误", TexasPokerHand4TestUtils.createPokerHandIsExpecteds(testPokers, expectedPokers));
	
	}
	@Test
	public void testStraightNull(){
		
		int[] testPokers={1,2,2,1,1,3,2,0,2,0,1,3,1,3};
		TexasPokerHand pk=TexasPokerHandFactory.createByNumArray(testPokers);
		Assert.assertFalse("顺子应该为空", pk.isStraight());
	
		
	}
	
}
