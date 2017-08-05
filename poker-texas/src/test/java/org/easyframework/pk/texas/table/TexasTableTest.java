package org.easyframework.pk.texas.table;

import org.easyframework.pk.texas.ITexasTable;
import org.easyframework.pk.texas.TexasCroupierConfig;
import org.easyframework.pk.texas.TexasPlayer;
import org.easyframework.pk.texas.TexasTable;
import org.easyframework.pk.texas.command.TexasCommandProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TexasTableTest {
	public final static Logger log=LoggerFactory.getLogger(TexasTableTest.class);
	/**
	 * 正确坐到相应坐位，如果坐位不为空，用户已在其它坐位，坐为编号不对，用户进入资金不足
	 */
	@Test
	public void testSeatDown(){
		ITexasTable table=new TexasTable(new TexasCroupierConfig(), new TexasCommandProcessor());
	
		TexasPlayer player1=new TexasPlayer("P1");
		player1.setBankroll(1000);
		TexasPlayer player2=new TexasPlayer("P2");
		player2.setBankroll(1000);
		TexasPlayer player3=new TexasPlayer("P3");
		player3.setBankroll(1);
		TexasPlayer player4=new TexasPlayer("P4");
		player4.setBankroll(1500);
		
		Assert.assertEquals(2,table.sitDown(player1, 1));
		log.debug("玩家1坐下后，等待开始游戏（人数不够)");
		Assert.assertEquals(-3,table.sitDown(player1, 1));
		log.debug("玩家1重复坐失败");
		Assert.assertEquals(-2,table.sitDown(player2, 1));
		log.debug("坐位2重复坐失败");
		Assert.assertEquals(-1,table.sitDown(player3, 3));
		log.debug("玩家3奖金不够坐失败");
		Assert.assertEquals(1,table.sitDown(player2, 2));
		log.debug("玩家2成功坐下后，开始游戏");
		Assert.assertEquals(-4,table.sitDown(player4, 40));
		log.debug("坐号有误");
		Assert.assertEquals(2,table.sitDown(player4, 4));
		log.debug("玩家4成功坐下后，等待开始游戏（待上一局游戏结束)");
		
		Assert.assertEquals(1,table.standUp(player4));
		log.debug("玩家4站起，不影响游戏");
		Assert.assertEquals(2,table.standUp(player1));
		log.debug("玩家1站起，结束游戏，只剩玩家2");
		
		Assert.assertEquals(1,table.sitDown(player1, 1));
		log.debug("重新坐下后，玩家1玩家2开始游戏");
		
		Assert.assertEquals(2,table.giveUp(player1));
		log.debug("两个玩家时，放弃后，结束本局，再开始一局");
		
		Assert.assertEquals(2,table.sitDown(player4, 4));
		log.debug("玩家4成功坐下（玩家1玩家2玩家4），等待开始游戏（待上一局游戏结束)");
		
		Assert.assertEquals(2,table.giveUp(player1));
		log.debug("玩家1､玩家2在玩，玩家4在等，玩家1放弃后，结束本局，再开始一局，则应为3个玩家同时玩");
		Assert.assertEquals(1,table.giveUp(player1));
		log.debug("三个玩家在玩，玩家1放弃，本局继续");
		Assert.assertEquals(2,table.giveUp(player2));
		log.debug("三个玩家在玩，玩家1放弃了，玩家2也放弃，本局结束，再开始新局");
		
	}

	/**
	 * 用户不在坐位，游戏末开始，用户站起后，再坐下不影响，游戏开始后，站起再坐下，状态变为弃版等待。
	 */
	//@Test
	public void testStandUp(){
		ITexasTable table=new TexasTable(new TexasCroupierConfig(), new TexasCommandProcessor());
		
		TexasPlayer player=new TexasPlayer("P1");
		player.setBankroll(1000);
		TexasPlayer player2=new TexasPlayer("P2");
		player2.setBankroll(1000);
		TexasPlayer player3=new TexasPlayer("P3");
		player3.setBankroll(1000);
		TexasPlayer player4=new TexasPlayer("P4");
		player4.setBankroll(1500);
		
		Assert.assertEquals(2,table.sitDown(player, 1));
		Assert.assertEquals(1,table.sitDown(player2, 2));
		log.debug("成功坐下后，开始游戏了");
		Assert.assertEquals(2,table.sitDown(player3, 3));
		log.debug("成功坐下后，等待开始游戏（待上一局游戏结束)");
		Assert.assertEquals(2,table.sitDown(player4, 4));
		log.debug("成功坐下后，等待开始游戏（待上一局游戏结束)");
		
		
	}

}
