package demopubsub;

import demopubsub.config.kafka.KafkaProcessor;

import java.util.Optional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    ManagerRepository managerRepository;


    // PuB/SUB 방식으로 호출한다고 하던 최초의 구상
    // 이 경우, 잔여 재고와 무관하게 계속해서 승인이 되는 현상이 나올 수 있음
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentApplied_RentApply(@Payload RentApplied rentApplied){

        if(rentApplied.isMe()){
            //System.out.println("##### listener RentApply : " + rentApplied.toJson());
            System.out.println("######################################################");
            System.out.println("######################################################");
            
            //신청건에는 신청건 Id, 사번, 마우스종류, 수량, 상태(ACCEPTED)를 지정하고 저장
            Manager manager = new Manager();
            manager.setApplyId(rentApplied.getId());
            manager.setEmpNo(rentApplied.getEmpno());
            manager.setMouseId(rentApplied.getMouseId());
            manager.setQty(rentApplied.getQty());
            manager.setApplyStatus("APPLIED");
            
            managerRepository.save(manager);
            
            System.out.println("######################################################");
            System.out.println("######################################################");
            
        }
    }
    

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverStockChanged_ConfirmNotify(@Payload StockChanged stockChanged){

        if(stockChanged.isMe()){
            
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReturnApplied_ReturnApply(@Payload ReturnApplied returnApplied){

        if(returnApplied.isMe()){
            
         //반환 신청건에는 신청건 Id, 사번, 마우스종류, 수량, 상태(RETURN_APPLIED)를 지정하고 저장

            
            Optional<Manager> managerOption = managerRepository.findById(returnApplied.getId());
            
            Manager manager = managerOption.get();
            manager.setApplyStatus("RETURN_APPLIED");
            managerRepository.save(manager);
            
            System.out.println("RETURN_APPLIED========================================");
            System.out.println("######################################################");
            System.out.println("######################################################");
        }
    }

}
