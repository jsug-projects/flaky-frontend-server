package jsug.flaky.auth;

import java.util.Optional;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import jsug.flaky.FlakyUser;

@Component
public class AccessTokenMapping {
	private final Cache cache;

	public AccessTokenMapping(CacheManager cacheManager) {
		this.cache = cacheManager.getCache("flakyUser");
	}

	public Optional<FlakyUser> getUser(String token) {
		return Optional.ofNullable(this.cache.get(token, FlakyUser.class));
	}

	public void putUser(String token, FlakyUser user) {
		this.cache.put(token, user);
	}

}
