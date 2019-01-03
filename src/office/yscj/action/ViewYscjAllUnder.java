package office.yscj.action;

import java.util.List;

import office.leave.pojo.LeavePage;
import office.userinfo.dao.UserInfoDAO;
import office.userinfo.pojo.UserInfo;
import office.util.Util;
import office.yscj.pojo.TYscj;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ccb.hibernate.HibernateSessionFactory;

public class ViewYscjAllUnder {
	private static final Log log = LogFactory.getLog(LeavePage.class);
	private String newnumber;
	private List<TYscj> list;
	

	public List<TYscj> getList() {
		return list;
	}

	public void setList(List<TYscj> list) {
		this.list = list;
	}

	public String getNewnumber() {
		return newnumber;
	}

	public void setNewnumber(String newnumber) {
		this.newnumber = newnumber;
	}
	
	public String execute() throws Exception
	{
		UserInfoDAO uidao = new UserInfoDAO();
		Query query;
		String hql = "";
		
		
		
		Session session = HibernateSessionFactory.getSession();
 	    Transaction trans=session.beginTransaction();
		try {
			UserInfo ui = uidao.findByNewNumber(newnumber);
			String newnumber1=newnumber.substring(0,8);
			hql = "from TYscj as ty where (substr(ty.thisunder,1,8)='"+newnumber1+"' and ty.status !='"+4+"') or (substr(ty.initiator,1,8)='"+newnumber1+"' and ty.status in (0,5))order by ty.id desc";
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
		return "success";
	}
	
	
	
}
