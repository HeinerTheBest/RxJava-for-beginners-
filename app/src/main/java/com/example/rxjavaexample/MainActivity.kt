package com.example.rxjavaexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable

class MainActivity : AppCompatActivity() {

    private val TAG = "Heinerthebest"

    //Const of Options to call the different example of the operator "from"
    private val fromArray = 0
    private val fromIterable = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callOperatorCreate(listOf("Apple", "", "Banana"))
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
     * A completable observable has 3 states (onNext, onError, onComplete)
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

    /**
     * Here you have a few example with the operator "From" but there are many
     */
    fun callOperatorFrom(fromOption: Int) {
        when(fromOption) {
            fromArray -> {
                Observable.fromArray("Apple", "Orange", "Banana")
                    .subscribe { Log.d(TAG, it) }
            }

            fromIterable -> {
                Observable.fromIterable(listOf("Apple", "Orange", "Banana"))
                    .subscribe(
                        { value -> Log.d(TAG,"Received: $value") },      // onNext
                        { error -> Log.d(TAG,"Error: $error") },         // onError
                        { Log.d(TAG,"Completed") }                       // onComplete
                    )
            }
        }
    }

    /**
     * Here you have the example with the operator "Create". This way you can
     * create an Observable from the ground up.
     *
     * we can set here how we want to emmit the different status of the Observable (onNext, onError, onComplete)
     *
     * if you want to call this and run the complete process till onComplete you can use this in can in the onCreate
     *
            getObservableFromList(listOf("Apple", "Orange", "Banana"))
                .subscribe { println("Received: $it") }
     *
     *
     * But if you want to see how to stop the subscription because an error you can use this one
     *
            getObservableFromList(listOf("Apple", "", "Banana"))
                .subscribe(
                    { v -> println("Received: $v") },
                    { e -> println("Error: $e") }
                )
     *
     */
    private fun callOperatorCreate(myList: List<String>): Observable<String> {
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
