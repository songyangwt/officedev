package office.zbsp.pojo;

/**
 * TZbspSummary entity. @author MyEclipse Persistence Tools
 */
public class TZbspSummary extends AbstractTZbspSummary implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TZbspSummary() {
	}

	/** full constructor */
	public TZbspSummary(Integer year, String name, String newnumber,
			String position, Integer chu, Integer times, Double days,
			Double didays, String remark) {
		super(year, name, newnumber, position, chu, times, days, didays, remark);
	}

}
