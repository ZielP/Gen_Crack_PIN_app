package com.example.crackthepin;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Md5HashTest {

    @Test
    public void generate_test(){
        assertEquals("Generate test 1", "098f6bcd4621d373cade4e832627b4f6", Md5Hash.getMd5("test"));
        assertEquals("Generate test 2", "827ccb0eea8a706c4c34a16891f84e7b", Md5Hash.getMd5("12345"));
        assertEquals("Generate test 3", "cdbc895d08b5d92db04174533a8548f7", Md5Hash.getMd5("PIN"));
    }

    @Test
    public void simple_crack_test() {
        assertEquals("Should work with simple PIN", "12345", Md5Hash.crack("827ccb0eea8a706c4c34a16891f84e7b"));
    }
    @Test
    public void harder_crack_test() {
        assertEquals("Should work with harder PIN", "00078", Md5Hash.crack("86aa400b65433b608a9db30070ec60cd"));
    }
}
