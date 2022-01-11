package dev.chintansoni.expensetracker

import android.app.Application

class ExpenseTrackerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoinDI()
    }
}