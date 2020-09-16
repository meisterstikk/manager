package demopubsub;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;
@Entity
@Table(name="Manager_table")
public class Manager {

    @Id
    @GeneratedValue
    private Long id;
    private String empNo;
    private String applyStatus;
    private Long mouseId;
    private Date startDate;
    private Date endDate;

    @PostPersist
    public void onPostPersist(){
        RentAccepted rentAccepted = new RentAccepted();
        BeanUtils.copyProperties(this, rentAccepted);
        rentAccepted.publishAfterCommit();


    }

    @PostUpdate
    public void onPostUpdate(){
        ReturnAccept returnAccept = new ReturnAccept();
        BeanUtils.copyProperties(this, returnAccept);
        returnAccept.publishAfterCommit();


        RentStarted rentStarted = new RentStarted();
        BeanUtils.copyProperties(this, rentStarted);
        rentStarted.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }
    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }
    public Long getMouseId() {
        return mouseId;
    }

    public void setMouseId(Long mouseId) {
        this.mouseId = mouseId;
    }
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }




}
