package org.easyframework.pk.texas;

import org.easyframework.pk.command.ICommandProcessor;
import org.easyframework.pk.texas.command.TexasCommandProcessor;

public class TexasCroupierTest {

	public void testSitDown(){
		ICommandProcessor cmdProcessor=new TexasCommandProcessor();
		ITexasCroupier croupier=new HkTexasCroupier(cmdProcessor);
		TexasPlayer player=new TexasPlayer();
		croupier.sitDown(player, 2);
		
	}
}
