package office.yscj.action;

import office.leave.dao.LeaveProcessDAO;
import office.leave.pojo.LeaveProcess;
import office.log.dao.OperateLogDAO;
import office.log.pojo.OperateLog;
import office.process.dao.ProcessDAO;
import office.process.pojo.Process;
import office.userinfo.dao.UserInfoDAO;
import office.userinfo.pojo.UserInfo;
import office.util.DateUtil;
import office.util.LeaveUtil;
import office.yscj.dao.TYscjDAO;
import office.yscj.dao.TYscjtzDAO;
import office.yscj.pojo.TYscj;
import office.yscj.pojo.TYscjtz;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ccb.hibernate.HibernateSessionFactory;

public class AllShenPi {
	private String [] yscjchoose;;
    private String message;
    private String newnumber;
    
	public String[] getYscjchoose() {
		return yscjchoose;
	}

	public void setYscjchoose(String[] yscjchoose) {
		this.yscjchoose = yscjchoose;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNewnumber() {
		return newnumber;
	}

	public void setNewnumber(String newnumber) {
		this.newnumber = newnumber;
	}

	public String execute() throws Exception
	{
		String result = "success";
		message = "审批成功";
		//Timestamp time = new Timestamp(System.currentTimeMillis()); 
		TYscjDAO tydao = new TYscjDAO();
		DateUtil du = new DateUtil();
		TYscjtzDAO tzdao = new TYscjtzDAO();
		LeaveProcessDAO lprodao = new LeaveProcessDAO();
		LeaveProcess lpro = new LeaveProcess();//审批意见写在leaveprocess中
		UserInfoDAO uidao = new UserInfoDAO();
		ProcessDAO pdao = new ProcessDAO();
		OperateLogDAO oldao = new OperateLogDAO();//记录日志
		OperateLog ol = new OperateLog();
		String time = du.getSimpleDateTime();
	
		Session session = HibernateSessionFactory.getSession();
 	    Transaction trans=session.beginTransaction();
 		try {
 			UserInfo ui = uidao.findByNewNumber(newnumber);
 			for(int i=0;i<yscjchoose.length;i++)
 			{
 				String number = yscjchoose[i];
 				TYscj ty = tydao.findAllByNumber(number);
 				UserInfo uiapply = uidao.findByNewNumber(ty.getApplicant());
 	 			Process p = pdao.findByItemAndApplicant("YSCJ", ty.getProcess());
 	 			String proc = p.getProcess();//完整请假流程
 	 			String jindu = ty.getJindu();
 	 			String under = ty.getThisunder();//当前审批人
 	 		  	int jindulength = ty.getJindu().length();
 	 			String undersign = proc.substring(jindulength, jindulength+1);
 	 			ol.setItem("YSCJ");
 	 			ol.setName(ui.getUsername());
 	 			ol.setNewnumber(newnumber);
 	 			ol.setTime(du.getDateTime());
 	 			ol.setRemark(number);
 	 			
 	 			lpro.setNumber(number);
 	 			lpro.setTime(time);
 	 			lpro.setViewer(ui.getUsername());
 	 			lpro.setViewernewnumber(newnumber);
 	 			if(newnumber.equals("91362239"))
 	 			{
 	 				lpro.setAuthority("A");
 	 			}
 	 			else if(newnumber.equals("20186393"))
 	 			{
 	 				lpro.setAuthority("H");
 	 			}
 	 			else
 	 			{
 	 				lpro.setAuthority("B");
 	 			}
 	 	
 	 			lpro.setRemark("批量审批");
 	 			if((newnumber.substring(0,8)).equals(under))
 	 			{
 	 				
 	 				
 	 				lpro.setOpinion(1);
 	 				if((proc.length()-jindu.length())==1)//BOSS审批完毕，进入待领取护照状态
 	 				{
 	 					ty.setJindu(proc);
 	 					ty.setStatus(8);
 	 					ty.setThisunder("89207003");//下一个处理人是因私出境管理员
 	 					//wpdao.submitWcggPage(number);//更新汇总表
 	 					ol.setOperate("审批完毕,"+LeaveUtil.NewNumberToNameNoSession(ty.getApplicant())+"已更新");
 	 					
 	 					TYscjtz tz = new TYscjtz();
 	 					UserInfo uitz = uidao.findByNewNumber(ty.getApplicant());
 	 		 	    	tz.setNumber(ty.getNumber());
 	 		 	    	tz.setDate(ty.getDate());
 	 		 	    	tz.setName(uiapply.getUsername());
 	 		 	    	tz.setTocountry(ty.getTocountry());
 	 		 	    	tz.setSumday(ty.getSumday());
 	 		 	    	tz.setBegindate(ty.getBegindate());
 	 		 	    	tz.setEnddate(ty.getEnddate());
 	 		 	    	tz.setStatus(10);
 	 		 	     	if(ty.getPassporttype()==1)
 	 			    	{
 	 			    		tz.setPassporttype("护照");
 	 			    	
 	 			    	}	
 	 			    	else if (ty.getPassporttype()==2)
 	 			    	{
 	 			    		tz.setPassporttype("港澳通行证");
 	 			    	}
 	 			    	else if(ty.getPassporttype()==3)
 	 			    	{
 	 			    		tz.setPassporttype("台湾通行证");
 	 			    	}
 	 		 	     	if(ty.getPassporttype()==1)
 	 			    	{
 	 		 	     		tz.setPassportnumber(uitz.getPassport());
 	 			    	
 	 			    	}	
 	 			    	else if (ty.getPassporttype()==2)
 	 			    	{
 	 			    		tz.setPassportnumber(uitz.getHkpassport());
 	 			    	}
 	 			    	else if(ty.getPassporttype()==3)
 	 			    	{
 	 			    		tz.setPassportnumber(uitz.getTwpassport());
 	 			    	}
 	 			    	
 	 		 	    	if(ty.getReason()==1)
 	 		 	    	{
 	 		 	    		tz.setReason("旅游");
 	 		 	    	
 	 		 	    	}	
 	 		 	    	else if (ty.getReason()==2)
 	 		 	    	{
 	 		 	    		tz.setReason("探亲");
 	 		 	    	}
 	 		 	    	else if(ty.getReason()==3)
 	 		 	    	{
 	 		 	    		tz.setReason("访友");
 	 		 	    	}
 	 		 	    	else if (ty.getReason()==4)
 	 		 	    	{
 	 		 	    		tz.setReason("其他");
 	 		 	    	}
 	 		 	    
 	 		 			tzdao.merge(tz);
 	 				}
 	 				else if (undersign.equals("B"))
 	 				{
 	 					ty.setThisunder("20186393");
 	 					ty.setJindu(ty.getJindu()+"B");
 	 					ty.setStatus(2);//流转中状态
 	 				}
 	 				else
 	 				{
 	 					ty.setThisunder("91362239");
 	 					ty.setJindu(ty.getJindu()+"H");
 	 					ty.setStatus(2);//流转中状态
 	 				}
 	 				ty.setPreunder(newnumber); 	 		
 	 				lprodao.merge(lpro);	 				
 	 	 			tydao.merge(ty);
 	 	 			oldao.merge(ol);
 			
 			}
 	 		
 			
 		}
 		
 		}
 		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			trans.rollback();
		}finally{
			trans.commit();
			session.flush();
			session.clear();
			session.close();
		}
		
		return result;
	}
}
