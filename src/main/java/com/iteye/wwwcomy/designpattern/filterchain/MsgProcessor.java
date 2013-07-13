package com.iteye.wwwcomy.designpattern.filterchain;

public class MsgProcessor {
	private String msg;

	/**
	 * 责任链
	 */
	private FilterChain fc;

	public MsgProcessor(FilterChain fc) {
		this.fc = fc;
	}

	/**
	 * 用责任链处理
	 * 
	 * @return
	 */
	public String process() {
		String r = msg;
		return fc.doFilter(r);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public FilterChain getFc() {
		return fc;
	}

	public void setFc(FilterChain fc) {
		this.fc = fc;
	}
}
