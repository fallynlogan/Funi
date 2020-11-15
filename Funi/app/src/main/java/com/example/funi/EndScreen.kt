package com.example.funi

data class EndScreen(var name:CharSequence = "", var gradeLevel:CharSequence = "", var subject:String = "") {
    fun end(grade:String?, text:String?, quizSubject: CharSequence?)
    {
        setName(text)
        setSubject(quizSubject)
        setGradeLevel(grade)
    }

    private fun setName(text:String?)
    {
        if (text != null) {
            name = text
        }
    }

    private fun setGradeLevel(grade:String?)
    {
        gradeLevel = grade.toString()
    }

    private fun setSubject(quizSubject:CharSequence?)
    {
        subject = quizSubject.toString()
    }
}