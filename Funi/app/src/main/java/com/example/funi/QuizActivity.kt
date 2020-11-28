 package com.example.funi

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quiz.*

 class QuizActivity : AppCompatActivity() {
     private var name : String? = null
     private var subject : CharSequence? = null
     private var gradeLevel : String? = null
     private var q: Question? = null
     private var myEndScreen = EndScreen()
     private var quiz: QuizInterface? = null
     private var observer: QuizObserver? = null
     private var endTime : Double? = null


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        name = intent.getStringExtra("playerName")
        subject = intent.getCharSequenceExtra("subject")
        gradeLevel = intent.getStringExtra("gradeLevel")
        makeQuiz()
         quizChronometer.base = SystemClock.elapsedRealtime();
        quizChronometer.start()


         //event listener for check answer button
        checkAnswerButton.setOnClickListener {
            val answerID = quizRadioGroup.checkedRadioButtonId
            val chosenAnswer = findViewById<RadioButton>(answerID).text
            val nextQuestion = quiz?.checkAnswer(chosenAnswer.toString())
            updateMissedQuestions()
            println(observer?.numIncorrect)
            if(!quiz?.hasEnded!! && observer?.numIncorrect!! < 3) {
                displayQuestion(nextQuestion)
            }
            else {
                // start end screen activity
                quizChronometer.stop()
                //get the time in milliseconds and convert to seconds https://stackoverflow.com/questions/526524/android-get-time-of-chronometer-widget
                endTime = (((SystemClock.elapsedRealtime() - quizChronometer.base)) * 0.001)
                myEndScreen.end(gradeLevel, name, subject, observer?.numIncorrect, endTime)
                println("Ended " + myEndScreen.gradeLevel + myEndScreen.name + myEndScreen.subject + myEndScreen.time + myEndScreen.numIncorrect)
                val intent = Intent(this, EndActivity::class.java)
                intent.putExtra("playerName", myEndScreen.name)
                intent.putExtra("subject", myEndScreen.subject)
                intent.putExtra("gradeLevel", myEndScreen.gradeLevel)
                intent.putExtra("numIncorrect", myEndScreen.numIncorrect)
                intent.putExtra("time", myEndScreen.time)
                startActivity(intent)
            }
        }
     }

      private fun makeQuiz() {
          var chosenQuiz = Quiz()
          when(subject) {
          "Reading" -> when (gradeLevel) {
                "pre-school" -> quiz = PreschoolReadingDecorator(chosenQuiz)
            }
            "Math" -> when (gradeLevel) {

            }
        }
          observer = QuizObserver(quiz)
          println("currentQuestion:" + quiz?.getCurrentQuestion());
          q = quiz?.currentQuestion
          displayQuestion(q)
     }

     private fun displayQuestion(q: Question?) {
         val question = q?.question
         val answerChoices = q?.answerChoices
         val resID = resources.getIdentifier(question, "drawable", packageName)
         questionImageView.setImageResource(resID)
         answer1.text = answerChoices?.get(0)
         answer2.text = answerChoices?.get(1)
         answer3.text = answerChoices?.get(2)
         answer4.text = answerChoices?.get(3)
     }

     private fun updateMissedQuestions() {
         when(observer?.numIncorrect) {
             1 -> wrong1.text = "X"
             2 -> wrong2.text = "X"
             3 -> wrong3.text = "X"
         }
     }

 }