package com.keeplearn.shichunjia.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class MyTest {
    public static void main(String[] args) {
        LockTest lockTest=new LockTest();
        for (int i = 1; i <= 10; i++) {
            final Integer tempNum=i;
            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lockTest.write(tempNum,tempNum);
            }).start();
        }

        for (int i = 1; i <= 10; i++) {
            final Integer tempNum=i;
            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lockTest.read(tempNum);
            }).start();
        }
    }
}


class LockTest{

    private Map<Integer,Integer> mapCatch =new HashMap<>();
    public ReadWriteLock lock = new ReentrantReadWriteLock();
    public Lock reentrantLock = new ReentrantLock();
    //  Condition 用户
    public Condition condition = reentrantLock.newCondition();
    public void write(Integer key,Integer value){
        lock.writeLock().lock();
        try {
            System.out.println("----开始"+key);
            mapCatch.put(key,value);
            System.out.println("----结束"+key);
        }finally {
            lock.writeLock().unlock();
        }

    }

    public void read(Integer key){
        lock.readLock().lock();
        try {
            System.out.println("=====开始"+key);
            mapCatch.get(key);
            System.out.println("=====结束"+key);
        }finally {
            lock.readLock().unlock();
        }

    }

}