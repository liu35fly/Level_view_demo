# 二进图[对阵图]
>这是我们项目中用到的一个视图，项目完了，抽空把这个视图封装了一下。
>先看效果
#### 空数据
 <img src="https://github.com/liu35fly/Level_view_demo/blob/master/screen/device-2017-10-29-165532.png" width = "180" height = "320" alt="空数据" align=center /><p>
#### 数据不全并点击事件
<img src="https://github.com/liu35fly/Level_view_demo/blob/master/screen/device-2017-10-29-165602.png" width = "180" height = "320" alt="数据不全并点击事件" align=center /><p>
#### 数据不全并长按事件
<img src="https://github.com/liu35fly/Level_view_demo/blob/master/screen/device-2017-10-29-165613.png" width = "180" height = "320" alt="数据不全并长按事件" align=center /><p>
#### 序列模式
<img src="https://github.com/liu35fly/Level_view_demo/blob/master/screen/device-2017-10-29-165638.png" width = "180" height = "320" alt="序列模式" align=center /><p>
#### 双集合模式
<img src="https://github.com/liu35fly/Level_view_demo/blob/master/screen/device-2017-10-29-170017.png" width = "180" height = "320" alt="双集合模式" align=center /><p>
***
# 用法说明
### 1.levelView的属性：
 * levelViewBackgroundColor="#888456"<p>
 设置levelView主区域的背景色
 * levelViewLineColor="#000000"<p>
 设置levelView中每个线条及圆形边框的颜色
 * levelViewTextColor="#000000"<p>
 设置levelView中每个圆形边框下面的字体颜色
 * levelViewRightTextColor="#aaaaaa"<p>
  设置levelView中每个圆形边框右边的字体颜色
 * levelViewIconBg=""<p>
 设置levelView中序列模式时图标的背景色
 * levelViewIconBgTag=""<p>
 设置levelView中图标的占位背景
 * levelViewTitleBackgroundColor=""<p>
 设置levelView顶部title的背景色
 * levelViewTitleTextColor=""<p>
 设置levelView顶部title的字体色

### 2.levelView的使用：

  &#160; &#160; &#160; &#160;将levelView实现后只需调用一个方法  addChildView(),填入相应数据即可展示如果需要每个子view的点击或长按监听，只需 setLevelViewListener（），其中所有信息都在childView.getData()中

eg.
<pre><code>
  level = findViewById(R.id.level_view)
         level!!.setLevelViewListener(object : LevelView.LevelViewListener{
             override fun onError(error: String?) {
             Log.e("ShowActivity","on error is "+error)
             }

             override fun onItemClik(childView: LevelViewBaseView?, i: Int) {
             Toast.makeText(this@ShowActivity,"点击了第${i}项,name is ${childView!!.name}" +
                     "count is ${childView!!.count}",Toast.LENGTH_SHORT).show()
              }

             override fun onItemLongClik(childView: LevelViewBaseView?, i: Int) {
                 Toast.makeText(this@ShowActivity,"长按了第${i}项,name is ${childView!!.name}" +
                         "count is ${childView!!.count}" +
                         "time is ${childView!!.data.icon}" +
                         "other is ${childView!!.data.list}",Toast.LENGTH_SHORT).show()
             }
         })
         when (type) {
             0 -> level!!.addChildView(25)
             2 -> level!!.addChildView(25, true)
         }
</code></pre>
********************************************************************************
# 使用
>
gradle
<pre><code>
  allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

  dependencies {
	    compile 'com.github.liu35fly:Level_view_demo:v1.0'
  }
</code></pre>

maven
<pre><code>
 <repositories>
 		<repository>
 		    <id>jitpack.io</id>
 		    <url>https://jitpack.io</url>
 		</repository>
 	</repositories>

 		<dependency>
     	    <groupId>com.github.liu35fly</groupId>
     	    <artifactId>Level_view_demo</artifactId>
     	    <version>v1.0</version>
     	</dependency>
</code></pre>


