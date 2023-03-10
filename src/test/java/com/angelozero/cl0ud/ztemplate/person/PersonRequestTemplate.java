package com.angelozero.cl0ud.ztemplate.person;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.angelozero.cl0ud.entrypoint.rest.request.PersonRequest;

public class PersonRequestTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(PersonRequest.class)
                .addTemplate("valid PersonRequest", new Rule() {
                    {
                        add("name", firstName());
                        add("age", random(12, 2, 32));
                    }
                });
    }
}
