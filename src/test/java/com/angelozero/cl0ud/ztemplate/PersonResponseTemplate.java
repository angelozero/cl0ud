package com.angelozero.cl0ud.ztemplate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.angelozero.cl0ud.entrypoint.person.rest.response.PersonResponse;

public class PersonResponseTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(PersonResponse.class)
                .addTemplate("valid PersonResponse", new Rule() {
                    {
                        add("id", random(Long.class, range(1L, 200L)));
                        add("name", firstName());
                        add("age", random(12, 2, 32));
                    }
                });
    }
}
