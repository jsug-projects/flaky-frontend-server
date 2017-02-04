package jsug.flaky;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	@GetMapping("/")
	String hello(@AuthenticationPrincipal FlakyUser flakyUser, Model model) {
		model.addAttribute("user", flakyUser);
		return "index";
	}

	@GetMapping("/event")
	String event(@AuthenticationPrincipal FlakyUser flakyUser) {
		return "event";
	}
}
