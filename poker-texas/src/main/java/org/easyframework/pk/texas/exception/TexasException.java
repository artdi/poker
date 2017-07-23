package org.easyframework.pk.texas.exception;

/**
 * 异常
 * 101：参数错误
 * @Description: 
 * @author artdi artditan@gmail.com 
 * @date 2017年7月2日 下午5:21:53
 */
public class TexasException extends RuntimeException {
	private int code=0;
	public TexasException(int code,String msg,Exception e){
		super(msg, e);
		this.code=code;
	}
}
