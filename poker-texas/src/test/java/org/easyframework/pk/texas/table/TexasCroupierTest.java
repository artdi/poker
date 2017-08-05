package org.easyframework.pk.texas.table;

import org.easyframework.pk.texas.ITexasTable;
import org.easyframework.pk.texas.TexasCroupier;
import org.easyframework.pk.texas.TexasCroupierConfig;
import org.easyframework.pk.texas.TexasPlayer;
import org.easyframework.pk.texas.TexasTable;
import org.easyframework.pk.texas.command.TexasCommandProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TexasCroupierTest {
	private static final Logger log=LoggerFactory.getLogger(TexasCroupierTest.class);
	
	@Test
	public void testSeatDown(){
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
		Assert.assertEquals(1,table.bet(player1, 10));
		log.debug("玩家1下注成功");
		
		
	}

}
