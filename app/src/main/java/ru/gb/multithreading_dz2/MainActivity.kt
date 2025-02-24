package ru.gb.multithreading_dz2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.gb.multithreading_dz2.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isTimer = true
    private var isTimer1 = false
    private var isTimer2 = false
    private var minutes1 = 0
    private var minutes2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.start.setOnClickListener {
            if (!isTimer1) {
                isTimer1 = true
                isTimer = true
            } else {
                isTimer2 = true
                isTimer = true
            }

            if (isTimer1) {
                createTimerValues1()
                    .observeOn(AndroidSchedulers.mainThread())
                    .map {
                        if (it < 10) {
                            if (minutes1 < 10) {
                                "0" + minutes1.toString() + ":0" + it.toString()
                            } else {
                                minutes1.toString() + ":0" + it.toString()
                            }
                        } else {
                            if (minutes1 < 10) {
                                "0" + minutes1.toString() + ":" + it.toString()
                            } else {
                                minutes1.toString() + ":" + it.toString()
                            }
                        }
                    }
                    .subscribe {
                        binding.timer1.text = it.toString()
                    }
            }
            if (isTimer2) {
                createTimerValues2()
                    .observeOn(AndroidSchedulers.mainThread())
                    .map {
                        if (it < 10) {
                            if (minutes2 < 10) {
                                "0" + minutes2.toString() + ":0" + it.toString()
                            } else {
                                minutes2.toString() + ":0" + it.toString()
                            }
                        } else {
                            if (minutes2 < 10) {
                                "0" + minutes2.toString() + ":" + it.toString()
                            } else {
                                minutes2.toString() + ":" + it.toString()
                            }
                        }
                    }
                    .subscribe {
                        binding.timer2.text = it.toString()
                    }
            }
        }

        binding.stop.setOnClickListener {
            isTimer = false
        }
    }

    fun createTimerValues1() = Observable.create<Int> {
        var speed = 0
        while (true) {
            if (isTimer) {
                Thread.sleep(1000)
                speed++
                if (speed == 60) {
                    minutes1++
                    speed = 0
                }
                if (minutes1 == 60) minutes1 = 0
                it.onNext(speed)
            }
        }
    }.subscribeOn(Schedulers.single())

    fun createTimerValues2() = Observable.create<Int> {
        var speed2 = 0
        while (true) {
            if (isTimer) {
                Thread.sleep(1000)
                speed2++
                if (speed2 == 60) {
                    minutes2++
                    speed2 = 0
                }
                if (minutes2 == 60) minutes2 = 0
                it.onNext(speed2)
            }
        }
    }.subscribeOn(Schedulers.newThread())
}