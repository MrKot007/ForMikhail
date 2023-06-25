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
    private lateinit var courses: List<ModelCourse>
    private lateinit var adapter: CoursesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        api.getTags().push(object: OnGetData<List<ModelTag>>{
            override fun onGet(data: List<ModelTag>) {
                tags = data
                binding.tagRecycler.adapter = TagsAdapter(data, object: OnClickTag{
                    override fun onClick(tag: ModelTag) {
                        val oldText = binding.search.text.toString()
                        if (tag.name in oldText) {
                            binding.search.setText(oldText.replace(tag.name, ""))
                        }else {
                            binding.search.setText("$oldText ${tag.name}")
                        }
                        val request = binding.search.text.split(" ")
                        searchCourses(request)
                    }
                })
                binding.tagRecycler.layoutManager = LinearLayoutManager(this@CatalogActivity, LinearLayoutManager.HORIZONTAL, false)
            }

            override fun onError(error: String) {
                Log.e("ERRROR", error)
            }
        }, this)
        api.getCourses().push(object: OnGetData<List<ModelCourse>>{
            override fun onGet(data: List<ModelCourse>) {
                courses = data
                adapter = CoursesAdapter(data, tags, object: OnClickCourse{
                    override fun onClick(course: ModelCourse) {
                        val courseIntent = Intent(this@CatalogActivity, CourseActivity::class.java)
                        courseIntent.putExtra("idCourse", course.id)
                        startActivity(courseIntent)
                    }
                })
                binding.courseRecycler.adapter = adapter
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
    private fun searchCourses(names: List<String>) {
        if (names.size != 0) {
            val requiredIds = mutableListOf<Int>()
            for (i in tags) {
                if (i.name in names) {
                    requiredIds.add(i.id)
                }
            }
            val suitableCourses = courses.filter { it.tags.any {it in requiredIds} }
            this.adapter.setData(suitableCourses)
        }else {
            this.adapter.setData(this.courses)
        }
    }
}