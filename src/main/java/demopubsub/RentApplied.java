package demopubsub;
import java.util.Date;
public class RentApplied extends AbstractEvent {

    private Long id;
    private String empno;
    private Long mouseId;
    private Integer qty;
    private String applyStatus;
    private Date startDate;
    private Date endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getEmpno() {
        return empno;
    }

    public void setEmpno(String empno) {
        this.empno = empno;
    }
    public Long getMouseId() {
        return mouseId;
    }

    public void setMouseId(Long mouseId) {
        this.mouseId = mouseId;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }
    public Date getStartDay() {
        return startDate;
    }

    public void setStartDay(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDay() {
        return endDate;
    }

    public void setEndDay(Date endDate) {
        this.endDate = endDate;
    }
}