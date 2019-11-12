package yun.tc.service;

import yun.tc.dao.TestDao;

public class TestService {
    public static void main(String[] args) {
        TestDao dao =new TestDao();
        dao.select();
    }
}
