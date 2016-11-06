package com.leafgraph.tshimizu.sysdev.memstashd.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by takahiro on 2016/11/02.
 */
public class CRLFBufferdReader extends BufferedReader{
    public CRLFBufferdReader(Reader in) {
        super(in);
    }

    public CRLFBufferdReader(Reader in, int sz) {
        super(in, sz);
    }

    @Override
    public String readLine() throws IOException {
        return super.readLine();
    }
}
