package jun.th.custome_filter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginCheckController {
  @GetMapping("/")
  public String test(){
    return "ログインできてるね";
  }
}
