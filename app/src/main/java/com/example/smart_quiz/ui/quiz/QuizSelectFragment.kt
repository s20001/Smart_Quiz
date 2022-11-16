package com.example.smart_quiz.ui.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smart_quiz.GameActivity
import com.example.smart_quiz.databinding.FragmentQuizSelectBinding
import com.example.smart_quiz.model.Detail
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.selects.select

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizSelectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizSelectFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var field_id: String? = null
    private var _binding: FragmentQuizSelectBinding? = null
    private val details: MutableList<Detail> = mutableListOf()
    private var recyclerView: RecyclerView? = null
    private lateinit var selectAdapter: SelectAdapter


    private val binding get() = _binding!!

    private val sampleList: MutableList<Detail> = mutableListOf(
        Detail(title = "samplequiz1", LikeNum = 3, q_id = "01"),
        Detail(title = "samplequiz2", LikeNum =4 , q_id = "02"),
        Detail(title = "samplequiz4", LikeNum = 7, q_id = "03"),
        Detail(title = "samplequiz5", LikeNum = 2, q_id = "04")
    )


    override fun onAttach(context: Context) {
        field_id = arguments?.getString("id")
        reader(field_id)
        super.onAttach(context)
        Log.d("QuizSelectFragment", "Start onAttach")


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            //field_id = it.getString("id")
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuizSelectBinding.inflate(inflater, container, false)
        Log.d("QuizSelectFragment", "Start onCreateView")



        recyclerView = binding.selectRecyclerView
        selectAdapter = SelectAdapter(sampleList)
        selectAdapter.itemClickListener = object : SelectAdapter.OnItemClickListener {
            override fun onItemClick(holder: SelectAdapter.ViewHolder) {
                val position = holder.absoluteAdapterPosition
                val text = holder.title.text
                val id = sampleList[position]
                Toast.makeText(context, "TEST $text" + id, Toast.LENGTH_LONG).show()
                val intent = Intent(context, GameActivity::class.java)
                startActivity(intent)
            }
        }



        recyclerView.let {
            it!!.adapter = selectAdapter
            it.layoutManager = LinearLayoutManager(view?.context)
            it.itemAnimator?.changeDuration = 0
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    fun reader(field: String?){
        Log.d("QuizSelectFragment", "Start reader")
        val database = Firebase.database.reference
        database.child("Details").child(field!!)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(datasnapshot: DataSnapshot){
                    for(data in datasnapshot.children){
                        val item = data.getValue(Detail::class.java)
                        details.add(
                            Detail(title = item!!.title.toString(),
                                LikeNum = item.LikeNum.toInt(),
                                q_id = item.q_id.toString()
                            )
                        )
                        Log.d("QuizSelectFragment", "==> $item")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    //データの取得に失敗したとき
                    Log.d("QuizSelectFragment", "ELLOR")
                    details.add(
                        Detail(title = "Not Found", LikeNum = 0, q_id = "not_found")
                    )
                }
            })
        Log.d("QuizSelectFragment", "Finish reader")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuizSelectFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuizSelectFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)

                }
            }
    }
}