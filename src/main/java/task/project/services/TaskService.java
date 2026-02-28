package task.project.services;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import task.project.entity.Task;
import task.project.entity.User;
import task.project.repository.TaskInterface;
import task.project.repository.UserInterface;

@Service
public class TaskService {
	
	public final TaskInterface taskInterface;
	private final UserInterface userRepository;
	
	public TaskService(TaskInterface taskInterface, UserInterface user) {
		this.taskInterface = taskInterface;
		this.userRepository = user;
	}
	
	public Task AddTask(Task task,Long userId) {
		User user = userRepository.findById(userId)
					.orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
			task.setUser(user);
		return taskInterface.save(task);

	}
	
	public List<Task> Gettasks(Long userId) {
	    User user = userRepository.findById(userId)
	                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
	    return taskInterface.findByUser(user); // Méthode dans TaskInterface
	}

	public String deleteTask(Long id, Long username) {

	    User user = userRepository.findById(username)
	            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

	    Task task = taskInterface.findById(id)
	            .orElseThrow(() -> new RuntimeException("Tâche introuvable"));

	    // Vérifier que la tâche appartient bien à l'utilisateur
	    if (!task.getUser(user).getId().equals(user.getId())) {
	        throw new RuntimeException("Accès refusé : cette tâche ne vous appartient pas");
	    }

	    taskInterface.delete(task);

	    return "Tâche supprimée avec succès";
	}

}
