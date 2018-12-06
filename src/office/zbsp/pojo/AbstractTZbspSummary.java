package office.zbsp.pojo;

/**
 * AbstractTZbspSummary entity provides the base persistence definition of the
 * TZbspSummary entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTZbspSummary implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer year;
	private String name;
	private String newnumber;
	private String position;
	private Integer chu;
	private Integer times;
	private Double days;
	private Double didays;
	private String remark;

	// Constructors

	/** default constructor */
	public AbstractTZbspSummary() {
	}

	/** full constructor */
	public AbstractTZbspSummary(Integer year, String name, String newnumber,
			String position, Integer chu, Integer times, Double days,
			Double didays, String remark) {
		this.year = year;
		this.name = name;
		this.newnumber = newnumber;
		this.position = position;
		this.chu = chu;
		this.times = times;
		this.days = days;
		this.didays = didays;
		this.remark = remark;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNewnumber() {
		return this.newnumber;
	}

	public void setNewnumber(String newnumber) {
		this.newnumber = newnumber;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getChu() {
		return this.chu;
	}

	public void setChu(Integer chu) {
		this.chu = chu;
	}

	public Integer getTimes() {
		return this.times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Double getDays() {
		return this.days;
	}

	public void setDays(Double days) {
		this.days = days;
	}

	public Double getDidays() {
		return this.didays;
	}

	public void setDidays(Double didays) {
		this.didays = didays;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}