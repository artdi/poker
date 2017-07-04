package org.easyframework.pk.texas;

/**
 * TexasCroupier 工厂
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月2日 下午5:59:45
 */
public class TexasCroupierFactory {
	
	/**
	 * 以某玩家新开启一个桌面
	 * @param config
	 * @param player
	 * @return
	 */
	public ITexasCroupier createCroupier(TexasCroupierConfig config,TexasPlayer player){
		return null;
	}
	/**
	 * 某玩家随机坐下一个桌
	 * @param config
	 * @param player
	 * @return
	 */
	public  ITexasCroupier playerSitDown(TexasCroupierConfig config,TexasPlayer player){
		return null;
	}

}
