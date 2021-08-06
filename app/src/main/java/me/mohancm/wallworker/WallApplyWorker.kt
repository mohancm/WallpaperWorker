package me.mohancm.wallworker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WallApplyWorker(context: Context, workerParam: WorkerParameters) :
    Worker(context, workerParam) {

    override fun doWork(): Result {
        val appContext = applicationContext
//        applyWallpaper(co)
//        activity.applyWallpaper(applicationContext)
        return Result.success()

    }
}