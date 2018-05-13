package org.easyframework.pk.texas;

import org.easyframework.pk.texas.ITexasTable;
import org.easyframework.pk.texas.ITexasTableView;
import org.easyframework.pk.texas.TexasCroupier;
import org.easyframework.pk.texas.TexasCroupierConfig;
import org.easyframework.pk.texas.TexasPlayer;
import org.easyframework.pk.texas.TexasTable;
import org.easyframework.pk.texas.TexasTableStatus;
import org.easyframework.pk.texas.TexasTableView;
import org.easyframework.pk.texas.command.TexasCommandProcessor;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import  org.easyframework.pk.texas.JackpotTest.*;

public class TexasCroupierTest {
	private static final Logger log=LoggerFactory.getLogger(TexasCroupierTest.class);
	
	/**
	 * //TODO 测试下注后，是否正确发牌
	 */
	//@Test
	public void testBealing(){
		ITexasTable table=new TexasCroupier(new TexasCroupierConfig(), new TexasCommandProcessor());
	
		TexasPlayer player1=new TexasPlayer("P1");
		player1.setBankroll(1000);
		TexasPlayer player2=new TexasPlayer("P2");
		player2.setBankroll(1000);
		TexasPlayer player4=new TexasPlayer("P4");
		player4.setBankroll(1500);
		table.sitDown(player1, 1);
		table.sitDown(player2, 2);
		
		table.sitDown(player4, 4);
		table.giveUp(player1);//玩家1放弃，重新开始
		Assert.assertTrue("应该有3个玩家在玩",3==table.view().getEffectivePlayer());

		
		
		Assert.assertEquals(1,table.bet(player1, 10));//庄家
		log.debug("玩家1下注成功");
		Assert.assertEquals(1,table.bet(player2, 10));//小盲
		log.debug("玩家2下注成功");
		Assert.assertEquals(1,table.bet(player4, 11));//大盲
		log.debug("玩家4加注成功");
		
		Assert.assertEquals(1,table.bet(player1, 10));
		log.debug("玩家1下注成功");
		Assert.assertEquals(1,table.bet(player2, 10));
		log.debug("玩家2下注成功");
		Assert.assertEquals(1,table.bet(player4, 12));
		log.debug("玩家4加注成功");
		table.view();
		Assert.assertEquals(1,table.bet(player1, 10));
		log.debug("玩家1下注成功");
		Assert.assertEquals(1,table.bet(player2, 10));
		log.debug("玩家2下注成功");
		Assert.assertEquals(1,table.bet(player4, 12));
		log.debug("玩家4加注成功");
		table.view();
		Assert.assertEquals(1,table.bet(player1, 10));
		log.debug("玩家1下注成功");
		Assert.assertEquals(1,table.bet(player2, 10));
		log.debug("玩家2下注成功");
		Assert.assertEquals(1,table.bet(player4, 12));
		log.debug("玩家4加注成功");
		table.view();
		
		
	}
	@Test
	public void testBet(){
		TexasCroupierConfig config=new TexasCroupierConfig();
		config.setSmallBlinds(5);
		TexasCroupier table=new TexasCroupier(config, new TexasCommandProcessor());
	
		TexasPlayer player1=new TexasPlayer("P1");
		player1.setBankroll(1000);
		TexasPlayer player2=new TexasPlayer("P2");
		player2.setBankroll(1000);
		
		TexasPlayer player4=new TexasPlayer("P4");
		player4.setBankroll(1500);
		table.sitDown(player1, 1);
		table.sitDown(player2, 2);
		log.debug("1､2坐号坐下，游戏已开始，最近0坐位号的为庄家，1为庄，2为小盲，1为大盲，轮到2表态");
		TexasTableView view=new TexasTableView(table);
		long[] result=view.getBets(0);
		long[] exp={0,10,5,0,0,0};
		Assert.assertTrue("下注后结果错误", JackpotTest.isEqArray(exp, result));
		result=view.getPlayerBankroll();
		exp[1]=990;
		exp[2]=995;
		Assert.assertTrue("下注后结果错误", JackpotTest.isEqArray(exp, result));
		
		table.sitDown(player4, 4);
		view=new TexasTableView(table);
		
		Assert.assertEquals("玩家2放弃，游戏重新开局",2,table.giveUp(player2));
		log.debug("重新开局,庄家为2,小盲为4,大盲为1,当前轮到2下注");
		view=new TexasTableView(table);
		Assert.assertEquals("当前轮到2下注",2,view.getBetWatingNo());
		Assert.assertEquals("新一局游戏id加1",2,view.getGameId());
		result=view.getBets();
		exp[1]=10;
		exp[2]=0;
		exp[4]=5;
		Assert.assertTrue("从新开始，下注额均为0", JackpotTest.isEqArray(exp, result));
		result=view.getBets(0);
		Assert.assertTrue("从新开始，下注额均为0", JackpotTest.isEqArray(exp, result));
		result=view.getPlayerBankroll();
		exp[1]=995;//990+15 - 10= 995
		exp[2]=995;//995(上一局结果)
		exp[4]=1495;//1500-5=1495
		Assert.assertTrue("从新开始，上一局分配结果不对", JackpotTest.isEqArray(exp, result));
		log.debug("是玩家2下注游戏继续");
		Assert.assertEquals(1,table.bet(player2, 10));
		
		
		Assert.assertEquals(1,table.bet(player4, 10));
		Assert.assertEquals(-2,table.bet(player1, 100000));
		
		Assert.assertEquals(1,table.bet(player1, 10));
		log.debug("玩家1下注成功");
		Assert.assertEquals(1,table.bet(player2, 10));
		log.debug("玩家2下注成功");
		Assert.assertEquals(1,table.bet(player4, 10));
		log.debug("玩家4下注成功");
		
		Assert.assertEquals(1,table.bet(player1, 10));
		log.debug("玩家1下注成功");
		Assert.assertEquals(-1,table.bet(player1, 10));
		log.debug("仅玩家2可下注");
		Assert.assertEquals(-1,table.bet(player4, 10));
		log.debug("仅玩家2可下注");
		Assert.assertEquals(1,table.bet(player2, 10));
		log.debug("玩家2下注成功");
		Assert.assertEquals(1,table.bet(player4, 10));
		log.debug("玩家4下注成功");
		
	}

}
