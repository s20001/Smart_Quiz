package com.example.smart_quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smart_quiz.adapter.ChoiceAdapter
import com.example.smart_quiz.databinding.ActivityEditBinding
import com.example.smart_quiz.model.Field
import com.example.smart_quiz.model.Quiz
import com.example.smart_quiz.ui.edit.CreateDialogFragment
import com.google.android.material.textfield.TextInputEditText

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private val createQuizList: MutableList<Quiz> = mutableListOf()
    private val quizTitleList: MutableList<String> = mutableListOf("何もありません")
    private var initial = false
    private lateinit var recyclerview: RecyclerView
    private val fieldList: MutableList<Field> = mutableListOf(
        Field(name = "IT", id = "field-it"),
        Field(name = "動物", id = "field-animal"),
        Field(name = "歴史", id = "field-history")
    )
    private var position: Int? = null //ユーザーが選択したプルダウンメニューのposition
    private var checker = false //ユーザーが項目を記入しているか
    private val spinnerItems = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fieldList.forEach { spinnerItems.add(it.name) }

        //recyclerviewの初期化
        recyclerview = binding.createdRecyclerview
        val quizAdapter = ChoiceAdapter(quizTitleList)
        recyclerview.let {
            it.layoutManager = LinearLayoutManager(this@EditActivity)
            it.adapter = quizAdapter
            it.itemAnimator?.changeDuration = 0
        }

        val spinner = binding.spField
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            spinnerItems
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, spposition: Int, p3: Long){
                val spinnerParent = parent as Spinner
                position = spposition
                checker = true
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                checker = false
            }
        }

        binding.btQuizAdd.setOnClickListener {
            showDialog()
        }


    }

    private fun showDialog() {
        val createDialog = CreateDialogFragment()
        createDialog.dialogClickListener = object : CreateDialogFragment.OnDialogClickListener {
            override fun onDialogClick(view: View?) {
                val question = view!!.findViewById<TextInputEditText>(R.id.ed_question)
                val correct = view.findViewById<TextInputEditText>(R.id.ed_correct)
                val choice1 = view.findViewById<TextInputEditText>(R.id.ed_choice1)
                val choice2 = view.findViewById<TextInputEditText>(R.id.ed_choice2)
                val choice3 = view.findViewById<TextInputEditText>(R.id.ed_choice3)

                createQuizList.add(
                    Quiz(
                        sentence = question.text.toString(),
                        correct = correct.text.toString(),
                        choice1 = choice1.text.toString(),
                        choice2 = choice2.text.toString(),
                        choice3 = choice3.text.toString()
                    )
                )

                if(!initial){
                    quizTitleList.clear()
                    initial = true
                }

                quizTitleList.add(question.text.toString())
                recyclerview.adapter?.notifyDataSetChanged()

                Log.d("EditActivity", "QuizList => ${createQuizList}")

            }
        }

        createDialog.show(supportFragmentManager, "create_dialog")

    }
}