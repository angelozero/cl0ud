package com.angelozero.cl0ud.ztemplate.jwt;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.angelozero.cl0ud.jwt.entrypoint.rest.AuthenticationResponse;
import com.angelozero.cl0ud.jwt.entrypoint.rest.RegisterRequest;

import java.util.UUID;

public class AuthenticationResponseTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(AuthenticationResponse.class)
                .addTemplate("valid AuthenticationResponse", new Rule() {
                    {
                        add("token", UUID.randomUUID().toString());
                    }
                });
    }
}
