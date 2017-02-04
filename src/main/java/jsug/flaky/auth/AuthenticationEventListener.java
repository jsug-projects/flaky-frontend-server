package jsug.flaky.auth;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import jsug.flaky.FlakyUser;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AuthenticationEventListener {

	private final AccessTokenMapping accessTokenMapping;

	@EventListener
	public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
		OAuth2Authentication authentication = (OAuth2Authentication) event
				.getAuthentication();
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication
				.getDetails();
		String token = details.getTokenValue();
		FlakyUser user = (FlakyUser) authentication.getPrincipal();
		accessTokenMapping.putUser(token, user);
	}
}
