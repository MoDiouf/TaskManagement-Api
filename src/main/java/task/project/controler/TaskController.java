package task.project.controler;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import task.project.entity.Task;
import task.project.services.JwtService;
import task.project.services.TaskService;

@RestController
@RequestMapping("task")
public class TaskController {

	public final TaskService taskservice;
	private final JwtService jwtservice;
	
	public TaskController(TaskService taskservice,JwtService jwtservice) {
		this.taskservice = taskservice;
		this.jwtservice = jwtservice;
	}
	
	@PostMapping("add")
	public ResponseEntity<Task> add(@RequestBody Task task,@RequestHeader("Authorization") String authHeader){
		
		String token = authHeader.substring(7);
	    Long userId = jwtservice.extractUserId(token);
		return ResponseEntity.ok(this.taskservice.AddTask(task, userId));
	}
	
	@GetMapping("")
	public ResponseEntity<List<Task>> task(@RequestHeader("Authorization") String authHeader){
	    
	    String token = authHeader.substring(7);
	    Long userId = jwtservice.extractUserId(token);

	    List<Task> tasks = this.taskservice.Gettasks(userId);
	    return ResponseEntity.ok(tasks);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(
	        @PathVariable Long id,
	        @RequestHeader("Authorization") String authHeader) {

	    String token = authHeader.substring(7);
	    String username = jwtservice.extractUsername(token);
	    Long userId = jwtservice.extractUserId(token);

	    return ResponseEntity.ok(taskservice.deleteTask(id, userId));
	}

	
}
