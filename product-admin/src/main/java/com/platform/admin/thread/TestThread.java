package com.platform.admin.thread;

public class TestThread extends Thread{
    private String ids;
    private String names;

    public TestThread() {
    }
    public TestThread(String ids, String names) {
        this.ids = ids;
        this.names = names;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    @Override
    public void run() {
        try {
//            Thread.sleep(1000);
            System.out.println("线程名称：" + Thread.currentThread().getName() + "正在执行"
                    + "id为" + ids + "name为" + names + "的认为");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
