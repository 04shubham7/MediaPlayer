package com.example.mediaplayer

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mediaplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var mediaPlayer: MediaPlayer
    var totaltime:Int=0

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mediaPlayer=MediaPlayer.create(this,R.raw.tamatar)
        mediaPlayer.setVolume(1f,1f)
        totaltime=mediaPlayer.duration


        binding.play.setOnClickListener {
            mediaPlayer.start()
        }
        binding.pause.setOnClickListener {
            mediaPlayer.pause()
        }
        binding.stop.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
        }
//        when user changes the time stamp of music, reflect that change
            binding.seekBar.max=totaltime
        binding.seekBar.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) { }

            override fun onStopTrackingTouch(p0: SeekBar?) { }

        })

//        change the seekbar position based on the music
        val handler= Handler()
        handler.postDelayed(object:Runnable{
            override fun run() {
                try {
                    binding.seekBar.progress=mediaPlayer.currentPosition
                    handler.postDelayed(this,1000)
                }catch (exception:java.lang.Exception){
                    binding.seekBar.progress=0
                }

            }

        },0)

    }
}