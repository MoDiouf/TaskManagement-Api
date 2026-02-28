package task.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import task.project.entity.Task;
import task.project.entity.User;

public interface TaskInterface extends JpaRepository<Task,Long>{

	List<Task> findByUser(User user);
	

}
