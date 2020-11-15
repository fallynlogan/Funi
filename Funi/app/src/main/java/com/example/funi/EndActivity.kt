package com.example.funi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_end.*

class EndActivity : AppCompatActivity() {
    private var myQuizScreen = QuizScreen()
    private var name : String? = null
    private var subject : CharSequence? = null
    private var gradeLevel : String? = null
    private var selectedGradePosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        name = intent.getStringExtra("playerName")
        subject = intent.getCharSequenceExtra("subject")
        gradeLevel = intent.getStringExtra("gradeLevel")

        //try again event listener
        tryAgainButton.setOnClickListener{
            when(gradeLevel) {
                "pre-school" -> selectedGradePosition = 0
                "kindergarten" -> selectedGradePosition = 1
                "1st grade" -> selectedGradePosition = 2
                "2nd grade" -> selectedGradePosition = 3
                "3rd grade" -> selectedGradePosition = 3
            }
            name?.let { it1 -> subject?.let { it2 ->
                myQuizScreen.quiz(selectedGradePosition, it1,
                    it2
                )
            } }
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("playerName", myQuizScreen.name)
            intent.putExtra("subject", myQuizScreen.subject)
            intent.putExtra("gradeLevel", myQuizScreen.gradeLevel)
            startActivity(intent)
        }

        //start over event listener
        startOverrButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}