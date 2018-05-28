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
	 * 测试奖金分配是否正确
	 */
	public void testAllot(){
		
	}
	/**
	 * 测试局id转换是否正确
	 */
	@Test
	public void testGameId(){
		TexasCroupierConfig config=new TexasCroupierConfig();
		config.setSmallBlinds(5);
		TexasCroupier table=new TexasCroupier(config, new TexasCommandProcessor());
	
		TexasPlayer player1=new TexasPlayer("P1");
		player1.setBankroll(1000);
		TexasPlayer player2=new TexasPlayer("P2");
		player2.setBankroll(1000);
		TexasPlayer player4=new TexasPlayer("P4");
		player4.setBankroll(1000);
		
		table.sitDown(player1, 1);
		table.sitDown(player2, 2);
		TexasTableView view=new TexasTableView(table);
		Assert.assertEquals("当前应为第一局",1,view.getGameId());
		table.sitDown(player4, 4);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为第一局",1,view.getGameId());
		
		table.giveUp(player1);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为第二局",2,view.getGameId());
		table.giveUp(player1);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为第二局",2,view.getGameId());
		table.giveUp(player1);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为第二局",2,view.getGameId());
		table.giveUp(player4);
		view=new TexasTableView(table);
		Assert.assertEquals("玩家1,4,均放弃，游戏结束，开始第三局",3,view.getGameId());
		
		table.standUp(player4);
		table.giveUp(player2);
		view=new TexasTableView(table);
		Assert.assertEquals("玩家4离开,2放弃，游戏结束，开始第四局",4,view.getGameId());
		
		table.standUp(player2);
		view=new TexasTableView(table);
		Assert.assertEquals("玩家2离开,游戏结束，等待第五局开始",4,view.getGameId());
		
		table.sitDown(player4, 3);
		view=new TexasTableView(table);
		Assert.assertEquals("玩家4进入,第五局开始",5,view.getGameId());
		
	}
	/**
	 * 测试发牌轮转是否正确
	 */
	@Test
	public void testSendCardIndex(){
		TexasCroupierConfig config=new TexasCroupierConfig();
		config.setSmallBlinds(5);
		TexasCroupier table=new TexasCroupier(config, new TexasCommandProcessor());
		
		TexasPlayer player1=new TexasPlayer("P1");
		player1.setBankroll(1000);
		TexasPlayer player2=new TexasPlayer("P2");
		player2.setBankroll(1000);
		TexasPlayer player4=new TexasPlayer("P4");
		player4.setBankroll(1000);
		TexasPlayer player5=new TexasPlayer("P5");
		player5.setBankroll(1000);
		table.sitDown(player1, 1);//庄，大盲
		table.sitDown(player2, 2);//小盲，
		table.sitDown(player4, 4);//等待
		table.sitDown(player5, 5);//等待
		
		table.giveUp(player2);
		/*
		 * //TODO 测试发牌轮
		 * 重新开始，
		 *      庄    小盲  大盲
		 * 玩家1,玩家2,玩家4,玩家5,
		 *            5	   10
		 *  10   20   15   20
		 *  20   0    10   30
		 *  30        0/30    10(发牌)
		 *  10             
		 * 1等押注
		 */
		Assert.assertEquals("玩家1下注，下一玩家继续", 1,table.bet(player1, 10));
		
		table.bet(player1, 10);
		table.bet(player2, 20);
		table.bet(player4, 15);
		table.bet(player5, 20);
		table.bet(player1, 20);
		table.giveUp(player2);
		table.bet(player4, 10);
		table.bet(player5, 30);
		table.bet(player1, 30);
		
		TexasTableView view=new TexasTableView(table);
		Assert.assertEquals("玩家4下注前，还是第一轮发牌", 0,view.getSendCardIndex());
		//table.giveUp(player4);
		table.bet(player4, 30);
		view=new TexasTableView(table);
		Assert.assertEquals("玩家4下注后，轮到玩家5，第二轮发牌", 1,view.getSendCardIndex());
		table.bet(player5, 10);
		view=new TexasTableView(table);
		Assert.assertEquals("轮到玩家1", 1,view.getSendCardIndex());
	}
	/**
	 * 测试玩家轮转是否正确
	 */
	@Test
	public void testWaintNo(){
		TexasCroupier table=new TexasCroupier(new TexasCroupierConfig(), new TexasCommandProcessor());
		
		TexasPlayer player1=new TexasPlayer("P1");
		player1.setBankroll(1000);
		TexasPlayer player2=new TexasPlayer("P2");
		player2.setBankroll(1000);
		TexasPlayer player4=new TexasPlayer("P4");
		player4.setBankroll(1000);
		TexasPlayer player5=new TexasPlayer("P5");
		player5.setBankroll(1000);
		table.sitDown(player1, 1);//庄，大盲
		table.sitDown(player2, 2);//小盲，
		
		table.sitDown(player4, 4);
		
		TexasTableView view=new TexasTableView(table);
		Assert.assertEquals("当前应为玩家2下注",2,view.getBetWatingNo());
		
		table.bet(player2, 50);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为玩家1下注",1,view.getBetWatingNo());
		table.bet(player1, 50);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为玩家2下注",2,view.getBetWatingNo());

		table.bet(player2, 50);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为玩家1下注",1,view.getBetWatingNo());
		table.bet(player1, 50);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为玩家2下注",2,view.getBetWatingNo());

		table.bet(player2, 50);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为玩家1下注",1,view.getBetWatingNo());
		table.bet(player1, 50);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为玩家2下注",2,view.getBetWatingNo());

		table.bet(player2, 50);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为玩家1下注",1,view.getBetWatingNo());
		table.bet(player1, 50);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为玩家2下注",2,view.getBetWatingNo());

		table.bet(player2, 50);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为玩家1下注",1,view.getBetWatingNo());
		table.bet(player1, 50);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为玩家2下注",2,view.getBetWatingNo());

		table.bet(player2, 50);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为玩家1下注",1,view.getBetWatingNo());
		table.bet(player1, 50);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为玩家2下注",2,view.getBetWatingNo());

		table.giveUp(player4);
		view=new TexasTableView(table);
		Assert.assertEquals("当前应为玩家2下注",2,view.getBetWatingNo());
		
		//新开局
		table.giveUp(player1);
		
		/*
		 * 新开局
		 * 玩家2为庄家，
		 * 玩家4小盲，
		 * 玩家1大盲
		 * 玩家2等待，
		 */
		view=new TexasTableView(table);
		Assert.assertEquals("玩家2为庄家",2,view.getDealerSeatNo());
		Assert.assertEquals("当前应为玩家2下注",2,view.getBetWatingNo());
		
		
		/*
		 * 新开局
		 * 玩家4为庄家，
		 * 玩家5小盲，
		 * 玩家1大盲
		 * 玩家2等待，
		 */
		table.sitDown(player5, 5);
		table.giveUp(player4);
		table.giveUp(player2);
		view=new TexasTableView(table);
		Assert.assertEquals("玩家4为庄家",4,view.getDealerSeatNo());
		Assert.assertEquals("当前应为玩家2下注",2,view.getBetWatingNo());
		
		TexasPlayer player6=new TexasPlayer("P6");
		player6.setBankroll(1000);
		table.sitDown(player6, 0);
		Assert.assertEquals("玩家6坐下，不影响玩家2下注",2,view.getBetWatingNo());
		
		table.giveUp(player2);
		view=new TexasTableView(table);
		Assert.assertEquals("玩家2放弃，轮到玩家4下注",4,view.getBetWatingNo());
		
		
	}
	//@Test
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
		
		view=new TexasTableView(table);
		Assert.assertEquals("应为第一轮押注",0,view.getSendCardIndex());
		
		Assert.assertEquals(1,table.bet(player1, 10));
		log.debug("玩家1下注成功");
		view=new TexasTableView(table);
		Assert.assertEquals("应为第二轮押注",1,view.getSendCardIndex());
		
		Assert.assertEquals(-1,table.bet(player1, 10));
		log.debug("仅玩家2可下注");
		Assert.assertEquals(-1,table.bet(player4, 10));
		log.debug("仅玩家2可下注");
		Assert.assertEquals(1,table.bet(player2, 10));
		log.debug("玩家2下注成功");
		Assert.assertEquals(1,table.bet(player4, 10));
		log.debug("玩家4下注成功");
		Assert.assertEquals(1,table.bet(player1, 10));
		log.debug("玩家1下注成功");
		
		Assert.assertEquals(1,table.bet(player2, 10));
		
		log.debug("玩家2下注成功");
		
		Assert.assertEquals(1,table.bet(player4, 10));
		log.debug("玩家4下注成功");
		Assert.assertEquals(1,table.bet(player1, 10));
		log.debug("玩家1下注成功");
		
	}

}
