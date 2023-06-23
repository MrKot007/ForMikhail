package com.example.courses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courses.Connection.api
import com.example.courses.databinding.ActivityCatalogBinding

class CatalogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCatalogBinding
    private lateinit var tags: List<ModelTag>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        api.getTags().push(object: OnGetData<List<ModelTag>>{
            override fun onGet(data: List<ModelTag>) {
                tags = data
                binding.tagRecycler.adapter = TagsAdapter(data)
                binding.tagRecycler.layoutManager = LinearLayoutManager(this@CatalogActivity, LinearLayoutManager.HORIZONTAL, false)
            }

            override fun onError(error: String) {
                Log.e("ERRROR", error)
            }
        }, this)
        api.getCourses("124").push(object: OnGetData<List<ModelCourse>>{
            override fun onGet(data: List<ModelCourse>) {
                binding.courseRecycler.adapter = CoursesAdapter(data, tags, object: OnClickCourse{
                    override fun onClick(course: ModelCourse) {
                        val courseIntent = Intent(this@CatalogActivity, CourseActivity::class.java)
                        courseIntent.putExtra("idCourse", course.id)
                        startActivity(courseIntent)
                    }

                })
                binding.courseRecycler.layoutManager = LinearLayoutManager(this@CatalogActivity)
            }

            override fun onError(error: String) {
                Log.e("ERRROR", error)
            }
        }, this)
        binding.toCart.setOnClickListener {
            startActivity(Intent(this@CatalogActivity, CartActivity::class.java))
        }
    }
}