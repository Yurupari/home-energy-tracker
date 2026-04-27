package com.yurupari.common_data.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig {

    @Value("${documentation.contact.url}")
    private String contactUrl;

    @Value("${documentation.license.name}")
    private String licenseName;

    @Value("${documentation.license.url}")
    private String licenseUrl;

    public OpenAPI serviceApiDocs(
            String title,
            String description,
            String version
    ) {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .description(description)
                        .contact(getContact())
                        .license(getLicense())
                        .version(version));
    }

    private Contact getContact() {
        var contact = new Contact();
        contact.setUrl(contactUrl);

        return contact;
    }

    private License getLicense() {
        var license = new License();
        license.setName(licenseName);
        license.setUrl(licenseUrl);

        return license;
    }
}
