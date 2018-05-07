package org.easyframework.pk.texas;

import org.easyframework.pk.texas.Jackpot;
import org.junit.Assert;
import org.junit.Test;

/**
 * 下注组件测试
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年9月10日 下午6:19:47
 */
public class JackpotTest {

	
	public static boolean  isEqArray(long[] a,long[] b){
		if(a.length==b.length){
			for(int i=0;i<a.length;i++){
				if(a[i]!=b[i]){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	@Test
	public void testGetBets(){
		Jackpot jackpot=getJackpot(0);
		
		long[] expected0={0,10,10,5,10};
		Assert.assertTrue("坐号3下注额应为5", isEqArray(jackpot.getBets(),expected0));
		
		jackpot=getJackpot(1);
		long[] expected1={0,10,30,10,25};
		Assert.assertTrue("坐号3下注额应为5", isEqArray(jackpot.getBets(),expected1));
		
		jackpot=getJackpot(2);
		long[] expected2={0,10,35,35,35};
		Assert.assertTrue("坐号3下注额应为5", isEqArray(jackpot.getBets(),expected2));
		
		jackpot=getJackpot(5);
		long[] expected5={0,10,65,70,45};
		long[] bets=jackpot.getBets();
		Assert.assertTrue("本轮下注{0,10,65,70,45}", isEqArray(bets,expected5));
	}
	@Test
	public void testAllocation(){
		Jackpot jackpot=getJackpot(5);
		//{0,10,65,70,45}
		long[] expected1={0,0,0,190,0};
		int[] orders1={0,0,0,1,0};
		long[] result1=jackpot.allocation(orders1);
		Assert.assertTrue("本轮下注{0,10,65,70,45}", isEqArray(result1,expected1));
		
		//一样分配
		jackpot = getJackpot(5);
		long[] expected2 = { 0, 10, 65, 70, 45 };
		int[] orders2 = { 1, 1, 1, 1, 1 };
		long[] result2 = jackpot.allocation(orders2);
		Assert.assertTrue("本轮下注{0,10,65,70,45}", isEqArray(result2, expected2));
		
		//一样分配{ 0, 10, 65, 70, 45 };m  10 10 10 10  20,20 35 35,35 25+20
		jackpot = getJackpot(5);
		long[] expected4 = { 0, 20, 0, 45, 125 };
		int[]    orders4 = { 0, 1,  3,  2,  1 };
		long[] result4 = jackpot.allocation(orders4);
		Assert.assertTrue("本轮下注{0, 20, 0, 45, 125 }", isEqArray(result4, expected4));

		//不能整除时，四舍5入会少掉1个单位
		jackpot=getJackpot(5);
		long[] expected3={0,33,88,0,68};
		int[] orders3={0,1,1,0,1};
		long[] result3=jackpot.allocation(orders3);
		Assert.assertTrue("本轮下注{0,33,88,0,68}", isEqArray(result3,expected3));
	}
	
	/**
	 * 5位家测试用例
	 * <br>第0行：3:5, 4:10, 0:0, 1:10,2:10,
	 * <br>第1行：3:5, 4:15, 0:0, 1:0, 2:20,
	 * <br>第2行：3:25,4:10, 0:0, 1:0, 2:5,
	 * 
	 * <br>第3行：3:5, 4:10, 0:0, 1:0, 2:10,
	 * <br>第4行：3:5, 4:0,  0:0, 1:0, 2:20,
	 * <br>第5行：3:25,4:0,  0:0, 1:0, 2:0,
	 * @return
	 */
	private static Jackpot getJackpot(int n){
		Jackpot j=new Jackpot(5);
		j.addBet(3, 0, 5);
		j.addBet(4, 0, 10);
		j.addBet(0, 0, 0);
		j.addBet(1, 0, 10);
		j.addBet(2, 0, 10);
		
		if(n==0)return j;
		
		j.addBet(3, 0, 5);
		j.addBet(4, 0, 15);
		j.addBet(0, 0, 0);
		j.addBet(1, 0, 0);
		j.addBet(2, 0, 20);
		if(n==1)return j;
		j.addBet(3, 0, 25);
		j.addBet(4, 0, 10);
		j.addBet(0, 0, 0);
		j.addBet(1, 0, 0);
		j.addBet(2, 0, 5);
		if(n==2)return j;
		
		j.addBet(3, 1, 5);
		j.addBet(4, 1, 10);
		j.addBet(0, 1, 0);
		j.addBet(1, 1, 0);
		j.addBet(2, 1, 10);
		
		if(n==3)return j;
		
		j.addBet(3, 2, 5);
		j.addBet(4, 2, 0);
		j.addBet(0, 2, 0);
		j.addBet(1, 2, 0);
		j.addBet(2, 2, 20);
		if(n==4)return j;
		j.addBet(3, 3, 25);
		j.addBet(4, 3, 0);
		j.addBet(0, 3, 0);
		j.addBet(1, 3, 0);
		j.addBet(2, 3, 0);
		if(n==5)return j;
		
		return j;
	}   
	@Test
	public void testMaxBetSeatNo(){
		Jackpot jackpot=getJackpot(-3);
		int[] seatNos={0,1,2,3,4};
		Assert.assertEquals("本轮下注最大为3", 3, jackpot.getMaxBetSeatNo(0, seatNos));
		
		jackpot=getJackpot(0);
		int[] seatNos1={0,1,2,3,4};
		Assert.assertEquals("本轮下注最大为4", 4, jackpot.getMaxBetSeatNo(0, seatNos1));
		
		jackpot=getJackpot(1);
		int[] seatNos2={0,1,2,3,4};
		Assert.assertEquals("本轮下注最大为2", 2, jackpot.getMaxBetSeatNo(0, seatNos2));
		int[] seatNos3={0,1,3,2};
		Assert.assertEquals("本轮下注最大为2", 2, jackpot.getMaxBetSeatNo(0, seatNos3));
		
		jackpot=getJackpot(5);
		int[] seatNos4={0,1,2};
		Assert.assertEquals("本轮下注最大为0", 0, jackpot.getMaxBetSeatNo(3, seatNos4));
		int[] seatNos5={0,1,2,3,4};
		Assert.assertEquals("本轮下注最大为3", 3, jackpot.getMaxBetSeatNo(3, seatNos5));
		int[] seatNos6={0,1};
		Assert.assertEquals("本轮下注最大为0", 0, jackpot.getMaxBetSeatNo(3, seatNos6));
		
	}
}
