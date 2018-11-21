package office.tempxx.action;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import office.leave.dao.LeavePageDAO;
import office.leave.dao.LeaveProcessDAO;
import office.leave.pojo.LeavePage;
import office.leave.pojo.LeaveProcess;
import office.mycalendar.dao.MyCalendarDAO;
import office.mycalendar.pojo.MyCalendar;
import office.pb.dao.ScpbTeamDAO;
import office.pb.pojo.ScpbTeam;
import office.pb2.dao.XxsqPageDAO;
import office.pb2.pojo.XxsqPage;
import office.tempxx.dao.TPeopleworkDAO;
import office.tempxx.dao.TTempxiaxianDAO;
import office.tempxx.dao.TUpdateTimeDAO;
import office.tempxx.pojo.TPeoplework;
import office.tempxx.pojo.TTempxiaxian;
import office.tempxx.pojo.TUpdateTime;
import office.userinfo.dao.UserInfoDAO;
import office.userinfo.pojo.UserInfo;
import office.util.LeaveUtil;
import office.util.UserUtil;
import office.util.Util;
import office.util.DateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ccb.hibernate.HibernateSessionFactory;
public class UpdateTempXx {
	   private LeavePage lp;
	   private LeavePageDAO lpdao;
	   private XxsqPage xp;
	   private XxsqPageDAO xpdao;
	   private MyCalendar mc;
	   private MyCalendarDAO mcdao;
	   private TPeoplework tw;
	   private TPeopleworkDAO twdao;
	   private UserInfo ui;
	   private UserInfoDAO uidao;
	   private TUpdateTime tt;
	   private TUpdateTimeDAO ttdao;
	   private TTempxiaxian tx;
	   private TTempxiaxianDAO txdao;
	   private String tip;
	
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public TTempxiaxian getTx() {
		return tx;
	}
	public void setTx(TTempxiaxian tx) {
		this.tx = tx;
	}
	public TTempxiaxianDAO getTxdao() {
		return txdao;
	}
	public void setTxdao(TTempxiaxianDAO txdao) {
		this.txdao = txdao;
	}
	public TUpdateTime getTt() {
		return tt;
	}
	public void setTt(TUpdateTime tt) {
		this.tt = tt;
	}
	public TUpdateTimeDAO getTtdao() {
		return ttdao;
	}
	public void setTtdao(TUpdateTimeDAO ttdao) {
		this.ttdao = ttdao;
	}
	public LeavePage getLp() {
		return lp;
	}
	public void setLp(LeavePage lp) {
		this.lp = lp;
	}
	public LeavePageDAO getLpdao() {
		return lpdao;
	}
	public void setLpdao(LeavePageDAO lpdao) {
		this.lpdao = lpdao;
	}
	public XxsqPage getXp() {
		return xp;
	}
	public void setXp(XxsqPage xp) {
		this.xp = xp;
	}
	public XxsqPageDAO getXpdao() {
		return xpdao;
	}
	public void setXpdao(XxsqPageDAO xpdao) {
		this.xpdao = xpdao;
	}
	public MyCalendar getMc() {
		return mc;
	}
	public void setMc(MyCalendar mc) {
		this.mc = mc;
	}
	public MyCalendarDAO getMcdao() {
		return mcdao;
	}
	public void setMcdao(MyCalendarDAO mcdao) {
		this.mcdao = mcdao;
	}
	public TPeoplework getTw() {
		return tw;
	}
	public void setTw(TPeoplework tw) {
		this.tw = tw;
	}
	public TPeopleworkDAO getTwdao() {
		return twdao;
	}
	public void setTwdao(TPeopleworkDAO twdao) {
		this.twdao = twdao;
	}
	public UserInfo getUi() {
		return ui;
	}
	public void setUi(UserInfo ui) {
		this.ui = ui;
	}
	public UserInfoDAO getUidao() {
		return uidao;
	}
	public void setUidao(UserInfoDAO uidao) {
		this.uidao = uidao;
	}
	public String execute() throws Exception
	{
		Session session = HibernateSessionFactory.getSession();
 	    Transaction trans = session.beginTransaction();
 	    ttdao = new TUpdateTimeDAO();
 	    uidao = new UserInfoDAO();
 	    lpdao = new LeavePageDAO();
 	    xpdao = new XxsqPageDAO();
 	    twdao = new TPeopleworkDAO();
 	    txdao = new TTempxiaxianDAO();
 	    tx = new TTempxiaxian();
		tt = ttdao.findAllByLastId();
	    List<MyCalendar> listmc;
	    List<MyCalendar> listmclp;
	    List<MyCalendar> listmclp1;
	    List<MyCalendar> listmclp2;
	    List<MyCalendar> listmcxp;
	    List<MyCalendar> listmcxp1;
	    List<MyCalendar> listmcxp2;
	    List<LeavePage>listlp;
	    List<XxsqPage>listxp;
	    Date now = new Date();
	    SimpleDateFormat dateform = new SimpleDateFormat("yyyyMMdd");
	    SimpleDateFormat dateform1 = new SimpleDateFormat("yyyyMM");
	    String time = dateform1.format(now);
	    //int valid = twdao.isvalid(time, name);
	    String begindate = tt.getDate();
	    String enddate = dateform.format(now);
	    MyCalendarDAO mcdao=new MyCalendarDAO();
		MyCalendar mc = new MyCalendar();
		TUpdateTime ttnew = new TUpdateTime();
		ttnew.setDate(enddate);
		ttnew.setStatus("0");
		listmc = mcdao.findByBeginAndEnd(begindate, enddate, 1);
	   //计算更新时间段内请假表和因公下线表计划外
		for(int i=0;i<listmc.size();i++)
	 	    { 	    	
	 	    	mc=listmc.get(i);
	 	    	String date = mc.getDate();
	 	    	listlp=lpdao.findAllByDateNotDel(date);
	 	    	for(int j=0;j<listlp.size();j++)
	 	    	{
	 	    		lp=listlp.get(j);
	 	    		//double days = lp.getDays();
	 	    	       
	 	    			String begindatelp=lp.getBegindate();
		 	    		String enddatelp=lp.getEnddate();
		 	    		String newnumberlp=lp.getApplicant();
		 	    		ui = uidao.findByNewNumber(newnumberlp);
		 	    		String position = ui.getPosition();
		 	    		String chu = position.substring(2, 3);
		 	 	 	    String zhi = position.substring(0, 1);
		 	 	 	    String zu = position.substring(4, 5);
		 	 	 	    String opnumber = ui.getOpnumber();
		 	 	 	    String numberlp = lp.getNumber();
		 	 	 	    String name = ui.getUsername();
		 	    		int samemonth = issamemonth(begindatelp,enddatelp);
		 	    		int sameyear = issameyear(begindatelp,enddatelp);
		 	    		if(samemonth==0)
		 	    		{
		 	    			String begindatelp1=begindatelp;
		 	    			String enddatelp1=mcdao.findEndDateByDate(begindatelp);
		 	    			String begindatelp2=enddatelp.substring(0, 6)+"01";
		 	    			String enddatelp2=enddatelp;
		 	    			List<String> listdate1 = new ArrayList<String>();
			 	    		listmclp1 = mcdao.findByBeginAndEnd(begindatelp1, enddatelp1, 1);
			 	    		listmclp2 = mcdao.findByBeginAndEnd(begindatelp2, enddatelp2, 1);
			 	    		int flag = 0;
			 	    		int valid = 0;
		 	    			for(int k=0;k<listmclp1.size();k++)
			 	 	 	    {
		 	    				
			 	 	 	    	mc=listmclp1.get(k);
			 	 	 	        valid = twdao.isvalid(mc.getDate().substring(0, 6));
			 	 	 	    	tw = twdao.findAllByOpNumber(opnumber, mc.getDate());
			 	 	 	    	if((chu.equals("2")||chu.equals("6")||chu.equals("3"))&&!(zhi.equals("2"))&&!(zhi.equals("1")))
			 	 	 	    	{
			 	 	 	    		if((valid==1)&&(tw!=null)&&(tw.getStatus().equals("上线")))
			 	 	 	  	 	    {
			 	 	 	  	 	    	flag=flag+1;
			 	 	 	  	 	    }
			 	 	 	    		else if(valid==0)
			 	 	 	    		{
			 	 	 	    			flag=flag+1;
			 	 	 	    		}
			 	 	 	    		else
			 	 	 	    		{
			 	 	 	    			listdate1.add(mc.getDate());
			 	 	 	    		}
			 	 	 	    	}
			 	 	 	    }
			 	    		if(flag!=0)
			 	    		{
			 	    			TTempxiaxian txtt = new TTempxiaxian();
			 	    			txtt = txdao.findAllByNumber(numberlp);
			 	    			if(txtt==null)
			 	    			{
			 	    			String ingnoreday="";	
			 	    			/*String begintime=""; 
			 	    			String endtime="";*/
			 	    			if(listdate1.size()>0)
			 	    			{	
			 	    			   ingnoreday=listdate1.get(0);
			 	    			   for(int b=1;b<listdate1.size();b++)
				 	    			{
				 	    				ingnoreday=ingnoreday+"、"+listdate1.get(b);
				 	    			}
			 	    			}
			 	    					 	    	
			 	    			tx.setBegindate(begindatelp1);
			 	    			tx.setChu(chuToName(chu));
			 	    			tx.setEnddate(enddatelp1);
			 	    			tx.setName(ui.getUsername());
			 	    			tx.setOpnumber(opnumber);
			 	    			tx.setOpzu(changenametozu(name));
			 	    			//tx.setOpzu( );
			 	    			tx.setReason("请休假");
			 	    			tx.setRelatednumber(lp.getNumber());
			 	    			tx.setReportdate(enddate);
			 	    			tx.setShuoming("("+typetoname(lp.getType())+")");
			 	    			tx.setZu(changenametozu(name));
			 	    			tx.setRemark("");
			 	    			ttnew.setStatus("1");
			 	    			if(enddate.compareTo(begindatelp1)>=0)
			 	    			{
			 	    				tx.setRemark("补报");
			 	    			}
			 	    			if(listdate1.size()>0)
			 	    			{
			 	    				tx.setRemark(ingnoreday+"已报下线计划");
			 	    			}
			 	    			if((enddate.compareTo(begindatelp1)>=0)&&(listdate1.size()>0))
			 	    			{
			 	    				tx.setRemark("补报，"+ingnoreday+"已报下线计划");
			 	    			}
			 	    			txdao.merge(tx);
			 	    			}
			 	    		}
			 	    		flag=0;
			 	    		valid=0;
			 	    		List<String> listdate2 = new ArrayList<String>();
			 	    		for(int k=0;k<listmclp2.size();k++)
			 	 	 	    {
			 	 	 	    	
			 	 	 	    	mc=listmclp2.get(k);
			 	 	 	        valid = twdao.isvalid(mc.getDate().substring(0, 6));
			 	 	 	        
			 	 	 	    	tw = twdao.findAllByOpNumber(opnumber, mc.getDate());
			 	 	 	    	if((chu.equals("2")||chu.equals("6")||chu.equals("3"))&&!(zhi.equals("2"))&&!(zhi.equals("1")))
			 	 	 	    	{
			 	 	 	    		if((valid==1)&&(tw!=null)&&(tw.getStatus().equals("上线")))
			 	 	 	  	 	    {
			 	 	 	  	 	    	flag=flag+1;
			 	 	 	  	 	    }
			 	 	 	    		else if(valid==0)
			 	 	 	    		{
			 	 	 	    			flag=flag+1;
			 	 	 	    		}
			 	 	 	    		else
			 	 	 	    		{
			 	 	 	    			listdate2.add(mc.getDate());
			 	 	 	    		}
			 	 	 	    	}
			 	 	 	    }
			 	    		if(flag!=0)
			 	    		{
			 	    			TTempxiaxian txtt = new TTempxiaxian();
			 	    			txtt = txdao.findAllByNumber(numberlp);
			 	    			if(txtt==null)
			 	    			{
			 	    			String ingnoreday="";	
			 	    			/*String begintime=""; 
			 	    			String endtime="";*/
			 	    			if(listdate2.size()>0)
			 	    			{	
			 	    			   ingnoreday=listdate2.get(0);
			 	    			   for(int b=1;b<listdate2.size();b++)
				 	    			{
				 	    				ingnoreday=ingnoreday+"、"+listdate2.get(b);
				 	    			}
			 	    			}
			 	    					 	    	
			 	    			tx.setBegindate(begindatelp2);
			 	    			tx.setChu(chuToName(chu));
			 	    			tx.setEnddate(enddatelp2);
			 	    			tx.setName(ui.getUsername());
			 	    			tx.setOpnumber(opnumber);
			 	    			tx.setOpzu(changenametozu(name));
			 	    			//tx.setOpzu( );
			 	    			tx.setReason("请休假");
			 	    			tx.setRelatednumber(lp.getNumber());
			 	    			tx.setReportdate(enddate);
			 	    			tx.setShuoming("("+typetoname(lp.getType())+")");
			 	    			tx.setZu(changenametozu(name));
			 	    			tx.setRemark("");
			 	    			ttnew.setStatus("1");
			 	    			if(enddate.compareTo(begindatelp2)>=0)
			 	    			{
			 	    				tx.setRemark("补报");
			 	    			}
			 	    			if(listdate2.size()>0)
			 	    			{
			 	    				tx.setRemark(ingnoreday+"已报下线计划");
			 	    			}
			 	    			if((enddate.compareTo(begindatelp2)>=0)&&(listdate2.size()>0))
			 	    			{
			 	    				tx.setRemark("补报，"+ingnoreday+"已报下线计划");
			 	    			}
			 	    			txdao.merge(tx);
			 	    			}
			 	    		}
		 	    		}
		 	    
		 	 	 	   // int valid = twdao.isvalid(time, name);
		 	    		else
		 	    		{
		 	    	 	    List<String> listdate = new ArrayList<String>();
			 	    		listmclp = mcdao.findByBeginAndEnd(begindatelp, enddatelp, 1);
			 	    		int flag = 0;
			 	    		int valid = 0;
			 	    		for(int k=0;k<listmclp.size();k++)
			 	 	 	    {
			 	 	 	    	
			 	 	 	    	mc=listmclp.get(k);    	
			 	 	 	        valid = twdao.isvalid(mc.getDate().substring(0, 6));
			 	 	 	    	tw = twdao.findAllByOpNumber(opnumber, mc.getDate());
			 	 	 	    	if((chu.equals("2")||chu.equals("6")||chu.equals("3"))&&!(zhi.equals("2"))&&!(zhi.equals("1")))
			 	 	 	    	{
			 	 	 	    		if((valid==1)&&(tw!=null)&&(tw.getStatus().equals("上线")))
			 	 	 	  	 	    {
			 	 	 	  	 	    	flag=flag+1;
			 	 	 	  	 	    }
			 	 	 	    		else if(valid==0)
			 	 	 	    		{
			 	 	 	    			flag=flag+1;
			 	 	 	    		}
			 	 	 	    		else
			 	 	 	    		{
			 	 	 	    			listdate.add(mc.getDate());
			 	 	 	    		}
			 	 	 	    	}
			 	 	 	    }
			 	    		if(flag!=0)
			 	    		{
			 	    			TTempxiaxian txtt = new TTempxiaxian();
			 	    			txtt = txdao.findAllByNumber(numberlp);
			 	    			if(txtt==null)
			 	    			{
			 	    			String ingnoreday="";	
			 	    			/*String begintime=""; 
			 	    			String endtime="";*/
			 	    			if(listdate.size()>0)
			 	    			{	
			 	    			   ingnoreday=listdate.get(0);
			 	    			   for(int b=1;b<listdate.size();b++)
				 	    			{
				 	    				ingnoreday=ingnoreday+"、"+listdate.get(b);
				 	    			}
			 	    			}
			 	    					 	    	
			 	    			tx.setBegindate(begindatelp);
			 	    			tx.setChu(chuToName(chu));
			 	    			tx.setEnddate(enddatelp);
			 	    			tx.setName(ui.getUsername());
			 	    			tx.setOpnumber(opnumber);
			 	    			tx.setOpzu(changenametozu(name));
			 	    			//tx.setOpzu( );
			 	    			tx.setReason("请休假");
			 	    			tx.setRelatednumber(lp.getNumber());
			 	    			tx.setReportdate(enddate);
			 	    			tx.setShuoming("("+typetoname(lp.getType())+")");
			 	    			tx.setZu(changenametozu(name));
			 	    			tx.setRemark("");
			 	    			ttnew.setStatus("1");
			 	    			if(enddate.compareTo(begindatelp)>=0)
			 	    			{
			 	    				tx.setRemark("补报");
			 	    			}
			 	    			if(listdate.size()>0)
			 	    			{
			 	    				tx.setRemark(ingnoreday+"已报下线计划");
			 	    			}
			 	    			if((enddate.compareTo(begindatelp)>=0)&&(listdate.size()>0))
			 	    			{
			 	    				tx.setRemark("补报，"+ingnoreday+"已报下线计划");
			 	    			}
			 	    			txdao.merge(tx);
			 	    			}
			 	    		}
		 	    			
		 	    		}
		 	 	
	 	    
	 	    	}
	 	    	listxp=xpdao.findAllByDateNotDel(date);
	 	    	for(int k=0;k<listxp.size();k++)
	 	    	{
	 	    		xp=listxp.get(k);
	 	    		if(xp.getReason()!=9)
	 	    		{		
	 	    		String begindatexp=xp.getBegindate();
	 	    		String enddatexp=xp.getEnddate();
	 	    		int samemonth = issamemonth(begindatexp,enddatexp);
	 	    		int sameyear = issameyear(begindatexp,enddatexp);
	 	    		String people = xp.getPeople();
	 	    		String []names = people.split("、");
	 	    		String numberxp = xp.getNumber();
	 	    	   // List<String> listdate = new ArrayList<String>();
	 	    		for(int m=0;m<names.length;m++)
	 	    		{
	 	    			String name = names[m];
	 	    			ui = uidao.findByName(name);
		 	    		String position = ui.getPosition();
		 	    		String chu = position.substring(2, 3);
		 	 	 	    String zhi = position.substring(0, 1);
		 	 	 	    String zu = position.substring(4, 5);
		 	 	 	    String opnumber = ui.getOpnumber();
		 	 	 	    if(samemonth==0)
		 	 	 	    {
		 	 	 	    	String begindatexp1=begindatexp;
		 	    			String enddatexp1=mcdao.findEndDateByDate(begindatexp);
		 	    			String begindatexp2=enddatexp.substring(0, 6)+"01";
		 	    			String enddatexp2=enddatexp;
		 	    			List<String> listdate1 = new ArrayList<String>();
			 	    		listmcxp1 = mcdao.findByBeginAndEnd(begindatexp1, enddatexp1, 1);
			 	    		listmcxp2 = mcdao.findByBeginAndEnd(begindatexp2, enddatexp2, 1);
			 	    		int flag = 0;
			 	    		int valid = 0;
			 	    		for(int n=0;n<listmcxp1.size();n++)
			 	 	 	    {
			 	 	 	    	
			 	 	 	    	mc=listmcxp1.get(n);
			 	 	 	        valid = twdao.isvalid(mc.getDate().substring(0, 6));
			 	 	 	    	tw = twdao.findAllByOpNumber(opnumber, mc.getDate());
			 	 	 	 	  if((chu.equals("2")||chu.equals("6")||chu.equals("3"))&&!(zhi.equals("2"))&&!(zhi.equals("1")))
		 	 	 	    	    {
		 	 	 	    		if((valid==1)&&(tw!=null)&&(tw.getStatus().equals("上线")))
		 	 	 	  	 	    {
		 	 	 	  	 	    	flag=flag+1;
		 	 	 	  	 	    }
		 	 	 	    		else if(valid==0)
		 	 	 	    		{
		 	 	 	    			flag=flag+1;
		 	 	 	    		}
		 	 	 	    		else
		 	 	 	    		{
		 	 	 	    			listdate1.add(mc.getDate());
		 	 	 	    		}
		 	 	 	    	 }
			 	 	 	    }
			 	    		if(flag!=0)
			 	    		{
			 	    			List<TTempxiaxian> listtxtt1 = new ArrayList<TTempxiaxian>();
			 	    			listtxtt1 = txdao.findAlllistByNumber(numberxp);
			 	    			int flag1=0;//判断是否已有此条记录
			 	    			if(listtxtt1!=null)
			 	    			{
			 	    				for(int d=0;d<listtxtt1.size();d++)
				 	    			{
				 	    				TTempxiaxian txtt1 = listtxtt1.get(d);
				 	    				if(name.equals( txtt1.getName()))
				 	    				{
				 	    					flag1=1; 
				 	    				}
				 	    				
				 	    			}
			 	    			}	
			 	    			
			 	    			if(listtxtt1==null||flag1==0)
			 	    			{
			 	    				    			
				 	    			String ingnoreday="";	
				 	    			/*String begintime=""; 
				 	    			String endtime="";*/
				 	    			if(listdate1.size()>0)
				 	    			{	
				 	    			   ingnoreday=listdate1.get(0);
				 	    			   for(int b=1;b<listdate1.size();b++)
					 	    			{
					 	    				ingnoreday=ingnoreday+"、"+listdate1.get(b);
					 	    			}
				 	    			}
			 	    				tx.setBegindate(begindatexp1);
				 	    			tx.setChu(chuToName(chu));
				 	    			tx.setEnddate(enddatexp1);
				 	    			tx.setName(ui.getUsername());
				 	    			tx.setOpnumber(opnumber);
				 	    			//tx.setOpzu( );
				 	    			tx.setOpzu(changenametozu(name));
				 	    			tx.setReason("因公下线");
				 	    			tx.setRelatednumber(xp.getNumber());
				 	    			tx.setReportdate(enddate);
				 	    			tx.setShuoming("("+ygxxReasonToString(xp.getReason())+")"+xp.getQita());
				 	    			tx.setZu(changenametozu(name));
				 	    			tx.setRemark("");
				 	    			ttnew.setStatus("1");
				 	    			if(enddate.compareTo(begindatexp1)>=0)
				 	    			{
				 	    				tx.setRemark("补报");
				 	    			}
				 	    			if(listdate1.size()>0)
				 	    			{
				 	    				tx.setRemark(ingnoreday+"已报下线计划");
				 	    			}
				 	    			if((enddate.compareTo(begindatexp1)>=0)&&(listdate1.size()>0))
				 	    			{
				 	    				tx.setRemark("补报，"+ingnoreday+"已报下线计划");
				 	    			}
				 	    			txdao.merge(tx);
			 	    			}	 	    			
			 	    		}
			 	    		
			 	    		flag=0;
			 	    		valid=0;
			 	    		List<String> listdate2 = new ArrayList<String>();
			 	    		for(int n=0;n<listmcxp2.size();n++)
			 	 	 	    {
			 	 	 	    	
			 	 	 	    	mc=listmcxp2.get(n);
			 	 	 	        valid = twdao.isvalid(mc.getDate().substring(0, 6));
			 	 	 	    	tw = twdao.findAllByOpNumber(opnumber, mc.getDate());
			 	 	 	 	  if((chu.equals("2")||chu.equals("6")||chu.equals("3"))&&!(zhi.equals("2"))&&!(zhi.equals("1")))
		 	 	 	    	    {
		 	 	 	    		if((valid==1)&&(tw!=null)&&(tw.getStatus().equals("上线")))
		 	 	 	  	 	    {
		 	 	 	  	 	    	flag=flag+1;
		 	 	 	  	 	    }
		 	 	 	    		else if(valid==0)
		 	 	 	    		{
		 	 	 	    			flag=flag+1;
		 	 	 	    		}
		 	 	 	    		else
		 	 	 	    		{
		 	 	 	    			listdate2.add(mc.getDate());
		 	 	 	    		}
		 	 	 	    	 }
			 	 	 	    }
			 	    		if(flag!=0)
			 	    		{
			 	    			List<TTempxiaxian> listtxtt2 = new ArrayList<TTempxiaxian>();
			 	    			listtxtt2 = txdao.findAlllistByNumber(numberxp);
			 	    			int flag1=0;//判断是否已有此条记录
			 	    			if(listtxtt2!=null)
			 	    			{
			 	    				for(int d=0;d<listtxtt2.size();d++)
				 	    			{
				 	    				TTempxiaxian txtt1 = listtxtt2.get(d);
				 	    				if(name.equals( txtt1.getName()))
				 	    				{
				 	    					flag1=1; 
				 	    				}
				 	    				
				 	    			}
			 	    			}	
			 	    			
			 	    			if(listtxtt2==null||flag1==0)
			 	    			{
			 	    				    			
				 	    			String ingnoreday="";	
				 	    			/*String begintime=""; 
				 	    			String endtime="";*/
				 	    			if(listdate2.size()>0)
				 	    			{	
				 	    			   ingnoreday=listdate2.get(0);
				 	    			   for(int b=1;b<listdate2.size();b++)
					 	    			{
					 	    				ingnoreday=ingnoreday+"、"+listdate2.get(b);
					 	    			}
				 	    			}
			 	    				tx.setBegindate(begindatexp2);
				 	    			tx.setChu(chuToName(chu));
				 	    			tx.setEnddate(enddatexp2);
				 	    			tx.setName(ui.getUsername());
				 	    			tx.setOpnumber(opnumber);
				 	    			//tx.setOpzu( );
				 	    			tx.setOpzu(changenametozu(name));
				 	    			tx.setReason("因公下线");
				 	    			tx.setRelatednumber(xp.getNumber());
				 	    			tx.setReportdate(enddate);
				 	    			tx.setShuoming("("+ygxxReasonToString(xp.getReason())+")"+xp.getQita());
				 	    			tx.setZu(changenametozu(name));
				 	    			tx.setRemark("");
				 	    			ttnew.setStatus("1");
				 	    			if(enddate.compareTo(begindatexp2)>=0)
				 	    			{
				 	    				tx.setRemark("补报");
				 	    			}
				 	    			if(listdate2.size()>0)
				 	    			{
				 	    				tx.setRemark(ingnoreday+"已报下线计划");
				 	    			}
				 	    			if((enddate.compareTo(begindatexp2)>=0)&&(listdate2.size()>0))
				 	    			{
				 	    				tx.setRemark("补报，"+ingnoreday+"已报下线计划");
				 	    			}
				 	    			txdao.merge(tx);
			 	    			}	 	    			
			 	    		}
			 	    		
		 	 	 	    }
		 	 	 	    
		 	 	 	    else
		 	 	 	    {
		 	 	 	        List<String> listdate = new ArrayList<String>();
			 	    		listmcxp = mcdao.findByBeginAndEnd(begindatexp, enddatexp, 1);
			 	    		int flag = 0;
			 	    		int valid = 0;
			 	    		for(int n=0;n<listmcxp.size();n++)
			 	 	 	    {
			 	 	 	    	
			 	 	 	    	mc=listmcxp.get(n);
			 	 	 	        valid = twdao.isvalid(mc.getDate().substring(0, 6));
			 	 	 	    	tw = twdao.findAllByOpNumber(opnumber, mc.getDate());
			 	 	 	 	  if((chu.equals("2")||chu.equals("6")||chu.equals("3"))&&!(zhi.equals("2"))&&!(zhi.equals("1")))
		 	 	 	    	    {
		 	 	 	    		if((valid==1)&&(tw!=null)&&(tw.getStatus().equals("上线")))
		 	 	 	  	 	    {
		 	 	 	  	 	    	flag=flag+1;
		 	 	 	  	 	    }
		 	 	 	    		else if(valid==0)
		 	 	 	    		{
		 	 	 	    			flag=flag+1;
		 	 	 	    		}
		 	 	 	    		else
		 	 	 	    		{
		 	 	 	    			listdate.add(mc.getDate());
		 	 	 	    		}
		 	 	 	    	 }
			 	 	 	    }
			 	    		if(flag!=0)
			 	    		{
			 	    			List<TTempxiaxian> listtxtt = new ArrayList<TTempxiaxian>();
			 	    			listtxtt = txdao.findAlllistByNumber(numberxp);
			 	    			int flag1=0;//判断是否已有此条记录
			 	    			if(listtxtt!=null)
			 	    			{
			 	    				for(int d=0;d<listtxtt.size();d++)
				 	    			{
				 	    				TTempxiaxian txtt1 = listtxtt.get(d);
				 	    				if(name.equals( txtt1.getName()))
				 	    				{
				 	    					flag1=1; 
				 	    				}
				 	    				
				 	    			}
			 	    			}	
			 	    			
			 	    			if(listtxtt==null||flag1==0)
			 	    			{
			 	    				    			
				 	    			String ingnoreday="";	
				 	    			/*String begintime=""; 
				 	    			String endtime="";*/
				 	    			if(listdate.size()>0)
				 	    			{	
				 	    			   ingnoreday=listdate.get(0);
				 	    			   for(int b=1;b<listdate.size();b++)
					 	    			{
					 	    				ingnoreday=ingnoreday+"、"+listdate.get(b);
					 	    			}
				 	    			}
			 	    				tx.setBegindate(begindatexp);
				 	    			tx.setChu(chuToName(chu));
				 	    			tx.setEnddate(enddatexp);
				 	    			tx.setName(ui.getUsername());
				 	    			tx.setOpnumber(opnumber);
				 	    			//tx.setOpzu( );
				 	    			tx.setOpzu(changenametozu(name));
				 	    			tx.setReason("因公下线");
				 	    			tx.setRelatednumber(xp.getNumber());
				 	    			tx.setReportdate(enddate);
				 	    			tx.setShuoming("("+ygxxReasonToString(xp.getReason())+")"+xp.getQita());
				 	    			tx.setZu(changenametozu(name));
				 	    			tx.setRemark("");
				 	    			ttnew.setStatus("1");
				 	    			if(enddate.compareTo(begindatexp)>=0)
				 	    			{
				 	    				tx.setRemark("补报");
				 	    			}
				 	    			if(listdate.size()>0)
				 	    			{
				 	    				tx.setRemark(ingnoreday+"已报下线计划");
				 	    			}
				 	    			if((enddate.compareTo(begindatexp)>=0)&&(listdate.size()>0))
				 	    			{
				 	    				tx.setRemark("补报，"+ingnoreday+"已报下线计划");
				 	    			}
				 	    			txdao.merge(tx);
			 	    			}	 	    			
			 	    		}
		 	 	 	    }
		 	   	
	 	    		  }
	 	    		}
	 	    	}
	 	    	
	 	    //计算审批明细表中是否存在撤销的数据
	 	       	              
	 	       String year = date.substring(0, 4);
	 	       String month = date.substring(4, 6);
	 	       String day = date.substring(6, 8);
	 	       LeaveProcess lpro = new LeaveProcess();
	 	       LeaveProcessDAO lprodao = new LeaveProcessDAO();
	 	       List<LeaveProcess> listlpro = lprodao.findByDelete(year, month, day);
	 	       for(int l=0;l<listlpro.size();l++)
	 	       {
	 	    	   lpro = listlpro.get(l);
	 	    	 
	 	    	   if((lpro.getNumber().contains("YGXX"))&&(lpro.getRemark().contains("已执行撤销员工")))
	 	    	   {
	 	    		   String temp1[]=lpro.getRemark().split("【");
	 	    		   String temp2[]=temp1[1].split("】");
	 	    		   String name = temp2[0];
	 	    		   XxsqPage xpdelete = xpdao.findAllByNumber(lpro.getNumber());
	 	    		    ui = uidao.findByName(name);
		 	    		String position = ui.getPosition();
		 	    		String chu = position.substring(2, 3);
		 	 	 	    String zhi = position.substring(0, 1);
		 	 	 	    String zu = position.substring(4, 5);
		 	 	 	    String opnumber = ui.getOpnumber();
		 	 	 	    TTempxiaxian txtt = new TTempxiaxian();
	 	    			txtt = txdao.findDeleteByNumberandName(lpro.getNumber(),name);
	 	    			TTempxiaxian txcun = new TTempxiaxian();
	 	    			txcun = txdao.findAllByNumberandName(lpro.getNumber(),name);
		 	 	  	   if((chu.equals("2")||chu.equals("6")||chu.equals("3"))&&!(zhi.equals("2"))&&!(zhi.equals("1")))
		 	 	  	   {
		 	 	  		if((txtt==null)&&(txcun!=null))
		 	 	  		{
		 	 	  		tx.setBegindate(xpdelete.getBegindate());
	 	    			tx.setChu(chuToName(chu));
	 	    			tx.setEnddate(xpdelete.getEnddate());
	 	    			tx.setName(ui.getUsername());
	 	    			tx.setOpnumber(opnumber);
	 	    			//tx.setOpzu( );
	 	    			tx.setOpzu(changenametozu(name));
	 	    			tx.setReason(ygxxReasonToString(xpdelete.getReason()));
	 	    			tx.setRelatednumber(xpdelete.getNumber());
	 	    			tx.setReportdate(enddate);
	 	    			tx.setShuoming(xpdelete.getRemark());
	 	    			tx.setZu(changenametozu(name));
	 	    			tx.setRemark("已撤销"+xpdelete.getDate());
	 	    			
	 	    			if(!(xpdelete.getDate().equals(year+month+day)))
	 	    			{
	 	    				txdao.merge(tx);
	 	    				ttnew.setStatus("1");
	 	    			}
	 	    			else
	 	    			{
	 	    				String sql = "delete from t_tempxiaxian where relatednumber = '"+lpro.getNumber()+"' and name = '"+name+"'";
	 	    				session.createSQLQuery(sql).executeUpdate();
	 	    			}
	 	    			
		 	 	  		}
		 	 	  		
		 	 	  	   }
		 	 	 	    
	 	    	   }
	 	    	   
	 	    	   if((lpro.getNumber().contains("YGXX"))&&(lpro.getRemark().contains("撤销审批表")))
	 	    	   {
	 	    		   XxsqPage xpdelete = xpdao.findAllByNumber(lpro.getNumber());
	 	    		   String names[] = xpdelete.getPeople().split("、");
	 	    		   for(int p=0;p<names.length;p++)
	 	    		   {
	 	    			   String name = names[p];
	 	    			    ui = uidao.findByName(name);
			 	    		String position = ui.getPosition();
			 	    		String chu = position.substring(2, 3);
			 	 	 	    String zhi = position.substring(0, 1);
			 	 	 	    String zu = position.substring(4, 5);
			 	 	 	    String opnumber = ui.getOpnumber();
			 	 	 	    TTempxiaxian txtt = new TTempxiaxian();
		 	    			txtt = txdao.findDeleteByNumberandName(lpro.getNumber(),name);
		 	    			TTempxiaxian txcun = new TTempxiaxian();
		 	    			txcun = txdao.findAllByNumberandName(lpro.getNumber(),name);
			 	 	 	 if((chu.equals("2")||chu.equals("6")||chu.equals("3"))&&!(zhi.equals("2"))&&!(zhi.equals("1")))
			 	 	 	 {
			 	 	 		if((txtt==null)&&(txcun!=null))
			 	 	 		{
			 	 	 			tx.setBegindate(xpdelete.getBegindate());
			 	    			tx.setChu(chuToName(chu));
			 	    			tx.setEnddate(xpdelete.getEnddate());
			 	    			tx.setName(ui.getUsername());
			 	    			tx.setOpnumber(opnumber);
			 	    			//tx.setOpzu( );
			 	    			tx.setOpzu(changenametozu(name));
			 	    			tx.setReason(ygxxReasonToString(xpdelete.getReason()));
			 	    			tx.setRelatednumber(xpdelete.getNumber());
			 	    			tx.setReportdate(enddate);
			 	    			tx.setShuoming(xpdelete.getRemark());
			 	    			tx.setZu(changenametozu(name));
			 	    			tx.setRemark("已撤销"+xpdelete.getDate());
			 	    			if(!(xpdelete.getDate().equals(year+month+day)))
			 	    			{
			 	    				txdao.merge(tx);
			 	    				ttnew.setStatus("1");
			 	    			}
			 	    			else
			 	    			{
			 	    				String sql = "delete from t_tempxiaxian  where relatednumber = '"+lpro.getNumber()+"' and name = '"+name+"'";
			 	    				session.createSQLQuery(sql).executeUpdate();
			 	    			}
			 	    			
			 	 	 		}
			 	 	 		
			 	 	 	 }
			 	 	 	    
	 	    		   }
	 	    	   }
	 	    	   if((lpro.getNumber().contains("QJSQ"))&&(lpro.getRemark().contains("已撤销")))
	 	    	   {
	 	    		   LeavePage lpdelete = lpdao.findByNumber(lpro.getNumber());
	 	    		   String newnumber = lpdelete.getApplicant();
	 	    		   ui = uidao.findByNewNumber(newnumber);
	 	    		   String name = ui.getUsername();
	 	    		   String position = ui.getPosition();
		 	    		String chu = position.substring(2, 3);
		 	 	 	    String zhi = position.substring(0, 1);
		 	 	 	    String zu = position.substring(4, 5);
		 	 	 	    String opnumber = ui.getOpnumber();
		 	 	 	    TTempxiaxian txtt = new TTempxiaxian();
	 	    			txtt = txdao.findDeleteByNumber(lpro.getNumber());
	 	    			TTempxiaxian txcun = new TTempxiaxian();
	 	    			txcun = txdao.findAllByNumber(lpro.getNumber());
		 	 	 	 if((chu.equals("2")||chu.equals("6")||chu.equals("3"))&&!(zhi.equals("2"))&&!(zhi.equals("1")))
		 	 	 	 {
		 	 	 		    if((txtt==null)&&(txcun!=null))
		 	 	 		    {
		 	 	 		    	tx.setBegindate(lpdelete.getBegindate());
			 	    			tx.setChu(chuToName(chu));
			 	    			tx.setEnddate(lpdelete.getEnddate());
			 	    			tx.setName(ui.getUsername());
			 	    			tx.setOpnumber(opnumber);
			 	    			//tx.setOpzu( );
			 	    			tx.setOpzu(changenametozu(name));
			 	    			tx.setReason("请休假");
			 	    			tx.setRelatednumber(lpdelete.getNumber());
			 	    			tx.setReportdate(enddate);
			 	    			tx.setShuoming(lpdelete.getRemark());
			 	    			tx.setZu(changenametozu(name));
			 	    			tx.setRemark("已撤销"+lpdelete.getDate());
			 	    			if(!(lpdelete.getDate().equals(year+month+day)))
			 	    			{
			 	    				txdao.merge(tx);
			 	    				ttnew.setStatus("1");
			 	    			}
			 	    			else
			 	    			{
			 	    				String sql = "delete from t_tempxiaxian where relatednumber = '"+lpro.getNumber()+"'";
			 	    				session.createSQLQuery(sql).executeUpdate();
			 	    			}
			 	    			
		 	 	 		    }
		 	 	 		    
		 	 	 	 }
		 	 	 	   
	 	    	   }
	 	       }
	 	    }
		ttdao.merge(ttnew);
		TUpdateTime ttstatus = new TUpdateTime();
		ttstatus = ttdao.findAllByLastId();
		tip = ttstatus.getStatus();
	    trans.commit();
		session.flush();
		session.clear();
		session.close();
	    return "success";
	}
	
	public String changenametozu(String name)
	{
		int num ;
		String zu="无" ;
		UserInfoDAO uizudao = new UserInfoDAO();
		UserInfo uizu = uizudao.findByName(name);
		String position = uizu.getPosition();
		String chu = position.substring(2, 3);
		if(chu.equals("2"))
		{
			zu="稽核团队";
			
		}
		else
		{
			ScpbTeam st = new ScpbTeam();
			ScpbTeamDAO stdao = new ScpbTeamDAO();
			st = stdao.findMaxNum();
			num = st.getNum();
			List<ScpbTeam> list = stdao.findAllItemMaxNum(num);
			for(int i =0;i<list.size();i++)
			{
				ScpbTeam stt = list.get(i);
				if(stt.getLeader().equals(name))
				{
					zu = numtoname(stt.getNo());
					break;
				}
				else
				{
					String []names = stt.getMember().split("、");
					for(int j=0;j<names.length;j++)
					{
						if(names[j].equals(name))
						{
							zu = numtoname(stt.getNo());
							break;
						}
						
					}
				}
			}
		}
		return zu;
		
	}
	
	public Date yeartoYM (String time)
	{
		int year = Integer.parseInt(time.substring(0, 4))-1900;
		int month = Integer.parseInt(time.substring(4, 6))-1;
		int day = Integer.parseInt(time.substring(6, 8));
		Date yearYM = new Date(year,month,day);
		return yearYM;
		
	}
	public String numtoname(int num)
	{
		String zuname="无";
		if(num==1)
		{
			zuname="排班一组";
		}
		if(num==2)
		{
			zuname="排班二组";
		}
		if(num==3)
		{
			zuname="排班三组";
		}
		if(num==4)
		{
			zuname="排班四组";
		}
		if(num==5)
		{
			zuname="排班五组";
		}
		if(num==6)
		{
			zuname="排班六组";
		}
		if(num==7)
		{
			zuname="外汇专班三组";
		}
		if(num==8)
		{
			zuname="外汇专班四组";
		}
		if(num==9)
		{
			zuname="外汇专班五组";
		}
		return zuname;
	}
	
	public String typetoname(int num)
	{
		String name="";
		if(num==1)
		{
			name="病假";
		}
		if(num==2)
		{
			name="年休假";
		}
		if(num==3)
		{
			name="事假";
		}
		if(num==4)
		{
			name="婚假";
		}
		if(num==5)
		{
			name="产假（或陪护假）";
		}
		if(num==6)
		{
			name="探亲假（配偶）";
		}
		if(num==7)
		{
			name="探亲假（父母）";
		}
		if(num==8)
		{
			name="丧假";
		}
		if(num==9)
		{
			name="工伤假";
		}
		if(num==10)
		{
			name="公假";
		}
		if(num==11)
		{
			name="加班调休";
		}
		if(num==12)
		{
			name="产检";
		}
		if(num==13)
		{
			name="陪考假";
		}
		if(num==14)
		{
			name="哺乳假";
		}
		return name;
	}
	public static String chuToName(String chu)
	{
		if(chu.equals("1"))
		{
			return "综合与生产管理处";
		}
		else if(chu.equals("2"))
		{
			return "合规与监督二处";
		}
		else if(chu.equals("3"))
		{
			return "通用业务二处";
		}
		else if(chu.equals("4"))
		{
			return "员工响应团队";
		}
		else if(chu.equals("5"))
		{
			return "研发支持二处";
		}
		else if(chu.equals("6"))
		{
			return "专业处理二处";
		}
		else
		{
			return "";
		}
	}
	
	public static String ygxxReasonToString(Integer type)
	{
		String result = "";
		if(type==1)
		{
			result = "优化创新";
		}
		else if(type==2)
		{
			result = "项目借调";
		}
		else if(type==3)
		{
			result = "外出公干";
		}
		else if(type==4)
		{
			result = "员工培训";
		}
		else if(type==5)
		{
			result = "中心检查";
		}
		else if(type==6)
		{
			result = "业务监控";
		}
		else if(type==7)
		{
			result = "临时抽调";
		}
		else if(type==8)
		{
			result = "轮岗实习";
		}
		else if(type==9)
		{
			result = "加班调休";
		}
		else if(type==10)
		{
			result = "党团工会";
		}
		else if(type==11)
		{
			result = "产量下线";
		}
		else if(type==12)
		{
			result = "组长下线";
		}
		else if(type==20)
		{
			result = "其他";
		}
		return result;
	}
	
	public static int issamemonth(String begindate,String enddate)
	{
		String begindateYM = begindate.substring(0, 6);
		String enddateYM = enddate.substring(0, 6);
		if(!begindateYM.equals(enddateYM))
		{
		    return 0;	
		}
		else
		{
			return 1;
		}
		
		
	}
	
	public static int issameyear(String begindate,String enddate)
	{
		String begindateY = begindate.substring(0, 4);
		String enddateY = enddate.substring(0, 4);
		if(!begindateY.equals(enddateY))
		{
		    return 0;	
		}
		else
		{
			return 1;
		}
				
	}
}
