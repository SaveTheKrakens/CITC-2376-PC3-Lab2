package com.savethekrakens.taskmanagerapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savethekrakens.taskmanagerapplication.ui.theme.TaskManagerApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerApplicationTheme {
                val tasks = remember { mutableStateListOf<String>() }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TopBar(onAddTask = { task -> tasks.add(task) })
                    }
                    ListOfTasks(tasks, onDeleteTask = { task -> tasks.remove(task) })
                }
            }
        }
    }
}

@Composable
fun TopBar(onAddTask: (String) -> Unit) {
    val textFieldValue = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Task Manager",
            style = TextStyle(fontSize = 24.sp),
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Start)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = textFieldValue.value,
                onValueChange = { textFieldValue.value = it },
                label = { Text("Add Task Here") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                textStyle = TextStyle(fontSize = 16.sp)

            )
            Button(
                onClick = {
                    onAddTask(textFieldValue.value)
                    textFieldValue.value = ""
                },
                modifier = Modifier.padding(4.dp),
                enabled = true,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow,
                    contentColor = Color.Black
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp,
                    disabledElevation = 0.dp
                )
            ) {
                Text("Add Task")
            }
        }
    }
}

@Composable
fun ListOfTasks(tasks: List<String>, onDeleteTask: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(tasks.size) { index ->
            TaskItem(tasks[index], onDeleteTask = onDeleteTask)
        }
    }
}

@Composable
fun TaskItem(taskName: String, onDeleteTask: (String) -> Unit) {
    val checkboxState = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkboxState.value,
            onCheckedChange = { checkboxState.value = it },
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = taskName,
            style = if (checkboxState.value) {
                TextStyle(fontSize = 16.sp, color = Color.Gray)
            } else {
                TextStyle(fontSize = 16.sp)
            },
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        )
        IconButton(
            onClick = {
                onDeleteTask(taskName)
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Task"
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TaskManagerApplicationTheme {
        TopBar(onAddTask = { })
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    TaskManagerApplicationTheme {
        TaskItem(
            "test task",
            onDeleteTask = {}
        )
    }
}