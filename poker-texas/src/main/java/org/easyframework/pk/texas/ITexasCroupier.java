package org.easyframework.pk.texas;

import java.util.Random;

import org.easyframework.pk.PokerCard;

public interface ITexasCroupier {
	
	/**
	 * 洗牌
	 */
	public void shuffle();
	/**
	 * 发牌
	 */
	public void dealing();
	/**
	 * 计算押注奖池情况
	 */
	public void countBet();
	/**
	 * 确定庄家及大小盲角色。
	 */
	public void countBlinds();
}
