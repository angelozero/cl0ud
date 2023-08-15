package com.angelozero.cl0ud.ztemplate.jwt;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.angelozero.cl0ud.jwt.service.dao.Authentication;

import java.util.UUID;

public class AuthenticationTemplate implements TemplateLoader {

    public static final String VALID_AUTHENTICATION = "valid AuthenticationResponse";

    @Override
    public void load() {
        Fixture.of(Authentication.class)
                .addTemplate(VALID_AUTHENTICATION, new Rule() {
                    {
                        add("token", UUID.randomUUID().toString());
                    }
                });
    }
}
