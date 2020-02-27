package com.example.rxjavaexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import org.reactivestreams.Subscriber

class MainActivity : AppCompatActivity() {

    val TAG = "Heinerthebest"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getObservableFromList(listOf("Apple", "", "Banana"))
            .subscribe(
                { v -> Log.d(TAG,"Received: $v") },
                {error -> Log.d(TAG,"Daamn you find this error $error")}
            )

    }


    /**
     * The just operator converts an Item into an Observable and emits it.
     */
    fun callObservableJust() {
        Observable.just("Hello Reactive World")
            .subscribe { value -> Log.d(TAG,value) }
    }

    /**
     * A complatable observable has 3 states (onNext, onError, onComplete)
     *
     * onNext will happen for each different case
     * onError will happen when it find and error and it will finish the process
     * onComplete will happen when the process is done
     *
     * onComplete and onError are exclusive
     *
     */
    fun callObservableCompletable() {
        Observable.just("Apple", "Orange", "Banana")
            .subscribe(
                { value -> Log.d(TAG,"Received: $value") }, // onNext
                { error -> Log.d(TAG,"Error: $error") },    // onError
                { Log.d(TAG,"Completed!") }                 // onComplete
            )
    }

    private fun getObservableFromList(myList: List<String>): Observable<String> {

       return Observable.create<String> { emitter ->
            myList.forEach { kind ->
                if (kind == "") {
                    emitter.onError(Exception("There's no value to show"))
                }
                emitter.onNext(kind)
            }
            emitter.onComplete()
        }
    }






}
