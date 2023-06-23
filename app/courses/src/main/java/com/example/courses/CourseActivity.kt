package com.example.courses

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courses.Connection.api
import com.example.courses.databinding.ActivityCourseBinding

val cart: MutableList<CourseData> = mutableListOf()
class CourseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseBinding
    private lateinit var tags: List<ModelTag>
    private lateinit var currentCourse: CourseData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        api.getTags().push(object: OnGetData<List<ModelTag>>{
            override fun onGet(data: List<ModelTag>) {
                tags = data
            }

            override fun onError(error: String) {
                Log.e("ERRROR", error)
            }

        }, this)
        api.getCourse(intent.getIntExtra("idCourse", 0)).push(object: OnGetData<CourseData>{
            override fun onGet(data: CourseData) {
                currentCourse = data
                binding.courseActivityTitle.text = data.title
                binding.courseActvivtyPrice.text = "₽${data.price}"
                binding.tagsRec.adapter = NestedRecyclerAdapter(tags, data.tags)
                binding.tagsRec.layoutManager = LinearLayoutManager(this@CourseActivity, LinearLayoutManager.HORIZONTAL, false)
                binding.description.text = data.description
                binding.planRec.adapter = PlanAdapter(data.plan)
                binding.planRec.layoutManager = LinearLayoutManager(this@CourseActivity, LinearLayoutManager.HORIZONTAL, false)
            }

            override fun onError(error: String) {
                Log.e("ERRROR", error)
            }
        }, this)
        binding.backToCatalog.setOnClickListener {
            finish()
        }
        binding.select.setOnClickListener {
            cart.add(currentCourse)
            binding.select.setCardBackgroundColor(Color.rgb(230, 230, 230))
            binding.selecttext.text = "Курс выбран"
        }
    }
}