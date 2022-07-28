package edu.pdx.cs410j.whitlock;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
    @Override
    public String getCaller() {
        return "123-456-8909";
    }

    @Override
    public String getCallee() {
        return "234-567-1234";
    }

    @Override
    public String getBeginTimeString() {
        return "1/1/2022 1:00 PM";
    }

    @Override
    public String getEndTimeString() {
        return "1/1/2022 2:00 PM";
    }
}
