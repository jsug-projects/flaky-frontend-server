package jsug.flaky.auth;

import org.springframework.http.ResponseEntity;
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
		System.out.println(authorization);
		String token = authorization.split(" ")[1];
		return accessTokenMapping.getUser(token).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}
