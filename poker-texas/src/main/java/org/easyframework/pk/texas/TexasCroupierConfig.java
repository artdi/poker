package org.easyframework.pk.texas;

/**
 * 德州扑克本桌配置
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月2日 下午5:42:30
 */
public class TexasCroupierConfig {
	private int maxPlayer=6;
	private int maxViewer=600;
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
	public int getMaxPlayer() {
		return maxPlayer;
	}
	public void setMaxPlayer(int maxPlayer) {
		this.maxPlayer = maxPlayer;
	}
	public int getMaxViewer() {
		return maxViewer;
	}
	public void setMaxViewer(int maxViewer) {
		this.maxViewer = maxViewer;
	}
	public int getCap() {
		return Cap;
	}
	public void setCap(int cap) {
		Cap = cap;
	}
	public long getSmallBlinds() {
		return smallBlinds;
	}
	public void setSmallBlinds(long smallBlinds) {
		this.smallBlinds = smallBlinds;
		this.bigBlinds=this.smallBlinds*2;
	}
	public long getBigBlinds() {
		return bigBlinds;
	}
	/*public void setBigBlinds(long bigBlinds) {
		this.bigBlinds = bigBlinds;
	}*/
	

	
}
