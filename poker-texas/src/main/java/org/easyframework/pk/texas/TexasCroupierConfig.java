package org.easyframework.pk.texas;

/**
 * 德州扑克本桌配置
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月2日 下午5:42:30
 */
public class TexasCroupierConfig {
	private int maxPlayer;
	private int maxViewer;
	/**
	 * 每个押注圈最多 加注 次数
	 */
	private int Cap=3;
	/**
	 * 小盲注
	 */
	private long smallBlinds =1 ;
	/**
	 * 大盲注
	 */
	private long bigBlinds = smallBlinds*2;
	

}
