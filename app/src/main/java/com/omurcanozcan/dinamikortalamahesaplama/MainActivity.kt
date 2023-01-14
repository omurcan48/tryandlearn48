package com.omurcanozcan.dinamikortalamahesaplama
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.added_courses.*
import kotlinx.android.synthetic.main.added_courses.view.*


class MainActivity : AppCompatActivity() {

    private val DERSLER = arrayOf("Matematik", "Türkçe", "Edebiyat", "Algoritma", "Tarih")

    private var allGrades:ArrayList<courses> = ArrayList(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, DERSLER)

        text1.setAdapter(adapter)

        if(scrollLinearLayout.childCount == 0){

            btnTwo.visibility = View.INVISIBLE

        } else {

            btnTwo.visibility = View.VISIBLE

        }

        btn1.setOnClickListener{

            if(!text1.text.isNullOrEmpty()){

            var inflater = LayoutInflater.from(this)
//            var inflater2 = LayoutInflater
//            var inflater3 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var newCourseView = inflater.inflate(R.layout.added_courses, null)

            var plusCourseName = text1.text.toString()
            var plusCourseCredit = spinnerOne.selectedItem.toString()
            var plusCourseNote = spinnerTwo.selectedItem.toString()

            newCourseView.newCourse.setText(plusCourseName)
            newCourseView.newCourseCredit.setSelection(getSpinnerIndex(spinnerOne, plusCourseCredit))
            newCourseView.newCourseNote.setSelection(getSpinnerIndex(spinnerTwo, plusCourseNote))

            newCourseView.courseDelete.setOnClickListener {

                scrollLinearLayout.removeView(newCourseView)

                if(scrollLinearLayout.childCount == 0){

                    btnTwo.visibility = View.INVISIBLE

                } else {

                    btnTwo.visibility = View.VISIBLE

                }

            }

            scrollLinearLayout.addView(newCourseView)

            btnTwo.visibility = View.VISIBLE

        }else {
            Toast.makeText(this,"Ders adını giriniz", Toast.LENGTH_LONG).show()
        }

        }

    }

    fun getSpinnerIndex(spinner: Spinner, aranacakDeger:String):Int {

        var index = 0

        for (i in 0..spinner.count){

            if(spinner.getItemAtPosition(i).toString().equals(aranacakDeger)){

                index = i

                break

            }

        }

        return index

    }

    fun noteToValue(noteName:String) : Double {

        var myNote:Double = 0.0

        when(noteName){

            "AA" -> myNote = 4.0
            "BA" -> myNote = 3.5
            "BB" -> myNote = 3.0
            "CB" -> myNote = 2.5
            "CC" -> myNote = 2.0
            "DC" -> myNote = 1.5
            "DD" -> myNote = 1.0
            "FF" -> myNote = 0.0

        }

        return myNote

    }

    fun ortalamaHesapla(view: View) {

        var totalGrade:Double = 0.0

        var totalCredit:Double = 0.0

        for (i in 0..scrollLinearLayout.childCount-1){

            var newLine = scrollLinearLayout.getChildAt(i)

            var onCourse = courses(newLine.newCourse.text.toString(), ((newLine.newCourseCredit.selectedItemPosition)+1).toString(), newLine.newCourseNote.selectedItem.toString())

            allGrades.add(onCourse)

        }

        for(thisCourse in allGrades){

            totalGrade += noteToValue(thisCourse.courseGrade)*(thisCourse.courseCredit.toDouble())

            totalCredit += thisCourse.courseCredit.toDouble()

        }

        Toast.makeText(this, "Ortalama: " + (totalGrade/totalCredit), Toast.LENGTH_LONG).show()

        allGrades.clear()


    }

}