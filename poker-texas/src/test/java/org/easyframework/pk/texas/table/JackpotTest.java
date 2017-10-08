package org.easyframework.pk.texas.table;

import org.easyframework.pk.texas.Jackpot;
import org.easyframework.pk.texas.TexasCroupierConfig;
import org.junit.Test;

/**
 * 下注组件测试
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年9月10日 下午6:19:47
 */
public class JackpotTest {

	@Test
	public void testMaxBet(){
		Jackpot jackpot=new Jackpot(new TexasCroupierConfig().getMaxPlayer());
		
	}
}
