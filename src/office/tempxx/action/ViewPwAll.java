package office.tempxx.action;
import java.util.List;

import office.tempxx.pojo.TPeoplework;
import office.userinfo.dao.UserInfoDAO;
import office.userinfo.pojo.UserInfo;
import office.util.Util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ccb.hibernate.HibernateSessionFactory;
public class ViewPwAll {
	private static final Log log = LogFactory.getLog(TPeoplework.class);
	private String newnumber;// 新一代员工编号
	private List<TPeoplework> list;
	private String title;
	private String name;
	private String begindate;
	private String enddate;
	private String strtemp;
	private int zhuan;
	private String zhiwu;
	private String type;
	private String RadioGroup1;
	private String RadioGroup2;
	private String isdai;
	private String remark;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsdai() {
		return isdai;
	}

	public void setIsdai(String isdai) {
		this.isdai = isdai;
	}

	public String getZhiwu() {
		return zhiwu;
	}

	public void setZhiwu(String zhiwu) {
		this.zhiwu = zhiwu;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRadioGroup1() {
		return RadioGroup1;
	}

	public void setRadioGroup1(String radioGroup1) {
		RadioGroup1 = radioGroup1;
	}

	public String getRadioGroup2() {
		return RadioGroup2;
	}

	public void setRadioGroup2(String radioGroup2) {
		RadioGroup2 = radioGroup2;
	}

	public List<TPeoplework> getList() {
		return list;
	}

	public void setList(List<TPeoplework> list) {
		this.list = list;
	}

	public int getZhuan() {
		return zhuan;
	}

	public void setZhuan(int zhuan) {
		this.zhuan = zhuan;
	}

	public String getNewnumber() {
		return newnumber;
	}

	public void setNewnumber(String newnumber) {
		this.newnumber = newnumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getStrtemp() {
		return strtemp;
	}

	public void setStrtemp(String strtemp) {
		this.strtemp = strtemp;
	}


	public String execute() throws Exception
	{
		String result = "success";
		UserInfoDAO uidao = new UserInfoDAO();
		UserInfo ui = new UserInfo();
		Query query;
		String hql = "";
		String position = "";
		String bd = "";
		String ed = "";
		String un = "";
		//title = "统计查询-明细查询";
		Session session = HibernateSessionFactory.getSession();
		Transaction trans = session.beginTransaction();
		try {
			ui = uidao.findByNewNumber(newnumber);
			if(ui!=null)
			{
				position = ui.getPosition();
			}
			
			hql = "from TPeoplework as tw where 1=1";
			if(name!=null&&zhuan==1)
			{
				strtemp = new String(name.getBytes("ISO8859-1"),"UTF-8");
			}
			else
			{
				strtemp = name;
			}
			if((strtemp!=null)&&(!strtemp.equals("")))
			{	
				hql +=" and tw.name = '"+strtemp+"'";
			}
			if(begindate!=null&&!begindate.equals("")&&enddate!=null&&!enddate.equals(""))
			{
				bd = begindate.replace("-","");
				ed = enddate.replace("-", "");
				hql += " and tw.date>='"+bd+"' and tw.date<='"+ed+"'";
			}
		
			
			hql += " order by tw.id";
			System.out.println(hql);
			query = session.createQuery(hql);
			list = query.list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			trans.commit();
			session.flush();
			session.clear();
			session.close();
		}
		
		return result;
	}
	
}
