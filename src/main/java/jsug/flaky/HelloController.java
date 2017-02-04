package jsug.flaky;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	@GetMapping("/event")
	String event(@AuthenticationPrincipal FlakyUser flakyUser) {
		return "event";
	}
}
