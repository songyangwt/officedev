package office.zbsp.pojo;

/**
 * TZbspPageDi entity. @author MyEclipse Persistence Tools
 */
public class TZbspPageDi extends AbstractTZbspPageDi implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TZbspPageDi() {
	}

	/** full constructor */
	public TZbspPageDi(String number, String name, String begindate,
			String enddate, Double days, Double jbdays, Double didays) {
		super(number, name, begindate, enddate, days, jbdays, didays);
	}

}
