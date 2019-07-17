package com.example.adela.proiectquizz.pojos;

public class Test {

    private long id;
    private String testName;
    private String testClass;
    private long createdBy;
    private String accessCode;

    public Test() {
    }

    public Test(long id, String testName, String testClass, long createdBy, String accessCode) {
        this.id = id;
        this.testName = testName;
        this.testClass = testClass;
        this.createdBy = createdBy;
        this.accessCode = accessCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestClass() {
        return testClass;
    }

    public void setTestClass(String testClass) {
        this.testClass = testClass;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
