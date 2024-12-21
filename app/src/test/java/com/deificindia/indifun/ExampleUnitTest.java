package com.deificindia.indifun;

import android.app.Application;
import android.content.Context;
import android.util.Pair;

import com.deificindia.indifun.Utility.Constants;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void matching_id(){
        Pair<Boolean, String> s = Constants.userUid("asdftasdft", "asdft");
        int index = "qwertyasdft".lastIndexOf("asdft");
        String str = "qwertyasdft".substring(0, index);
        System.out.println(""+str);
        assertEquals("asdft", s.second);
    }

    @Test
    public void t(){
        List<String> testStrings = Arrays.asList("d01", "d10", "d11", "d12", "d02", "d20", "d21", "d03", "d30");
        List<String> testStrings2 = Arrays.asList("c", "a", "g", "dc", "r", "z", "d", "f", "a");


        List<String> expected =
                Arrays.asList("d1.webp", "d2.webp", "d3.webp", "d10.webp", "d11.webp", "d12.webp", "d20.webp", "d21.webp", "d30.webp");

       /* Collections.sort(testStrings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1 == null) return -1;
                else if (o2 == null) return 1;

                return o1.compareTo(o2);
            }
        });*/
        Collections.sort(testStrings, String.CASE_INSENSITIVE_ORDER);
        //assertEquals(expected, testStrings);
        System.out.println(testStrings);
    }

    @Test
    public void testFile(){
        String [] str = new String[]{"Q4","Q7","Q2","Q1"};
        List<String> list = Arrays.asList(str);

        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        System.out.println(list);
    }

    @Test
    public void testId(){
        String identity = "kLapj2xOYYh2MxrqawCG7XzjeyL2kLapj2xOYYh2MxrqawCG7XzjeyL2";
        String identity2 = "kLapj2xOYYh2MxrqawCG7XzjeyL20YU3juKldBOquZaXVLjtdggx3qb2";
        String identity3 = "0YU3juKldBOquZaXVLjtdggx3qb2kLapj2xOYYh2MxrqawCG7XzjeyL2";

        String expected = "0YU3juKldBOquZaXVLjtdggx3qb2";

        String result = Constants.userUid(identity, "kLapj2xOYYh2MxrqawCG7XzjeyL2").second;

        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    public void rxTest(){
        final FutureTask<String> future = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws InterruptedException {
                //Log.d(RXTAG, "Callable called on thread " + Thread.currentThread().getName());
                //Utils.assertNotUIThread();
                Thread.sleep(TimeUnit.SECONDS.toMillis(10000)); // Simulates network latency
                return "hello";
            }
        });


        Observable.fromFuture(future, Schedulers.io())
                .doOnSubscribe(disposable -> future.run())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        System.out.println("onNext "+ s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("onError "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });

    }

    @Test
    public void rxTest3(){
        //GifParser.instance(Context)

        float b = 2*0.70f;
        System.out.println(""+Math.round(b));
        assertEquals(1.50, 2*0.75);

    }




}