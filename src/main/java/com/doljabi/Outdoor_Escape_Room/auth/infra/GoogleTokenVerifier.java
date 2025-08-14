package com.doljabi.Outdoor_Escape_Room.auth.infra;

import com.doljabi.Outdoor_Escape_Room.common.error.AppException;
import com.doljabi.Outdoor_Escape_Room.common.error.GlobalErrorCode;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Component
public class GoogleTokenVerifier {
    private final GoogleIdTokenVerifier verifier;

    public GoogleTokenVerifier(@Value("${oauth.google.client-id}") String clientId){
        this.verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance()
        ).setAudience(Collections.singleton(clientId)).build();
    }

    public String verify(String idToken){
        try{
            GoogleIdToken token = verifier.verify(idToken);
            if(token == null){
                throw new AppException(GlobalErrorCode.INVALID_ID_TOKEN);
            }
            return token.getPayload().getSubject();
        } catch (GeneralSecurityException | IOException e){
            throw new AppException(GlobalErrorCode.INVALID_ID_TOKEN);
        }
    }
}
