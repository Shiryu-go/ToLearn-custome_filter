package jun.th.custome_filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
public class CustomAuthenticationProvider implements AuthenticationProvider {

  // ユーザ情報リポジトリ
/*   @Autowired
  UserRepository userRepository; */

  // テナント情報リポジトリ

  @Override
  //このAuthenticationはどこから持ってくる奴？空でもいいのかな。principalは今回UserRepository（っていうか今回はエンティティとしてだ！）なんだけれど
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    // CustomAuthenticationFilterクラスから渡されたものを取得
    UserEntity principal =  (UserEntity) authentication.getPrincipal();

    if (principal == null) {
      throw new BadCredentialsException("ユーザー情報が渡されていません。がありません");
    }
    //ここに入ってたUser, SpringSecurityのデフォルト実装のUserじゃん。
    //
    //User user = userRepository.findByEmail(principal.getEmail());
    //emailはここに入っているだけで、
    UserDetails user = User.builder().username("gorira@uho.com").password("36b671c35323b4467ab83baae46e82912895221ca677366dbf37b3e262a4baca7e700c7b253c544e").build();
    // ユーザー情報を取得できなかった場合
    if (user == null) {
      throw new BadCredentialsException("ユーザが存在しません");
    }
    //パスワードがまちがっていた場合
    if (!new StandardPasswordEncoder().matches(principal.getPassword(), user.getPassword())) {
      throw new BadCredentialsException("パスワードが間違っています");
    }

    List<GrantedAuthority> authorityList = new ArrayList<>();

    // 権限付与処理

    UserDetails loginUser = User.builder().username(user.getUsername()).password(user.getPassword()).build();

    return new UsernamePasswordAuthenticationToken(loginUser, principal.getPassword(),
        authorityList);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }

}
