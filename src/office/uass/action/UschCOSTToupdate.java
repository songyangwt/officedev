package office.uass.action;

import office.uass.dao.UassCodeDAO;
import office.uass.pojo.UassCode;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ccb.hibernate.HibernateSessionFactory;

public class UschCOSTToupdate {

	private int id;
	private UassCode uc;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UassCode getUc() {
		return uc;
	}
	public void setUc(UassCode uc) {
		this.uc = uc;
	}
	public String execute() throws Exception
	{
		UassCodeDAO ucdao = new UassCodeDAO();
		Session session = HibernateSessionFactory.getSession();
 	    Transaction trans=session.beginTransaction();
 	   try {
 		  uc = ucdao.findAllById(id);
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
