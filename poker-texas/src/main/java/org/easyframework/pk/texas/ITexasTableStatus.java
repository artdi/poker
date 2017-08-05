package org.easyframework.pk.texas;

import java.util.List;
import java.util.Map;
/**
 * 桌面状态
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月22日 下午4:11:40
 */
public interface ITexasTableStatus {

	/**
	 * 桌面状态名称
	 * @return
	 */
	public TexasTableStatus getStatusName();
	/**
	 * 获取玩家数量
	 * @return
	 */
	public int getPlayerNum();
	/**
	 * 获取各坐位的人，坐位无人则为空
	 * @return  TexasPlayer[]
	 */
	public TexasPlayer[] getPlayers();
	
	public TexasPlayer getBetPlayer();
	/**
	 * 获取奖池，key为奖池奖金数，value为本奖池参与人
	 * @return
	 */
	public Map<Long, List<TexasPlayer>> getBets();
	/**
	 * 获取参观人
	 * @return
	 */
	public List<TexasPlayer> getViewer();
	
	
	
}
