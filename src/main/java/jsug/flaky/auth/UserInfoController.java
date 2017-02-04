package jsug.flaky.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import jsug.flaky.FlakyUser;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserInfoController {
	private final AccessTokenMapping accessTokenMapping;

	@GetMapping("userinfo")
	ResponseEntity<?> userinfo(@RequestHeader("Authorization") String authorization) {
		String token = authorization.split(" ")[1];
		return accessTokenMapping.getUser(token)
				.map(user -> ResponseEntity.ok(createUserInfo(user)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("me")
	Object me(@AuthenticationPrincipal FlakyUser user) {
		return user;
	}

	@GetMapping("token")
	String token(OAuth2Authentication authentication) {
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication
				.getDetails();
		return details.getTokenValue();
	}

	Map<String, Object> createUserInfo(FlakyUser user) {
		Map<String, Object> map = new HashMap<>();
		map.put("firstName", user.getFirstName());
		map.put("lastName", user.getLastName());
		map.put("memberId", user.getMemberId());
		map.put("user", user.getMemberId());
		return map;
	}
}
