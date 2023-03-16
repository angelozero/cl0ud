package com.angelozero.cl0ud.ztemplate.person;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.angelozero.cl0ud.entrypoint.rest.request.PersonRequest;

public class PersonRequestTemplate implements TemplateLoader {

    public static final String VALID_PERSON_REQUEST = "valid PersonRequest";

    @Override
    public void load() {
        Fixture.of(PersonRequest.class)
                .addTemplate(VALID_PERSON_REQUEST, new Rule() {
                    {
                        add("name", firstName());
                        add("age", random(12, 2, 32));
                    }
                });
    }
}
