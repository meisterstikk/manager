package demopubsub;

import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import demopubsub.external.Stock;
import demopubsub.external.StockService;

import java.util.List;
import java.util.Date;
@Entity
@Table(name="Manager_table")
public class Manager {

    @Id
    @GeneratedValue
    private Long id;
    private Long applyId;
    private String empNo;
    private String applyStatus;
    private Long mouseId;
    private Integer qty;
    private Date startDate;
    private Date endDate;

    
    //신청건이 접수되어 manager의 객체가 생성된 상태 : 특별히 진행할 내용은 없음
    @PostPersist
    public void onPostPersist(){
        //rentAccept 이벤트 발생
        RentSubmitted rentSubmitted = new RentSubmitted();
        BeanUtils.copyProperties(this, rentSubmitted);
        rentSubmitted.publishAfterCommit();
    }

    //신청건의 상태가 변경된 후에 실행되는 것
    //1. 신청건의 상태를 "승인됨"으로 변경한 경우 실행
    //2. 신청건의 상태가 "대여됨"으로 변경한 경우 실행
    //3. 신청건의 상태가 "취소신청됨"으로 변경한 경우 실행

    @PostUpdate
    public void onPostUpdate(){
        try{
    //1. 신청건의 상태를 "승인됨ACCEPTED"으로 변경한 경우 실행
            if(this.getApplyStatus().equals("ACCEPTED")) {
                RentAccepted rentAccepted = new RentAccepted();
                System.out.println("[HNR_DEBUG] ==================================");
                System.out.println("[HNR_DEBUG] ==================================");
                System.out.println("[HNR_DEBUG] ==================================");
                System.out.println("[HNR_DEBUG] ==================================");
                System.out.println("[HNR_DEBUG] ==================================");
                System.out.println("[HNR_DEBUG] ==================================");
                
                System.out.println("======= getMouseId() == " + getMouseId()+"    qty == "+getQty());
                //ManagerApplication의 feign 사용
                StockService stockService = ManagerApplication.applicationContext.getBean(StockService.class);
                Integer curStock = 0;
                try {
                    curStock = stockService.getStock(mouseId).getQty();}
                    catch (Exception e){
                        this.setApplyStatus("ERROR");
                    System.out.println("[ERROR] ==================================");
                    System.out.println("[ERROR] ==================================");
                    BeanUtils.copyProperties(this, rentAccepted);
                    rentAccepted.publishAfterCommit();
                    };

                if(curStock < this.getQty() ){
                    this.setApplyStatus("PENDING");

                    System.out.println("[REQ QTY] =================================="+ this.getQty().toString());
                    System.out.println("[RES QTY] =================================="+ curStock.toString());
                    System.out.println("[PENDING] ==================================");
                    System.out.println("[PENDING] ==================================");
                    BeanUtils.copyProperties(this, rentAccepted);
                    rentAccepted.publishAfterCommit();
                } else{
                    //수량 차감
                    System.out.println("[ACCEPTED] ==================================");
                    System.out.println("[ACCEPTED] ==================================");
                    
                    Stock stock = new Stock();
                    stock.setId(this.getMouseId());
                    stock.setQty(this.getQty());
                    stockService.decreaseStock(stock);
                    System.out.println("[STOCK REDUCED] ==================================");
                    System.out.println("[STOCK REDUCED] ==================================");

                    BeanUtils.copyProperties(this, rentAccepted);
                    rentAccepted.publishAfterCommit();
               }
                System.out.println("[REQ QTY] =================================="+ this.getQty().toString());
                System.out.println("[RES QTY] =================================="+ curStock.toString());

                System.out.println("[HNR_DEBUG] ==================================");
                System.out.println("[HNR_DEBUG] ==================================");

                }
                

    //2. 신청건의 상태가 "대여됨RENTED"으로 변경한 경우 그대로 Publish(VIEW만 바뀐다)
    if(this.getApplyStatus().equals("RENTED")) {
        RentStarted rentStarted = new RentStarted();
        BeanUtils.copyProperties(this, rentStarted);
        rentStarted.publishAfterCommit();
    }
    //3. 신청건의 상태가 "취소신청됨"으로 변경한 경우 실행
    if(this.getApplyStatus().equals("RETURNED")) {

        ReturnAccept returnAccept = new ReturnAccept();
        BeanUtils.copyProperties(this, returnAccept);
        returnAccept.publishAfterCommit();
    }

    } catch (Exception e){
                    e.printStackTrace();
                    this.setApplyStatus("ERROR");
                    System.out.println("[ERROR] ==================================");
                    System.out.println("[ERROR] ==================================");
                }


    }


    public Long getId() {
        return id;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }
    public Long getApplyId() {
        return applyId;
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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }




}
