package office.uass.action;

import java.util.List;

import office.leave.dao.LeaveProcessDAO;
import office.leave.pojo.LeaveProcess;
import office.process.action.FindNextUnder;
import office.process.dao.ProcessDAO;
import office.process.pojo.Process;
import office.uass.dao.UassCostHnDAO;
import office.uass.dao.UassCostHnPeopleDAO;
import office.uass.dao.UassPtDAO;
import office.uass.dao.UassPtPeopleDAO;
import office.uass.pojo.UassCostHn;
import office.uass.pojo.UassCostHnPeople;
import office.uass.pojo.UassPt;
import office.uass.pojo.UassPtPeople;
import office.userinfo.dao.UserInfoDAO;
import office.userinfo.pojo.UserInfo;
import office.util.DateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ccb.hibernate.HibernateSessionFactory;

public class UschUnderDetail {
	private String number;
	private String undersign;
	private UassCostHn up;
	private List<LeaveProcess> listlp;
	private List<UserInfo> listui;
	private List<UassCostHnPeople> listupp;
	private int youshenpi;
	private UserInfo ui;
	private String position;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getUndersign() {
		return undersign;
	}
	public void setUndersign(String undersign) {
		this.undersign = undersign;
	}
	public UassCostHn getUp() {
		return up;
	}
	public void setUp(UassCostHn up) {
		this.up = up;
	}
	public List<LeaveProcess> getListlp() {
		return listlp;
	}
	public void setListlp(List<LeaveProcess> listlp) {
		this.listlp = listlp;
	}
	public List<UserInfo> getListui() {
		return listui;
	}
	public void setListui(List<UserInfo> listui) {
		this.listui = listui;
	}
	public List<UassCostHnPeople> getListupp() {
		return listupp;
	}
	public void setListupp(List<UassCostHnPeople> listupp) {
		this.listupp = listupp;
	}
	public int getYoushenpi() {
		return youshenpi;
	}
	public void setYoushenpi(int youshenpi) {
		this.youshenpi = youshenpi;
	}
	public UserInfo getUi() {
		return ui;
	}
	public void setUi(UserInfo ui) {
		this.ui = ui;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String execute() throws Exception {
		DateUtil du = new DateUtil();
		UassCostHnDAO updao = new UassCostHnDAO();
		UassCostHnPeopleDAO uppdao = new UassCostHnPeopleDAO();
		LeaveProcessDAO lpdao = new LeaveProcessDAO();
		FindNextUnder fnu = new FindNextUnder();
		ProcessDAO pdao = new ProcessDAO();
		UserInfoDAO uidao = new  UserInfoDAO();
		Session session = HibernateSessionFactory.getSession();
		Transaction trans = session.beginTransaction();

		up = updao.findAllByNumber(number);
		listlp = lpdao.findAllByNumber(number);
		listupp = uppdao.findAllByNumber(number);
		listui = fnu.findNextUnder("USCH", number);
		ui = uidao.findByNewNumber(up.getInitiator());
		position = ui.getPosition();
		Process p = pdao.findByItemAndApplicant("USCH", up.getProcess());

		int jindulength = up.getJindu().length();
		String proc = p.getProcess();

		undersign = proc.substring(jindulength, jindulength + 1);

		if (listui.isEmpty()) {
			youshenpi = 0;
		} else {
			youshenpi = 1;
		}
		trans.commit();
		session.flush();
		session.clear();
		session.close();
		return "success";
	}
}
