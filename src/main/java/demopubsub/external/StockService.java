
package demopubsub.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="feignstock", url="${feignstock.url}")
public interface StockService {

    // JSON, POST 방식으로 REQ 발송/수신
//    @RequestMapping(method= RequestMethod.POST, path="/stocks")
//    public void stockCheckService(@PathVariable("mouseId") Long mouseId, @PathVariable("qty") Integer qty); // 변수로 마우스 품목번호, 수량받음
    
    
    //예제에서 복사한 것
    //@RequestMapping(method = RequestMethod.GET, value = "/stocks/{mouseId}", consumes =  "application/json")
    @RequestMapping(method = RequestMethod.GET, value = "/stocks/{mouseId}", consumes =  "application/json", produces = "application/json")
    Stock getStock(@PathVariable("mouseId") Long mouseId);

 //   재고 차감 <- 구현 해야되나...시간없으면 PASS!
//    @RequestMapping(method = RequestMethod.PUT, value = "/stocks/decreaseStock/{mouseId}", consumes = "application/json", produces = "application/json")
//    void decreaseStock(@PathVariable("mouseId") Long mouseId, int qty);

    @RequestMapping(method = RequestMethod.POST, value = "/stocks", consumes = "application/json")
    void decreaseStock(Stock stock);

    @RequestMapping(method = RequestMethod.PUT, value = "/stocks/increaseStock/{mouseId}", consumes =  "application/json",produces = "application/json")
    void increaseStock(@PathVariable("mouseId") Long mouseId, Integer qty);
    
    
}