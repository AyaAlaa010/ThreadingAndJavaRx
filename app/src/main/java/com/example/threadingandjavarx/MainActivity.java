package com.example.threadingandjavarx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }
private  void filterOperator(){
    ArrayList<Order> orders= new ArrayList<>();
    orders.add(new Order("Apple mac book pro",true));
    orders.add(new Order("Apple mac book air",false));
    orders.add(new Order("Apple mac ",false));
    orders.add(new Order("Android A B",true));
    orders.add(new Order("Android C D ",false));
    orders.add(new Order("Android E F",true));

    Log.i(TAG, "onCreate:  "+Thread.currentThread().getName());
    Observable
            .fromIterable(orders)
            .filter(new Predicate<Order>() {
                @Override
                public boolean test(@NonNull Order order) throws Exception {
                    if(order.getName().contains("Apple")){
                        return true;
                    }
                    else{
                        return false;}
                }
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Order>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onNext(@NonNull Order order) {
                    Log.i(TAG, "onNext: "+order.getName());

                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Log.i(TAG, "onError: "+e.getLocalizedMessage());
                }

                @Override
                public void onComplete() {
                    Log.i(TAG, "onComplete: ");
                }
            });
}
    private void initData(){
    ArrayList<Order> orders= new ArrayList<>();
    orders.add(new Order("Apple mac book pro",true));
    orders.add(new Order("Apple mac book air",false));
    orders.add(new Order("Apple mac ",false));
    orders.add(new Order("Android A B",true));
    orders.add(new Order("Android C D ",false));
    orders.add(new Order("Android E F",true));








}


    private void workerTHread(){
        // worker thread
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: "+Thread.currentThread().getName());
            }

        });
        thread.start();
    }


    private void timerOperator(){
        Observable.timer(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        Log.i(TAG, "onNext: "+aLong);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: "+e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });



    }
    private void timerOperatorUsingSingal(){


        Single.timer(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);

                    }

                    @Override
                    public void onSuccess(@NonNull Long aLong) {
                        Log.i(TAG, "onSuccess: ");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: "+e.getLocalizedMessage());
                    }
                });

    }
    private void intervalOperator(){
        Observable
                .interval(1,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .takeWhile(new AppendOnlyLinkedArrayList.NonThrowingPredicate<Long>() {
                    @Override
                    public boolean test(Long aLong) {
                        return aLong<30;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        Log.i(TAG, "onNext: "+aLong);
                        ((TextView)findViewById(R.id.txt)).setText((aLong+1)+"/30");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: "+e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });
    }


}