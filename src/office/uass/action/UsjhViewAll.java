package office.uass.action;

import java.util.ArrayList;
import java.util.List;

import office.uass.pojo.UassCostWb;
import office.uass.pojo.UassJihe;
import office.userinfo.dao.UserInfoDAO;
import office.userinfo.pojo.UserInfo;
import office.util.Util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ccb.hibernate.HibernateSessionFactory;

public class UsjhViewAll {
	private static final Log log = LogFactory.getLog(UscwViewAll.class);
	private String newnumber;// 新一代员工编号
	private List<UassJihe> list;
	private String autho;
	private String name;
	private String begindate;
	private String enddate;
	private String strtemp;
	private int zhuan;
	/**
	* 获得默认的分页大小
	*/
	private int pageSize = Util.pagesize;

	/**
	* 总页数
	*/
	private int totalPages = -1;

	/**
	* 当前页
	*/
	private int currentPage = -1;

	/**
	* 上一页
	*/
	private int previousPage = 1;

	/**
	* 下一页
	*/
	private int nextPage = 1;
	/**
	* 第一页
	*/
	private int firstPage = 1;
	/**
	* 最后一页
	*/
	private int lastPage = 1;
	/**
	* 总记录条数
	*/
	private long totalRows = -1;
	public String getNewnumber() {
		return newnumber;
	}

	public void setNewnumber(String newnumber) {
		this.newnumber = newnumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}

	public String getAutho() {
		return autho;
	}

	public void setAutho(String autho) {
		this.autho = autho;
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

	public int getZhuan() {
		return zhuan;
	}

	public void setZhuan(int zhuan) {
		this.zhuan = zhuan;
	}

	public List<UassJihe> getList() {
		return list;
	}

	public void setList(List<UassJihe> list) {
		this.list = list;
	}

	public String execute() throws Exception
	{
		String result = "success";
		UserInfoDAO uidao = new UserInfoDAO();
		list = new ArrayList<UassJihe>();
		UserInfo ui = new UserInfo();
		Query query;
		String hql = "";
		String position = "";
		String zhi = "";
		String chu = "";
		String bd = "";
		String ed = "";
		Session session = HibernateSessionFactory.getSession();
		Transaction trans = session.beginTransaction();
		try {
			ui = uidao.findByNewNumber(newnumber);
			position = ui.getPosition();
			autho = ui.getAuthority();
			zhi = position.substring(0,1);
			chu = position.substring(2,3);
			hql = "select * from t_uass_jihe as uc";
			if(name!=null&&zhuan==1)
			{
				strtemp = new String(name.getBytes("ISO8859-1"),"UTF-8");
			}
			else
			{
				strtemp = name;
			}
			if(strtemp==null)
			{
				strtemp="";
			}
			hql +=" where uc.content like '%"+strtemp+"%'";
			if(begindate!=null&&!begindate.equals("")&&enddate!=null&&!enddate.equals(""))
			{
				bd = begindate.replace("-","");
				ed = enddate.replace("-", "");
				hql += " and uc.date>='"+bd+"' and uc.date<='"+ed+"'";
			}
			if(chu.equals("1")||zhi.equals("0")||zhi.equals("1")||autho.contains("I")||autho.contains("L")||autho.contains("o"))//主任//处长//管理员看全部
			{
				hql += "";
			}
			else//普通员工
			{
				String newnumber1=newnumber.substring(0,8);
				hql += " and substr(uc.initiator,1,8)='"+newnumber1+"'";
			}
			hql += " order by locate('6',uc.status), uc.id desc";
			System.out.println(hql);
			query = session.createSQLQuery(hql).addEntity(UassJihe.class);
			query.setFirstResult(pageSize * (currentPage - 1));
			query.setMaxResults(pageSize);
			totalRows = session.createSQLQuery(hql).addEntity(UassJihe.class).list().size();
			initPageProperties();
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
	
	/**
	 * 初始化页面属性<br>
	 * 必须在已获得totalRows值之后再调用该函数<br>
	 * 调用方式为：<br>
	 * 
	 * 给totalRows赋值<br>
	 * 调用initPageProperties(form)方法<br>
	 * 给list赋值<br>
	 * 调用initAttribute(request)方法<br>
	 * 
	 * 该方法在调用查询语句之前调用<br>
	 * pageSize为系统默认的分页的大小，如要更改pageSize，应在掉用setPageSize方法后再调用该方法<br>
	 * 
	 * 
	 */
	protected void initPageProperties() {

		if (totalRows == -1) {
			log.error("没有初始化totalRows参数！");
		}

		firstPage = 1;
		
		currentPage = currentPage <= 1 ? 1 : currentPage;

		totalPages = (totalRows % pageSize == 0) ? ((int) (totalRows / pageSize))
				: ((int) (totalRows / pageSize + 1));

		currentPage = currentPage >= totalPages ? totalPages : currentPage;

		previousPage = currentPage > 1 ? currentPage - 1 : 1;

		nextPage = currentPage < totalPages ? currentPage + 1 : totalPages;

		lastPage = totalPages;
	}
}
