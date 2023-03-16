package com.angelozero.cl0ud.ztemplate.jwt;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.angelozero.cl0ud.jwt.service.dao.User;

import java.util.Random;
import java.util.UUID;

public class UserTemplate implements TemplateLoader {

    public static final String VALID_USER = "valid User";

    @Override
    public void load() {
        Fixture.of(User.class)
                .addTemplate(VALID_USER, new Rule() {
                    {
                        add("id", 10);
                        add("email", UUID.randomUUID().toString());
                        add("fullname", firstName());
                        add("password", UUID.randomUUID().toString());
                        add("accountNonExpired", new Random().nextBoolean());
                        add("accountNonLocked", new Random().nextBoolean());
                        add("credentialsNonExpired", new Random().nextBoolean());
                        add("enable", new Random().nextBoolean());
                        add("role", UUID.randomUUID().toString());
                    }
                });
    }
}
