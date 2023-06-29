package jun.th.custome_filter;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomeFilter1 extends UsernamePasswordAuthenticationFilter {

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) {

    try {
      //ここは、適当なユーザー情報を格納するためのクラスにリクエストボディの代入を行う。
      UserEntity customPrincipal = new ObjectMapper().readValue(request.getInputStream(),
          UserEntity.class);

      UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
          customPrincipal, null);
      //UsernamePasswordAuthenticationFilterで元々用意されているメソッド。
      	/* 	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		    authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	      } */
        //UsernamePasswordAuthenticationTokenをauthrequestとして、buildDetails(request)している。
      setDetails(request, authRequest);

      return this.getAuthenticationManager().authenticate(authRequest);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  //AuhtenticationManagerから呼び出しされたAuthenticationProviderの認証がderの認証が成功するとこのメソッドを呼ぶのかSpringコンテナから呼び出しされるのか
  //DockerコンテナとSpringコンテナも違うし難しい…。
  protected void successfulAuthentication(HttpServletRequest req,
      HttpServletResponse res,
      FilterChain chain,
      Authentication auth) {

    SecurityContextHolder.getContext().setAuthentication(auth);

  }
}
