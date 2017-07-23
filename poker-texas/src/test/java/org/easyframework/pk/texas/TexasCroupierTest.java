package org.easyframework.pk.texas;

import org.easyframework.pk.command.ICommandProcessor;
import org.easyframework.pk.texas.command.TexasCommandProcessor;
import org.easyframework.pk.texas.exception.TexasException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TexasCroupierTest {
	@Rule  
    public ExpectedException thrown= ExpectedException.none(); 
	@Test
	public void testSitDown(){
		ICommandProcessor cmdProcessor=new TexasCommandProcessor();
		ITexasCroupier croupier=new HkTexasCroupier(cmdProcessor);
		TexasPlayer player=new TexasPlayer("P2");
		player.setBankroll(1000);
		croupier.sitDown(player, 2);
		thrown.expect(TexasException.class);
		thrown.expectMessage("坐位已有人坐");
		croupier.sitDown(player, 2);
	}
	@Test
	public void testSitDown2(){
		ICommandProcessor cmdProcessor=new TexasCommandProcessor();
		ITexasCroupier croupier=new HkTexasCroupier(cmdProcessor);
		TexasPlayer player=new TexasPlayer("P2");
		player.setBankroll(1000);
		croupier.sitDown(player, 2);
		TexasPlayer player1=new TexasPlayer("P1");
		player1.setBankroll(1000);
		croupier.sitDown(player1, 1);
		croupier.view();
		ITexasTableStatus tableStatus=(ITexasTableStatus)croupier;
		Assert.assertTrue("有两个玩家，应该开始游戏",TexasTableStatusName.waitBet==tableStatus.getStatusName());
		
	}
}
