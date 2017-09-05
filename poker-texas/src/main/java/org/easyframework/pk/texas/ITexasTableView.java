package org.easyframework.pk.texas;

import java.util.List;
import java.util.Map;
/**
 * 桌面状态
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月22日 下午4:11:40
 */
public interface ITexasTableView {

	/**
	 * 桌面状态名称
	 * @return
	 */
	public TexasTableStatus getStatusName();
	/**
	 * 获取已坐在桌上的玩家数量
	 * @return
	 */
	public int getPlayerNum();
	/**
	 * 获取游戏中的玩家手中牌数量
	 * @return
	 */
	public int getPlayerPokerNum();
	/**
	 * 获取某坐位玩家手中牌数量
	 * @param seatNo
	 * @return
	 */
	public int getPlayerPokerNum(int seatNo);
	/**
	 * 游戏中，计算未结束玩家数量
	 * @return
	 */
	public int getEffectivePlayer();
}
