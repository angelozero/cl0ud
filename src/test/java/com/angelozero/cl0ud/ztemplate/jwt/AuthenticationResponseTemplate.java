package com.angelozero.cl0ud.ztemplate.jwt;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.AuthenticationResponse;

import java.util.UUID;

public class AuthenticationResponseTemplate implements TemplateLoader {

    public static final String VALID_AUTHENTICATION_RESPONSE = "valid AuthenticationResponse";

    @Override
    public void load() {
        Fixture.of(AuthenticationResponse.class)
                .addTemplate(VALID_AUTHENTICATION_RESPONSE, new Rule() {
                    {
                        add("token", UUID.randomUUID().toString());
                    }
                });
    }
}
