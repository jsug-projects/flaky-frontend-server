package jsug.flaky;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class FlakyPrincipalExtractor implements PrincipalExtractor {
	private final RestTemplate restTemplate;
	private final String membersApi;

	public FlakyPrincipalExtractor(RestTemplate restTemplate,
			@Value("${members.url}") String membersApi) {
		this.restTemplate = restTemplate;
		this.membersApi = membersApi;
	}

	@Override
	public Object extractPrincipal(Map<String, Object> map) {
		String github = getValue(map, "login");
		FlakyUser user;
		try {
			user = restTemplate.getForObject("{path}/search/findByGithub?github={github}",
					FlakyUser.class, membersApi, github);
		}
		catch (HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				// Register new member
				Map<String, Object> newUser = new HashMap<>();
				newUser.put("memberId", UUID.randomUUID());
				newUser.put("github", github);
				newUser.put("company", getValue(map, "company"));
				Optional.ofNullable(map.get("name")).ifPresent(name -> {
					String[] split = String.valueOf(name).split(" ");
					newUser.put("firstName", split[0]);
					newUser.put("lastName", split[1]);
				});
				user = restTemplate.postForObject("{path}", newUser, FlakyUser.class,
						membersApi);
			}
			else {
				throw e;
			}
		}
		return user;
	}

	private String getValue(Map<String, Object> map, String key) {
		return Optional.ofNullable(map.get(key)).map(Object::toString).orElse("");
	}
}
