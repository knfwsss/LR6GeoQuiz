package com.example.geoquiz

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geoquiz.ui.theme.GeoQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoQuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun QuizScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var currentIndex by rememberSaveable { mutableIntStateOf(0) }
    var answered by rememberSaveable { mutableStateOf(false) }
    val question = questionBank[currentIndex]

    fun checkAnswer(userAnswer: Boolean) {
        val messageResId = if (userAnswer == question.answer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show()
        answered = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(question.textResId),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        if (!answered) {
            Row {
                Button(onClick = { checkAnswer(true) }) {
                    Text(stringResource(R.string.true_button))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { checkAnswer(false) }) {
                    Text(stringResource(R.string.false_button))
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            currentIndex = (currentIndex + 1) % questionBank.size
            answered = false
        }) {
            Text(stringResource(R.string.next_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizPreview() {
    GeoQuizTheme {
        QuizScreen()
    }
}
