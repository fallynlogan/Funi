 package com.example.funi

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
     private var answer : CharSequence? = null


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

         //test
         answer1.text = "1"
         answer2.text = "2"
         answer3.text = "3"
         answer4.text = "4"

        name = intent.getStringExtra("playerName")
        subject = intent.getCharSequenceExtra("subject")
        gradeLevel = intent.getStringExtra("gradeLevel")

        //test logs
        name?.let { Log.i("name received", it)}
        subject?.let { Log.i("subject received", it.toString())}
        gradeLevel?.let {Log.i("gradeLevel received", it)}

        makeQuiz()

         //event listener for check answer button
        checkAnswerButton.setOnClickListener {
            val answerID = quizRadioGroup.checkedRadioButtonId
            val answer = findViewById<RadioButton>(answerID).text
            quiz?.checkAnswer(answer.toString())
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
        runQuiz()
     }

     fun runQuiz() {
         val it: MutableIterator<Question>? = quiz?.questions?.iterator()
         if (it != null) {
             while(it.hasNext()) {
                 val q = it.next()
                 val question = q.question
                 val answer = q.answer
                 val answerChoices = q.answerChoices
                 println(" question: $question \n answer: $answer \n answerChoices: $answerChoices")
             }
         }
     }

 }