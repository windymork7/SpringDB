package kr.co.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BController
{
	@RequestMapping("/")
	public String home()
	{
		return "home";
	}
}
