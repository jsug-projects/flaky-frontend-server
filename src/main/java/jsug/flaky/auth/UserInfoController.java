package jsug.flaky.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserInfoController {
	private final AccessTokenMapping accessTokenMapping;

	@GetMapping("userinfo")
	ResponseEntity<?> userinfo(@RequestHeader("Authorization") String authorization) {
		String token = authorization.split(" ")[1];
		return accessTokenMapping.getUser(token).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("me")
	String userinfo(OAuth2Authentication authentication) {
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication
				.getDetails();
		return details.getTokenValue();
	}
}
