package info.tduty.typetalk.data.model

data class TaskStateUpdated(
    val lessonId: String,
    val taskId: String,
    val isCompleted: Boolean
)
