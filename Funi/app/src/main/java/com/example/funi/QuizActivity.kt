 package com.example.funi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quiz.*

 class QuizActivity : AppCompatActivity() {
     private var name : String? = null
     private var subject : CharSequence? = null
     private var gradeLevel : String? = null
     private var quiz : Quiz? = null
     private var q: Question? = null
     private var myEndScreen = EndScreen()


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        name = intent.getStringExtra("playerName")
        subject = intent.getCharSequenceExtra("subject")
        gradeLevel = intent.getStringExtra("gradeLevel")

        makeQuiz()

         //event listener for check answer button
        checkAnswerButton.setOnClickListener {
            val answerID = quizRadioGroup.checkedRadioButtonId
            val chosenAnswer = findViewById<RadioButton>(answerID).text
            val nextQuestion = quiz?.checkAnswer(chosenAnswer.toString())
            if(!quiz?.hasEnded!!) {
                displayQuestion(nextQuestion)
            }
            else {
                // start end screen activity
                myEndScreen.end(gradeLevel, name, subject)
                println("Ended " + myEndScreen.gradeLevel + myEndScreen.name + myEndScreen.subject)
                val intent = Intent(this, EndActivity::class.java)
                intent.putExtra("playerName", myEndScreen.name)
                intent.putExtra("subject", myEndScreen.subject)
                intent.putExtra("gradeLevel", myEndScreen.gradeLevel)
                startActivity(intent)
            }
        }
     }

      fun makeQuiz() {
        when(subject) {
            "Reading" -> when (gradeLevel) {
                "pre-school" -> quiz = PreschoolReadingQuiz()
                "kindergarten" -> quiz = KindergartenReadingQuiz()
                "1st grade" -> quiz = FirstgradeReadingQuiz()
                "2nd grade" -> quiz = SecondgradeReadingQuiz()
                "3rd grade" -> quiz = ThirdgradeReadingQuiz()
            }
            "Math" -> when (gradeLevel) {
                "pre-school" -> quiz = PreschoolMathQuiz()
                "kindergarten" -> quiz = KindergartenMathQuiz()
                "1st grade" -> quiz = FirstgradeMathQuiz()
                "2nd grade" -> quiz = SecondgradeMathQuiz()
                "3rd grade" -> quiz = ThirdgradeMathQuiz()
            }
        }
          q = quiz?.getQuiz()
          displayQuestion(q)
     }

     fun displayQuestion(q: Question?) {
         val question = q?.question
         val answerChoices = q?.answerChoices
         quizText.text = question
         answer1.text = answerChoices?.get(0)
         answer2.text = answerChoices?.get(1)
         answer3.text = answerChoices?.get(2)
         answer4.text = answerChoices?.get(3)
     }

 }