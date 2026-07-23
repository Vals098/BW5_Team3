package epic_energy.BW5_Team3.services;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailgunService {

    @Value("${mailgun.api.key}")
    private String apiKey;

    @Value("${mailgun.domain}")
    private String domain;

    public JsonNode sendEmail(String to, String subject, String bodyText) {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + domain + "/messages")
                .basicAuth("api", apiKey)
                .queryString("from", "Epic Energy Services <postmaster@" + domain + ">")
                .queryString("to", to)
                .queryString("subject", subject)
                .queryString("text", bodyText)
                .asJson();

        return request.getBody();
    }
}