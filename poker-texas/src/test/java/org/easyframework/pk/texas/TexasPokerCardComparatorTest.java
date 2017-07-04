package org.easyframework.pk.texas;

import org.easyframework.pk.PokerCard;
import org.junit.Assert;
import org.junit.Test;


public class TexasPokerCardComparatorTest {

	@Test
	public void testComparator(){
		TexasPokerCardComparator c=new TexasPokerCardComparator();
		Assert.assertEquals(-1,c.compare(new PokerCard(3, 0), new PokerCard(1, 0)));
		Assert.assertEquals(0,c.compare(new PokerCard(1, 0), new PokerCard(1, 0)));
		Assert.assertEquals(1,c.compare(new PokerCard(1, 0), new PokerCard(10, 0)));
		
		Assert.assertEquals(1,c.compare(new PokerCard(1, 3), new PokerCard(1, 0)));
		Assert.assertEquals(0,c.compare(new PokerCard(1, 0), new PokerCard(1, 0)));
		Assert.assertEquals(-1,c.compare(new PokerCard(1, 1), new PokerCard(1, 2)));
		
		Assert.assertEquals(1,c.compare(new PokerCard(1, 3), new PokerCard(13, 0)));
		Assert.assertEquals(-1,c.compare(new PokerCard(13, 3), new PokerCard(1, 0)));
		Assert.assertEquals(1,c.compare(new PokerCard(13, 3), new PokerCard(13, 0)));
		
	}
}
