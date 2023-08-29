package com.angelozero.cl0ud.ztemplate.jwt;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.AuthenticationRequest;

import java.util.UUID;

public class AuthenticationRequestTemplate implements TemplateLoader {

    public static final String VALID_AUTHENTICATION_REQUEST = "valid AuthenticationRequest";

    @Override
    public void load() {
        Fixture.of(AuthenticationRequest.class)
                .addTemplate(VALID_AUTHENTICATION_REQUEST, new Rule() {
                    {
                        add("email", UUID.randomUUID().toString());
                        add("password", UUID.randomUUID().toString());
                    }
                });
    }
}
