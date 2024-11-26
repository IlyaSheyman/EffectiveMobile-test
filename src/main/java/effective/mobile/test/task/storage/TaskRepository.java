package effective.mobile.test.task.storage;

import effective.mobile.test.constants.Priority;
import effective.mobile.test.constants.Status;
import effective.mobile.test.task.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    Page<Task> findByAssigneeId(int assigneeId, Pageable pageable);
    Page<Task> findByAssigneeIdAndStatus(int id, Status status, Pageable pageable);
    Page<Task> findByAssigneeIdAndPriority(int id, Priority priority, Pageable pageable);

    Page<Task> findByAuthorId(Integer id, Pageable pageable);
    Page<Task> findByAuthorIdAndStatus(Integer id, Status status, Pageable pageable);
    Page<Task> findByAuthorIdAndPriority(Integer id, Priority priority, Pageable pageable);
}
