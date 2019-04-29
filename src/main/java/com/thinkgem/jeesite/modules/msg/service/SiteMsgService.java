/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.msg.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.msg.entity.SiteMsg;
import com.thinkgem.jeesite.modules.msg.dao.SiteMsgDao;

/**
 * 单表生成Service
 * @author ThinkGem
 * @version 2019-04-28
 */
@Service
@Transactional(readOnly = true)
public class SiteMsgService extends CrudService<SiteMsgDao, SiteMsg> {

	public SiteMsg get(String id) {
		return super.get(id);
	}
	
	public List<SiteMsg> findList(SiteMsg siteMsg) {
		return super.findList(siteMsg);
	}
	
	public Page<SiteMsg> findPage(Page<SiteMsg> page, SiteMsg siteMsg) {
		return super.findPage(page, siteMsg);
	}
	
	@Transactional(readOnly = false)
	public void save(SiteMsg siteMsg) {
		super.save(siteMsg);
	}
	
	@Transactional(readOnly = false)
	public void delete(SiteMsg siteMsg) {
		super.delete(siteMsg);
	}
	
}