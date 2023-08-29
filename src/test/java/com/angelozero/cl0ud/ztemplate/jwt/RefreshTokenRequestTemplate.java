package com.angelozero.cl0ud.ztemplate.jwt;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.RefreshTokenRequest;

import java.util.UUID;

public class RefreshTokenRequestTemplate implements TemplateLoader {

    public static final String VALID_REFRESH_TOKEN_REQUEST = "valid AuthenticationRequest";

    @Override
    public void load() {
        Fixture.of(RefreshTokenRequest.class)
                .addTemplate(VALID_REFRESH_TOKEN_REQUEST, new Rule() {
                    {
                        add("token", UUID.randomUUID().toString());
                    }
                });
    }
}
