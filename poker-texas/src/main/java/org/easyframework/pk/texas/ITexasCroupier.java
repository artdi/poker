package org.easyframework.pk.texas;

import org.easyframework.pk.texas.exception.TexasException;

/**
 * 房间、桌面、荷官
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月2日 下午5:15:50
 */
public interface ITexasCroupier {

	/**
	 * 获取状态序号，Croupier同一时间只充许一个操作时，如果StaticId不连继，表示操作有跳跃。
	 * @return
	 */
	public int getStaticId();
	/**
	 * 玩家坐下，成功返回Croupier最新状态
	 * @param player
	 * @return
	 * @throws TexasException  玩家已满
	 */
	public ITexasCroupier sitDown(TexasPlayer player) throws TexasException;
	/**
	 * 玩家增加筹码
	 * @param player
	 * @param betNum
	 * @return
	 * @throws TexasException  玩家当前状态不能增加筹码、筹码超出限制
	 */
	public ITexasCroupier playerAddChip(TexasPlayer player,long betNum) throws TexasException;
	/**
	 * 玩家站起，站起还没离开桌面，还可以观看，或坐下、或退出
	 * @param player
	 * @return
	 * @throws TexasException  玩家不在桌面上
	 */
	public ITexasCroupier standUp(TexasPlayer player) throws TexasException;
	/**
	 * 玩家押注，
	 * @param player
	 * @param betNum
	 * @return
	 * @throws TexasException  不是玩家押注状态，玩家押注金额不足
	 */
	public ITexasCroupier bet(TexasPlayer player,long betNum) throws TexasException;
	/**
	 * 返回Croupier的状态，不能编辑，用于展示用。多用于反回客户端展示
	 * @return
	 */
	public ITexasCroupier view();
	
}
