package effective.mobile.test.task.storage;

import effective.mobile.test.task.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
