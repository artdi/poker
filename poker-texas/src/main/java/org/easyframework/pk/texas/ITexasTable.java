package org.easyframework.pk.texas;

import org.easyframework.pk.texas.exception.TexasException;

/**
 * 房间、桌面、荷官
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月2日 下午5:15:50
 */
public interface ITexasTable extends ITexasTableView{

	
	/**
	 * 坐下：玩家占领座位<br>玩家够时，并启动游戏。
	 * <br>庄家确定办法：默认为0坐号，每局开始轮到下一有效玩家，庄家后第一个有效玩家为小盲
	 * @param player  玩家
	 * @param seatNo  坐那个位置
	 * @return 1:成功坐下，且已开始游戏 <br>2:成功坐下，等待开始游戏（游戏人数不够或等待上一局游戏结束）
	 * 	<br>错误时：  -1:钱大于最低值， -2:已被其它玩家先坐,-3:已在座位，重复操作,-4:参数有误（坐号不对）
	 */
	public int sitDown(TexasPlayer player,Integer seatNo) ;
	/**
	 * 玩家站起，放弃玩家本局游戏，并离开坐位
	 * @param player 玩家
	 * @return  1:玩家放弃后，本局继续,2:玩家放弃后，本局结束，-1: 玩家不在桌面上
	 */
	public int standUp(TexasPlayer player);
	/**
	 * 放弃本局游戏，如果放弃后，游戏人数不够，则结束本局游戏
	 * @param player 玩家
	 * @return  1:玩家放弃后，本局继续,2:玩家放弃后，本局结束，-1: 玩家不在桌面上
	 */
	public int giveUp(TexasPlayer player);
	/**
	 * 玩家押注，
	 * @param player
	 * @param betNum
	 * @return 1:押注成功，本局下一玩家继续，2:押注成功，本局结束，开始下一局
	 * 			<br>-1:不是玩家押注状态，-2:玩家押注金额不足,-3:玩家已离开
	 */
	public int bet(TexasPlayer player,long betNum);
	/**
	 * 返回ITexasTable的状态，不能编辑，用于展示用。多用于反回客户端展示
	 * @return
	 */
	public ITexasTableView view();
	
}
