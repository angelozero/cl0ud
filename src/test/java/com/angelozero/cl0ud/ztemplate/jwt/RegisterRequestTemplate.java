package com.angelozero.cl0ud.ztemplate.jwt;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.RegisterRequest;

import java.util.UUID;

public class RegisterRequestTemplate implements TemplateLoader {

    public static final String VALID_REGISTER_REQUEST = "valid RegisterRequest";

    @Override
    public void load() {
        Fixture.of(RegisterRequest.class)
                .addTemplate(VALID_REGISTER_REQUEST, new Rule() {
                    {
                        add("email", UUID.randomUUID().toString());
                        add("fullname", firstName());
                        add("password", UUID.randomUUID().toString());
                        add("role", UUID.randomUUID().toString());
                    }
                });
    }
}
