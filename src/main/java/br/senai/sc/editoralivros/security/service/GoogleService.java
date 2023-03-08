package br.senai.sc.editoralivros.security.service;

import br.senai.sc.editoralivros.security.users.UserGoogle;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class GoogleService extends DefaultOAuth2UserService {

    // Converter o usuário do tipo OAuth2User para a nossa classe de usuário
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest){
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return new UserGoogle(oAuth2User);
    }
}
