package com.angelozero.cl0ud.ztemplate.person;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.angelozero.cl0ud.usecase.model.Person;

public class PersonTemplate implements TemplateLoader {

    public static final String VALID_PERSON = "valid Person";
    public static final String VALID_PERSON_WITHOUT_ID = "valid Person without id";

    @Override
    public void load() {
        Fixture.of(Person.class)
                .addTemplate(VALID_PERSON, new Rule() {
                    {
                        add("id", random(Long.class, range(1L, 200L)));
                        add("name", firstName());
                        add("age", random(12, 2, 32));
                    }
                })
                .addTemplate("valid Person without id", new Rule() {
                    {
                        add("name", firstName());
                        add("age", random(12, 2, 32));
                    }
                });
    }
}
