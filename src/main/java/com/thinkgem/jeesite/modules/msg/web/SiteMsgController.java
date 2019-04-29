/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.msg.web;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.h2.util.New;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.msg.entity.SiteMsg;
import com.thinkgem.jeesite.modules.msg.service.SiteMsgService;
import com.thinkgem.jeesite.modules.msg.utils.MsgUtils;

/**
 * 单表生成Controller
 * @author ThinkGem
 * @version 2019-04-28
 */
@Controller
@RequestMapping(value = "${adminPath}/msg/siteMsg")
public class SiteMsgController extends BaseController {

	@Autowired
	private SiteMsgService siteMsgService;
	
	@ModelAttribute
	public SiteMsg get(@RequestParam(required=false) String id) {
		SiteMsg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = siteMsgService.get(id);
		}
		if (entity == null){
			entity = new SiteMsg();
		}
		return entity;
	}
	
	@RequiresPermissions("msg:siteMsg:view")
	@RequestMapping(value = {"list", ""})
	public String list(SiteMsg siteMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SiteMsg> page = siteMsgService.findPage(new Page<SiteMsg>(request, response), siteMsg); 
		model.addAttribute("page", page);
		return "modules/msg/siteMsgList";
	}

	@RequiresPermissions("msg:siteMsg:view")
	@RequestMapping(value = "form")
	public String form(SiteMsg siteMsg, Model model) {
		model.addAttribute("siteMsg", siteMsg);
		return "modules/msg/siteMsgForm";
	}

	@RequiresPermissions("msg:siteMsg:edit")
	@RequestMapping(value = "save")
	public String save(SiteMsg siteMsg, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, siteMsg)){
			return form(siteMsg, model);
		}
		if(StringUtils.isEmpty(siteMsg.getCreatetime())) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			siteMsg.setCreatetime(simpleDateFormat.format(new Date()));
		}
		String message = siteMsg.getMessage();
		String phone = siteMsg.getPhone();
		if(StringUtils.isEmpty(message)){
            addMessage(redirectAttributes, "请输入发送信息");
        }
        List<String> phones = new ArrayList<String>();
        if(!StringUtils.isEmpty(phone)){
            if(phone.indexOf(",")!= -1){
                String[] phonenumber = phone.split(",");
                for (int i = 0;i<phonenumber.length;i++){
                    phones.add(phonenumber[i]);
                }
            }else{
                phones.add(phone);
            }
        }else{
        	addMessage(redirectAttributes, "请输入接收号码");
        }
        List<String> failedPhone = new ArrayList<String>();
        String failMsg = "";
        for(int i = 0;i<phones.size();i++){
            String rtn="";
			try {
				rtn = MsgUtils.sendMessage(phones.get(i), message);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            try {
                JSONObject jo = new JSONObject(rtn);
                int state = jo.getInt("state");
                if(state != 1){
                    failedPhone.add(phones.get(i));
                    failMsg = jo.getString("remarks");
                }
            }catch (Exception e){
                addMessage(redirectAttributes, "返回数值解析失败");
            }

        }

        if(failedPhone.size()>0){
            int size = failedPhone.size();
            addMessage(redirectAttributes, size+"条消息发送失败。");
        }else{
            addMessage(redirectAttributes, "发送成功");
        }
		siteMsgService.save(siteMsg);
		addMessage(redirectAttributes, "短信发送成功。");
		return "redirect:"+Global.getAdminPath()+"/msg/siteMsg/?repage";
	}
	
	@RequiresPermissions("msg:siteMsg:edit")
	@RequestMapping(value = "delete")
	public String delete(SiteMsg siteMsg, RedirectAttributes redirectAttributes) {
		siteMsgService.delete(siteMsg);
		addMessage(redirectAttributes, "删除单表成功");
		return "redirect:"+Global.getAdminPath()+"/msg/siteMsg/?repage";
	}
	
	@RequiresPermissions("msg:siteMsg:sendMessage")
	@RequestMapping(value="sendMsg")
	@ResponseBody
	public String sendMessage(String phone,String message, RedirectAttributes redirectAttributes) {
		String returns = "";
		if(StringUtils.isEmpty(message)){
			returns = "请输入发送信息";
            addMessage(redirectAttributes, "请输入发送信息");
        }
        List<String> phones = new ArrayList<String>();
        if(!StringUtils.isEmpty(phone)){
            if(phone.indexOf(",")!= -1){
                String[] phonenumber = phone.split(",");
                for (int i = 0;i<phonenumber.length;i++){
                    phones.add(phonenumber[i]);
                }
            }else{
                phones.add(phone);
            }
        }else{
        	returns = "请输入接收号码";
        	addMessage(redirectAttributes, "请输入接收号码");
        }
        List<String> failedPhone = new ArrayList<String>();
        String failMsg = "";
        for(int i = 0;i<phones.size();i++){
            String rtn="";
			try {
				rtn = MsgUtils.sendMessage(phones.get(i), message);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            try {
                JSONObject jo = new JSONObject(rtn);
                int state = jo.getInt("state");
                if(state != 1){
                    failedPhone.add(phones.get(i));
                    failMsg = jo.getString("remarks");
                }
            }catch (Exception e){
            	returns = "返回数值解析失败";
                addMessage(redirectAttributes, "返回数值解析失败");
            }

        }

        if(failedPhone.size()>0){
            int size = failedPhone.size();
            returns = size+"条消息发送失败。";
            addMessage(redirectAttributes, size+"条消息发送失败。");
        }else{
        	returns = "发送成功";
            addMessage(redirectAttributes, "发送成功");
        }
        
        return returns;
        
	}

}