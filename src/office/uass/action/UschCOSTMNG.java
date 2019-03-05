package office.uass.action;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ccb.hibernate.HibernateSessionFactory;

import office.uass.dao.UassCodeDAO;
import office.uass.pojo.UassCode;
import office.uass.pojo.UassCodeBean;

public class UschCOSTMNG {

	private List<UassCode> list;
	public List<UassCode> getList() {
		return list;
	}
	public void setList(List<UassCode> list) {
		this.list = list;
	}
	public String execute() throws Exception
	{
		UassCodeDAO ucdao = new UassCodeDAO();
		Session session = HibernateSessionFactory.getSession();
 	    Transaction trans=session.beginTransaction();
 	   try {
 		   list = ucdao.findAllByOrder();
 	   } catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			trans.commit();
			session.flush();
			session.clear();
			session.close();
		}
		return "success";
	}
}
