package jun.th.custome_filter;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class Encoding {
  public static void main(String args[]){
    String A= new StandardPasswordEncoder().encode("password");
    System.out.println(A);
  }
}
