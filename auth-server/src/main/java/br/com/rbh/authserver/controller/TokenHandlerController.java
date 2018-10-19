package br.com.rbh.authserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TokenHandlerController {

	@RequestMapping("/token-handler")
	public String tokenHandler() {
		return "token-handler";
	}
}
