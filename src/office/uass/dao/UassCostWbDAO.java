package office.uass.dao;
// default package

import ccb.dao.BaseHibernateDAO;
import java.util.List;

import office.uass.pojo.UassCostWb;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for UassCostWb entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .UassCostWb
  * @author MyEclipse Persistence Tools 
 */

public class UassCostWbDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(UassCostWbDAO.class);
		//property constants
	public static final String NUMBER = "number";
	public static final String PROCESS = "process";
	public static final String JINDU = "jindu";
	public static final String DATE = "date";
	public static final String STATUS = "status";
	public static final String PREUNDERTAKE = "preundertake";
	public static final String UNDERTAKE = "undertake";
	public static final String INITIATOR = "initiator";
	public static final String APPLICANT = "applicant";
	public static final String POSITION = "position";
	public static final String TEL = "tel";
	public static final String CONTENT = "content";
	public static final String SXTIME = "sxtime";
	public static final String FILENAME1 = "filename1";
	public static final String FILENAME2 = "filename2";
	public static final String FILENAME3 = "filename3";
	public static final String REMARK = "remark";



    
    public void save(UassCostWb transientInstance) {
        log.debug("saving UassCostWb instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(UassCostWb persistentInstance) {
        log.debug("deleting UassCostWb instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public UassCostWb findById( java.lang.Integer id) {
        log.debug("getting UassCostWb instance with id: " + id);
        try {
            UassCostWb instance = (UassCostWb) getSession()
                    .get("UassCostWb", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(UassCostWb instance) {
        log.debug("finding UassCostWb instance by example");
        try {
            List results = getSession()
                    .createCriteria("UassCostWb")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding UassCostWb instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from UassCostWb as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByNumber(Object number
	) {
		return findByProperty(NUMBER, number
		);
	}
	
	public List findByProcess(Object process
	) {
		return findByProperty(PROCESS, process
		);
	}
	
	public List findByJindu(Object jindu
	) {
		return findByProperty(JINDU, jindu
		);
	}
	
	public List findByDate(Object date
	) {
		return findByProperty(DATE, date
		);
	}
	
	public List findByStatus(Object status
	) {
		return findByProperty(STATUS, status
		);
	}
	
	public List findByPreundertake(Object preundertake
	) {
		return findByProperty(PREUNDERTAKE, preundertake
		);
	}
	
	public List findByUndertake(Object undertake
	) {
		return findByProperty(UNDERTAKE, undertake
		);
	}
	
	public List findByInitiator(Object initiator
	) {
		return findByProperty(INITIATOR, initiator
		);
	}
	
	public List findByApplicant(Object applicant
	) {
		return findByProperty(APPLICANT, applicant
		);
	}
	
	public List findByPosition(Object position
	) {
		return findByProperty(POSITION, position
		);
	}
	
	public List findByTel(Object tel
	) {
		return findByProperty(TEL, tel
		);
	}
	
	public List findByContent(Object content
	) {
		return findByProperty(CONTENT, content
		);
	}
	
	public List findBySxtime(Object sxtime
	) {
		return findByProperty(SXTIME, sxtime
		);
	}
	
	public List findByFilename1(Object filename1
	) {
		return findByProperty(FILENAME1, filename1
		);
	}
	
	public List findByFilename2(Object filename2
	) {
		return findByProperty(FILENAME2, filename2
		);
	}
	
	public List findByFilename3(Object filename3
	) {
		return findByProperty(FILENAME3, filename3
		);
	}
	
	public List findByRemark(Object remark
	) {
		return findByProperty(REMARK, remark
		);
	}
	

	public List findAll() {
		log.debug("finding all UassCostWb instances");
		try {
			String queryString = "from UassCostWb";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public UassCostWb merge(UassCostWb detachedInstance) {
        log.debug("merging UassCostWb instance");
        try {
            UassCostWb result = (UassCostWb) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(UassCostWb instance) {
        log.debug("attaching dirty UassCostWb instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(UassCostWb instance) {
        log.debug("attaching clean UassCostWb instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    public UassCostWb findAllByNumber(String number) {
    	log.debug("finding all UassCostWb instances");
    	try {
    		String queryString = "from UassCostWb as ucw where ucw.number='"+number+"'";
             Query queryObject = getSession().createQuery(queryString);
             List list = queryObject.list();
             if(list.isEmpty())
             {
            	 return null;
             }
             else
             {
            	 return (UassCostWb) list.get(0);
             }
    	} catch (RuntimeException re) {
    		log.error("find all failed", re);
    		throw re;
    	}
    }
}