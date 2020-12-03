package com.example.funi

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_end.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class EndActivity : AppCompatActivity() {
    private var myQuizScreen = QuizScreen()
    private var name : String? = null
    private var subject : CharSequence? = null
    private var gradeLevel : String? = null
    private var numIncorrect : Int? = null
    private var time : Double? = null
    private var selectedGradePosition = 0
    private var listView: ListView? = null
    private var player : Player? = null
    private var players : MutableList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        name = intent.getStringExtra("playerName")
        subject = intent.getCharSequenceExtra("subject")
        gradeLevel = intent.getStringExtra("gradeLevel")
        numIncorrect = intent.getIntExtra("numIncorrect", 0)
        time = intent.getDoubleExtra("time", 0.0)

        gameOverTextview.text = "Game Over, $name"

        if(numIncorrect!! < 3) {
            showAlert()
        }
        displayLeaderBoard()
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

        //switch subject event listener
        switchSubjectButton.setOnClickListener {
            when(subject) {
                "Reading" -> subject = "Math"
                "Math" -> subject = "Reading"
            }
            name?.let { it1 -> subject?.let { it2 -> myQuizScreen.quiz(selectedGradePosition, it1, it2) } }
            println("myQuizScreenName"+ myQuizScreen.name)
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

    private fun showAlert() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Congratulations, $name!")
        alertDialogBuilder.setMessage("Would you like to add your name to the Funi leader board?")
        //alertDialogBuilder.setIcon(R.drawable)
        alertDialogBuilder.setPositiveButton("Yes") { dialog, _ ->  dialog.dismiss()
        addToLeaderBoard()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->  dialog.dismiss()
        }
        val alert: AlertDialog = alertDialogBuilder.create()
        alert.show()

    }

    private fun displayLeaderBoard() {
        listView = leaderboardListView
        when(gradeLevel) {
            "pre-school" -> leaderBoardTextview.text = "Pre-School $subject Leader Board"
        }
        players= mutableListOf()
        File(this.getFilesDir().getPath().toString() +"/preschoolReading.txt").forEachLine { players!!.add(it) }
        println("list $$$" + players!!.size)
        val adapter : ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            players!!
        )
        listView?.adapter = adapter
    }

    private fun addToLeaderBoard() {
        player = numIncorrect?.let { Player(name, gradeLevel, subject.toString(), time, it) }
        try {
            when (subject) {
                "Reading" -> when (gradeLevel) {
                    "pre-school" -> File(this.getFilesDir().getPath().toString() +"/preschoolReading.txt").printWriter().use { out ->
                        if (player != null) {
                            out.println(player!!.description)
                        }
                        players?.forEach { out.println(it) }
                    }
                    "kindergarten" -> print("")
                    "1st grade" -> print("")
                    "2nd grade" -> print("")
                    "3rd grade" -> print("")
                }
                "Math" -> when (gradeLevel) {
                    "pre-school" -> print("")
                    "kindergarten" -> print("")
                    "1st grade" -> print("")
                    "2nd grade" -> print("")
                    "3rd grade" -> print("")
                }
            }
            println("$$$$ directory: " + this.getFilesDir().getPath().toString())
        } catch (ioException : IOException) {
            println("exception $$$$" + ioException)
        }
    }


    //add stuff for leader board saving state
    private fun updateUI() {
        name = intent.getStringExtra("playerName")
        subject = intent.getCharSequenceExtra("subject")
        gradeLevel = intent.getStringExtra("gradeLevel")
        numIncorrect = intent.getIntExtra("numIncorrect", 0)
        time = intent.getDoubleExtra("time", 0.0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("name", name)
        outState.putString("gradeLevel", gradeLevel)
        outState.putCharSequence("subject", subject)
        time?.let { outState.putDouble("time", it) }
        numIncorrect?.let { outState.putInt("numIncorrect", it) }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        name = savedInstanceState.getString("name")
        gradeLevel = savedInstanceState.getString("gradeLevel")
        subject = savedInstanceState.getCharSequence("subject")
        time = savedInstanceState.getDouble("time")
        numIncorrect = savedInstanceState.getInt("numIncorrect")
        updateUI()
    }
}