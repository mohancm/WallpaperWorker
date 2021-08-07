package me.mohancm.wallpaperscheduler

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import me.mohancm.wallpaperscheduler.databinding.ActivityMainBinding
import java.io.IOException
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit

class WallpaperWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val TAG = "WallWorker"
    private lateinit var binding: ActivityMainBinding

    override fun doWork(): Result {
        applyWallpaper()

        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()

        // Set Execution around 12:00 AM (When everyone sleeping)
        dueDate.set(Calendar.HOUR_OF_DAY, 24)
        dueDate.set(Calendar.MINUTE, 0)
        dueDate.set(Calendar.SECOND, 0)

        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }

        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
        val dailyWallRequest = OneTimeWorkRequestBuilder<WallpaperWorker>()
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .addTag(TAG)
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueue(dailyWallRequest)
        return Result.success()
        Log.i(TAG, "Worker started for $timeDiff")
    }

    @SuppressLint("ResourceType")
    fun applyWallpaper() {
        // creating the instance of the WallpaperManager
        var wallpaperManager = WallpaperManager.getInstance(applicationContext)
        try {
            val images = Model()
            images.imageList = images.imageList
            var image = images.imageList.random()
            wallpaperManager.setResource(image)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}