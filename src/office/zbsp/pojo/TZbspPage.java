package office.zbsp.pojo;

/**
 * TZbspPage entity. @author MyEclipse Persistence Tools
 */
public class TZbspPage extends AbstractTZbspPage implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TZbspPage() {
	}

	/** full constructor */
	public TZbspPage(String number, Integer process, String jindu, String date,
			Integer status, String preunder, String thisunder,
			String initiator, String applicant, String people, String chu,
			String zu, String tel, String reason, String begindate,
			String enddate, Integer halfday, Double days, Double jbdays,
			Double didays, String remark) {
		super(number, process, jindu, date, status, preunder, thisunder,
				initiator, applicant, people, chu, zu, tel, reason, begindate,
				enddate, halfday, days, jbdays, didays, remark);
	}

}
