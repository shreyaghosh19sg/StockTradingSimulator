let tasks = [];

function addTask() {
  const desc = document.getElementById('description').value.trim();
  const priority = parseInt(document.getElementById('priority').value);

  if (!desc) {
    alert("Please enter a task description.");
    return;
  }

  tasks.push({ description: desc, priority });
  tasks.sort((a, b) => a.priority - b.priority);

  displayTasks();
  document.getElementById('description').value = "";
}

function displayTasks() {
  const taskList = document.getElementById('taskList');
  taskList.innerHTML = '';

  tasks.forEach(task => {
    const li = document.createElement('li');
    li.textContent = `[${task.priority}] ${task.description}`;
    taskList.appendChild(li);
  });
}
