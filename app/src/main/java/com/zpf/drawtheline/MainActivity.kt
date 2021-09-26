package com.zpf.drawtheline

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.anim.DefaultAnimator
import com.lzf.easyfloat.enums.ShowPattern
import com.lzf.easyfloat.enums.SidePattern
import com.lzf.easyfloat.interfaces.OnPermissionResult
import com.lzf.easyfloat.permission.PermissionUtils
import com.lzf.easyfloat.utils.DisplayUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_two)
        supportActionBar?.hide()
//        val view = layoutInflater.inflate(R.layout.activity_two, null, false)
        EasyFloat.with(this)
            .setTag("ASDASDASD")
            // 设置浮窗xml布局文件/自定义View，并可设置详细信息
            .setLayout(R.layout.activity_two){

            }
            // 设置浮窗显示类型，默认只在当前Activity显示，可选一直显示、仅前台显示
            .setShowPattern(ShowPattern.BACKGROUND)
            // 设置浮窗是否可拖拽
            .setDragEnable(false)
            // 创建浮窗（这是关键哦😂）
            .setMatchParent(widthMatch = true, heightMatch = true)
            .show()
        var x=1
        EasyFloat.with(this).setLayout(R.layout.pop_xml){
            it.findViewById<Button>(R.id.button).setOnClickListener{
                val floatView = EasyFloat.getFloatView("ASDASDASD")
                val findViewById = floatView!!.findViewById<MeasurementTwoView>(R.id.mainmesure)
                if (x%2==0){
                    EasyFloat.hide("ASDASDASD")
//                    findViewById.setOnTouchListener { v, event -> return@setOnTouchListener false }
                }else{
//                    findViewById.setOnTouchListener { v, event -> return@setOnTouchListener true }
                    EasyFloat.show( "ASDASDASD")
                }
                x++
            }
        }.setShowPattern(ShowPattern.BACKGROUND)
            .show()



    }


}