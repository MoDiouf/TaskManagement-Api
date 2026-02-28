package task.project.controler;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import task.project.entity.User;
import task.project.services.AuthService;
import task.project.services.JwtService;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

	private final AuthService authservice;
	
	public AuthController(AuthService auth) {
		this.authservice = auth;
	}
	
	@PostMapping("register")
	public ResponseEntity<String> register(@RequestBody User user){
		
		return ResponseEntity.ok(this.authservice.Register(user));
	}
	
	@PostMapping("login")
	public ResponseEntity<Map<String,Object>> login(@RequestBody User user){

		return ResponseEntity.ok(this.authservice.login(user.getUsername(),user.getPassword()));
	}

}
