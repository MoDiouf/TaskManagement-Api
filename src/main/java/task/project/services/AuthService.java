package task.project.services;

import java.util.HashMap;
import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import task.project.entity.User;
import task.project.repository.UserInterface;

@Service
public class AuthService {
	
	private final UserInterface userInterface ;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService ;
	public AuthService(UserInterface userinterface , PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.jwtService = jwtService;
		this.userInterface =userinterface;
		this.passwordEncoder = passwordEncoder;
	}
	
	public String Register(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword())); 
		userInterface.save(user);
		
		return jwtService.generateToken(user.getPrenom(), user.getId());
	}
	
	public Map<String, Object> login(String username, String password) {

        User user = userInterface.findByUsername(username)
        		.orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));


        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        String token = jwtService.generateToken(user.getPrenom(), user.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("prenom", user.getPrenom());
        response.put("nom", user.getNom());
        response.put("token", token);

        return response;
    }


	


	
}
