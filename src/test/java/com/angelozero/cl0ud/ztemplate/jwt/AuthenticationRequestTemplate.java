package com.angelozero.cl0ud.ztemplate.jwt;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.angelozero.cl0ud.jwt.entrypoint.rest.AuthenticationRequest;

import java.util.UUID;

public class AuthenticationRequestTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(AuthenticationRequest.class)
                .addTemplate("valid AuthenticationRequest", new Rule() {
                    {
                        add("email", UUID.randomUUID().toString());
                        add("password", UUID.randomUUID().toString());
                    }
                });
    }
}
