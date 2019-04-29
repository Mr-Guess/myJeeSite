/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.msg.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author ThinkGem
 * @version 2019-04-28
 */
public class SiteMsg extends DataEntity<SiteMsg> {
	
	private static final long serialVersionUID = 1L;
	private String phone;		// 电话号码
	private String message;		// 消息内容
	private String createtime;		// 创建时间
	
	public SiteMsg() {
		super();
	}

	public SiteMsg(String id){
		super(id);
	}

	@Length(min=0, max=255, message="电话号码长度必须介于 0 和 255 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=2000, message="消息内容长度必须介于 0 和 2000 之间")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
}