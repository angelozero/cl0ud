package com.angelozero.cl0ud.ztemplate.jwt;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;

import java.util.Random;
import java.util.UUID;

import static br.com.six2six.bfgex.RandomGen.email;

public class UserEntityTemplate implements TemplateLoader {

    public static final String VALID_USER_ENTITY = "valid User Entity";

    @Override
    public void load() {
        Fixture.of(UserEntity.class)
                .addTemplate(VALID_USER_ENTITY, new Rule() {
                    {
                        add("email", email());
                        add("fullname", firstName());
                        add("password", UUID.randomUUID().toString());
                        add("accountNonExpired", true);
                        add("accountNonLocked",true);
                        add("credentialsNonExpired", true);
                        add("enable", true);
                        add("role", "ADMIN");
                    }
                });
    }
}
