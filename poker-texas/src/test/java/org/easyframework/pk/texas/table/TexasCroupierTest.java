package org.easyframework.pk.texas.table;

import org.easyframework.pk.texas.ITexasTable;
import org.easyframework.pk.texas.ITexasTableView;
import org.easyframework.pk.texas.TexasCroupier;
import org.easyframework.pk.texas.TexasCroupierConfig;
import org.easyframework.pk.texas.TexasPlayer;
import org.easyframework.pk.texas.TexasTable;
import org.easyframework.pk.texas.TexasTableStatus;
import org.easyframework.pk.texas.command.TexasCommandProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TexasCroupierTest {
	private static final Logger log=LoggerFactory.getLogger(TexasCroupierTest.class);
	@Test
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
		Assert.assertTrue("应该有2个玩家在玩",2==table.view().getEffectivePlayer());
		Assert.assertTrue("游戏已开始",TexasTableStatus.waitBet==table.view().getStatusName());
		table.sitDown(player4, 4);
		Assert.assertTrue("应该有2个玩家在玩",2==table.view().getEffectivePlayer());
		Assert.assertTrue("应该有三个玩家坐下",3==table.view().getPlayerNum());
		Assert.assertEquals(2,table.giveUp(player1));
		Assert.assertTrue("玩家2放弃，游戏重新开局",TexasTableStatus.waitBet==table.view().getStatusName());
		Assert.assertTrue("应该有3个玩家在玩",3==table.view().getEffectivePlayer());
		//log.debug("玩家2放弃，游戏重新开局");
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
	//@Test
	public void testBet(){
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
		
		Assert.assertEquals(2,table.giveUp(player1));
		log.debug("玩家2放弃，游戏重新开局");
		Assert.assertEquals(-1,table.bet(player2, 10));
		log.debug("不是玩家2下注");
		
		Assert.assertEquals(-1,table.bet(player4, 10));
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
