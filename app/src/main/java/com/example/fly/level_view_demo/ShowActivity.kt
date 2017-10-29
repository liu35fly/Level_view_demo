package com.example.fly.level_view_demo

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.levelviewlibray.LevelView
import com.example.levelviewlibray.LevelViewBaseData
import com.example.levelviewlibray.LevelViewBaseView

class ShowActivity : AppCompatActivity() {

    private var level: LevelView? = null

    companion object {
        fun gotoShowActivity(context: Context, type: Int) {
            var intent = Intent(context, ShowActivity::class.java)
            intent.putExtra("type", type)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        var type = intent.getIntExtra("type", 0)
        level = findViewById(R.id.level_view)
        level!!.setLevelViewListener(object : LevelView.LevelViewListener{
            override fun onError(error: String?) {
            Log.e("ShowActivity","on error is "+error)
            }

            override fun onItemClik(childView: LevelViewBaseView?, i: Int) {
            Toast.makeText(this@ShowActivity,"点击了第${i}项,name is ${childView!!.name}" +
                    "count is ${childView!!.count}",Toast.LENGTH_SHORT).show()
             }

            override fun onItemLongClik(dataBean: LevelViewBaseData?, i: Int) {
                Toast.makeText(this@ShowActivity,"长按了第${i}项,name is ${dataBean!!.name}" +
                        "count is ${dataBean!!.count}" +
                        "time is ${dataBean!!.time}" +
                        "address is ${dataBean!!.address}" +
                        "match is ${dataBean!!.match}",Toast.LENGTH_SHORT).show()
            }
        })
        when (type) {
            0 -> level!!.addChildView(25)
            1 -> showPartView()
            2 -> level!!.addChildView(25, true)
            3 -> showTwoView()
        }
    }

    private fun showPartView() {
        var list = mutableListOf<LevelViewBaseData>()
        var mydata: MyData
        for (i in 0 until 30) {
            mydata = MyData("$i", "name->$i", "$i", "http://upload.jianshu.io/users/upload_avatars/586279/f25227826f82.png?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96", "time:$i", "address<$i>", "test")
            list.add(mydata)
        }
        level!!.addChildView(64, list, null, false)
    }

    private fun showTwoView() {
        var listOne = mutableListOf<LevelViewBaseData>()
        var listTwo = mutableListOf<LevelViewBaseData>()
        var mydata: MyData
        for (i in 0 until 30) {
            mydata = MyData("$i", "name->$i", "$i", "http://upload.jianshu.io/users/upload_avatars/586279/f25227826f82.png?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96", "time:$i", "address<$i>", "test")
            listOne.add(mydata)
        }
        for (i in 0 until 3) {
            mydata = MyData("$i", "name->$i", "$i", "http://upload.jianshu.io/users/upload_avatars/586279/f25227826f82.png?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96", "time:$i", "address<$i>", "test")
            listTwo.add(mydata)
        }
        level!!.addChildView(16, listOne, listTwo, null, false)
    }
}
