package me.mohancm.wallpaperscheduler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import me.mohancm.wallpaperscheduler.databinding.ActivityMainBinding
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val manager = WorkManager.getInstance(this)

    private lateinit var hours: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()

        // Set Execution around 12 AM
        dueDate.set(Calendar.HOUR_OF_DAY, 24)
        dueDate.set(Calendar.MINUTE, 0)
        dueDate.set(Calendar.SECOND, 0)

        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }

        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis

        val periodicWork =
            OneTimeWorkRequest.Builder(WallpaperWorker::class.java)
                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
                .addTag("WallpaperPeriodic")
                .build()

        manager.getWorkInfoByIdLiveData(periodicWork.id).observeForever {
            if (it != null) {
                Log.d("WallpaperActivity", "Status changed to ${it.state.isFinished}")
            }
        }

        fun startWork() {
            manager.enqueue(periodicWork)
        }

        fun stopWork() {
            manager.cancelAllWorkByTag("WallpaperPeriodic")
        }

        val millis: Long = timeDiff

        val hms = String.format(
            "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(
                    millis
                )
            ),
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(
                    millis
                )
            )
        )

        binding.setWallButton.setOnClickListener {
            startWork()
            Log.d("WallpaperActivity", "Time set for $hms hours")
            Toast.makeText(this, "Time remaining : $hms hours", Toast.LENGTH_LONG).show()
        }

        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                startWork()
                Toast.makeText(this, "Automatic Wallpaper Scheduling started", Toast.LENGTH_SHORT)
                    .show()
            } else {
                stopWork()
                Toast.makeText(this, "Automatic Wallpaper Scheduling Stopped", Toast.LENGTH_SHORT)
                    .show()
            }

        }


    }


}