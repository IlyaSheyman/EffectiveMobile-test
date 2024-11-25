package effective.mobile.test.task.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import effective.mobile.test.constants.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusRequest {
    @NotNull(message = "Status cannot be null")
    private Status status;

    @JsonProperty("task_id")
    @NotNull(message = "Task ID cannot be null")
    private Integer taskId;

}
