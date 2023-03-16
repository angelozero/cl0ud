package com.angelozero.cl0ud.ztemplate.person;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.angelozero.cl0ud.entrypoint.rest.response.PersonResponse;

public class PersonResponseTemplate implements TemplateLoader {

    public static final String VALID_PERSON_RESPONSE = "valid PersonResponse";

    @Override
    public void load() {
        Fixture.of(PersonResponse.class)
                .addTemplate(VALID_PERSON_RESPONSE, new Rule() {
                    {
                        add("id", random(Long.class, range(1L, 200L)));
                        add("name", firstName());
                        add("age", random(12, 2, 32));
                    }
                });
    }
}
