package javaIO.网络IO.netty权威指南.netty.msgPackDemo;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxi on 09/06/2018.
 */
public class Demo1 {

    public static void main(String[] args) throws IOException {

        System.out.println(new Integer(1) == Integer.valueOf(1));
        List<String> src = new ArrayList<String>();
        src.add("msgpack");
        src.add("kumofs");
        src.add("viver");
        MessagePack msgpack = new MessagePack();
        byte[] raw = msgpack.write(src);

        List<String> dst1 = msgpack.read(raw, Templates.tList(Templates.TString));

        for(String dst : dst1){
            System.out.println(dst);
        }
    }
}
