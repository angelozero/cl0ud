package com.angelozero.cl0ud.ztemplate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.usecase.model.Person;

public class PersonEntityTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(PersonEntity.class)
                .addTemplate("valid PersonEntity", new Rule() {
                    {
                        add("id", random(Long.class, range(1L, 200L)));
                        add("name", firstName());
                        add("age", random(12, 2, 32));
                    }
                });
    }
}
