package office.uass.action;

import office.uass.dao.UassCostHnDAO;
import office.uass.dao.UassCostWbDAO;
import office.uass.dao.UassJiheDAO;
import office.uass.dao.UassPtDAO;
import office.uass.pojo.UassCostHn;
import office.uass.pojo.UassCostWb;
import office.uass.pojo.UassJihe;
import office.uass.pojo.UassPt;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ccb.hibernate.HibernateSessionFactory;

public class UassQueren {
	private String message;
	private String number;
	private String remark;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String execute() throws Exception
	{
		UassCostHnDAO uchdao = new UassCostHnDAO();
		String result = "success";
		message = "处理成功！";
		Session session = HibernateSessionFactory.getSession();
 	    Transaction trans=session.beginTransaction();
		if(number!=null)
		{
			if(number.contains("USPT"))
			{
				UassPtDAO updao = new UassPtDAO();
				UassPt up = updao.findAllByNumber(number);
				up.setStatus(4);
				updao.merge(up);
				
			}
			else if(number.contains("USCH"))
			{
				UassCostHnDAO updao = new UassCostHnDAO();
				UassCostHn up = updao.findAllByNumber(number);
				if(!remark.isEmpty())
				{
					up.setRemark(remark);
				}
				up.setStatus(4);
				updao.merge(up);
				uchdao.submitUassCostHn(number);
			}
			else if(number.contains("USCW"))
			{
				UassCostWbDAO updao = new UassCostWbDAO();
				UassCostWb up = updao.findAllByNumber(number);
				up.setStatus(4);
				updao.merge(up);
			}
			else if(number.contains("USJH"))
			{
				UassJiheDAO updao = new UassJiheDAO();
				UassJihe up = updao.findAllByNumber(number);
				up.setStatus(4);
				updao.merge(up);
			}
		}
		trans.commit();
		session.flush();
		session.clear();
		session.close();
		return result;
	}
}
