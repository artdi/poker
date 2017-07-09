package org.easyframework.pk.texas;



import org.easyframework.pk.PokerCard;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TexasUtilsTest {
	private static Logger log=LoggerFactory.getLogger(TexasUtilsTest.class);

	@Test
	public void testCountValue(){
		int[] maxFlushStraightPokers={3,3,2,3,1,3,13,3,12,3,10,3,11,3};//皇家同花顺
		TexasPokerHand pokerHand=TexasPokerHandFactory.createByNumArray(maxFlushStraightPokers);
		TexasPokerHand maxFlushStraightPokerHand = TexasUtils.countValue(pokerHand);
		log.debug(PokerCard.getPokerCardsName(maxFlushStraightPokerHand.getMaxPokers()));
		
		int[] flushStraightPokers={3,3,2,3,1,3,13,3,12,3,4,3,5,3};//同花顺
		TexasPokerHand pokerHand2=TexasPokerHandFactory.createByNumArray(flushStraightPokers);
		TexasPokerHand flushStraightPokerHand = TexasUtils.countValue(pokerHand2);
		
		Assert.assertTrue(maxFlushStraightPokerHand.getValue()>flushStraightPokerHand.getValue());
		
		
		int[] fourAcePokers={3,1,2,3,1,3,1,2,1,0,4,3,1,1};//四条
		TexasPokerHand pokerHand5=TexasPokerHandFactory.createByNumArray(fourAcePokers);
		TexasPokerHand fourAcePokerHand = TexasUtils.countValue(pokerHand5);
		int[] fourPokers={3,1,3,3,3,0,13,3,12,3,3,2,5,3};//四条
		TexasPokerHand pokerHand6=TexasPokerHandFactory.createByNumArray(fourPokers);
		TexasPokerHand fourPokerHand = TexasUtils.countValue(pokerHand6);
		
		Assert.assertTrue(flushStraightPokerHand.getValue()>fourAcePokerHand.getValue());
		Assert.assertTrue(flushStraightPokerHand.getValue()>fourPokerHand.getValue());
		Assert.assertTrue(fourAcePokerHand.getValue()>fourPokerHand.getValue());
		
		int[] fullHouseAcePokers={3,1,3,3,1,3,1,2,1,0,4,3,11,1};//葫芦Ａ
		TexasPokerHand pokerHand7=TexasPokerHandFactory.createByNumArray(fullHouseAcePokers);
		TexasPokerHand fullHouseAcePokerHand = TexasUtils.countValue(pokerHand7);
		int[] fullHousePokers={3,1,3,3,3,0,13,2,11,0,4,3,11,1};//葫芦
		TexasPokerHand pokerHand8=TexasPokerHandFactory.createByNumArray(fullHousePokers);
		TexasPokerHand fullHousePokerHand = TexasUtils.countValue(pokerHand8);
		
		Assert.assertTrue(fourPokerHand.getValue()>fullHouseAcePokerHand.getValue());
		Assert.assertTrue(fullHouseAcePokerHand.getValue()>fullHousePokerHand.getValue());
		
		int[] flushPokers={3,0,2,3,1,3,13,3,12,3,4,3,5,3};//同花
		TexasPokerHand pokerHand3=TexasPokerHandFactory.createByNumArray(flushPokers);
		TexasPokerHand flushPokerHand = TexasUtils.countValue(pokerHand3);
		
		Assert.assertTrue(fullHousePokerHand.getValue()>flushPokerHand.getValue());
		Assert.assertTrue(flushStraightPokerHand.getValue()>flushPokerHand.getValue());
		
		int[] straightPokers={3,1,2,3,1,3,13,3,12,3,4,3,5,3};//顺子
		TexasPokerHand pokerHand4=TexasPokerHandFactory.createByNumArray(straightPokers);
		TexasPokerHand straightPokerHand = TexasUtils.countValue(pokerHand4);
		
		Assert.assertTrue(fullHousePokerHand.getValue()>straightPokerHand.getValue());
		
		int[] threePokers={3,1,6,3,1,3,1,2,1,0,4,3,11,1};//three Ace
		TexasPokerHand pokerHand9=TexasPokerHandFactory.createByNumArray(threePokers);
		TexasPokerHand threeAcePokerHand = TexasUtils.countValue(pokerHand9);
		int[] threeKingPokers={3,1,6,3,13,3,13,2,13,0,4,3,1,1};//three king
		TexasPokerHand pokerHand10=TexasPokerHandFactory.createByNumArray(threeKingPokers);
		TexasPokerHand threeKingPokerHand = TexasUtils.countValue(pokerHand10);
		int[] threePokers2={2,1,  2,3,  1,3,  10,2,  12,0,  4,3,  2,0};//three 2
		TexasPokerHand pokerHand11=TexasPokerHandFactory.createByNumArray(threePokers2);
		TexasPokerHand threeTwoPokerHand = TexasUtils.countValue(pokerHand11);
		
		Assert.assertTrue(straightPokerHand.getValue()>threeAcePokerHand.getValue());
		Assert.assertTrue(threeAcePokerHand.getValue()>threeKingPokerHand.getValue());
		Assert.assertTrue(threeKingPokerHand.getValue()>threeTwoPokerHand.getValue());
		
		int[] twoPairPokers={2,1,  2,3,  1,3,  1,2,  12,0,  4,3,  12,1};//two pair 
		TexasPokerHand pokerHand12=TexasPokerHandFactory.createByNumArray(twoPairPokers);
		TexasPokerHand twoPairPokerHand = TexasUtils.countValue(pokerHand12);
		
		int[] twoPairAcePokers={13,1,  2,3,  1,3,  1,2,  12,0,  4,3,  12,1};//two Ace 
		TexasPokerHand pokerHand13=TexasPokerHandFactory.createByNumArray(twoPairAcePokers);
		TexasPokerHand twoPairAcePokerHand = TexasUtils.countValue(pokerHand13);
		
		int[] twoPairTwoPokers={2,1,  2,3,  1,3,  4,2,  12,0,  4,3,  12,1};//two pair 
		TexasPokerHand pokerHand14=TexasPokerHandFactory.createByNumArray(twoPairTwoPokers);
		TexasPokerHand twoPairTwoPokerHand = TexasUtils.countValue(pokerHand14);
		
		Assert.assertTrue(threeTwoPokerHand.getValue()>twoPairAcePokerHand.getValue());
		Assert.assertTrue(twoPairAcePokerHand.getValue()>twoPairPokerHand.getValue());
		Assert.assertTrue(twoPairPokerHand.getValue()>twoPairTwoPokerHand.getValue());
		

		int[] pairAcePokers={7,1,  2,3,  1,3,  1,2,  12,0,  4,3,  13,1};// pair Ace
		TexasPokerHand pokerHand16=TexasPokerHandFactory.createByNumArray(pairAcePokers);
		TexasPokerHand pairAcePokerHand = TexasUtils.countValue(pokerHand16);
		
		int[] pairTwoPokers={2,1,  7,3,  6,3,  1,2,  12,0,  4,3,  12,1};// pair 12
		TexasPokerHand pokerHand15=TexasPokerHandFactory.createByNumArray(pairTwoPokers);
		TexasPokerHand pairTwoPokerHand = TexasUtils.countValue(pokerHand15);
		
		
		int[] singlePokers={7,1,  2,3,  1,3,  6,2,  12,0,  4,3,  13,1};// single
		TexasPokerHand pokerHand17=TexasPokerHandFactory.createByNumArray(singlePokers);
		TexasPokerHand singleHand = TexasUtils.countValue(pokerHand17);
		
		int[] singlePokers2={7,1,  2,3,  10,3,  9,2,  12,0,  4,3,  13,1};// single
		TexasPokerHand pokerHand18=TexasPokerHandFactory.createByNumArray(singlePokers2);
		TexasPokerHand singleHand2 = TexasUtils.countValue(pokerHand18);
		
		log.debug(PokerCard.getPokerCardsName(twoPairTwoPokerHand.getMaxPokers()));
		log.debug("pairTwoPokerHand.getValue():"+Long.toBinaryString(pairTwoPokerHand.getValue()));
		log.debug("singleHand.getValue():"+Long.toBinaryString(singleHand.getValue()));
		
		Assert.assertTrue(twoPairTwoPokerHand.getValue()>pairAcePokerHand.getValue());
		Assert.assertTrue(pairAcePokerHand.getValue()>pairTwoPokerHand.getValue());
		Assert.assertTrue(pairTwoPokerHand.getValue()>singleHand.getValue());
		Assert.assertTrue(singleHand.getValue()>singleHand2.getValue());
		
	}
}
