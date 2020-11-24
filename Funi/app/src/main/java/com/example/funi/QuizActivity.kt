 package com.example.funi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quiz.*

 class QuizActivity : AppCompatActivity() {
     private var name : String? = null
     private var subject : CharSequence? = null
     private var gradeLevel : String? = null
     private var q: Question? = null
     private var myEndScreen = EndScreen()
     private var quiz: QuizInterface? = null


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
          var chosenQuiz = Quiz()
          when(subject) {
          "Reading" -> when (gradeLevel) {
                "pre-school" -> quiz = PreschoolReadingDecorator(chosenQuiz)
            }
            "Math" -> when (gradeLevel) {

            }
        }
          println("currentQuestion:" + quiz?.getCurrentQuestion());
          q = quiz?.currentQuestion
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