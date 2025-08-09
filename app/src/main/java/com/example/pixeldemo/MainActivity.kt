package com.example.pixeldemo

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: DemoPagerAdapter
    private lateinit var statusText: TextView

    private val handler = Handler(Looper.getMainLooper())
    private val slideDelayMs = 5000L
    private var isPlaying = true

    private val autoSlideRunnable = object : Runnable {
        override fun run() {
            val itemCount = adapter.itemCount
            if (itemCount == 0) return
            val next = (viewPager.currentItem + 1) % itemCount
            viewPager.setCurrentItem(next, true)
            handler.postDelayed(this, slideDelayMs)
        }
    }

    private var longPressDownTime = 0L
    private val longPressThreshold = 5000L // 5 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // keep screen bright and on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        // set very high brightness for demo (if device allows)
        try {
            val lp = window.attributes
            lp.screenBrightness = 1.0f
            window.attributes = lp
        } catch (e: Exception) { /* ignore */ }

        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        statusText = findViewById(R.id.statusText)

        // Prepare demo pages (replace with real asset ids)
        val pages = listOf(
            DemoPageData(R.drawable.demo_intro, "Meet Pixel 4a", "Compact — powerful — affordable"),
            DemoPageData(R.drawable.demo_camera, "Night Sight", "Amazing low light photos"),
            DemoPageData(R.drawable.demo_battery, "All-day Battery", "Power that lasts"),
            DemoPageData(R.drawable.demo_assistant, "Google Assistant", "Help when you need it"),
            DemoPageData(R.drawable.demo_design, "Beautiful Design", "Lightweight matte finish"),
        )

        adapter = DemoPagerAdapter(this, pages)
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){})
        startAutoSlide()

        // Tap to toggle play/pause
        viewPager.setOnClickListener {
            togglePlay()
        }
        // Long press detection via touch events
        val root = findViewById<View>(R.id.root)
        root.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    longPressDownTime = System.currentTimeMillis()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val held = System.currentTimeMillis() - longPressDownTime
                    if (held >= longPressThreshold) {
                        showExitDialog()
                    } else {
                        togglePlay()
                    }
                    longPressDownTime = 0L
                }
            }
            true
        }

        // immersive full-screen
        enterImmersiveMode()
    }

    private fun enterImmersiveMode() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }

    private fun startAutoSlide() {
        isPlaying = true
        statusText.text = "Playing"
        handler.removeCallbacks(autoSlideRunnable)
        handler.postDelayed(autoSlideRunnable, slideDelayMs)
    }

    private fun stopAutoSlide() {
        isPlaying = false
        statusText.text = "Paused"
        handler.removeCallbacks(autoSlideRunnable)
    }

    private fun togglePlay() {
        if (isPlaying) stopAutoSlide() else startAutoSlide()
    }

    private fun showExitDialog() {
        AlertDialog.Builder(this)
            .setTitle("Exit Demo")
            .setMessage("Are you sure you want to exit the demo?")
            .setPositiveButton("Exit") { _, _ -> finish() }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        enterImmersiveMode()
        if (isPlaying) startAutoSlide()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(autoSlideRunnable)
    }
}
